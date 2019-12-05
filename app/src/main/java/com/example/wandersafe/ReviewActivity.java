package com.example.wandersafe;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ReviewActivity extends AppCompatActivity {

    private Button add_Review;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_berkeley);
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        add_Review = findViewById(R.id.add_button);
        add_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(ReviewActivity.this, AddAReviewBerkActivity.class);
                ReviewActivity.this.startActivity(newIntent);
            }
        });
    }

}
