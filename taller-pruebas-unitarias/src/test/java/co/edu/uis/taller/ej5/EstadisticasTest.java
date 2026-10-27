package co.edu.uis.taller.ej5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ejercicio 5 - RETO: Cacería de bugs.
 *
 * Las pruebas marcadas con [BUG] son las que destaparon cada error.
 */
class EstadisticasTest {

    private final Estadisticas estadisticas = new Estadisticas();

    // ---------------------------------------------------------------
    // maximo()
    // ---------------------------------------------------------------
    @Test
    @DisplayName("El máximo de {3, 9, 4} es 9")
    void maximoDeValoresPositivos() {
        assertEquals(9, estadisticas.maximo(new int[]{3, 9, 4}));
    }

    @Test
    @DisplayName("[BUG 1] El máximo de datos todos negativos no puede ser 0")
    void maximoDeValoresNegativos() {
        // El código arrancaba con max = 0, un valor que no está en los datos.
        // Con todos los números por debajo de 0 nunca entraba al if y devolvía 0.
        assertEquals(-2, estadisticas.maximo(new int[]{-5, -2, -8}));
    }

    @Test
    @DisplayName("El máximo de un solo dato es ese dato")
    void maximoDeUnSoloDato() {
        assertEquals(7, estadisticas.maximo(new int[]{7}));
        assertEquals(-7, estadisticas.maximo(new int[]{-7}));
    }

    @Test
    @DisplayName("El máximo también funciona si está al principio o al final")
    void maximoEnLosExtremos() {
        assertEquals(9, estadisticas.maximo(new int[]{9, 1, 2}));
        assertEquals(9, estadisticas.maximo(new int[]{1, 2, 9}));
    }

    @Test
    @DisplayName("maximo() con datos nulos o vacíos lanza IllegalArgumentException")
    void maximoConDatosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> estadisticas.maximo(null));
        assertThrows(IllegalArgumentException.class, () -> estadisticas.maximo(new int[]{}));
    }

    // ---------------------------------------------------------------
    // minimo()
    // ---------------------------------------------------------------
    @Test
    @DisplayName("El mínimo de {3, 9, 4} es 3")
    void minimoDeValoresPositivos() {
        assertEquals(3, estadisticas.minimo(new int[]{3, 9, 4}));
    }

    @Test
    @DisplayName("El mínimo de datos todos positivos no se queda en 0")
    void minimoDeTodosPositivos() {
        // El mismo error que en maximo() habría salido aquí si hubieran
        // inicializado min = 0. No fue el caso: minimo() sí parte de datos[0].
        assertEquals(5, estadisticas.minimo(new int[]{8, 5, 9}));
    }

    @Test
    @DisplayName("El mínimo de un solo dato es ese dato")
    void minimoDeUnSoloDato() {
        assertEquals(4, estadisticas.minimo(new int[]{4}));
    }

    @Test
    @DisplayName("minimo() con datos nulos o vacíos lanza IllegalArgumentException")
    void minimoConDatosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> estadisticas.minimo(null));
        assertThrows(IllegalArgumentException.class, () -> estadisticas.minimo(new int[]{}));
    }

    // ---------------------------------------------------------------
    // mediana()
    // ---------------------------------------------------------------
    @Test
    @DisplayName("La mediana de una cantidad impar es el valor central")
    void medianaDeCantidadImpar() {
        // Ordenado queda {1, 2, 3}: el central es 2.0.
        assertEquals(2.0, estadisticas.mediana(new int[]{1, 3, 2}), 0.001);
    }

    @Test
    @DisplayName("[BUG 2] La mediana de {4, 1, 3, 2} es 2.5, no 2.0")
    void medianaDeCantidadPar() {
        // Los dos centrales son 2 y 3. El código hacía (2 + 3) / 2 entre enteros,
        // así que Java descartaba los decimales y devolvía 2.0.
        assertEquals(2.5, estadisticas.mediana(new int[]{4, 1, 3, 2}), 0.001);
    }

    @Test
    @DisplayName("La mediana no altera el arreglo que recibe")
    void medianaNoModificaElArregloOriginal() {
        int[] datos = {4, 1, 3, 2};

        estadisticas.mediana(datos);

        assertArrayEquals(new int[]{4, 1, 3, 2}, datos,
                "mediana() debería ordenar una copia, no los datos de quien la llama");
    }

    @Test
    @DisplayName("La mediana de un solo dato es ese dato")
    void medianaDeUnSoloDato() {
        assertEquals(5.0, estadisticas.mediana(new int[]{5}), 0.001);
    }

    @Test
    @DisplayName("mediana() con datos nulos o vacíos lanza IllegalArgumentException")
    void medianaConDatosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> estadisticas.mediana(null));
        assertThrows(IllegalArgumentException.class, () -> estadisticas.mediana(new int[]{}));
    }

    // ---------------------------------------------------------------
    // contarAprobados()
    // ---------------------------------------------------------------
    @Test
    @DisplayName("[BUG 3] Con una sola nota aprobatoria el conteo es 1, no 0")
    void contarAprobadosConUnaSolaNota() {
        // El for iba hasta notas.length - 1, así que nunca miraba la última nota.
        // Con un solo elemento no entraba al ciclo ni una vez.
        assertEquals(1, estadisticas.contarAprobados(new double[]{4.5}));
    }

    @Test
    @DisplayName("[BUG 3] La última nota del arreglo también cuenta")
    void contarAprobadosCuentaLaUltimaNota() {
        // 2.0 reprueba y 4.0 aprueba: si se ignora la última, daría 0.
        assertEquals(1, estadisticas.contarAprobados(new double[]{2.0, 4.0}));
    }

    @Test
    @DisplayName("Cuenta correctamente una mezcla de notas")
    void contarAprobadosConMezclaDeNotas() {
        assertEquals(3, estadisticas.contarAprobados(new double[]{4.5, 2.0, 3.0, 1.5, 5.0}));
    }

    @Test
    @DisplayName("Valor límite: 3.0 aprueba y 2.99 no")
    void contarAprobadosEnElLimite() {
        assertEquals(1, estadisticas.contarAprobados(new double[]{3.0}));
        assertEquals(0, estadisticas.contarAprobados(new double[]{2.99}));
    }

    @Test
    @DisplayName("Sin notas aprobatorias el conteo es 0")
    void contarAprobadosSinAprobados() {
        assertEquals(0, estadisticas.contarAprobados(new double[]{1.0, 2.0, 2.9}));
    }

    @Test
    @DisplayName("Un arreglo vacío devuelve 0, pero uno nulo lanza excepción")
    void contarAprobadosConDatosLimite() {
        assertEquals(0, estadisticas.contarAprobados(new double[]{}));
        assertThrows(IllegalArgumentException.class, () -> estadisticas.contarAprobados(null));
    }
}
