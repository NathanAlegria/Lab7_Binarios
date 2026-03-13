/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Logica;

/**
 *
 * @author Nathan
 */
public interface ControlReproduccion {
   void reproducir(String rutaArchivo);
   void pausar();
   void reanudar();
   void detener();
   void reiniciar();
   boolean estaReproduciendo();
   boolean estaPausado();
   long obtenerTiempoActualEnSegundos();
}
