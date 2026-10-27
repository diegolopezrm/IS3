package co.edu.uis.taller.ej1;

import java.util.List;

/**
 * Ejercicio 1 - Calculadora de notas en escala de 0.0 a 5.0.
 *
 * Reglas del negocio:
 *  - Una nota válida está entre 0.0 y 5.0 (inclusive).
 *  - El estudiante APRUEBA si su nota es mayor o igual a 3.0.
 *  - No se puede calcular el promedio de una lista nula o vacía.
 */
public class CalculadoraNotas {

    public static final double NOTA_MINIMA = 0.0;
    public static final double NOTA_MAXIMA = 5.0;
    public static final double NOTA_APROBATORIA = 3.0;

    /**
     * Calcula el promedio de una lista de notas.
     *
     * @throws IllegalArgumentException si la lista es nula, está vacía
     *                                  o contiene una nota fuera del rango 0.0 - 5.0
     */
    public double promedio(List<Double> notas) {
        if (notas == null || notas.isEmpty()) {
            throw new IllegalArgumentException("La lista de notas no puede estar vacía");
        }
        double suma = 0;
        for (double nota : notas) {
            if (nota < NOTA_MINIMA || nota > NOTA_MAXIMA) {
                throw new IllegalArgumentException("Nota fuera de rango: " + nota);
            }
            suma += nota;
        }
        return suma / notas.size();
    }

    /**
     * Indica si una nota es aprobatoria (mayor o igual a 3.0).
     */
    public boolean aprobo(double nota) {
        return nota > NOTA_APROBATORIA;
    }
}
