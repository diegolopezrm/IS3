package co.edu.demoacademico.controller;

import co.edu.demoacademico.model.Estudiante;
import co.edu.demoacademico.service.EstudianteService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService service;

    public EstudianteController(EstudianteService service) {
        this.service = service;
    }

    @PostMapping
    public Estudiante crear(@Valid @RequestBody Estudiante estudiante) {
        return service.crear(estudiante);
    }

    @GetMapping
    public List<Estudiante> listar() {
        return service.listar();
    }

    // Ejemplo: /api/estudiantes/pagina?page=0&size=5&sort=nombre,asc
    @GetMapping("/pagina")
    public Page<Estudiante> listarPaginado(@ParameterObject Pageable pageable) {
        return service.listar(pageable);
    }

    // Ejemplo: /api/estudiantes/buscar?email=ana@demo.com
    @GetMapping("/buscar")
    public Estudiante buscarPorEmail(@RequestParam String email) {
        return service.buscarPorEmail(email);
    }
}
