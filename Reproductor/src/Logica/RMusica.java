/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Logica.ControlReproduccion;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 *
 * @author Nathan
 */
public class RMusica implements ControlReproduccion {

    private Player player;
    private FileInputStream fis;
    private BufferedInputStream bis;
    private Thread hilo;

    private boolean pausado = false;
    private long bytesRestantes = 0;
    private long longitudTotal = 0;
    private String rutaActual;
    private long tiempoInicio = 0;
    private long tiempoAcumulado = 0;

    @Override
    public void reproducir(String rutaArchivo) {
        if (rutaArchivo == null || rutaArchivo.isEmpty()) {
            return;
        }

        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return;
        }

        detener();

        try {
            rutaActual = rutaArchivo;
            longitudTotal = archivo.length();
            bytesRestantes = longitudTotal;
            tiempoAcumulado = 0;
            pausado = false;

            fis = new FileInputStream(archivo);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
            tiempoInicio = System.currentTimeMillis();

            hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        player.play();
                    } catch (Exception e) {
                        System.out.println("Error al reproducir.");
                    }
                }
            });

            hilo.start();
        } catch (Exception e) {
            System.out.println("Error al iniciar reproducción.");
        }
    }

    @Override
    public void pausar() {
        if (player != null && !pausado) {
            try {
                bytesRestantes = fis.available();
                tiempoAcumulado = tiempoAcumulado + (System.currentTimeMillis() - tiempoInicio);
                player.close();
                pausado = true;
            } catch (Exception e) {
                System.out.println("Error al pausar.");
            }
        }
    }

    @Override
    public void reanudar() {
        if (!pausado || rutaActual == null) {
            return;
        }

        try {
            fis = new FileInputStream(rutaActual);
            long bytesLeidos = longitudTotal - bytesRestantes;
            fis.skip(bytesLeidos);

            bis = new BufferedInputStream(fis);
            player = new Player(bis);
            tiempoInicio = System.currentTimeMillis();
            pausado = false;

            hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        player.play();
                    } catch (Exception e) {
                        System.out.println("Error al reanudar.");
                    }
                }
            });

            hilo.start();
        } catch (Exception e) {
            System.out.println("Error al reanudar reproducción.");
        }
    }

    @Override
    public void detener() {
        try {
            if (player != null) {
                player.close();
            }
            if (bis != null) {
                bis.close();
            }
            if (fis != null) {
                fis.close();
            }
        } catch (Exception e) {
            System.out.println("Error al detener.");
        }

        player = null;
        bis = null;
        fis = null;
        pausado = false;
        bytesRestantes = 0;
        tiempoInicio = 0;
        tiempoAcumulado = 0;
    }

    @Override
    public void reiniciar() {
        if (rutaActual != null) {
            reproducir(rutaActual);
        }
    }

    @Override
    public boolean estaReproduciendo() {
        return player != null && !pausado;
    }

    @Override
    public boolean estaPausado() {
        return pausado;
    }

    @Override
    public long obtenerTiempoActualEnSegundos() {
        if (estaReproduciendo()) {
            long tiempoActual = tiempoAcumulado + (System.currentTimeMillis() - tiempoInicio);
            return tiempoActual / 1000;
        }

        if (estaPausado()) {
            return tiempoAcumulado / 1000;
        }

        return 0;
    }
}
