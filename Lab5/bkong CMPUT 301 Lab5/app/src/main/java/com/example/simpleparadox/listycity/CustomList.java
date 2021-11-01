package com.example.simpleparadox.listycity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<City> {
    private ArrayList<City> cities;
    private Context context;
    private DeleteButtonListener deleteButtonListener;

    /**
     * Interface for button listener
     */
    public interface DeleteButtonListener {
        void onDeleteClickListener(int position);
    }

    /**
     * Set the delete button listener
     */
    public void setDeleteButtonListener(DeleteButtonListener deleteButtonListener) {
        this.deleteButtonListener = deleteButtonListener;
    }

    public CustomList(Context context, ArrayList<City> cities){
        super(context,0, cities);
        this.cities = cities;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        // Create view holder to store buttons in this view
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.content, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.deleteButton = (Button) view.findViewById(R.id.delete_button);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        City city = cities.get(position);

        TextView cityName = view.findViewById(R.id.city_text);
        TextView provinceName = view.findViewById(R.id.province_text);

        cityName.setText(city.getCityName());
        provinceName.setText(city.getProvinceName());

        // Set up delete button
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteButtonListener != null) {
                    deleteButtonListener.onDeleteClickListener(position);
                }
            }
        });

        return view;
    }

    /**
     * Class to delete button for this item
     */
    private static class ViewHolder {
        Button deleteButton;
    }
}
