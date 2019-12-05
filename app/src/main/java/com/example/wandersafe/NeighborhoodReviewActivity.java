package com.example.wandersafe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NeighborhoodReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        LinearLayout linLayout = findViewById(R.id.ReviewContainer);
        LayoutInflater inflater = getLayoutInflater();
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
    }
}
