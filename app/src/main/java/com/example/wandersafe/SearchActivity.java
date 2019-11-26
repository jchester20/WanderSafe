package com.example.wandersafe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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
        ImageButton minimizeBtn = (ImageButton) findViewById(R.id.arrowDown);
        minimizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SearchActivity.this, MapsActivity.class);
                // myIntent.putExtra("key", value); //Optional parameters
                SearchActivity.this.startActivity(myIntent);
            }
        });
    }
}
