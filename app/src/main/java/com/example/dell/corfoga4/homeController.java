package com.example.dell.corfoga4;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dell.corfoga4.utilidades.Animales;
import com.example.dell.corfoga4.utilidades.ClientService;
import com.example.dell.corfoga4.utilidades.DBHelper;
import com.example.dell.corfoga4.utilidades.Fincas;
import com.example.dell.corfoga4.utilidades.Propietarios;
import com.example.dell.corfoga4.utilidades.Registros;
import com.example.dell.corfoga4.utilidades.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

/**
 * Created by Dell on 24/12/2016.
 */

public class homeController extends AppCompatActivity {
    private String BASEURL ="https://mobilerest.herokuapp.com";
    private DBHelper baseDatos;
    LinearLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        progressBar =(LinearLayout) findViewById(R.id.progressBarLayout);

        Button chorotega = (Button) findViewById(R.id.chorotega);
        Button huetarNorte = (Button) findViewById(R.id.huetarNorte);
        Button brunca = (Button) findViewById(R.id.brunca);
        Button huetarAtlantica = (Button) findViewById(R.id.huetarAtlantica);
        Button central = (Button) findViewById(R.id.central);
        Button pacificoCentral = (Button) findViewById(R.id.pacificoCentral);
        Button todasFincas = (Button) findViewById(R.id.todas);

        chorotega.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent ven = new Intent(homeController.this, FincaActivity.class);
                ven.putExtra("botonEscogido","Chorotega");
                startActivity(ven);
            }
        });

        huetarNorte.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent ven = new Intent(homeController.this, FincaActivity.class);
                ven.putExtra("botonEscogido","Huetar Norte");
                startActivity(ven);
            }
        });

        brunca.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent ven = new Intent(homeController.this, FincaActivity.class);
                ven.putExtra("botonEscogido","Brunca");
                startActivity(ven);
            }
        });

        huetarAtlantica.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent ven = new Intent(homeController.this, FincaActivity.class);
                ven.putExtra("botonEscogido","Huetar Atlántica");
                startActivity(ven);
            }
        });

        central.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent ven = new Intent(homeController.this, FincaActivity.class);
                ven.putExtra("botonEscogido","Central");
                startActivity(ven);
            }
        });

        pacificoCentral.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent ven = new Intent(homeController.this, FincaActivity.class);
                ven.putExtra("botonEscogido","Pacífico Central");
                startActivity(ven);
            }
        });

        todasFincas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent ven = new Intent(homeController.this, FincaActivity.class);
                ven.putExtra("botonEscogido","Todas");
                startActivity(ven);
            }
        });

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    public void subirDatos(){
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    baseDatos = new DBHelper(homeController.this);
                    Retrofit query = new Retrofit.Builder()
                            .baseUrl(BASEURL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ClientService serviceInspeccion = query.create(ClientService.class);
                    ClientService serviceDetalles = query.create(ClientService.class);
                    ClientService serviceDetalles_Inspeccion = query.create(ClientService.class);

                    SQLiteDatabase db = baseDatos.getWritableDatabase();
                    Cursor datosInspecciones = db.rawQuery("select * from inspecciones where sincronizado = 0", null);


                    datosInspecciones.moveToFirst();
                    while ( !datosInspecciones.isAfterLast()) {
                        serviceInspeccion.subirInspeccion(datosInspecciones.getString(1),
                                Integer.parseInt(datosInspecciones.getString(3)),
                                Integer.parseInt(datosInspecciones.getString(4))).enqueue(new Callback<POST>() {
                            @Override
                            public void onResponse(Call<POST> call, Response<POST> response) {

                                if(response.isSuccessful()) {
                                    Log.i("homeController", "post submitted to API." + response.body().toString());
                                }
                                else {
                                    Log.i("homeController", "post submitted to API." + response.body().toString());
                                }

                            }

                            @Override
                            public void onFailure(Call<POST> call, Throwable t) {


                            }
                        });

                        baseDatos.modificarInspeccion(datosInspecciones.getString(0),datosInspecciones.getString(1),
                                Integer.parseInt(datosInspecciones.getString(3)),
                                Integer.parseInt(datosInspecciones.getString(4)));

                        datosInspecciones.moveToNext();
                    }

                    Cursor datosDetalles = db.rawQuery("select * from detalles where sincronizado = 0", null);

                    datosDetalles.moveToFirst();

                    Log.i("homeController","------------------------");
                    while ( !datosDetalles.isAfterLast()) {

                        serviceDetalles.subirDetalles(
                                Double.parseDouble(datosDetalles.getString(1)),
                                Double.parseDouble(datosDetalles.getString(3)),
                                Integer.parseInt(datosDetalles.getString(4)),
                                datosDetalles.getString(5)).enqueue(new Callback<POST>() {

                            @Override
                            public void onResponse(Call<POST> call, Response<POST> response) {

                                if(response.isSuccessful()) {
                                    Log.i("homeController", "post submitted to API.");
                                }

                            }

                            @Override
                            public void onFailure(Call<POST> call, Throwable t) {


                            }
                        });


                        baseDatos.actualizarDetalle(datosDetalles.getString(0),
                                Float.parseFloat(datosDetalles.getString(1)),
                                Float.parseFloat(datosDetalles.getString(3)),
                                Integer.parseInt(datosDetalles.getString(4)),
                                datosDetalles.getString(5));

                        datosDetalles.moveToNext();

                    }



                }catch (Exception e){
                    Log.i("homeController",e.toString());
                }

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
            }
        }.execute();



    }

    public void eliminarDatosCelular(){
        baseDatos = new DBHelper(homeController.this);
        baseDatos.deleteDataBase();

        Toast.makeText(getApplicationContext(),"Se han eliminado los datos del teléfono",Toast.LENGTH_LONG).show();

    }


    public void sincronizarDatos(){
        if (isOnline()) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    baseDatos = new DBHelper(homeController.this);
                    Retrofit query = new Retrofit.Builder()
                            .baseUrl(BASEURL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ClientService service = query.create(ClientService.class);

                    Call<List<Animales>> result = service.getAnimales();
                    result.enqueue(new Callback<List<Animales>>() {
                        @Override
                        public void onResponse(Call<List<Animales>> call, Response<List<Animales>> response) {
                            int longLista = response.body().size();
                            for(int i =0; i< longLista;i++) {

                                baseDatos.insertarGanado(response.body().get(i).getRaza(), response.body().get(i).getSexo(),
                                        response.body().get(i).getNombre(),response.body().get(i).getRegistro(),
                                        response.body().get(i).getCodigo(), response.body().get(i).getFecha_nacimiento(),
                                        response.body().get(i).getOrigen_reproductivo(),
                                        response.body().get(i).getFecha_destete());
                            }

                        }

                        @Override
                        public void onFailure(Call<List<Animales>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Ha ocurrido un error sincronizando datos de los animales, pruebe más tarde",Toast.LENGTH_LONG).show();

                        }



                    });

                    Call<List<Propietarios>> resultPropietarios = service.getPropietarios();
                    resultPropietarios.enqueue(new Callback<List<Propietarios>>() {
                        @Override
                        public void onResponse(Call<List<Propietarios>> call, Response<List<Propietarios>> response) {
                            int longLista = response.body().size();
                            for(int i =0; i< longLista;i++) {
                                baseDatos.insertarPropietarios(response.body().get(i).getId(), response.body().get(i).getUsuario_cedula());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Propietarios>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Ha ocurrido un error sincronizando datos de los Propietarios, pruebe más tarde",Toast.LENGTH_LONG).show();

                        }
                    });

                    Call<List<Registros>> resultRegistros = service.getRegistros();
                    resultRegistros.enqueue(new Callback<List<Registros>>() {
                        @Override
                        public void onResponse(Call<List<Registros>> call, Response<List<Registros>> response) {
                            int longLista = response.body().size();
                            for(int i =0; i< longLista;i++) {
                                baseDatos.insertarRegistros(response.body().get(i).getId(),
                                        response.body().get(i).getFecha_emitido(),
                                        response.body().get(i).getPropietario_id(),
                                        response.body().get(i).getAnimal_registro());
                            }

                        }

                        @Override
                        public void onFailure(Call<List<Registros>> call, Throwable t) {

                            Toast.makeText(getApplicationContext(),"Ha ocurrido un error sincronizando datos de los Registros, pruebe más tarde",Toast.LENGTH_LONG).show();

                        }
                    });

                    Call<List<Fincas>> resultFincas = service.getFincas();
                    resultFincas.enqueue(new Callback<List<Fincas>>() {
                        @Override
                        public void onResponse(Call<List<Fincas>> call, Response<List<Fincas>> response) {
                            int longLista = response.body().size();
                            for(int i =0; i< longLista;i++) {

                                baseDatos.insertarFincas(response.body().get(i).getId(),
                                        response.body().get(i).getNombre(),
                                        response.body().get(i).getRegion(),
                                        response.body().get(i).getPropietario_id());
                            }

                        }

                        @Override
                        public void onFailure(Call<List<Fincas>> call, Throwable t) {

                            Toast.makeText(getApplicationContext(),"Ha ocurrido un error sincronizando datos de los Fincas, pruebe más tarde",Toast.LENGTH_LONG).show();

                        }
                    });
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    progressBar.setVisibility(View.GONE);

                }
            }.execute();

        }
        else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"No hay conexión a internet",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void acercade(){
        AlertDialog.Builder myMessage = new AlertDialog.Builder(this);
        myMessage.setTitle("Acerca de");
        myMessage.setMessage("Este proyecto ha sido creado por estudiantes del ITCR sede San Carlos");
        AlertDialog dialog = myMessage.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_salir:
                finish();
                break;

            case R.id.action_descargar:
                progressBar.setVisibility(View.VISIBLE);
                sincronizarDatos();
                break;

            case R.id.action_subir:
                progressBar.setVisibility(View.VISIBLE);
                subirDatos();
                break;
            case R.id.acerca_de:
                acercade();
                break;

            case R.id.elimianr_datos:
                eliminarDatosCelular();
                break;


            default:
                break;
        }
        return true;
    }

}