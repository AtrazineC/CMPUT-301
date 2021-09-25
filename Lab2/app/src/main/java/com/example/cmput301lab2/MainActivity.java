package com.example.cmput301lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // Variables
    ListView cityView;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    // Store state of currently selected city
    int currentPosition = 0;
    Boolean citySelected = false;

    // OnClick handler
    private AdapterView.OnItemClickListener cityClickedHandler = (parent, v, position, id) -> {
        currentPosition = position;
        citySelected = true;
        findViewById(R.id.deleteCity).setEnabled(citySelected);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.deleteCity).setEnabled(citySelected);
        cityView = findViewById(R.id.list_view);

        // Create a string array
        String[] cities = {"Edmonton", "Calgary", "Berlin", "Tokyo", "Beijing", "Toronto", "Montreal"};
        dataList = new ArrayList<String>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<String>(this, R.layout.content, dataList);
        cityView.setAdapter(cityAdapter);
        cityView.setOnItemClickListener(cityClickedHandler);
    }

    /*
        Add a city to the data list
     */
    public void addCity(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextCityName);
        String message = editText.getText().toString();

        // Make sure message is not empty
        if (!message.matches("")) {
            editText.getText().clear();
            dataList.add(message);
            cityAdapter.notifyDataSetChanged();
        }
    }

    /*
        Delete the currently selected city from the data list
     */
    public void deleteCity(View view) {
        citySelected = false;
        findViewById(R.id.deleteCity).setEnabled(citySelected);
        dataList.remove(currentPosition);
        cityAdapter.notifyDataSetChanged();
    }
}