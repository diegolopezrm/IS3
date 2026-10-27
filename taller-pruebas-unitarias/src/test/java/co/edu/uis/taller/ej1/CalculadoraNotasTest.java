package co.edu.uis.taller.ej1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ejercicio 1 - Primeras pruebas con el patrón AAA.
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
    // PASO 3: 4.5 es aprobatoria
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Una nota de 4.5 es aprobatoria")
    void notaCuatroPuntoCincoAprueba() {
        // Arrange
        double nota = 4.5;

        // Act
        boolean resultado = calculadora.aprobo(nota);

        // Assert
        assertTrue(resultado, "4.5 está por encima de 3.0, debería aprobar");
    }

    // ---------------------------------------------------------------
    // PASO 4: 2.9 NO es aprobatoria
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Una nota de 2.9 no es aprobatoria")
    void notaDosPuntoNueveReprueba() {
        // Arrange
        double nota = 2.9;

        // Act
        boolean resultado = calculadora.aprobo(nota);

        // Assert
        assertFalse(resultado, "2.9 está por debajo de 3.0, no debería aprobar");
    }

    // ---------------------------------------------------------------
    // PASO 5: VALOR LÍMITE. Esta es la prueba que descubrió el bug:
    // la regla dice "mayor o igual a 3.0", pero aprobo() usaba >.
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Valor límite: una nota de 3.0 exacta es aprobatoria")
    void notaTresExactaAprueba() {
        // Arrange
        double nota = CalculadoraNotas.NOTA_APROBATORIA;

        // Act
        boolean resultado = calculadora.aprobo(nota);

        // Assert
        assertTrue(resultado, "3.0 es el límite inclusive: debe aprobar");
    }

    // Los dos vecinos del límite, para dejar documentado dónde está la frontera
    @Test
    @DisplayName("Valor límite: 2.99 reprueba y 3.01 aprueba")
    void vecinosDelLimiteDeAprobacion() {
        assertFalse(calculadora.aprobo(2.99), "2.99 está justo por debajo del límite");
        assertTrue(calculadora.aprobo(3.01), "3.01 está justo por encima del límite");
    }

    // ---------------------------------------------------------------
    // PASO 7: lista vacía lanza IllegalArgumentException
    // ---------------------------------------------------------------
    @Test
    @DisplayName("El promedio de una lista vacía lanza IllegalArgumentException")
    void promedioDeListaVaciaLanzaExcepcion() {
        // Arrange
        List<Double> vacia = List.of();

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> calculadora.promedio(vacia));
    }

    @Test
    @DisplayName("El promedio de una lista nula lanza IllegalArgumentException")
    void promedioDeListaNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> calculadora.promedio(null));
    }

    // ---------------------------------------------------------------
    // PASO 8: nota fuera de rango, excepción Y mensaje
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Una nota de 5.5 lanza excepción con mensaje 'fuera de rango'")
    void notaMayorACincoLanzaExcepcionConMensaje() {
        // Arrange
        List<Double> notas = List.of(4.0, 5.5);

        // Act
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.promedio(notas)
        );

        // Assert
        assertTrue(ex.getMessage().contains("fuera de rango"),
                "El mensaje debería explicar que la nota está fuera de rango, pero fue: " + ex.getMessage());
    }

    @Test
    @DisplayName("Una nota negativa también está fuera de rango")
    void notaNegativaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> calculadora.promedio(List.of(-0.5)));
    }

    // Los extremos válidos SÍ deben aceptarse: 0.0 y 5.0 están dentro del rango
    @Test
    @DisplayName("Valores límite del rango: 0.0 y 5.0 son notas válidas")
    void extremosDelRangoSonValidos() {
        assertEquals(2.5, calculadora.promedio(List.of(0.0, 5.0)), 0.001);
    }
}
