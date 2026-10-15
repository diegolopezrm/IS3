package co.edu.demoacademico.grupos.port;

import co.edu.demoacademico.grupos.model.Grupo;

/**
 * Contrato público del módulo grupos.
 *
 * Igual que EstudianteQueryPort: expone la consulta que otros módulos
 * necesitan sin exponer GrupoRepository.
 */
public interface GrupoQueryPort {
    Grupo obtenerPorId(Long id);
}
