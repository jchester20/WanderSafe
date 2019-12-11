package com.example.wandersafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NeighborhoodReviewActivity extends AppCompatActivity {
    private Button add_Review;
    private TextView close_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        LinearLayout linLayout = findViewById(R.id.ReviewContainer);
        LayoutInflater inflater = getLayoutInflater();
        //get sent review
        Intent intent = getIntent();
        String[] incoming = intent.getStringArrayExtra("Review");
        if (incoming != null) {
            View reviewView = inflater.inflate(R.layout.review_view, null);
            TextView date = reviewView.findViewById(R.id.DateText);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date2 = new Date();
            date.setText(dateFormat.format(date2));
            TextView name = reviewView.findViewById(R.id.NameText);
            name.setText(incoming[0]);
            TextView review = reviewView.findViewById(R.id.BodyText);
            review.setText(incoming[1]);
            ImageView[] starArray = {
                    reviewView.findViewById(R.id.S1),
                    reviewView.findViewById(R.id.S2),
                    reviewView.findViewById(R.id.S3),
                    reviewView.findViewById(R.id.S4),
                    reviewView.findViewById(R.id.S5)
            };
            int stars = Integer.parseInt(incoming[2]);
            for (int i = 0; i < stars; i++) {
                starArray[i].setVisibility(View.VISIBLE);
            }
            linLayout.addView(reviewView);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.user_reviews_csv)));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                View reviewView = inflater.inflate(R.layout.review_view, null);
                String[] row = csvLine.split(",");
                TextView date = reviewView.findViewById(R.id.DateText);
                date.setText(row[0]);
                TextView name = reviewView.findViewById(R.id.NameText);
                name.setText(row[1]);
                TextView review = reviewView.findViewById(R.id.BodyText);
                review.setText(row[2]);
                ImageView[] starArray = {
                        reviewView.findViewById(R.id.S1),
                        reviewView.findViewById(R.id.S2),
                        reviewView.findViewById(R.id.S3),
                        reviewView.findViewById(R.id.S4),
                        reviewView.findViewById(R.id.S5)
                };
                int stars = Integer.parseInt(row[3]);
                for (int i = 0; i < stars; i++) {
                    starArray[i].setVisibility(View.VISIBLE);
                }
                linLayout.addView(reviewView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add_Review = findViewById(R.id.add_button);
        add_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(NeighborhoodReviewActivity.this, AddAReviewActivity.class);
                NeighborhoodReviewActivity.this.startActivity(newIntent);
            }
        });

        close_btn = findViewById(R.id.close);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(NeighborhoodReviewActivity.this, MapsActivity.class);
                NeighborhoodReviewActivity.this.startActivity(newIntent);
            }
        });
    }
}
