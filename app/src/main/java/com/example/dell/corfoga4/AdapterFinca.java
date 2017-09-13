package com.example.dell.corfoga4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.Filter;
import android.widget.Filterable;

/**
 * Created by Dell on 3/1/2017.
 */

public class AdapterFinca extends BaseAdapter implements Filterable{

    protected Activity activity;
    private static LayoutInflater inflater = null;
    //protected ArrayList<Finca> items;

    protected ArrayList<Finca> originalItems;
    protected ArrayList<Finca> filteredItems;

    public AdapterFinca (Activity activity, ArrayList<Finca> items) {
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.originalItems = items;
        this.filteredItems = items;
    }

    @Override
    public int getCount() {

        return filteredItems.size();
    }

    public void clear() {
        originalItems.clear();
    }

    public void addAll(ArrayList<Finca> animal) {
        for (int i = 0; i < animal.size(); i++) {
            originalItems.add(animal.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {

        return filteredItems.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            v = inflater.inflate(R.layout.lista, null);
        }

        Finca dir = filteredItems.get(position);

        TextView title = (TextView) v.findViewById(R.id.nombreAnimal);
        title.setText(dir.getNombre());

        TextView description = (TextView) v.findViewById(R.id.registro);
        description.setText(dir.getRegion());

        //v.setBackgroundColor(Color.parseColor("#3f834D"));
        v.setPadding(50,50,50,50);



        return v;

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0){
                    results.values = originalItems;
                    results.count = originalItems.size();
                }
                else{
                    String filterString = constraint.toString().toLowerCase();

                    ArrayList<Finca> filterResultsData = new ArrayList<>();
                    for (Finca data : originalItems){
                        if (data.getNombre().toLowerCase().contains(filterString)){
                            filterResultsData.add(data);
                        }
                    }
                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItems = (ArrayList<Finca>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
