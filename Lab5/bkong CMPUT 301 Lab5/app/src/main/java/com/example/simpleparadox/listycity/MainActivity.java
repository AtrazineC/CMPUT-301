package com.example.simpleparadox.listycity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CustomList.DeleteButtonListener {
    // Declare the variables so that you will be able to reference it later.
    ListView cityList;
    CustomList cityAdapter;
    ArrayList<City> cityDataList;

    final String TAG = "Sample";
    Button addCityButton;
    EditText addCityEditText;
    EditText addProvinceEditText;
    FirebaseFirestore db;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views
        addCityButton = findViewById(R.id.add_city_button);
        addCityEditText = findViewById(R.id.add_city_field);
        addProvinceEditText = findViewById(R.id.add_province_edit_text);

        // Set up database stuff
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Cities");

        // Set up adapter stuff
        cityList = findViewById(R.id.city_list);
        cityDataList = new ArrayList<>();
        cityAdapter = new CustomList(this, cityDataList);
        cityAdapter.setDeleteButtonListener(this);
        cityList.setAdapter(cityAdapter);

        // Add button click listener
        addCityButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get city name and province name
                final String cityName = addCityEditText.getText().toString();
                final String provinceName = addProvinceEditText.getText().toString();

                // Check something was actually input
                if (cityName.length() > 0 && provinceName.length() > 0) {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("Province Name", provinceName);

                    // Send request to add city to database
                    collectionReference
                            .document(cityName)
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // These are a method which gets executed when the task is succeeded
                                    Log.d(TAG, "Data has been added successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // These are a method which gets executed if there’s any problem
                                    Log.d(TAG, "Data could not be added!" + e.toString());
                                }
                            });

                    // Reset text boxes
                    addCityEditText.setText("");
                    addProvinceEditText.setText("");
                }
            }
        });

        // Listener for when database changes
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                // Clear the old list
                cityDataList.clear();

                // Adding the cities and provinces from FireStore
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {

                    Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                    String city = doc.getId();
                    String province = (String) doc.getData().get("Province Name");
                    cityDataList.add(new City(city, province));
                }

                // Notifying the adapter to render any new data fetched from the cloud
                cityAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Called when delete button is clicked at the given position
     * @param position The position in the list to delete
     */
    public void onDeleteClickListener(int position) {
        City city = cityDataList.get(position);
        String cityName = city.getCityName();

        // Send request to delete city from database
        collectionReference
                .document(cityName)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d(TAG, "Data has been deleted successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d(TAG, "Data could not be deleted!" + e.toString());
                    }
                });
    }
}
