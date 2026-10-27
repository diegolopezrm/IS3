package co.edu.uis.taller.ej3;

import java.util.List;
import java.util.Optional;

/**
 * Ejercicio 3 - Acceso a los datos de estudiantes.
 * En producción esto consulta una base de datos; en las pruebas lo SIMULAMOS con Mockito.
 */
public interface RepositorioEstudiantes {

    Optional<Estudiante> buscarPorCodigo(String codigo);

    List<Estudiante> listarTodos();

    void guardar(Estudiante estudiante);
}
