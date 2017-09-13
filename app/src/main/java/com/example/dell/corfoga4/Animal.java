package com.example.dell.corfoga4;

import static com.example.dell.corfoga4.R.id.registro;

/**
 * Created by Dell on 4/1/2017.
 */

public class Animal {
    private String registro;
    private String codigo;
    private String nombre;
    private String raza;
    private String sexo;
    private String nacimiento;
    private int origen;
    private String destete;

    public Animal() {
        super();
    }

    public Animal(String registro, String codigo, String nombre, String raza, String sexo, String nacimiento, int origen, String destete) {
        this.registro = registro;
        this.codigo = codigo;
        this.nombre = nombre;
        this.raza = raza;
        this.sexo = sexo;
        this.nacimiento = nacimiento;
        this.origen = origen;
        this.destete = destete;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public String getDestete() {
        return destete;
    }

    public void setDestete(String destete) {
        this.destete = destete;
    }
}
