package co.edu.uis.taller.ej3;

import java.util.List;
import java.util.Optional;

/**
 * Implementación "real" que necesitaría una base de datos.
 * Úsala en una prueba y verás por qué necesitamos mocks.
 */
public class RepositorioEstudiantesBD implements RepositorioEstudiantes {

    private static final String MENSAJE =
            "Se requiere conexión a la base de datos institucional";

    @Override
    public Optional<Estudiante> buscarPorCodigo(String codigo) {
        throw new UnsupportedOperationException(MENSAJE);
    }

    @Override
    public List<Estudiante> listarTodos() {
        throw new UnsupportedOperationException(MENSAJE);
    }

    @Override
    public void guardar(Estudiante estudiante) {
        throw new UnsupportedOperationException(MENSAJE);
    }
}
