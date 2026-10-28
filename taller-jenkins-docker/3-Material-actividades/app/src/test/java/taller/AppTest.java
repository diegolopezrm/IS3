package taller;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Prueba de integración: levanta el servidor en un puerto libre y le hace peticiones. */
class AppTest {

    private static HttpServer server;
    private static String base;
    private static final HttpClient cliente = HttpClient.newHttpClient();

    @BeforeAll
    static void iniciar() throws Exception {
        server = App.crearServidor(0); // 0 = cualquier puerto libre
        server.start();
        base = "http://localhost:" + server.getAddress().getPort();
    }

    @AfterAll
    static void detener() {
        server.stop(0);
    }

    private HttpResponse<String> get(String ruta) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(base + ruta)).GET().build();
        return cliente.send(req, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void healthRespondeOk() throws Exception {
        HttpResponse<String> resp = get("/health");
        assertEquals(200, resp.statusCode());
        assertEquals("{\"status\":\"ok\"}", resp.body());
    }

    @Test
    void versionRespondeLaVersionActual() throws Exception {
        HttpResponse<String> resp = get("/version");
        assertEquals(200, resp.statusCode());
        assertEquals("{\"version\":\"" + App.VERSION + "\"}", resp.body());
    }

    @Test
    void sumarDevuelveResultado() throws Exception {
        HttpResponse<String> resp = get("/sumar?a=4&b=6");
        assertEquals(200, resp.statusCode());
        assertEquals("{\"resultado\":10}", resp.body());
    }

    @Test
    void sumarRechazaTexto() throws Exception {
        assertEquals(400, get("/sumar?a=x&b=1").statusCode());
    }
}
