package com.example.dell.corfoga4;

/**
 * Created by Dell on 3/1/2017.
 */

public class Finca {

    private int id;
    private String nombre;
    private String region;
    private int propietario;

    public Finca() {
        super();
    }

    public Finca(int id, String nombre, String region, int propietario) {
        this.id = id;
        this.nombre = nombre;
        this.region = region;
        this.propietario = propietario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPropietario() {
        return propietario;
    }

    public void setPropietario(int propietario) {
        this.propietario = propietario;
    }
}
