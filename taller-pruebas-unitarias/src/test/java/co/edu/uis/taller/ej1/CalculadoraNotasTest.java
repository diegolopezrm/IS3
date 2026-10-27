package co.edu.uis.taller.ej1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ejercicio 1 - Primeras pruebas con el patrón AAA.
 * Sigue las instrucciones de la presentación paso a paso.
 */
class CalculadoraNotasTest {

    private CalculadoraNotas calculadora;

    @BeforeEach
    void setUp() {
        // Se ejecuta ANTES de cada prueba: cada prueba recibe una calculadora nueva
        calculadora = new CalculadoraNotas();
    }

    // ---------------------------------------------------------------
    // PASO 1: Prueba de ejemplo (ya está completa). Ejecútala.
    // ---------------------------------------------------------------
    @Test
    @DisplayName("El promedio de 3.0, 4.0 y 5.0 es 4.0")
    void promedioDeTresNotas() {
        // Arrange
        List<Double> notas = List.of(3.0, 4.0, 5.0);

        // Act
        double resultado = calculadora.promedio(notas);

        // Assert
        assertEquals(4.0, resultado, 0.001);
    }

    // ---------------------------------------------------------------
    // PASO 3: Escribe una prueba que verifique que 4.5 es aprobatoria.
    //         Nombre sugerido: notaCuatroPuntoCincoAprueba
    // ---------------------------------------------------------------
    // TODO


    // ---------------------------------------------------------------
    // PASO 4: Escribe una prueba que verifique que 2.9 NO es aprobatoria.
    //         Nombre sugerido: notaDosPuntoNueveReprueba
    // ---------------------------------------------------------------
    // TODO


    // ---------------------------------------------------------------
    // PASO 5: Prueba el VALOR LÍMITE: 3.0 debe ser aprobatoria.
    //         Nombre sugerido: notaTresExactaAprueba
    //         (¡Atención! Esta prueba va a descubrir algo...)
    // ---------------------------------------------------------------
    // TODO


    // ---------------------------------------------------------------
    // PASO 7: Verifica que una lista vacía lanza IllegalArgumentException.
    //         Usa assertThrows(...)
    // ---------------------------------------------------------------
    // TODO


    // ---------------------------------------------------------------
    // PASO 8: Verifica que una nota de 5.5 lanza IllegalArgumentException
    //         y que el mensaje contiene "fuera de rango".
    // ---------------------------------------------------------------
    // TODO

}
