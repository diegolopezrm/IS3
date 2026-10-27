package co.edu.uis.taller.ej5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SOLUCIÓN - Ejercicio 5
 * Las pruebas marcadas con [BUG] fallaban con el código original.
 */
class EstadisticasTest {

    private final Estadisticas estadisticas = new Estadisticas();

    // ----- maximo() -----
    @Test
    @DisplayName("maximo de {3, 9, 4} es 9")
    void maximoCasoNormal() {
        assertEquals(9, estadisticas.maximo(new int[]{3, 9, 4}));
    }

    @Test
    @DisplayName("[BUG] maximo de {-5, -2, -8} es -2")
    void maximoConTodosNegativos() {
        assertEquals(-2, estadisticas.maximo(new int[]{-5, -2, -8}));
    }

    @Test
    @DisplayName("maximo de un arreglo vacío lanza excepción")
    void maximoArregloVacio() {
        assertThrows(IllegalArgumentException.class, () -> estadisticas.maximo(new int[]{}));
    }

    // ----- minimo() -----
    @Test
    @DisplayName("minimo de {3, 9, 4} es 3")
    void minimoCasoNormal() {
        assertEquals(3, estadisticas.minimo(new int[]{3, 9, 4}));
    }

    @Test
    @DisplayName("minimo de {-5, -2, -8} es -8")
    void minimoConNegativos() {
        assertEquals(-8, estadisticas.minimo(new int[]{-5, -2, -8}));
    }

    // ----- mediana() -----
    @Test
    @DisplayName("mediana de {5, 1, 3} (impar) es 3")
    void medianaCantidadImpar() {
        assertEquals(3.0, estadisticas.mediana(new int[]{5, 1, 3}), 0.001);
    }

    @Test
    @DisplayName("[BUG] mediana de {4, 1, 3, 2} (par) es 2.5")
    void medianaCantidadPar() {
        assertEquals(2.5, estadisticas.mediana(new int[]{4, 1, 3, 2}), 0.001);
    }

    // ----- contarAprobados() -----
    @Test
    @DisplayName("contarAprobados de {2.0, 3.5, 4.0} es 2")
    void contarAprobadosCasoNormal() {
        assertEquals(2, estadisticas.contarAprobados(new double[]{2.0, 3.5, 4.0}));
    }

    @Test
    @DisplayName("[BUG] contarAprobados de {4.5} (un solo elemento) es 1")
    void contarAprobadosUnSoloElemento() {
        assertEquals(1, estadisticas.contarAprobados(new double[]{4.5}));
    }

    @Test
    @DisplayName("contarAprobados cuenta el 3.0 como aprobado")
    void contarAprobadosValorLimite() {
        assertEquals(1, estadisticas.contarAprobados(new double[]{2.9, 3.0}));
    }
}
