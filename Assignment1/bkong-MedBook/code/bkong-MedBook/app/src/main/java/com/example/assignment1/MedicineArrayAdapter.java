package com.example.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Custom array adapter for medicine
 */
public class MedicineArrayAdapter extends ArrayAdapter<Medicine> {
    private MedicineButtonListener medicineButtonListener;

    /**
     * Interface for medicine button listener
     */
    public interface MedicineButtonListener {
        void onEditMedicineClickListener(int position);
        void onDeleteMedicineClickListener(int position);
    }

    private final ArrayList<Medicine> medicineList;
    private final Context context;

    /**
     * Constructor
     */
    public MedicineArrayAdapter(Context context, ArrayList<Medicine> medicineList) {
        super(context, 0, medicineList);
        this.context = context;
        this.medicineList = medicineList;
    }

    /**
     * Set the medicine button listener
     */
    public void setMedicineButtonListener(MedicineButtonListener medicineButtonListener) {
        this.medicineButtonListener = medicineButtonListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Create view holder to store buttons in this view
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.medicine_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.editButton = (ImageButton) convertView.findViewById(R.id.edit_button);
            viewHolder.deleteButton = (ImageButton) convertView.findViewById(R.id.delete_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get medicine at this position
        Medicine medicine = medicineList.get(position);

        // Medicine name
        TextView medicineNameTextView = convertView.findViewById(R.id.medicine_name);
        medicineNameTextView.setText(medicine.getName());

        // Medicine dosage info
        TextView medicineDosageTextView = convertView.findViewById(R.id.medicine_dosage);
        medicineDosageTextView.setText(getDoseText(medicine));

        // Date text
        TextView medicineStartDateTextView = convertView.findViewById(R.id.medicine_start_date);
        medicineStartDateTextView.setText(getDateText(medicine));

        // Set up edit button
        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (medicineButtonListener != null) {
                    medicineButtonListener.onEditMedicineClickListener(position);
                }
            }
        });

        // Set up delete button
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (medicineButtonListener != null) {
                    medicineButtonListener.onDeleteMedicineClickListener(position);
                }
            }
        });

        return convertView;
    }

    /**
     * Build text for dosage info from medicine info
     * @param medicine The medicine
     * @return Dose info in string format
     */
    private String getDoseText(Medicine medicine) {
        int dose = medicine.getDose();
        int doseFrequency = medicine.getDailyFrequency();
        Medicine.DoseUnit doseUnit = medicine.getDoseUnit();

        String doseUnitText;
        switch (doseUnit) {
            case MCG:
                doseUnitText = "mcg";
                break;
            case DROP:
                if (dose > 1) {
                    doseUnitText = " drops";
                } else {
                    doseUnitText = " drop";
                }
                break;
            default:
                doseUnitText = "mg";
                break;
        }

        if (doseFrequency > 1) {
            return String.format("%d%s %d times a day", dose, doseUnitText, doseFrequency);
        } else {
            return String.format("%d%s %d time a day", dose, doseUnitText, doseFrequency);
        }
    }

    /**
     * Build text for start date from medicine info
     * @param medicine The medicine
     * @return Start date info in string format
     */
    private String getDateText(Medicine medicine) {
        Date date = medicine.getStartDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return String.format("Date started: %s", dateFormat.format(date));
    }

    /**
     * Class to store edit and delete button for this item
     */
    private static class ViewHolder {
        ImageButton editButton;
        ImageButton deleteButton;
    }
}
