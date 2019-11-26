package com.example.wandersafe;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {
    private PopupWindow popup;

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

//        TextView textView = (TextView) findViewById(R.id.textViewSF);
//
//        textView.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View v)
//            {
//                int[] location = new int[2];
//                currentRowId = position;
//                currentRow = v;
//                // Get the x, y location and store it in the location[] array
//                // location[0] = x, location[1] = y.
//                v.getLocationOnScreen(location);
//
//                //Initialize the Point with x, and y positions
//                point = new Point();
//                point.x = location[0];
//                point.y = location[1];
//                showStatusPopup(TasksListActivity.this, point);
//            }
//        });

    }

    public void showPopup(View v) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView mapTextView = (TextView) findViewById(R.id.popupMapView);
                mapTextView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // go back to map view
                        popupWindow.dismiss();
                        Intent myIntent = new Intent(SearchActivity.this, MapsActivity.class);
                        SearchActivity.this.startActivity(myIntent);

                    }
                });

                TextView overviewTextView = (TextView) findViewById(R.id.popupOverview);
                // TODO: add connection to Yalini's overview page
                overviewTextView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // go back to map view
                        popupWindow.dismiss();
                        // TODO: add intent to go to overview activity

                    }
                });
                return true;
            }
        });
    }
}
