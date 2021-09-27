package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Main activity.
 *
 * Stores list of medicines currently in the list.
 * Uses MedicineArrayAdapter to display list of medicines on medicineListView.
 * Listens to add/edit action from MedicineFragment.
 * Listens to edit/delete action from MedicineArrayAdapter.
 */
public class MainActivity extends AppCompatActivity implements
        MedicineFragment.OnFragmentInteractionListener,
        MedicineArrayAdapter.MedicineButtonListener {

    ListView medicineListView;
    MedicineArrayAdapter medicineAdapter;
    ArrayList<Medicine> medicineList;
    TextView totalDoseCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the medicine ListView
        medicineListView = findViewById(R.id.medicine_list);

        // Find the total dosage text
        totalDoseCounter = findViewById(R.id.total_doses_text);

        // List set up
        medicineList = new ArrayList<>();
        medicineAdapter = new MedicineArrayAdapter(this, medicineList);
        medicineAdapter.setMedicineButtonListener(this);
        medicineListView.setAdapter(medicineAdapter);

        // Set listener for add button
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MedicineFragment addMedicineFragment = new MedicineFragment();
                addMedicineFragment.show(getSupportFragmentManager(), "ADD_MEDICINE");
            }
        });

        // Set initial total dose count
        updateDoseCount();
    }

    /**
     * Called when edit button for medicine is clicked at the given position
     * @param position The position in the list of the medicine to edit
     */
    public void onEditMedicineClickListener(int position) {
        Medicine currentMedicine = medicineList.get(position);
        MedicineFragment addMedicineFragment = new MedicineFragment(currentMedicine);
        addMedicineFragment.show(getSupportFragmentManager(), "EDIT_MEDICINE");
    }

    /**
     * Called when delete button for medicine is clicked at the given position
     * @param position The position in the list of the medicine to delete
     */
    public void onDeleteMedicineClickListener(int position) {
        medicineList.remove(position);
        medicineAdapter.notifyDataSetChanged();
        updateDoseCount();
    }

    /**
     * Add a new medicine when add action is confirmed
     * @param newMedicine The new medicine to add
     */
    @Override
    public void onOkPressedAdd(Medicine newMedicine) {
        medicineAdapter.add(newMedicine);
        updateDoseCount();
    }

    /**
     * Edit the existing medicine when edit action is confirmed
     * @param medicine The medicine to edit
     * @param name The new name
     * @param startDate The new start date
     * @param dailyFrequency The new daily frequency
     * @param dose The new dose
     * @param doseUnit The new dose unit
     */
    @Override
    public void onOkPressedEdit(Medicine medicine,
                         String name,
                         Date startDate,
                         int dailyFrequency,
                         int dose,
                         Medicine.DoseUnit doseUnit) {
        // Set new properties
        medicine.setName(name);
        medicine.setStartDate(startDate);
        medicine.setDailyFrequency(dailyFrequency);
        medicine.setDose(dose);
        medicine.setDoseUnit(doseUnit);

        // Notify medicine adapter of data change
        medicineAdapter.notifyDataSetChanged();
        updateDoseCount();
    }

    /**
     * Iterate through list of medicines and count total doses per day.
     * Then update TextView
     */
    private void updateDoseCount() {
        int count = 0;

        for (Medicine medicine : medicineList) {
            count += medicine.getDailyFrequency();
        }

        totalDoseCounter.setText(String.format("Total daily doses: %d", count));
    }
}