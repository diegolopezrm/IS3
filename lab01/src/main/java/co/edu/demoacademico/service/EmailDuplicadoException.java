package co.edu.demoacademico.service;

/**
 * CAPA: LÓGICA DE NEGOCIO (excepción de negocio).
 *
 * Se lanza cuando se intenta crear un estudiante con un email ya registrado.
 * El GlobalExceptionHandler la traduce a una respuesta HTTP 409 Conflict.
 */
public class EmailDuplicadoException extends RuntimeException {

    public EmailDuplicadoException(String email) {
        super("Ya existe un estudiante con el email: " + email);
    }
}
