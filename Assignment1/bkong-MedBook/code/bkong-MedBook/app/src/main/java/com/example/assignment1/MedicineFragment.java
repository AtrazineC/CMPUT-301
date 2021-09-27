package com.example.assignment1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MedicineFragment extends DialogFragment {
    /**
     * Interface for fragment interaction listener
     */
    public interface OnFragmentInteractionListener {
        void onOkPressedAdd(Medicine newMedicine);
        void onOkPressedEdit(Medicine medicine,
                             String name,
                             Date startDate,
                             int dailyFrequency,
                             int dose,
                             Medicine.DoseUnit doseUnit);
    }

    /**
     * EditText, RadioGroup, CalendarView storage
     */
    private EditText medicineNameEditText;
    private EditText doseFrequencyEditText;
    private EditText doseAmountEditText;
    private RadioGroup unitRadioGroup;
    private RadioButton unitMgRadioButton;
    private RadioButton unitMcgRadioButton;
    private RadioButton unitDropRadioButton;
    private DatePicker startDatePicker;

    /**
     * Storage of medicine if editing instead of adding
     */
    private Medicine medicine;
    private Boolean isEdit = false;

    /**
     * Listener for interaction
     */
    private OnFragmentInteractionListener listener;

    /**
     * Constructor for editing (pass in medicine to be edited)
     */
    public MedicineFragment(Medicine medicine) {
        super();
        this.medicine = medicine;
        this.isEdit = true;
    }

    /**
     * Default constructor
     */
    public MedicineFragment() {
        super();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_medicine_fragment_layout, null);

        // Get all the TextViews, etc. from fragment
        medicineNameEditText = view.findViewById(R.id.edit_medicine_name);
        doseFrequencyEditText = view.findViewById(R.id.edit_dose_frequency);
        doseAmountEditText = view.findViewById(R.id.edit_dose_amount);
        unitRadioGroup = view.findViewById(R.id.edit_unit_radio_group);
        unitMgRadioButton = view.findViewById(R.id.mg_radio_button);
        unitMcgRadioButton = view.findViewById(R.id.mcg_radio_button);
        unitDropRadioButton = view.findViewById(R.id.drops_radio_button);
        startDatePicker = view.findViewById(R.id.start_date_picker);

        // Determine title and set previous fields if editing existing medicine
        String title;

        if (isEdit) {
            title = getString(R.string.edit_header_text);

            // Set existing fields
            medicineNameEditText.setText(medicine.getName());
            doseFrequencyEditText.setText(Integer.toString(medicine.getDailyFrequency()));
            doseAmountEditText.setText(Integer.toString(medicine.getDose()));

            // Radio group
            switch (medicine.getDoseUnit()) {
                case DROP:
                    unitRadioGroup.check(R.id.drops_radio_button);
                case MCG:
                    unitRadioGroup.check(R.id.mcg_radio_button);
                default:
                    unitRadioGroup.check(R.id.mg_radio_button);
            }

            // Start date
            Date startDate = medicine.getStartDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            startDatePicker.updateDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE));
        } else {
            title = getString(R.string.add_header_text);
        }

        // Build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog alertDialog = builder
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", null)
                .create();
        alertDialog.show();

        // Set custom listener for alert dialog
        Button okButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.setOnClickListener(new CustomListener(alertDialog));

        return alertDialog;
    }

    /**
     * Custom listener class to prevent dialog from closing if input is invalid
     */
    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;

        /**
         * Constructor
         */
        public CustomListener(Dialog dialog) {
            this.dialog = dialog;
        }

        /**
         * Override onClick so that dialog isn't dismissed when invalid input is entered
         */
        @Override
        public void onClick(View v) {
            // Check if input is valid
            boolean isValid = true;

            // Get the input from the views
            String medicineNameInput = medicineNameEditText.getText().toString();
            String doseFrequencyInput = doseFrequencyEditText.getText().toString();
            String doseAmountInput = doseAmountEditText.getText().toString();

            // Get unit input
            Medicine.DoseUnit doseUnit;
            int unitIdInput = unitRadioGroup.getCheckedRadioButtonId();

            if (unitIdInput == R.id.mg_radio_button) {
                doseUnit = Medicine.DoseUnit.MG;
            } else if (unitIdInput == R.id.mcg_radio_button) {
                doseUnit = Medicine.DoseUnit.MCG;
            } else {
                doseUnit = Medicine.DoseUnit.DROP;
            }

            // Get date input
            Calendar calendar = new GregorianCalendar(
                    startDatePicker.getYear(),
                    startDatePicker.getMonth(),
                    startDatePicker.getDayOfMonth());
            Date startDateInput = new Date(calendar.getTimeInMillis());

            // Verify name input is valid
            if (medicineNameInput.length() < 1) {
                isValid = false;
                medicineNameEditText.setError("Enter a name");
            } else if (medicineNameInput.length() > Medicine.MAX_LENGTH_NAME) {
                medicineNameEditText.setError("Name must be under 40 characters");
                isValid = false;
            }

            // Convert strings to ints and verify they're positive
            int doseFrequency = 0;
            try {
                if (!doseFrequencyInput.isEmpty()) {
                    doseFrequency = Integer.parseInt(doseFrequencyInput);

                    if (doseFrequency < 1) {
                        isValid = false;
                        doseFrequencyEditText.setError("Enter a positive number");
                    }
                } else {
                    isValid = false;
                    doseFrequencyEditText.setError("Enter a positive number");
                }
            } catch (NumberFormatException ex) {
                isValid = false;
                doseFrequencyEditText.setError("Enter a positive number");
                ex.printStackTrace();
            }

            int doseAmount = 0;
            try {
                if (!doseAmountInput.isEmpty()) {
                    doseAmount = Integer.parseInt(doseAmountInput);

                    if (doseAmount < 1) {
                        isValid = false;
                        doseAmountEditText.setError("Enter a positive number");
                    }
                } else {
                    isValid = false;
                    doseAmountEditText.setError("Enter a positive number");
                }
            } catch (NumberFormatException ex) {
                isValid = false;
                doseAmountEditText.setError("Enter a positive number");
                ex.printStackTrace();
            }

            if (isValid) {
                // Trigger listener
                if (isEdit) {
                    listener.onOkPressedEdit(
                            medicine,
                            medicineNameInput,
                            startDateInput,
                            doseFrequency,
                            doseAmount,
                            doseUnit);
                } else {
                    listener.onOkPressedAdd(new Medicine(
                            medicineNameInput,
                            startDateInput,
                            doseFrequency,
                            doseAmount,
                            doseUnit
                    ));
                }

                // Close dialog
                dialog.dismiss();
            }
        }
    }
}
