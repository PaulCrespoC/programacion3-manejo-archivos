/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea_manejo_archivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author paulc
 */
public class Main {

    private static String path = "E:\\productos.bin";
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Ingresar\n2. Recuperar\n3. Terminar");
            int opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    ingresar();
                    break;
                case 2:
                    recuperar();
                    break;
                case 3:
                    System.exit(0);
                    break;
            }
        }
    }

    public static void ingresar() {
        String contenido = LectorEscritor.leer(path);
        System.out.println(
                contenido != null && contenido.length() > 0
                ? "------ El archivo tiene más datos ------"
                : "------ El archivo está vacío ------"
        );
        System.out.println("Ingresa en el siguiente formato: producto:valor ");
        
        if (contenido != null && contenido.length() > 0) {
            String entrada = sc.next();
            contenido += "\n" + entrada;
            LectorEscritor.escribir(path, contenido);
            System.out.println("---- Producto agregado correctamente ----");
            return;
        }
        String entrada = sc.next();
        LectorEscritor.escribir(path, entrada);
        System.out.println("---- Producto ingresado correctamente ----");
    }

    public static void recuperar() {
        if (LectorEscritor.existe(path)) {
            String contenido = LectorEscritor.leer(path);
            ArrayList<Producto> productos = Producto.multiple(contenido);
            productos.forEach(producto -> {
                System.out.println("Producto: " + producto.getNombre() + " Precio: " + producto.getPrecio());
            });
        }
    }
}

class Producto {

    private String nombre;
    private float precio;

    public Producto(String nombre, float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    // Parser
    public Producto(String contenido) {
        String split[] = contenido.split(":");
        this.nombre = split[0];
        this.precio = Float.parseFloat(split[1]);
    }

    // Converter
    public String parse() {
        return this.nombre + ":" + String.valueOf(this.precio);
    }

    public static ArrayList<Producto> multiple(String contenido) {
        String split[] = contenido.split("\n");
        if (split != null && split.length > 0) {
            ArrayList<Producto> productos = new ArrayList<>();
            for (String individual : split) {
                productos.add(new Producto(individual));
            }
            return productos;
        }
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return String.valueOf(precio);
    }

}

class LectorEscritor {

    public static boolean existe(String path) {
        String output = LectorEscritor.leer(path);
        return output != null && output.length() > 0;
    }

    public static String leer(String path) {
        try {
            File archivo = new File(path);
            FileInputStream lector = new FileInputStream(archivo);
            byte contenido[] = new byte[(int) archivo.length()];
            lector.read(contenido);
            lector.close();
            return new String(contenido, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean escribir(String path, String texto) {
        try {
            File archivo = new File(path);
            FileOutputStream escritor = new FileOutputStream(archivo);
            escritor.write(texto.getBytes());
            escritor.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean adjuntar(String path, String texto) {
        try {
            File archivo = new File(path);
            FileOutputStream escritor = new FileOutputStream(archivo);
            escritor.write(texto.getBytes());
            escritor.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
