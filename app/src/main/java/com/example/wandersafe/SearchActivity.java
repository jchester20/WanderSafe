package com.example.wandersafe;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView searchView = (SearchView) findViewById(R.id.searchview);
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);

        searchView.setQueryHint("City or neighborhood");
    }
}
