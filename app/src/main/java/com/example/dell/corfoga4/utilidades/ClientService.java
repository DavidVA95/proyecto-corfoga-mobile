package com.example.dell.corfoga4.utilidades;

import com.example.dell.corfoga4.Finca;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by Eladio on 3/5/2017.
 */

public interface ClientService {

    @GET("/users/email/{email}/password/{password}")
    Call<List<User>> getCliente(@Path("email") String email, @Path("password") String password);///, retrofit2.Callback<List<User>> list);

    @GET("/animales/")
    Call<List<Animales>> getAnimales();

    @GET("/propietarios/")
    Call<List<Propietarios>> getPropietarios();

    @GET("/registros/")
    Call<List<Registros>> getRegistros();

    @GET("/fincas/")
    Call<List<Fincas>> getFincas();

    @POST("/inspeccion/{fecha}/{finca_id}/{num_visita}/")

    Call<POST> subirInspeccion(@Path("fecha") String fecha,
                        @Path("finca_id") int finca_id,
                        @Path("num_visita") int num_visita);

    @POST("/detalles/{peso}/{ce}/{sa}/{observaciones}/")
    Call<POST> subirDetalles(@Path("peso") double peso,
                               @Path("ce") double ce,
                               @Path("sa") int sa,
                               @Path("observaciones") String observaciones);
}
