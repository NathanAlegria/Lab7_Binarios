/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author Nathan
 */
public final class Tiempo {

    private Tiempo() {
    }

    public static String formatearSegundos(long totalSegundos) {
        long minutos = totalSegundos / 60;
        long segundos = totalSegundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    public static int convertirASegundos(String tiempo) {
        if (tiempo == null || !tiempo.contains(":")) {
            return 0;
        }

        String[] partes = tiempo.split(":");
        int minutos = Integer.parseInt(partes[0]);
        int segundos = Integer.parseInt(partes[1]);

        return minutos * 60 + segundos;
    }
}
