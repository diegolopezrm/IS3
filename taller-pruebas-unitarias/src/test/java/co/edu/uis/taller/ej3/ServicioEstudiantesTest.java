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
    // PASO 1: Por qué hacen falta los mocks.
    // Con la implementación real no se puede probar nada sin base de datos.
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Con el repositorio real haría falta la base de datos institucional")
    void conElRepositorioRealNoSePuedeProbar() {
        ServicioEstudiantes servicioReal = new ServicioEstudiantes(new RepositorioEstudiantesBD());

        UnsupportedOperationException ex = assertThrows(
                UnsupportedOperationException.class,
                () -> servicioReal.consultarEstado("2201234")
        );
        assertTrue(ex.getMessage().contains("base de datos"));
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
    // PASO 4: promedio 2.5 -> BAJO RENDIMIENTO
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Promedio 2.5 está en BAJO RENDIMIENTO")
    void estudianteConMalPromedioEsBajoRendimiento() {
        // Arrange
        Estudiante luis = new Estudiante("2209876", "Luis", 2.5);
        when(repositorio.buscarPorCodigo("2209876")).thenReturn(Optional.of(luis));

        // Act
        String estado = servicio.consultarEstado("2209876");

        // Assert
        assertEquals(ServicioEstudiantes.BAJO_RENDIMIENTO, estado);
    }

    @Test
    @DisplayName("Valor límite: un promedio de 3.0 exacto todavía es NORMAL")
    void promedioDeTresExactoEsNormal() {
        when(repositorio.buscarPorCodigo("2200000"))
                .thenReturn(Optional.of(new Estudiante("2200000", "Límite", 3.0)));

        assertEquals(ServicioEstudiantes.NORMAL, servicio.consultarEstado("2200000"));
    }

    // ---------------------------------------------------------------
    // PASO 5: estudiante inexistente -> NoSuchElementException
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Un estudiante que no existe lanza NoSuchElementException")
    void estudianteInexistenteLanzaExcepcion() {
        // Arrange: el mock responde "no lo encontré"
        when(repositorio.buscarPorCodigo("0000000")).thenReturn(Optional.empty());

        // Act
        NoSuchElementException ex = assertThrows(
                NoSuchElementException.class,
                () -> servicio.consultarEstado("0000000")
        );

        // Assert
        assertTrue(ex.getMessage().contains("0000000"),
                "El mensaje debería decir qué código no se encontró");
    }

    // ---------------------------------------------------------------
    // PASO 6: promedioGeneral() con varios estudiantes
    // ---------------------------------------------------------------
    @Test
    @DisplayName("El promedio general de 3.0, 4.0 y 5.0 es 4.0")
    void promedioGeneralDeTresEstudiantes() {
        // Arrange
        when(repositorio.listarTodos()).thenReturn(List.of(
                new Estudiante("1", "Ana", 3.0),
                new Estudiante("2", "Luis", 4.0),
                new Estudiante("3", "Sara", 5.0)
        ));

        // Act
        double promedio = servicio.promedioGeneral();

        // Assert
        assertEquals(4.0, promedio, 0.001);
    }

    // Esta prueba nació del reporte de cobertura del ejercicio 6:
    // la rama "lista vacía" de promedioGeneral() no la ejecutaba nadie.
    @Test
    @DisplayName("Si no hay estudiantes, el promedio general es 0.0")
    void promedioGeneralSinEstudiantesEsCero() {
        when(repositorio.listarTodos()).thenReturn(List.of());

        assertEquals(0.0, servicio.promedioGeneral(), 0.001);
    }

    // ---------------------------------------------------------------
    // PASO 7: VERIFICACIÓN con verify(...)
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Registrar un estudiante válido llama a guardar() una vez")
    void registrarEstudianteValidoLlamaAGuardar() {
        // Arrange
        Estudiante nuevo = new Estudiante("2211111", "Carlos", 3.8);

        // Act
        servicio.registrar(nuevo);

        // Assert: no miramos un valor de retorno, miramos que la colaboración ocurrió
        verify(repositorio, times(1)).guardar(nuevo);
    }

    @Test
    @DisplayName("Registrar con código vacío lanza excepción y NO guarda nada")
    void registrarConCodigoVacioNoGuarda() {
        // Arrange
        Estudiante invalido = new Estudiante("", "Sin código", 4.0);

        // Act
        assertThrows(IllegalArgumentException.class, () -> servicio.registrar(invalido));

        // Assert: lo importante es que el repositorio quedó intacto
        verifyNoInteractions(repositorio);
    }

    // Reto extra del ejercicio 6: la otra mitad de la condición de registrar().
    @Test
    @DisplayName("Registrar con código nulo también lanza excepción y no guarda")
    void registrarConCodigoNuloNoGuarda() {
        Estudiante invalido = new Estudiante(null, "Sin código", 4.0);

        assertThrows(IllegalArgumentException.class, () -> servicio.registrar(invalido));
        verifyNoInteractions(repositorio);
    }

    @Test
    @DisplayName("Registrar con código en blanco tampoco guarda")
    void registrarConCodigoEnBlancoNoGuarda() {
        Estudiante invalido = new Estudiante("   ", "Espacios", 4.0);

        assertThrows(IllegalArgumentException.class, () -> servicio.registrar(invalido));
        verifyNoInteractions(repositorio);
    }
}
