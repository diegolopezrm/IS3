package co.edu.demoacademico.client;

import co.edu.demoacademico.dto.TokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Cliente declarativo hacia auth-service.
 *
 * La URL sale de services.auth.url en application.yaml, así que cambiar de
 * localhost a un nombre de servicio de Docker no obliga a tocar el código.
 */
@FeignClient(name = "authClient", url = "${services.auth.url}")
public interface AuthFeignClient {

    @GetMapping("/auth/validate")
    TokenValidationResponse validate(@RequestParam("token") String token);
}
