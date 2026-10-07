package co.edu.demoacademico.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint simple que se usa para comprobar que el contenedor responde,
 * sin depender de la base de datos ni del resto del dominio.
 */
@RestController
public class HolaController {

    @GetMapping("/hola")
    public String hola() {
        return "Hola desde Spring Boot con Docker";
    }
}
