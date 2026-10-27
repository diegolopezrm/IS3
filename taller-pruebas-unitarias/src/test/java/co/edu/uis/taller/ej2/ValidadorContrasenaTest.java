package co.edu.uis.taller.ej2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ejercicio 2 - Excepciones y pruebas parametrizadas.
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
    // PASO 3: Una prueba por regla incumplida
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Contraseña nula: excepción con el mensaje de nula")
    void contrasenaNulaLanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> validador.validar(null)
        );
        assertEquals(ValidadorContrasena.MSG_NULA, ex.getMessage());
    }

    @Test
    @DisplayName("Sin mayúscula: excepción con el mensaje de mayúscula")
    void contrasenaSinMayusculaLanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> validador.validar("segura2026")
        );
        assertEquals(ValidadorContrasena.MSG_MAYUSCULA, ex.getMessage());
    }

    @Test
    @DisplayName("Sin número: excepción con el mensaje de número")
    void contrasenaSinNumeroLanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> validador.validar("SeguraUIS")
        );
        assertEquals(ValidadorContrasena.MSG_NUMERO, ex.getMessage());
    }

    // El orden de las reglas importa: si falla más de una, se reporta la primera.
    @Test
    @DisplayName("Si incumple varias reglas, reporta la primera: la longitud")
    void contrasenaQueIncumpleVariasReglasReportaLaPrimera() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> validador.validar("ab")   // corta, sin mayúscula y sin número
        );
        assertEquals(ValidadorContrasena.MSG_LONGITUD, ex.getMessage());
    }

    // Valor límite de la longitud: 7 falla, 8 pasa.
    @Test
    @DisplayName("Valor límite de longitud: 7 caracteres falla, 8 pasa")
    void limiteDeLongitud() {
        assertFalse(validador.esValida("Abcde12"));   // 7
        assertTrue(validador.esValida("Abcdef12"));   // 8
    }

    // ---------------------------------------------------------------
    // PASO 4: Prueba parametrizada con @ValueSource
    // ---------------------------------------------------------------
    @ParameterizedTest(name = "\"{0}\" no es una contraseña válida")
    @DisplayName("Varias contraseñas inválidas devuelven false en esValida()")
    @ValueSource(strings = {
            "corta1A",       // menos de 8
            "sinmayus2026",  // sin mayúscula
            "SinNumeroUIS",  // sin número
            "        ",      // solo espacios
            ""               // vacía
    })
    void contrasenasInvalidasDevuelvenFalse(String contrasena) {
        assertFalse(validador.esValida(contrasena));
    }

    @ParameterizedTest(name = "\"{0}\" sí es una contraseña válida")
    @DisplayName("Varias contraseñas correctas devuelven true")
    @ValueSource(strings = {
            "Segura2026",
            "Abcdef12",
            "P4ssword",
            "UIS2026*clave"
    })
    void contrasenasValidasDevuelvenTrue(String contrasena) {
        assertTrue(validador.esValida(contrasena));
    }

    // ---------------------------------------------------------------
    // PASO 5: Prueba parametrizada con @CsvSource
    // ---------------------------------------------------------------
    @ParameterizedTest(name = "\"{0}\" debe fallar con: {1}")
    @DisplayName("Cada contraseña inválida produce su mensaje de error")
    @CsvSource({
            "Ab1,          Debe tener al menos 8 caracteres",
            "segura2026,   Debe tener al menos una mayúscula",
            "SeguraUIS,    Debe tener al menos un número",
            "abc,          Debe tener al menos 8 caracteres"
    })
    void cadaContrasenaInvalidaTieneSuMensaje(String contrasena, String mensajeEsperado) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> validador.validar(contrasena)
        );
        assertEquals(mensajeEsperado, ex.getMessage());
    }

    // esValida() no debe propagar la excepción, solo convertirla en false.
    @Test
    @DisplayName("esValida() no lanza excepción ni con null")
    void esValidaNuncaLanzaExcepcion() {
        assertDoesNotThrow(() -> validador.esValida(null));
        assertFalse(validador.esValida(null));
    }
}
