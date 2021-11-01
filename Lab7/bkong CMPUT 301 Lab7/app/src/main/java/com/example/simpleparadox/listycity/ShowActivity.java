package com.example.simpleparadox.listycity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_city_activity);

        // Get the Intent that started this activity and extract the city name
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String cityName = extras.getString("cityName");

            // Capture the layout's TextView and set the string as its text
            TextView textView = findViewById(R.id.city_name);
            textView.setText(cityName);
        }

        // Back button
        final Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                // Return to main activity
                finish();
            }
        });
    }
}