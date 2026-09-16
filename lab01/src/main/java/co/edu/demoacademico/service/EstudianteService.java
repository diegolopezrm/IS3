package co.edu.demoacademico.service;

import co.edu.demoacademico.model.Estudiante;
import co.edu.demoacademico.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CAPA: LÓGICA DE NEGOCIO.
 *
 * Concentra las reglas del módulo Estudiantes (email único) y coordina el
 * acceso a la BD a través del Repository. No conoce HTTP: no maneja códigos
 * de estado ni cuerpos de respuesta, solo objetos de dominio y excepciones.
 */
@Service
public class EstudianteService {

    // Dependencia hacia la capa de datos, inyectada por constructor.
    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    public Estudiante crear(Estudiante estudiante) {

        // ----------------------------------------
        // ZONA DE LÓGICA DE NEGOCIO: email único
        // ----------------------------------------
        if (repository.existsByEmail(estudiante.getEmail())) {
            throw new EmailDuplicadoException(estudiante.getEmail());
        }

        // ============================
        // ZONA DE ACCESO A LA BD:
        // persistencia vía Repository
        // ============================
        return repository.save(estudiante);
    }

    public List<Estudiante> listar() {
        // ============================
        // ZONA DE ACCESO A LA BD:
        // consulta vía Repository
        // ============================
        return repository.findAll();
    }

    public Estudiante buscarPorEmail(String email) {
        // ============================
        // ZONA DE ACCESO A LA BD:
        // consulta derivada findByEmail
        // ============================
        return repository.findByEmail(email)
                .orElseThrow(() -> new EstudianteNoEncontradoException(email));
    }
}
