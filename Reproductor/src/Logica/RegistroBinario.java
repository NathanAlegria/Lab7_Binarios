/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Nathan
 */
public abstract class RegistroBinario {
    
    protected void escribirCadena(RandomAccessFile raf, String texto, int tam) throws IOException {
        StringBuilder sb = new StringBuilder(texto == null ? "" : texto);

        if (sb.length() > tam) {
            sb.setLength(tam);
        }

        while (sb.length() < tam) {
            sb.append(' ');
        }

        raf.writeChars(sb.toString());
    }
    
    protected String leerCadena(RandomAccessFile raf, int tamanio) throws IOException {
        char[] caracteres = new char[tamanio];

        for (int i = 0; i < tamanio; i++) {
            caracteres[i] = raf.readChar();
        }

        return new String(caracteres).trim();
    }

    public abstract void escribir(RandomAccessFile raf) throws IOException;
}
