package co.edu.uis.taller.ej5;

import java.util.Arrays;

/**
 * Ejercicio 5 - RETO: Cacería de bugs.
 *
 * Esta clase fue escrita "a la carrera" y NO tiene pruebas.
 * Se sabe que tiene 3 errores escondidos.
 * Tu misión: escribir pruebas que los descubran y luego corregirlos.
 *
 * NO leas el código buscando el error: ¡escribe pruebas y deja que ellas lo encuentren!
 */
public class Estadisticas {

    /** Devuelve el mayor valor del arreglo. */
    public int maximo(int[] datos) {
        validar(datos);
        int max = 0;
        for (int dato : datos) {
            if (dato > max) {
                max = dato;
            }
        }
        return max;
    }

    /** Devuelve el menor valor del arreglo. */
    public int minimo(int[] datos) {
        validar(datos);
        int min = datos[0];
        for (int i = 1; i < datos.length; i++) {
            if (datos[i] < min) {
                min = datos[i];
            }
        }
        return min;
    }

    /**
     * Devuelve la mediana: el valor central de los datos ordenados.
     * Si la cantidad de datos es par, es el promedio de los dos valores centrales.
     * Ejemplo: {1, 2, 3, 4} -> 2.5
     */
    public double mediana(int[] datos) {
        validar(datos);
        int[] copia = datos.clone();
        Arrays.sort(copia);
        int n = copia.length;
        if (n % 2 == 0) {
            return (copia[n / 2 - 1] + copia[n / 2]) / 2;
        }
        return copia[n / 2];
    }

    /** Cuenta cuántas notas son aprobatorias (mayores o iguales a 3.0). */
    public int contarAprobados(double[] notas) {
        if (notas == null) {
            throw new IllegalArgumentException("Las notas no pueden ser nulas");
        }
        int aprobados = 0;
        for (int i = 0; i < notas.length - 1; i++) {
            if (notas[i] >= 3.0) {
                aprobados++;
            }
        }
        return aprobados;
    }

    private void validar(int[] datos) {
        if (datos == null || datos.length == 0) {
            throw new IllegalArgumentException("Los datos no pueden estar vacíos");
        }
    }
}
