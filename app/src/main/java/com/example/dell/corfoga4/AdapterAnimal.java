package com.example.dell.corfoga4;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.dell.corfoga4.utilidades.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.webkit.WebSettings.PluginState.ON;

/**
 * Created by Dell on 4/1/2017.
 */

public class AdapterAnimal extends BaseAdapter implements Filterable{
    protected Activity activity;
    private static LayoutInflater inflater = null;
    //protected ArrayList<Animal> items;

    protected ArrayList<Animal> animalesOriginales;
    protected ArrayList<Animal> animalesFiltrados;

    public AdapterAnimal (Activity activity, ArrayList<Animal> items) {
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.items = items;
        this.animalesOriginales = items;
        this.animalesFiltrados = items;
    }

    @Override
    public int getCount() {

        return animalesFiltrados.size();
    }

    @Override
    public Object getItem(int arg0) {

        return animalesFiltrados.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            v = inflater.inflate(R.layout.lista_animal, null);
        }

        Animal dir = animalesFiltrados.get(position);

        TextView title = (TextView) v.findViewById(R.id.nombreAnimal);
        title.setText(dir.getCodigo());

        TextView description = (TextView) v.findViewById(R.id.registro);
        description.setText(dir.getRegistro());

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = df.format(c.getTime());

        final SQLiteDatabase db = new DBHelper(activity).getWritableDatabase();

        int cantidad = (int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) " +
                "FROM detalles_inspecciones JOIN inspecciones " +
                "ON detalles_inspecciones.id_inspeccion = inspecciones.id " +
                "WHERE detalles_inspecciones.codigo_animal = ? AND inspecciones.fecha = ?", new String[]{dir.getCodigo(), fechaActual});

        //System.out.println("Fecha inspección: " + fechaInspeccion + ", Fecha actual: " + fechaActual);
        System.out.println("El animal " + dir.getCodigo() + " ya ha sido inspeccionado " + cantidad + " vez(ces)");
        if (cantidad >= 1){
            v.setBackgroundColor(Color.parseColor("#3f834D"));
        }
        else{
            // Use your default color here
            v.setBackgroundColor(Color.WHITE);
        }

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
                    results.values = animalesOriginales;
                    results.count = animalesOriginales.size();
                }
                else{
                    String filterString = constraint.toString().toLowerCase();

                    ArrayList<Animal> filterResultsData = new ArrayList<>();
                    for (Animal animal : animalesOriginales){
                        if (animal.getCodigo().toLowerCase().contains(filterString)){
                            filterResultsData.add(animal);
                        }
                    }
                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                animalesFiltrados = (ArrayList<Animal>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}