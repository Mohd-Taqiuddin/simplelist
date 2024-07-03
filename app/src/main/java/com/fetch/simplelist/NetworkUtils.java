package com.fetch.simplelist;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";
    private static final String DATA_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

    public interface FetchItemsCallback {
        void onSuccess(List<Item> items);
        void onError(String errorMessage);
    }

    public static void fetchItems(@NonNull Context context, @NonNull FetchItemsCallback callback) {
        new AsyncTask<Void, Void, List<Item>>() {
            @Override
            protected List<Item> doInBackground(Void... voids) {
                try {
                    URL url = new URL(DATA_URL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode != HttpURLConnection.HTTP_OK) {
                        return null;
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    List<Item> items = new Gson().fromJson(reader, new TypeToken<List<Item>>() {}.getType());
                    reader.close();

                    // Filter out items with null or empty name
                    items = items.stream()
                            .filter(item -> item.getName() != null && !item.getName().trim().isEmpty())
                            .collect(Collectors.toList());

                    // Sort items by listId then by name
                    items.sort(Comparator.comparing(Item::getListId).thenComparing(Item::getName));

                    return items;
                } catch (Exception e) {
                    Log.e(TAG, "Error fetching items", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                if (items != null) {
                    callback.onSuccess(items);
                } else {
                    callback.onError("Failed to fetch items");
                }
            }
        }.execute();
    }
}
