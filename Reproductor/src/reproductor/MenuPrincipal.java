/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductor;
 
import Logica.BibliotecaMusical;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Nathan
 */
public class MenuPrincipal {

    private JFrame frame;
    private BibliotecaMusical biblioteca;

    public MenuPrincipal() {
        biblioteca = new BibliotecaMusical();

        frame = new JFrame("Menú Principal");
        frame.setSize(420, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        construirGUI();
        frame.setVisible(true);
    }

    private void construirGUI() {
        JLabel titulo = new JLabel("Reproductor de Música", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        JButton abrir = new JButton("Abrir Reproductor");
        abrir.setFont(new Font("Arial", Font.PLAIN, 16));
        abrir.addActionListener(e -> {
            new Reproductor(biblioteca);
            frame.dispose();
        });

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        abrir.setAlignmentX(Component.CENTER_ALIGNMENT);

        centro.add(Box.createVerticalStrut(30));
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(30));
        centro.add(abrir);

        frame.add(centro, BorderLayout.CENTER);
    }
}