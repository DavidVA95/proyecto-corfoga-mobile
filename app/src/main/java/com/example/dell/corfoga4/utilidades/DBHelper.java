package com.example.dell.corfoga4.utilidades;

/**
 * Created by Dell on 23/12/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.R.attr.type;
import static android.R.attr.version;
import static android.icu.text.MessagePattern.ArgType.SELECT;


public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "corfoga.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuarios(" +
                "usuario text NOT NULL UNIQUE primary key," +
                "contrasena text NOT NULL,"+
                "token text NOT NULL)");

        db.execSQL("create table propietarios(" +
                "id integer primary key," +
                "id_usuario integer)");

        db.execSQL("create table animales(" +
                "registro text primary key," +
                "codigo text," +
                "nombre text," +
                "raza text," +
                "sexo text," +
                "nacimiento text," +
                "origen integer," +
                "destete text)");

        db.execSQL("create table registros(" +
                "id integer primary key," +
                "fecha text," +
                "id_propietario integer," +
                "registro_animal text," +
                "foreign key (id_propietario) references propietarios(id)," +
                "foreign key (registro_animal) references animales(registro))");

        db.execSQL("create table fincas(" +
                "id integer primary key," +
                "nombre text," +
                "region text," +
                "id_propietario integer," +
                "foreign key (id_propietario) references propietarios(id))");

        db.execSQL("create table inspecciones(" +
                "id integer primary key autoincrement," +
                "fecha text," +
                "sincronizado integer, "+

                "id_finca integer," +
                "num_visita integer," +
                "foreign key (id_finca) references fincas(id))");

        db.execSQL("create table detalles(" +
                "id integer primary key autoincrement, " +
                "peso real, " +
                "sincronizado integer, "+
                "ce real, " +
                "sa integer, " +
                "observaciones text)");

        db.execSQL("create table detalles_inspecciones(" +
                "id_detalle integer primary key," +
                "id_inspeccion integer," +
                "sincronizado integer, "+
                "codigo_animal text," +
                "comentario text," +
                "foreign key (id_detalle) references detalles(id)," +
                "foreign key (id_inspeccion) references inspecciones(id)," +
                "foreign key (codigo_animal) references animales(codigo))");

        //db.execSQL("insert into usuarios (usuario,contrasena) values('admin','admin')");

        ///db.execSQL("insert into propietarios(id) values(4075)");

        /*db.execSQL("insert into animales (raza,sexo,nombre,registro,codigo) values('brahman gris','H','405/06 IA','01-7070','588565')");
        db.execSQL("insert into animales (raza,sexo,nombre,registro,codigo) values('brahman gris','H','455/07 IA','02-3459','405008')");
        db.execSQL("insert into animales (raza,sexo,nombre,registro,codigo) values('brahman gris','H','444/07 IA','03-3498','785008')");
        db.execSQL("insert into animales (raza,sexo,nombre,registro,codigo) values('brahman gris','H','222/07 IA','04-3985','456608')");
        db.execSQL("insert into animales (raza,sexo,nombre,registro,codigo) values('brahman gris','H','658/08 IA','05-1937','756465')");
        db.execSQL("insert into animales (raza,sexo,nombre,registro,codigo) values('brahman gris','H','584/08 IA','06-4978','588575')");
        db.execSQL("insert into animales (raza,sexo,nombre,registro,codigo) values('brahman gris','H','154/08 IA','07-9857','677755')");
        db.execSQL("insert into animales (raza,sexo,nombre,registro,codigo) values('brahman gris','H','894/08 IA','08-3847','585755')");
        db.execSQL("insert into animales (raza,sexo,nombre,registro,codigo) values('brahman gris','H','574/08 IA','09-8775','111755')");
        db.execSQL("insert into animales (raza,sexo,nombre,registro,codigo) values('brahman gris','H','333/08 IA','10-4857','333755')");
*/
        /*db.execSQL("insert into registros(fecha, id_propietario, registro_animal) values('20170111', 4075, '01-7070')");
        db.execSQL("insert into registros(fecha, id_propietario, registro_animal) values('20170111', 4075, '02-3459')");
        db.execSQL("insert into registros(fecha, id_propietario, registro_animal) values('20170111', 4075, '03-3498')");
        db.execSQL("insert into registros(fecha, id_propietario, registro_animal) values('20170111', 4075, '04-3985')");
        db.execSQL("insert into registros(fecha, id_propietario, registro_animal) values('20170111', 4075, '05-1937')");
        db.execSQL("insert into registros(fecha, id_propietario, registro_animal) values('20170111', 4075, '06-4978')");
        db.execSQL("insert into registros(fecha, id_propietario, registro_animal) values('20170111', 4075, '07-9857')");
        db.execSQL("insert into registros(fecha, id_propietario, registro_animal) values('20170111', 4075, '08-3847')");
        db.execSQL("insert into registros(fecha, id_propietario, registro_animal) values('20170111', 4075, '09-8775')");
        db.execSQL("insert into registros(fecha, id_propietario, registro_animal) values('20170111', 4075, '10-4857')");*/

        /*db.execSQL("insert into fincas (nombre, region, id_propietario) values('Santa Maria','Chorotega', 4075)");
        db.execSQL("insert into fincas (nombre, region, id_propietario) values('La Garita','Chorotega', 4075)");
        db.execSQL("insert into fincas (nombre, region, id_propietario) values('El Mordor','Chorotega', 4075)");
        db.execSQL("insert into fincas (nombre, region) values('Los Angeles','Huetar Norte')");
        db.execSQL("insert into fincas (nombre, region) values('Buenos Aires','Huetar Norte')");
        db.execSQL("insert into fincas (nombre, region) values('Tres lecheras','Huetar Norte')");
        db.execSQL("insert into fincas (nombre, region) values('Don Pedro','Brunca')");
        db.execSQL("insert into fincas (nombre, region) values('Cacahuate','Brunca')");
        db.execSQL("insert into fincas (nombre, region) values('San Luis','Huetar Atlantica')");*/



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("create table usuarios(id integer primary key autoincrement,usuario text NOT NULL UNIQUE,contrasena text NOT NULL)");
        db.execSQL("create table animales(id integer primary key autoincrement,raza text,sexo text,nombre text,codigo text)");

        db.execSQL("insert into animales (raza,sexo,nombre,codigo) values('brahman gris','H','405/06 IA','588565')");
        db.execSQL("insert into animales (raza,sexo,nombre,codigo) values('brahman gris','H','455/07 IA','405008')");
        db.execSQL("insert into animales (raza,sexo,nombre,codigo) values('brahman gris','H','424/08 IA','777755')");
        db.execSQL("insert into usuarios (usuario,contrasena) values('nano','1234')");*/
    }

    public void deleteDataBase() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("propietarios", "id" + ">" + "-1", null);
        db.delete("animales", "registro" + "!=" + "-1", null);
        db.delete("registros", "id" + ">" + "-1", null);
        db.delete("fincas", "id" + ">" + "-1", null);
        db.delete("inspecciones", "id" + ">" + "-1", null);
        db.delete("detalles", "id" + ">" + "-1", null);
        db.delete("detalles_inspecciones", "id_detalle" + ">" + "-1", null);

    }

    public long insertarFincas(int id, String nombre, String region, int id_propietario) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("nombre", nombre);
        values.put("region", region);
        values.put("id_propietario", id_propietario);

        return db.insert("fincas", null, values);

    }

    public long insertarRegistros(int id, String fecha, int id_propietario, String registro_animal) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("fecha", fecha);
        values.put("id_propietario", id_propietario);
        values.put("registro_animal", registro_animal);


        return db.insert("registros", null, values);



    }

    public long insertarPropietarios(int id, int id_usuario) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("id_usuario", id_usuario);

        return db.insert("propietarios", null, values);

    }

    public long insertarUsuario(String username, String password, String token) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("usuario",username);
        values.put("contrasena", password);
        values.put("token", token);
        System.out.println("Usuario: "+username+" contraseña: "+password+ " Token: "+token);


        return db.insert("usuarios", null, values);

    }

    public long insertarGanado(String raza, String sexo, String nombre, String registro, String codigo, String nacimiento, String origen, String destete){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put("codigo", codigo);
        values.put("nombre", nombre);
        values.put("raza", raza);
        values.put("sexo", sexo);
        values.put("nacimiento", nacimiento);
        values.put("origen", origen);
        values.put("destete", destete);
        values.put("registro", registro);



        return db.insert("animales", null, values);

    }

    public long insertarInspeccion(int id_finca, int num_visita) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = df.format(c.getTime());

        values.put("fecha",fecha);
        values.put("id_finca", id_finca);
        values.put("num_visita", num_visita);
        values.put("sincronizado", 0);

        return db.insert("inspecciones", null, values);

    }

    public void modificarInspeccion(String id,String fecha, int id_finca, int num_visita) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put("fecha",fecha);
        values.put("id_finca", id_finca);
        values.put("num_visita", num_visita);
        values.put("sincronizado", 1);

        db.update("inspecciones", values, "id="+id, null);


    }

    public long insertarDetalle(String idAnimal, float peso, float ce, int alimentacion, String observaciones) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("peso", peso);
        values.put("ce", ce);
        values.put("sa", alimentacion);
        values.put("observaciones", observaciones);
        values.put("sincronizado", 0);
        db.insert("detalles", null, values);


        int idInspeccion = (int) DatabaseUtils.longForQuery(db, "SELECT id FROM inspecciones ORDER BY id DESC LIMIT 1;", null);
        //int idInspeccion = c.getInt(0);
        int idDetalle = (int) DatabaseUtils.longForQuery(db, "SELECT id FROM detalles ORDER BY id DESC LIMIT 1;", null);
        //int idDetalle = c.getInt(0);

        values = new ContentValues();
        values.put("id_inspeccion", idInspeccion);
        values.put("id_detalle", idDetalle);
        values.put("codigo_animal", idAnimal);

        System.out.println("El detalle " + idDetalle + " del animal " + idAnimal + " se guardó en la inspección " + idInspeccion);

        return db.insert("detalles_inspecciones", null, values);
    }

    public void actualizarDetalle(String id, float peso, float ce, int alimentacion, String observaciones) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("peso", peso);
        values.put("ce", ce);
        values.put("sa", alimentacion);
        values.put("observaciones", observaciones);
        values.put("sincronizado", 1);

        db.update("detalles", values, "id="+id, null);

    }

}
