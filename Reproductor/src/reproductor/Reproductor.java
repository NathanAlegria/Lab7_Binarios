/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductor;

import Logica.BibliotecaMusical;
import Logica.Cancion;
import Logica.ControlReproduccion;
import Logica.RMusica;
import Logica.Tiempo;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Nathan
 */
public class Reproductor extends JFrame {

    private BibliotecaMusical biblioteca;
    private ControlReproduccion reproductor;

    private DefaultListModel<String> model;
    private JList<String> listaCanciones;
    private JLabel imagenLabel;
    private JLabel cancionLabel;
    private JLabel artistaLabel;
    private JLabel generoLabel;
    private JLabel duracionLabel;
    private JProgressBar barra;
    private Cancion cancionActual;
    private Timer timer;

    public Reproductor(BibliotecaMusical biblioteca) {
        this.biblioteca = biblioteca;
        this.reproductor = new RMusica();

        setTitle("Reproductor de Música");
        setSize(900, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        construirGUI();
        refrescarLista();
        setVisible(true);
    }

    private void construirGUI() {
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 10));

        model = new DefaultListModel<String>();
        listaCanciones = new JList<String>(model);
        listaCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCanciones.setFont(new Font("Arial", Font.PLAIN, 14));

        listaCanciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarCancion();
            }
        });

        panelIzquierdo.add(new JScrollPane(listaCanciones), BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 15));

        imagenLabel = new JLabel("No hay imagen", SwingConstants.CENTER);
        imagenLabel.setPreferredSize(new Dimension(260, 260));
        imagenLabel.setMinimumSize(new Dimension(260, 260));
        imagenLabel.setMaximumSize(new Dimension(260, 260));
        imagenLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        cancionLabel = new JLabel("Canción: ");
        artistaLabel = new JLabel("Artista: ");
        generoLabel = new JLabel("Género: ");
        duracionLabel = new JLabel("00:00 / 00:00");

        Font infoFont = new Font("Arial", Font.BOLD, 14);
        cancionLabel.setFont(infoFont);
        artistaLabel.setFont(infoFont);
        generoLabel.setFont(infoFont);
        duracionLabel.setFont(infoFont);

        barra = new JProgressBar();
        barra.setMinimum(0);
        barra.setValue(0);

        JPanel panelInfo = new JPanel(new GridLayout(4, 1, 5, 5));
        panelInfo.add(cancionLabel);
        panelInfo.add(artistaLabel);
        panelInfo.add(generoLabel);
        panelInfo.add(duracionLabel);

        JPanel panelControles = new JPanel(new GridLayout(2, 3, 10, 10));
        JButton btnPlay = new JButton("Play");
        JButton btnPause = new JButton("Pause");
        JButton btnStop = new JButton("Stop");
        JButton btnAdd = new JButton("Add");
        JButton btnSelect = new JButton("Select");
        JButton btnRemove = new JButton("Remove");

        btnPlay.addActionListener(e -> play());
        btnPause.addActionListener(e -> pauseOrResume());
        btnStop.addActionListener(e -> stop());
        btnAdd.addActionListener(e -> new AgregarCancion(biblioteca, this));
        btnSelect.addActionListener(e -> seleccionarCancionManual());
        btnRemove.addActionListener(e -> eliminarCancionSeleccionada());

        panelControles.add(btnPlay);
        panelControles.add(btnPause);
        panelControles.add(btnStop);
        panelControles.add(btnAdd);
        panelControles.add(btnSelect);
        panelControles.add(btnRemove);

        panelDerecho.add(imagenLabel);
        panelDerecho.add(Box.createVerticalStrut(15));
        panelDerecho.add(panelInfo);
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add(barra);
        panelDerecho.add(Box.createVerticalStrut(20));
        panelDerecho.add(panelControles);

        add(panelIzquierdo, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);
    }

    public void refrescarLista() {
        model.clear();
        List<Cancion> canciones = biblioteca.obtenerCanciones();
        cargarModeloRecursivo(canciones, 0);
    }

    private void cargarModeloRecursivo(List<Cancion> canciones, int indice) {
        if (indice >= canciones.size()) {
            return;
        }

        model.addElement(canciones.get(indice).toString());
        cargarModeloRecursivo(canciones, indice + 1);
    }

    private void seleccionarCancionManual() {
        if (listaCanciones.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una canción de la lista.");
            return;
        }
        seleccionarCancion();
    }

    private void seleccionarCancion() {
        int indice = listaCanciones.getSelectedIndex();
        if (indice == -1) {
            return;
        }

        cancionActual = biblioteca.obtenerCancion(indice);
        if (cancionActual == null) {
            return;
        }

        cancionLabel.setText("Canción: " + cancionActual.getNombre());
        artistaLabel.setText("Artista: " + cancionActual.getArtista());
        generoLabel.setText("Género: " + cancionActual.getGenero().name());
        mostrarImagen(cancionActual.getImagen());

        int totalSegundos = Tiempo.convertirASegundos(cancionActual.getDuracion());
        barra.setMaximum(totalSegundos);
        barra.setValue(0);
        duracionLabel.setText("00:00 / " + cancionActual.getDuracion());
    }

    private void play() {
        if (cancionActual == null) {
            JOptionPane.showMessageDialog(this, "Primero selecciona una canción.");
            return;
        }

        if (reproductor.estaPausado()) {
            reproductor.reanudar();
        } else {
            reproductor.reproducir(cancionActual.getArchivoMusica());
        }

        iniciarTemporizador();
    }

    private void pauseOrResume() {
        if (cancionActual == null) {
            return;
        }

        if (reproductor.estaReproduciendo()) {
            reproductor.pausar();
            detenerTemporizador();
        } else if (reproductor.estaPausado()) {
            reproductor.reanudar();
            iniciarTemporizador();
        }
    }

    private void stop() {
        reproductor.detener();
        detenerTemporizador();
        barra.setValue(0);

        if (cancionActual != null) {
            duracionLabel.setText("00:00 / " + cancionActual.getDuracion());
        } else {
            duracionLabel.setText("00:00 / 00:00");
        }
    }

    private void eliminarCancionSeleccionada() {
        int indice = listaCanciones.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una canción para eliminar.");
            return;
        }

        Cancion seleccionada = biblioteca.obtenerCancion(indice);
        if (seleccionada == null) {
            return;
        }

        if (cancionActual != null
                && cancionActual.getNombre().equals(seleccionada.getNombre())
                && cancionActual.getArtista().equals(seleccionada.getArtista())
                && (reproductor.estaReproduciendo() || reproductor.estaPausado())) {

            stop();
            cancionActual = null;
            cancionLabel.setText("Canción: ");
            artistaLabel.setText("Artista: ");
            generoLabel.setText("Género: ");
            imagenLabel.setIcon(null);
            imagenLabel.setText("No hay imagen");
        }

        biblioteca.eliminarCancionPorIndice(indice);
        refrescarLista();
    }

    private void iniciarTemporizador() {
        detenerTemporizador();
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (cancionActual == null) {
                    return;
                }

                long actual = reproductor.obtenerTiempoActualEnSegundos();
                int total = Tiempo.convertirASegundos(cancionActual.getDuracion());

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (actual <= total) {
                            barra.setValue((int) actual);
                            duracionLabel.setText(Tiempo.formatearSegundos(actual) + " / " + cancionActual.getDuracion());
                        } else {
                            stop();
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    private void detenerTemporizador() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void mostrarImagen(String rutaImagen) {
        imagenLabel.setText(null);
        imagenLabel.setIcon(null);

        if (rutaImagen == null || rutaImagen.isEmpty()) {
            imagenLabel.setText("No hay imagen");
            return;
        }

        File archivo = new File(rutaImagen);
        if (!archivo.exists()) {
            imagenLabel.setText("No hay imagen");
            return;
        }

        ImageIcon icon = new ImageIcon(rutaImagen);
        Image img = icon.getImage().getScaledInstance(260, 260, Image.SCALE_SMOOTH);
        imagenLabel.setIcon(new ImageIcon(img));
    }
}
