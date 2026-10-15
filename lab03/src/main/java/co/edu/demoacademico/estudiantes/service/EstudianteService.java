package co.edu.demoacademico.estudiantes.service;

import co.edu.demoacademico.estudiantes.model.Estudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstudianteService {

    Estudiante crear(Estudiante e);

    Estudiante obtenerPorId(Long id);

    Page<Estudiante> listar(Pageable pageable);

    Estudiante actualizar(Long id, Estudiante e);

    void eliminar(Long id);
}
