package com.example.dell.corfoga4;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.support.v7.widget.SearchView;
import android.widget.Toast;


import com.example.dell.corfoga4.utilidades.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Dell on 3/1/2017.
 */

public class FincaActivity extends AppCompatActivity{

    public static ArrayList<Finca> arregloFinca;
    private static String boton;
    private AdapterFinca adapter;
    private int selectedFinca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fincas);
        boton = getIntent().getExtras().getString("botonEscogido");
        cargar();
    }

    public void cargar(){
        final SQLiteDatabase db = new DBHelper(FincaActivity.this).getWritableDatabase();
        int i = 0;

        if(db != null){
            if(boton.equals("Todas")){
                Cursor consulta = db.rawQuery("select * from fincas", null);
                arregloFinca = new ArrayList<>();
                if(consulta.moveToFirst()==true) {
                    do{
                        //lista con toda la info
                        Finca nf = new Finca(consulta.getInt(0),consulta.getString(1), consulta.getString(2), consulta.getInt(3));
                        arregloFinca.add(nf);
                        i++;
                    }while (consulta.moveToNext());
                }
            }
            else{
                Cursor consulta = db.rawQuery("select * from fincas where region = "+"'"+boton+"'"+"",null);
                arregloFinca = new ArrayList<>();
                if(consulta.moveToFirst()==true) {
                    do{
                        //lista con toda la info
                        Finca nf = new Finca(consulta.getInt(0),consulta.getString(1), consulta.getString(2), consulta.getInt(3));
                        arregloFinca.add(nf);
                        i++;
                    }while (consulta.moveToNext());
                }
            }

            //Crear el list view personalizada
            ListView lv = (ListView) findViewById(R.id.lista);
            adapter = new AdapterFinca(this, arregloFinca);
            lv.setAdapter(adapter);

            //Al presionar las celdas

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedFinca = arregloFinca.indexOf(parent.getAdapter().getItem(position));
                    //Toast.makeText(getApplicationContext(),"celda numero: "+ id0 , Toast.LENGTH_SHORT).show();
                    //new Alerta().show(getSupportFragmentManager(), "Alerta");
                    seleccionarFinca();
                }
            });
        }
    }

    public void seleccionarFinca(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FincaActivity.this);
        alertDialog.setTitle("Iniciar Inspección");
        alertDialog.setMessage("¿Desea iniciar una inspección en esta finca?");

        final EditText input = new EditText(FincaActivity.this);
        input.setHint("Digite el número de visita");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setFilters( new InputFilter[] { new InputFilter.LengthFilter(1) } );

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        //alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("Sí",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String visita = input.getText().toString();
                        try {
                            int numVisita = Integer.parseInt(visita);
                            if (numVisita >= 1 && numVisita <= 4) {

                                int idFinca = arregloFinca.get(selectedFinca).getId();

                                final SQLiteDatabase db = new DBHelper(FincaActivity.this).getWritableDatabase();
                                if(db != null){
                                    new DBHelper(FincaActivity.this).insertarInspeccion(idFinca, numVisita);
                                }

                                //Cursor consulta = db.rawQuery("SELECT * FROM tablename ORDER BY column DESC LIMIT 1", null);
                                //int idInspeccion = consulta.getInt(0);

                                /*int cantidad = (int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM inspecciones", null);
                                Toast.makeText(getApplicationContext(),
                                        "Existe un total de " + cantidad + " inspecciones", Toast.LENGTH_SHORT).show();*/

                                Intent ven = new Intent(FincaActivity.this, AnimalActivity.class);
                                ven.putExtra("idFinca", idFinca);
                                startActivity(ven);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Número de visita inválido", Toast.LENGTH_SHORT).show();
                            }
                        }catch (NumberFormatException ex){
                            Toast.makeText(getApplicationContext(),
                                    "Número de visita inválido", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Buscar...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}