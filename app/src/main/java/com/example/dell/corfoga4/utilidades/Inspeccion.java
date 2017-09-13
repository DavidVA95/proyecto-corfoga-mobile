package com.example.dell.corfoga4.utilidades;

/**
 * Created by Eladio on 25/5/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Inspeccion {
    @SerializedName("fecha")
    @Expose
    private Date fecha;
    @SerializedName("finca_id")
    @Expose
    private int finca_id;
    @SerializedName("num_visita")
    @Expose
    private int num_visita;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getFinca_id() {
        return finca_id;
    }

    public void setFinca_id(int finca_id) {
        this.finca_id = finca_id;
    }

    public int getNum_visita() {
        return num_visita;
    }

    public void setNum_visita(int num_visita) {
        this.num_visita = num_visita;
    }
}
