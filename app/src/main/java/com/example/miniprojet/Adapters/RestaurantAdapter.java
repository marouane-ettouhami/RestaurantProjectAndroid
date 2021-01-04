package com.example.miniprojet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.miniprojet.R;
import com.example.miniprojet.models.Restaurant;

import java.util.ArrayList;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
   private ArrayList<Restaurant> restaurants;

    public RestaurantAdapter(Context context, int resource, ArrayList<Restaurant> restaurants) {
        super(context, resource, restaurants);
        this.restaurants = restaurants;
    }

    public void update(ArrayList<Restaurant> results) {
        restaurants = new ArrayList<>();
        restaurants.addAll(results);
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView textViewName;
        TextView textViewState;

    }



    @Override
    public int getCount() {
        return restaurants.size();
    }

    @NonNull
    @Override
    public View getView(int position , View convertView , ViewGroup parent) {

       LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.liste_restau, parent, false);


        TextView nameRestau = (TextView) convertView.findViewById(R.id.nameRestau);
        nameRestau.setText(restaurants.get(position).getName().toString());

        TextView state = (TextView) convertView.findViewById(R.id.stateRestau);
        state.setText(restaurants.get(position).getEtat().toString());

        TextView distance = (TextView) convertView.findViewById(R.id.distance);
        distance.setText(Double.toString(restaurants.get(position).getDistance()) + "km");





        return convertView;
        /*
      View row;
      row = convertView;
      ViewHolder viewHolder;
      if(row == null) {
          row = LayoutInflater.from(getContext()).inflate(R.layout.liste_restau, parent , false);
          viewHolder = new ViewHolder();
          viewHolder.textViewName = row.findViewById(R.id.nameRestau);
          viewHolder.textViewState = row.findViewById(R.id.stateRestau);
          row.setTag(viewHolder);
      }

      else {
          viewHolder = (ViewHolder) row.getTag();
      }

      viewHolder.textViewName.setText(restaurants.get(position).getName());
      viewHolder.textViewState.setText(restaurants.get(position).getEtat());

      return row;
      */
    }
}
