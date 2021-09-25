package com.example.simpleparadox.listycity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.OnFragmentInteractionListener {
    // Declare the variables so that you will be able to reference it later.
    ListView cityList;
    ArrayAdapter<City> cityAdapter;
    ArrayList<City> cityDataList;

    /**
     * OnClick handler for each item in the city list
     */
    private AdapterView.OnItemClickListener cityClickedHandler = (parent, v, position, id) -> {
        City city = cityDataList.get(position);
        AddCityFragment addCityFragment = new AddCityFragment(city);
        addCityFragment.setTitle("Edit city");
        addCityFragment.show(getSupportFragmentManager(), "ADD_CITY");
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        String[] cities = {"Edmonton", "Vancouver", "Toronto", "Hamilton", "Denver", "Los Angeles"};
        String[] provinces = {"AB", "BC", "ON", "ON", "CO", "CA"};

        cityDataList = new ArrayList<City>();

        for (int i = 0; i < cities.length; i++) {
            cityDataList.add(new City(cities[i], provinces[i]));
        }

        cityAdapter = new CustomList(this, cityDataList);
        cityList.setAdapter(cityAdapter);
        cityList.setOnItemClickListener(cityClickedHandler);

        final FloatingActionButton addCityButton = findViewById(R.id.add_city_button);
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCityFragment addCityFragment = new AddCityFragment();
                addCityFragment.setTitle("Add city");
                addCityFragment.show(getSupportFragmentManager(), "ADD_CITY");
            }
        });
    }

    /**
     * What happens when the confirm add city button is clicked
     * @param newCity The new city
     */
    @Override
    public void onOkPressedAdd(City newCity) {
        cityAdapter.add(newCity);
    }

    /**
     * What happens when the comfirm edit city button is clicked
     * @param city The city to edit
     * @param newCityName The new city name
     * @param newProvinceName The new province name
     */
    @Override
    public void onOkPressedEdit(City city, String newCityName, String newProvinceName) {
        city.setCityName(newCityName);
        city.setProvinceName(newProvinceName);
        cityAdapter.notifyDataSetChanged();
    }
}
