/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author Nathan
 */
public class ArchivoMultimedia {
    private String nombre;
    private String rutaArchivo;
    private String duracion;

    public ArchivoMultimedia(String nombre, String rutaArchivo, String duracion) {
        this.nombre = nombre;
        this.rutaArchivo = rutaArchivo;
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
}
