/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Logica.CancionesRegistro;
import Logica.Cancion;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nathan
 */
public class BibliotecaMusical {

    private List<Cancion> canciones;
    private File archivo;

    public BibliotecaMusical() {
        canciones = new ArrayList<>();
        archivo = new File("playlist.dat");
        cargarDesdeArchivo();
    }

    public void agregarCancion(Cancion cancion) {
        canciones.add(cancion);
        guardarTodo();
    }

    public List<Cancion> obtenerCanciones() {
        return new ArrayList<>(canciones);
    }

    public Cancion obtenerCancion(int indice) {
        if (indice < 0 || indice >= canciones.size()) {
            return null;
        }
        return canciones.get(indice);
    }

    public boolean eliminarCancionPorIndice(int indice) {
        if (indice < 0 || indice >= canciones.size()) {
            return false;
        }

        canciones.remove(indice);
        guardarTodo();
        return true;
    }

    public int buscarIndiceRecursivo(String nombre, String artista) {
        return buscarIndiceRecursivo(nombre, artista, 0);
    }

    private int buscarIndiceRecursivo(String nombre, String artista, int indice) {
        if (indice >= canciones.size()) {
            return -1;
        }

        Cancion actual = canciones.get(indice);

        if (actual.getNombre().equalsIgnoreCase(nombre)
                && actual.getArtista().equalsIgnoreCase(artista)) {
            return indice;
        }

        return buscarIndiceRecursivo(nombre, artista, indice + 1);
    }

    public boolean eliminarCancionRecursiva(String nombre, String artista) {
        int indice = buscarIndiceRecursivo(nombre, artista);
        if (indice == -1) {
            return false;
        }

        canciones.remove(indice);
        guardarTodo();
        return true;
    }

    private void guardarTodo() {
        try {
            RandomAccessFile raf = new RandomAccessFile(archivo, "rw");
            raf.setLength(0);
            guardarRecursivo(raf, 0);
            raf.close();
        } catch (IOException e) {
            System.out.println("Error al guardar archivo.");
        }
    }

    private void guardarRecursivo(RandomAccessFile raf, int indice) throws IOException {
        if (indice >= canciones.size()) {
            return;
        }

        CancionesRegistro registro = new CancionesRegistro(canciones.get(indice));
        registro.escribir(raf);

        guardarRecursivo(raf, indice + 1);
    }

    private void cargarDesdeArchivo() {
        if (!archivo.exists()) {
            return;
        }

        canciones.clear();

        try {
            RandomAccessFile raf = new RandomAccessFile(archivo, "r");
            long totalRegistros = raf.length() / CancionesRegistro.RECORD_SIZE;
            cargarRecursivo(raf, 0, (int) totalRegistros);
            raf.close();
        } catch (IOException e) {
            System.out.println("Error al cargar archivo.");
        }
    }

    private void cargarRecursivo(RandomAccessFile raf, int indice, int totalRegistros) throws IOException {
        if (indice >= totalRegistros) {
            return;
        }

        Cancion cancion = CancionesRegistro.leer(raf);
        canciones.add(cancion);

        cargarRecursivo(raf, indice + 1, totalRegistros);
    }
}
