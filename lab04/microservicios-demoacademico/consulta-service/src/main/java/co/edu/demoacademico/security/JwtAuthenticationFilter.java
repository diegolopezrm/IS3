package co.edu.demoacademico.security;

import co.edu.demoacademico.service.TokenValidationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Intercepta las peticiones a /api/estudiantes y exige un JWT válido.
 *
 * El filtro no sabe validar tokens por su cuenta: delega en auth-service a
 * través de TokenValidationService. Centralizar aquí la comprobación evita
 * repetirla en cada endpoint del controlador.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String RUTA_PROTEGIDA = "/api/estudiantes";

    private final TokenValidationService tokenValidationService;

    public JwtAuthenticationFilter(TokenValidationService tokenValidationService) {
        this.tokenValidationService = tokenValidationService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        if (path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/h2-console")) {
            return true;
        }

        // El alta de estudiantes queda fuera del filtro para poder cargar datos
        // sin token, tal como lo pide el enunciado.
        return "POST".equalsIgnoreCase(request.getMethod()) && RUTA_PROTEGIDA.equals(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        if (!path.startsWith(RUTA_PROTEGIDA)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = TokenUtils.normalize(request.getHeader("Authorization"));

        if (token == null) {
            rechazar(response, "Token no enviado o formato inválido");
            return;
        }

        if (!tokenValidationService.isValid(token)) {
            rechazar(response, "Token inválido o expirado");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void rechazar(HttpServletResponse response, String mensaje) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(mensaje);
    }
}
