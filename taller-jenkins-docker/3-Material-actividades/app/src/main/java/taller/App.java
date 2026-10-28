package taller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * App de ejemplo del taller: una API HTTP mínima usando solo el JDK.
 *
 *   GET /health          -> {"status":"ok"}
 *   GET /version         -> {"version":"1.0.0"}
 *   GET /sumar?a=2&b=3   -> {"resultado":5}
 */
public class App {

    /** Actividad 5: cambiar este valor, hacer push y ver la nueva versión desplegada. */
    public static final String VERSION = "1.0.0";

    public static HttpServer crearServidor(int puerto) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(puerto), 0);
        server.createContext("/health", ex -> responder(ex, 200, "{\"status\":\"ok\"}"));
        server.createContext("/version", ex -> responder(ex, 200, "{\"version\":\"" + VERSION + "\"}"));
        server.createContext("/sumar", App::sumar);
        return server;
    }

    private static void sumar(HttpExchange ex) throws IOException {
        Map<String, String> params = parametros(ex.getRequestURI().getRawQuery());
        try {
            int a = Integer.parseInt(params.get("a"));
            int b = Integer.parseInt(params.get("b"));
            responder(ex, 200, "{\"resultado\":" + Calculadora.sumar(a, b) + "}");
        } catch (NumberFormatException e) {
            responder(ex, 400, "{\"error\":\"a y b deben ser numeros enteros\"}");
        }
    }

    private static Map<String, String> parametros(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null) {
            return params;
        }
        for (String par : query.split("&")) {
            String[] kv = par.split("=", 2);
            if (kv.length == 2) {
                params.put(kv[0], kv[1]);
            }
        }
        return params;
    }

    private static void responder(HttpExchange ex, int codigo, String json) throws IOException {
        byte[] cuerpo = json.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "application/json");
        ex.sendResponseHeaders(codigo, cuerpo.length);
        try (OutputStream out = ex.getResponseBody()) {
            out.write(cuerpo);
        }
    }

    public static void main(String[] args) throws IOException {
        int puerto = Integer.parseInt(System.getenv().getOrDefault("PORT", "8000"));
        crearServidor(puerto).start();
        System.out.println("App escuchando en el puerto " + puerto);
    }
}
