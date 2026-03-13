/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author Nathan
 */
public class Cancion extends ArchivoMultimedia {

    private String artista;
    private GeneroMusical genero;
    private String imagen;

    public Cancion(String nombre, String artista, String duracion,
                   GeneroMusical genero, String archivoMusica, String imagen) {
        super(nombre, archivoMusica, duracion);
        this.artista = artista;
        this.genero = genero;
        this.imagen = imagen;
    }

    public String getArtista() {
        return artista;
    }

    public GeneroMusical getGenero() {
        return genero;
    }

    public String getImagen() {
        return imagen;
    }

    public String getArchivoMusica() {
        return getRutaArchivo();
    }

    @Override
    public String toString() {
        return getNombre() + " - " + artista;
    }
}
