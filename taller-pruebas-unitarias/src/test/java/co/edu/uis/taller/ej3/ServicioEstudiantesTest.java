package co.edu.uis.taller.ej3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Ejercicio 3 - Aislar dependencias con Mockito.
 * Sigue las instrucciones de la presentación paso a paso.
 */
class ServicioEstudiantesTest {

    private RepositorioEstudiantes repositorio;   // el mock
    private ServicioEstudiantes servicio;         // la clase que probamos

    @BeforeEach
    void setUp() {
        // PASO 2: creamos un objeto SIMULADO del repositorio
        repositorio = mock(RepositorioEstudiantes.class);
        // y se lo inyectamos al servicio por el constructor
        servicio = new ServicioEstudiantes(repositorio);
    }

    // ---------------------------------------------------------------
    // PASO 3: Ejemplo completo - STUBBING con when(...).thenReturn(...)
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Promedio 4.2 está en estado NORMAL")
    void estudianteConBuenPromedioEsNormal() {
        // Arrange: le "enseñamos" al mock qué responder
        Estudiante ana = new Estudiante("2201234", "Ana", 4.2);
        when(repositorio.buscarPorCodigo("2201234")).thenReturn(Optional.of(ana));

        // Act
        String estado = servicio.consultarEstado("2201234");

        // Assert
        assertEquals(ServicioEstudiantes.NORMAL, estado);
    }

    // ---------------------------------------------------------------
    // PASO 4: Estudiante con promedio 2.5 -> BAJO_RENDIMIENTO
    // ---------------------------------------------------------------
    // TODO


    // ---------------------------------------------------------------
    // PASO 5: Estudiante inexistente -> NoSuchElementException
    //         (el mock debe devolver Optional.empty())
    // ---------------------------------------------------------------
    // TODO


    // ---------------------------------------------------------------
    // PASO 6: promedioGeneral() con 3 estudiantes (listarTodos)
    // ---------------------------------------------------------------
    // TODO


    // ---------------------------------------------------------------
    // PASO 7: VERIFICACIÓN con verify(...):
    //   a) registrar() con código válido llama a repositorio.guardar(...)
    //   b) registrar() con código vacío lanza excepción y NO llama a nada
    //      (usa verifyNoInteractions)
    // ---------------------------------------------------------------
    // TODO

}
