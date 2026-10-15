package co.edu.demoacademico.estudiantes.port;

import co.edu.demoacademico.estudiantes.model.Estudiante;

/**
 * Contrato público del módulo estudiantes.
 *
 * Es lo único que otros módulos pueden usar para consultar un estudiante.
 * El repositorio del módulo queda como detalle interno: nadie fuera de
 * 'estudiantes' lo importa.
 */
public interface EstudianteQueryPort {
    Estudiante obtenerPorId(Long id);
}
