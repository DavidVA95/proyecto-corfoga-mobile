package com.example.dell.corfoga4;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.corfoga4.utilidades.DBHelper;

import java.util.ArrayList;

import static android.R.id.input;
import static com.example.dell.corfoga4.FincaActivity.arregloFinca;

/**
 * Created by Dell on 4/1/2017.
 */

public class AnimalActivity extends AppCompatActivity {

    static ArrayList<Animal> arregloAnimal;
    AdapterAnimal adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animales);
        cargar();
    }

    public void cargar(){
        int i = 0;
        DBHelper admin = new DBHelper(AnimalActivity.this);
        SQLiteDatabase db = admin.getReadableDatabase();
        int idFinca = getIntent().getExtras().getInt("idFinca");
        if(db != null){

            Cursor consulta = db.rawQuery("SELECT animales.* FROM animales " +
                    "JOIN registros ON animales.registro = registros.registro_animal " +
                    "JOIN fincas ON registros.id_propietario = fincas.id_propietario " +
                    "WHERE fincas.id = " + idFinca + "", null);
            arregloAnimal = new ArrayList<>();
            if(consulta.moveToFirst()==true) {
                do{
                    //lista con toda la info
                    Animal na = new Animal(consulta.getString(0), consulta.getString(1),
                            consulta.getString(2), consulta.getString(3),
                            consulta.getString(4), consulta.getString(5),
                            consulta.getInt(6), consulta.getString(7));
                    arregloAnimal.add(na);
                    i++;
                }while (consulta.moveToNext());
            }

            //Crear el list view personalizada
            ListView lv = (ListView) findViewById(R.id.listaAnimal);
            adapter = new AdapterAnimal(this, arregloAnimal);
            lv.setAdapter(adapter);

            //Al presionar las celdas

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int idAnimal = arregloAnimal.indexOf(parent.getAdapter().getItem(position));
                    //Animal animalSeleccionado = arregloAnimal.get(idAnimal);
                    //Toast.makeText(getApplicationContext(),"celda numero: "+ pos,Toast.LENGTH_SHORT).show();
                    Intent ven = new Intent(AnimalActivity.this, informationActivity.class);
                    ven.putExtra("animal",idAnimal);
                    startActivity(ven);
                }
            });

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnimalActivity.this);
        alertDialog.setTitle("Finalizar Inspección");
        alertDialog.setMessage("¿Seguro que desea finalizar la inspección en esta finca?");

        alertDialog.setPositiveButton("Sí",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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