package co.edu.demoacademico.repository;

import co.edu.demoacademico.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CAPA: ACCESO A DATOS  <-- ZONA DE BASE DE DATOS
 *
 * Único punto del proyecto que habla con H2. Spring Data JPA genera la
 * implementación en tiempo de ejecución a partir de la firma de los métodos
 * (query methods), por eso no hay SQL escrito a mano.
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    // ============================
    // ZONA DE ACCESO A LA BD (JPA)
    // ============================
    // SELECT * FROM estudiante WHERE email = ?
    Optional<Estudiante> findByEmail(String email);

    // SELECT COUNT(*) > 0 FROM estudiante WHERE email = ?
    boolean existsByEmail(String email);
}
