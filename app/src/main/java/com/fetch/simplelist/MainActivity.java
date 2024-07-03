package com.fetch.simplelist;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchAndDisplayItems();
    }

    private void fetchAndDisplayItems() {
        NetworkUtils.fetchItems(this, new NetworkUtils.FetchItemsCallback() {
            @Override
            public void onSuccess(List<Item> items) {
                ItemAdapter adapter = new ItemAdapter(items);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Error fetching items: " + errorMessage);
                Toast.makeText(MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
