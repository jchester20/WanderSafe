package com.example.wandersafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddAReviewBerkActivity extends AppCompatActivity {

    private TextView close_btn;
    private Button post_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review_berkeley);
        
        close_btn = findViewById(R.id.close4);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(AddAReviewBerkActivity.this, MapsActivity.class);
                AddAReviewBerkActivity.this.startActivity(newIntent);
            }
        });

        post_btn = findViewById(R.id.post_button1);
        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(AddAReviewBerkActivity.this, ReviewActivity.class);
                AddAReviewBerkActivity.this.startActivity(newIntent);
            }
        });
    }
}
