package co.edu.uis.taller.ej2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ejercicio 2 - Excepciones y pruebas parametrizadas.
 * Sigue las instrucciones de la presentación paso a paso.
 */
class ValidadorContrasenaTest {

    private final ValidadorContrasena validador = new ValidadorContrasena();

    // ---------------------------------------------------------------
    // PASO 1: Ejemplo completo - una contraseña correcta es válida.
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Contraseña válida: cumple todas las reglas")
    void contrasenaCorrectaEsValida() {
        assertTrue(validador.validar("Segura2026"));
    }

    // ---------------------------------------------------------------
    // PASO 2: Ejemplo completo - verificar excepción Y su mensaje.
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Contraseña corta: excepción con mensaje correcto")
    void contrasenaCortaLanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> validador.validar("Ab1")
        );
        assertEquals(ValidadorContrasena.MSG_LONGITUD, ex.getMessage());
    }

    // ---------------------------------------------------------------
    // PASO 3: Escribe pruebas para:
    //   a) contraseña nula         -> MSG_NULA
    //   b) sin mayúscula           -> MSG_MAYUSCULA   (ej: "segura2026")
    //   c) sin número              -> MSG_NUMERO      (ej: "SeguraUIS")
    // ---------------------------------------------------------------
    // TODO


    // ---------------------------------------------------------------
    // PASO 4: Prueba PARAMETRIZADA con @ValueSource:
    //   varias contraseñas inválidas -> esValida(...) debe ser false
    // ---------------------------------------------------------------
    // TODO


    // ---------------------------------------------------------------
    // PASO 5: Prueba PARAMETRIZADA con @CsvSource:
    //   cada fila = contraseña, mensaje de error esperado
    // ---------------------------------------------------------------
    // TODO

}
