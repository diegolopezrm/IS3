package co.edu.uis.taller.ej1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SOLUCIÓN - Ejercicio 1
 */
class CalculadoraNotasTest {

    private CalculadoraNotas calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraNotas();
    }

    @Test
    @DisplayName("El promedio de 3.0, 4.0 y 5.0 es 4.0")
    void promedioDeTresNotas() {
        List<Double> notas = List.of(3.0, 4.0, 5.0);

        double resultado = calculadora.promedio(notas);

        assertEquals(4.0, resultado, 0.001);
    }

    @Test
    @DisplayName("Una nota de 4.5 es aprobatoria")
    void notaCuatroPuntoCincoAprueba() {
        boolean resultado = calculadora.aprobo(4.5);

        assertTrue(resultado);
    }

    @Test
    @DisplayName("Una nota de 2.9 NO es aprobatoria")
    void notaDosPuntoNueveReprueba() {
        boolean resultado = calculadora.aprobo(2.9);

        assertFalse(resultado);
    }

    @Test
    @DisplayName("Valor límite: 3.0 es aprobatoria")
    void notaTresExactaAprueba() {
        boolean resultado = calculadora.aprobo(3.0);

        assertTrue(resultado, "3.0 debería ser aprobatoria");
    }

    @Test
    @DisplayName("Una lista vacía lanza IllegalArgumentException")
    void listaVaciaLanzaExcepcion() {
        List<Double> vacia = List.of();

        assertThrows(IllegalArgumentException.class, () -> calculadora.promedio(vacia));
    }

    @Test
    @DisplayName("Una nota de 5.5 está fuera de rango")
    void notaFueraDeRangoLanzaExcepcion() {
        List<Double> notas = List.of(4.0, 5.5);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.promedio(notas)
        );

        assertTrue(ex.getMessage().contains("fuera de rango"));
    }
}
