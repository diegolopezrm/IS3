package co.edu.uis.taller.ej5;

import java.util.Arrays;

/**
 * SOLUCIÓN - Ejercicio 5 (los 3 bugs corregidos).
 */
public class Estadisticas {

    /** Devuelve el mayor valor del arreglo. */
    public int maximo(int[] datos) {
        validar(datos);
        int max = datos[0];               // BUG 1 CORREGIDO: antes era "int max = 0;"
        for (int dato : datos) {          // fallaba si todos los números eran negativos
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

    /** Devuelve la mediana. */
    public double mediana(int[] datos) {
        validar(datos);
        int[] copia = datos.clone();
        Arrays.sort(copia);
        int n = copia.length;
        if (n % 2 == 0) {
            return (copia[n / 2 - 1] + copia[n / 2]) / 2.0;   // BUG 2 CORREGIDO: antes "/ 2"
        }                                                     // (división entera: 2.5 -> 2.0)
        return copia[n / 2];
    }

    /** Cuenta cuántas notas son aprobatorias (mayores o iguales a 3.0). */
    public int contarAprobados(double[] notas) {
        if (notas == null) {
            throw new IllegalArgumentException("Las notas no pueden ser nulas");
        }
        int aprobados = 0;
        for (int i = 0; i < notas.length; i++) {   // BUG 3 CORREGIDO: antes "notas.length - 1"
            if (notas[i] >= 3.0) {                  // (nunca revisaba la última nota)
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
