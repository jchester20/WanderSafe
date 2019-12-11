package com.example.wandersafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddAReviewBerkActivity extends AppCompatActivity {

    private TextView close_btn;
    private Button post_btn;
    private ArrayList<LinearLayout> starGroups;
    private int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review_berkeley);

        starGroups = new ArrayList<>();
        starGroups.add((LinearLayout) findViewById(R.id.stargroup1));
        starGroups.add((LinearLayout) findViewById(R.id.stargroup2));
        starGroups.add((LinearLayout) findViewById(R.id.stargroup3));
        starGroups.add((LinearLayout) findViewById(R.id.stargroup4));
        rating = 0;

        for (LinearLayout l : starGroups) {
            final LinearLayout layout = l;
            for (int i = 0; i < 5; i++) {
                ImageView star = (ImageView) l.getChildAt(i);
                final int index = i + 1;
                star.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (layout.equals(findViewById(R.id.stargroup1))) {
                            rating = index;
                        }
                        for (int j = 0; j < index; j++) {
                            ImageView star = (ImageView) layout.getChildAt(j);
                            star.setImageDrawable(getDrawable(R.drawable.yellow_star));
                        }
                        for (int j = index; j < 5; j++) {
                            ImageView star = (ImageView) layout.getChildAt(j);
                            star.setImageDrawable(getDrawable(R.drawable.white_star));
                        }
                    }
                });
            }
        }

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
                EditText reviewText = (EditText) findViewById(R.id.editText3);
                EditText nameText = (EditText) findViewById(R.id.editText);
                String reviewString = reviewText.getText().toString();
                String nameString = nameText.getText().toString();
                if (!reviewString.equals("") && !nameString.equals("") && rating != 0) {
                    String[] review = { nameString, reviewString, Integer.toString(rating) };
                    Intent newIntent = new Intent(AddAReviewBerkActivity.this, ReviewActivity.class);
                    newIntent.putExtra("Review", review);
                    AddAReviewBerkActivity.this.startActivity(newIntent);
                }
            }
        });
    }
}
