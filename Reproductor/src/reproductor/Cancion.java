/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductor;

/**
 *
 * @author Nathan
 */
public class Cancion extends ArchivoMultimedia {
    private String artista;
    private GeneroMusical genero;
    private String imagen;

    public Cancion(String artista, GeneroMusical genero, String imagen, String nombre, String rutaArchivo, String duracion) {
        super(nombre, rutaArchivo, duracion);
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
    
    public String getArchivoMusical(){
        return getRutaArchivo();
    }
    
    public String toString(){
        return getNombre()+" - "+artista;
    }
}
