package com.example.dell.corfoga4;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.corfoga4.utilidades.DBHelper;

import static com.example.dell.corfoga4.AnimalActivity.arregloAnimal;

/**
 * Created by Dell on 4/1/2017.
 */

public class informationActivity extends AppCompatActivity {
    int indice;
    EditText txtPeso;
    EditText txtCE;
    Spinner spinner;
    MultiAutoCompleteTextView txtObserv;
    Animal animalSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);

        indice = getIntent().getExtras().getInt("animal");
        animalSeleccionado = arregloAnimal.get(indice);

        txtPeso = (EditText) findViewById(R.id.txtPeso);
        txtCE = (EditText) findViewById(R.id.txtCE);
        spinner = (Spinner) findViewById(R.id.spinAlimentacion);
        txtObserv = (MultiAutoCompleteTextView) findViewById(R.id.txtObservaciones);

        ((TextView) findViewById(R.id.registro)).setText(animalSeleccionado.getRegistro());
        ((TextView) findViewById(R.id.codigo)).setText(animalSeleccionado.getCodigo());
        ((TextView) findViewById(R.id.nombreAnimal)).setText(animalSeleccionado.getNombre());
        ((TextView) findViewById(R.id.raza)).setText(animalSeleccionado.getRaza());
        ((TextView) findViewById(R.id.sexo)).setText(animalSeleccionado.getSexo());
        ((TextView) findViewById(R.id.nacimiento)).setText(animalSeleccionado.getNacimiento());


        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.alimentacion, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button btnGuardar = (Button) findViewById(R.id.btnInspeccion);
        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                guardarInfo();
            }
        });

    }

    public void guardarInfo(){
        int alimentacion = spinner.getSelectedItemPosition() + 1;
        String observaciones = txtObserv.getText().toString();
        final SQLiteDatabase db = new DBHelper(informationActivity.this).getWritableDatabase();

        String regexStr = "^[0-9]+(\\.[0-9]+)?$";

        if (txtPeso.getText().toString().trim().matches(regexStr) && txtCE.getText().toString().trim().matches(regexStr)){
            float peso = Float.parseFloat(txtPeso.getText().toString());
            float ce = Float.parseFloat(txtCE.getText().toString());

            if(db != null){
                new DBHelper(informationActivity.this).insertarDetalle(animalSeleccionado.getCodigo(), peso, ce, alimentacion, observaciones);
            }
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Por favor, revise que los datos ingresados estén escritos correctamente", Toast.LENGTH_SHORT).show();
        }

        //Cursor consulta = db.rawQuery("SELECT * FROM tablename ORDER BY column DESC LIMIT 1", null);
        //int idInspeccion = consulta.getInt(0);

        //int cantidad = (int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM detalles_inspecciones", null);
        //Toast.makeText(getApplicationContext(),
          //      "Existe un total de " + cantidad + " detalles", Toast.LENGTH_SHORT).show();


    }

}
