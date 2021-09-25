package com.example.simpleparadox.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    private String title = "Add city";
    private City city;
    private Boolean isEdit = false;
    private EditText cityName;
    private EditText provinceName;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressedAdd(City newCity);
        void onOkPressedEdit(City city, String newCityName, String newProvinceName);
    }

    /**
     * Constructor with city
     * @param city The city
     */
    public AddCityFragment(City city) {
        this.city = city;
        this.isEdit = true;
    }

    /**
     * Default constructor
     */
    public AddCityFragment() {
        super();
    };

    /**
     * Set title on fragment
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onAttach(Context context) {
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_city_fragment_layout, null);
        cityName = view.findViewById(R.id.city_name_editText);
        provinceName = view.findViewById(R.id.province_editText);

        // If editing, add the previous city name
        if (isEdit) {
            cityName.setText(city.getCityName());
            cityName.setSelection(cityName.length()); // Set cursor at end of line
            provinceName.setText(city.getProvinceName());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());  
        return builder
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String cityInput = cityName.getText().toString();
                        String provinceInput = provinceName.getText().toString();

                        if (isEdit) {
                            listener.onOkPressedEdit(city, cityInput, provinceInput);
                        } else {
                            listener.onOkPressedAdd(new City(cityInput, provinceInput));
                        }
                    }
                }).create();
    }
}
