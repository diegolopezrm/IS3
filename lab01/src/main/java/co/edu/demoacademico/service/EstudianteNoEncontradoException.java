package co.edu.demoacademico.service;

/**
 * CAPA: LÓGICA DE NEGOCIO (excepción de negocio).
 *
 * Se lanza cuando la búsqueda por email no encuentra ningún estudiante.
 * El GlobalExceptionHandler la traduce a una respuesta HTTP 404 Not Found.
 */
public class EstudianteNoEncontradoException extends RuntimeException {

    public EstudianteNoEncontradoException(String email) {
        super("No se encontró un estudiante con el email: " + email);
    }
}
