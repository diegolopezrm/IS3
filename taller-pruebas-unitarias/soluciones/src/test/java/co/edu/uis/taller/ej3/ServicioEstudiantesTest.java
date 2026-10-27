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
 * SOLUCIÓN - Ejercicio 3 (incluye la prueba extra del Ejercicio 6)
 */
class ServicioEstudiantesTest {

    private RepositorioEstudiantes repositorio;
    private ServicioEstudiantes servicio;

    @BeforeEach
    void setUp() {
        repositorio = mock(RepositorioEstudiantes.class);
        servicio = new ServicioEstudiantes(repositorio);
    }

    @Test
    @DisplayName("Promedio 4.2 está en estado NORMAL")
    void estudianteConBuenPromedioEsNormal() {
        Estudiante ana = new Estudiante("2201234", "Ana", 4.2);
        when(repositorio.buscarPorCodigo("2201234")).thenReturn(Optional.of(ana));

        String estado = servicio.consultarEstado("2201234");

        assertEquals(ServicioEstudiantes.NORMAL, estado);
    }

    // ----- PASO 4 -----
    @Test
    @DisplayName("Promedio 2.5 está en BAJO RENDIMIENTO")
    void estudianteConPromedioBajo() {
        Estudiante luis = new Estudiante("2205678", "Luis", 2.5);
        when(repositorio.buscarPorCodigo("2205678")).thenReturn(Optional.of(luis));

        String estado = servicio.consultarEstado("2205678");

        assertEquals(ServicioEstudiantes.BAJO_RENDIMIENTO, estado);
    }

    // ----- PASO 5 -----
    @Test
    @DisplayName("Estudiante inexistente lanza excepción")
    void estudianteInexistenteLanzaExcepcion() {
        when(repositorio.buscarPorCodigo("0000000")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> servicio.consultarEstado("0000000"));
    }

    // ----- PASO 6 -----
    @Test
    @DisplayName("El promedio general de 3.0, 4.0 y 5.0 es 4.0")
    void promedioGeneralDeTresEstudiantes() {
        when(repositorio.listarTodos()).thenReturn(List.of(
                new Estudiante("1", "Ana", 3.0),
                new Estudiante("2", "Luis", 4.0),
                new Estudiante("3", "Sofía", 5.0)
        ));

        double promedio = servicio.promedioGeneral();

        assertEquals(4.0, promedio, 0.001);
    }

    // ----- PASO 7a -----
    @Test
    @DisplayName("Registrar un estudiante válido lo guarda")
    void registrarGuardaEnRepositorio() {
        Estudiante nuevo = new Estudiante("2209999", "Camila", 0.0);

        servicio.registrar(nuevo);

        verify(repositorio).guardar(nuevo);                 // se llamó exactamente 1 vez
        verify(repositorio, times(1)).guardar(nuevo);       // forma equivalente y explícita
    }

    // ----- PASO 7b -----
    @Test
    @DisplayName("Código vacío: lanza excepción y no guarda")
    void registrarSinCodigoNoGuarda() {
        Estudiante invalido = new Estudiante("", "Sin código", 0.0);

        assertThrows(IllegalArgumentException.class, () -> servicio.registrar(invalido));

        verifyNoInteractions(repositorio);
    }

    // ----- EJERCICIO 6: prueba agregada tras revisar el reporte de cobertura -----
    @Test
    @DisplayName("Si no hay estudiantes, el promedio general es 0.0")
    void promedioGeneralSinEstudiantesEsCero() {
        when(repositorio.listarTodos()).thenReturn(List.of());

        assertEquals(0.0, servicio.promedioGeneral(), 0.001);
    }
}
