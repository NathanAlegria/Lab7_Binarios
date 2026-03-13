/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Logica.GeneroMusical;
import Logica.RegistroBinario;
import Logica.Cancion;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Nathan
 */
public class CancionesRegistro extends RegistroBinario {

    public static final int NOMBRE_SIZE = 100;
    public static final int ARTISTA_SIZE = 100;
    public static final int DURACION_SIZE = 20;
    public static final int GENERO_SIZE = 30;
    public static final int MUSICA_SIZE = 400;
    public static final int IMAGEN_SIZE = 400;

    public static final int RECORD_SIZE =(NOMBRE_SIZE + ARTISTA_SIZE + DURACION_SIZE + GENERO_SIZE + MUSICA_SIZE + IMAGEN_SIZE) * 2;

    private Cancion cancion;

    public CancionesRegistro(Cancion cancion) {
        this.cancion = cancion;
    }

    public Cancion getCancion() {
        return cancion;
    }

    @Override
    public void escribir(RandomAccessFile raf) throws IOException {
        escribirCadena(raf, cancion.getNombre(), NOMBRE_SIZE);
        escribirCadena(raf, cancion.getArtista(), ARTISTA_SIZE);
        escribirCadena(raf, cancion.getDuracion(), DURACION_SIZE);
        escribirCadena(raf, cancion.getGenero().name(), GENERO_SIZE);
        escribirCadena(raf, cancion.getArchivoMusica(), MUSICA_SIZE);
        escribirCadena(raf, cancion.getImagen(), IMAGEN_SIZE);
    }

    public static Cancion leer(RandomAccessFile raf) throws IOException {
        CancionesRegistro aux = new CancionesRegistro(null);

        String nombre = aux.leerCadena(raf, NOMBRE_SIZE);
        String artista = aux.leerCadena(raf, ARTISTA_SIZE);
        String duracion = aux.leerCadena(raf, DURACION_SIZE);
        String generoTexto = aux.leerCadena(raf, GENERO_SIZE);
        String archivo = aux.leerCadena(raf, MUSICA_SIZE);
        String imagen = aux.leerCadena(raf, IMAGEN_SIZE);

        GeneroMusical genero;
        try {
            genero = GeneroMusical.valueOf(generoTexto);
        } catch (Exception e) {
            genero = GeneroMusical.OTRO;
        }

        return new Cancion(nombre, artista, duracion, genero, archivo, imagen);
    }
}