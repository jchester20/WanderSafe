package com.example.wandersafe;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CitySummaryActivity extends AppCompatActivity {

    private Button elmwood_btn;
    private TextView close_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_summary);

        elmwood_btn = findViewById(R.id.button6);

        elmwood_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(CitySummaryActivity.this, NeighborhoodReviewActivity.class);
                CitySummaryActivity.this.startActivity(newIntent);
            }
        });
        close_btn = findViewById(R.id.close3);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(CitySummaryActivity.this, MapsActivity.class);
                CitySummaryActivity.this.startActivity(newIntent);
            }
        });


    }

}
