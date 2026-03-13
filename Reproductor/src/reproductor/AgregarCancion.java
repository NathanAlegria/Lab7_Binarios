/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductor;

import Logica.DuracionInvalidaException;
import Logica.CancionNoEncontradaException;
import Logica.BibliotecaMusical;
import Logica.Cancion;
import Logica.GeneroMusical;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 *
 * @author Nathan
 */
public class AgregarCancion extends JFrame {

    private BibliotecaMusical biblioteca;
    private Reproductor reproductor;

    private JTextField nombreField;
    private JTextField artistaField;
    private JComboBox<GeneroMusical> generoCombo;
    private JTextField imagenField;
    private JTextField musicaField;

    public AgregarCancion(BibliotecaMusical biblioteca, Reproductor reproductor) {
        this.biblioteca = biblioteca;
        this.reproductor = reproductor;

        setTitle("Agregar Canción");
        setSize(520, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        construirGUI();
        setVisible(true);
    }

    private void construirGUI() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Agregar nueva canción");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titulo, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        nombreField = new JTextField(20);
        formPanel.add(nombreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Artista:"), gbc);

        gbc.gridx = 1;
        artistaField = new JTextField(20);
        formPanel.add(artistaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Género:"), gbc);

        gbc.gridx = 1;
        generoCombo = new JComboBox<GeneroMusical>(GeneroMusical.values());
        formPanel.add(generoCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Imagen:"), gbc);

        gbc.gridx = 1;
        formPanel.add(crearPanelArchivoImagen(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Música MP3:"), gbc);

        gbc.gridx = 1;
        formPanel.add(crearPanelArchivoMusica(), gbc);

        JPanel botones = new JPanel();
        JButton guardar = new JButton("Guardar");
        JButton cancelar = new JButton("Cancelar");

        guardar.addActionListener(e -> guardarCancion());
        cancelar.addActionListener(e -> dispose());

        botones.add(guardar);
        botones.add(cancelar);

        add(formPanel, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelArchivoImagen() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        imagenField = new JTextField();
        imagenField.setEditable(false);

        JButton buscar = new JButton("Buscar...");
        buscar.addActionListener(e -> seleccionarImagen());

        panel.add(imagenField, BorderLayout.CENTER);
        panel.add(buscar, BorderLayout.EAST);
        return panel;
    }

    private JPanel crearPanelArchivoMusica() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        musicaField = new JTextField();
        musicaField.setEditable(false);

        JButton buscar = new JButton("Buscar...");
        buscar.addActionListener(e -> seleccionarMusica());

        panel.add(musicaField, BorderLayout.CENTER);
        panel.add(buscar, BorderLayout.EAST);
        return panel;
    }

    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagenField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void seleccionarMusica() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos MP3", "mp3"));

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            musicaField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void guardarCancion() {
        String nombre = nombreField.getText().trim();
        String artista = artistaField.getText().trim();
        String imagen = imagenField.getText().trim();
        String musica = musicaField.getText().trim();
        GeneroMusical genero = (GeneroMusical) generoCombo.getSelectedItem();

        if (nombre.isEmpty() || artista.isEmpty() || musica.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Completa nombre, artista y archivo de musica.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String duracion = obtenerDuracion(musica);

            Cancion nueva = new Cancion(nombre, artista, duracion, genero, musica, imagen);
            biblioteca.agregarCancion(nueva);

            if (reproductor != null) {
                reproductor.refrescarLista();
            }

            JOptionPane.showMessageDialog(this,
                    "Cancion guardada correctamente.",
                    "Exito",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (CancionNoEncontradaException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (DuracionInvalidaException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerDuracion(String ruta) throws DuracionInvalidaException, CancionNoEncontradaException {

        try {

            File archivo = new File(ruta);

            if (!archivo.exists()) {
                throw new CancionNoEncontradaException("El archivo de musica no existe.");
            }

            FileInputStream fis = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fis);

            Bitstream bitstream = new Bitstream(bis);
            Header header;

            try {
                header = bitstream.readFrame();
            } catch (javazoom.jl.decoder.BitstreamException e) {
                bis.close();
                fis.close();
                throw new DuracionInvalidaException("Error leyendo el archivo MP3.");
            }

            if (header == null) {
                bis.close();
                fis.close();
                throw new DuracionInvalidaException("No se pudo leer el encabezado del MP3.");
            }

            int bitrate = header.bitrate();

            if (bitrate <= 0) {
                bis.close();
                fis.close();
                throw new DuracionInvalidaException("Bitrate invalido.");
            }

            long tamanoArchivo = archivo.length();
            long duracionSegundos = (tamanoArchivo * 8) / bitrate;

            if (duracionSegundos <= 0) {
                bis.close();
                fis.close();
                throw new DuracionInvalidaException("Duracion invalida.");
            }

            long minutos = duracionSegundos / 60;
            long segundos = duracionSegundos % 60;

            bis.close();
            fis.close();

            String min = (minutos < 10 ? "0" : "") + minutos;
            String seg = (segundos < 10 ? "0" : "") + segundos;

            return min + ":" + seg;

        } catch (java.io.IOException e) {

            throw new DuracionInvalidaException("Error al leer el archivo de musica.");
        }
    }
}
