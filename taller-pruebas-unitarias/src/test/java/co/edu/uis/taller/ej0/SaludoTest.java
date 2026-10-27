package co.edu.uis.taller.ej0;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ejercicio 0 - Tu primera prueba unitaria.
 * NO necesitas modificar nada: solo ejecútala y verifica que sale en VERDE.
 */
class SaludoTest {

    @Test
    @DisplayName("saludar() debe incluir el nombre recibido")
    void saludarIncluyeElNombre() {
        // Arrange (Preparar)
        Saludo saludo = new Saludo();

        // Act (Actuar)
        String resultado = saludo.saludar("UIS");

        // Assert (Afirmar)
        assertEquals("Hola, UIS!", resultado);
    }
}
