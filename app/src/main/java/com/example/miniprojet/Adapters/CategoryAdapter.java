package com.example.miniprojet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.miniprojet.R;
import com.example.miniprojet.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private ArrayList<Category> categories;


    public CategoryAdapter(Context context, int resource, ArrayList<Category> categories) {
        super(context, resource, categories);
        this.categories = categories;
    }

    @NonNull
    @Override
    public View getView(int position , View convertView , ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.liste_cellule, parent, false);


     /*   TextView id = (TextView) convertView.findViewById(R.id.categoryId);
        id.setText(categories.get(position).getId().toString());  */

        TextView name = (TextView) convertView.findViewById(R.id.categoryName);
        name.setText(categories.get(position).getName().toString());

        return convertView;

    }
}
