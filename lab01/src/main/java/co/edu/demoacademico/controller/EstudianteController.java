package co.edu.demoacademico.controller;

import co.edu.demoacademico.model.Estudiante;
import co.edu.demoacademico.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CAPA: PRESENTACIÓN (API REST).
 *
 * Expone los endpoints HTTP y delega toda la lógica en el Service. Con @Valid
 * dispara las validaciones declaradas en la entidad antes de llamar al negocio.
 *
 * Flujo: HTTP -> Controller -> Service -> Repository -> H2
 */
@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService service;

    public EstudianteController(EstudianteService service) {
        this.service = service;
    }

    // POST /api/estudiantes
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estudiante crear(@Valid @RequestBody Estudiante estudiante) {
        return service.crear(estudiante);
    }

    // GET /api/estudiantes
    @GetMapping
    public List<Estudiante> listar() {
        return service.listar();
    }

    // GET /api/estudiantes/buscar?email=ana@demo.com
    @GetMapping("/buscar")
    public Estudiante buscarPorEmail(@RequestParam String email) {
        return service.buscarPorEmail(email);
    }
}
