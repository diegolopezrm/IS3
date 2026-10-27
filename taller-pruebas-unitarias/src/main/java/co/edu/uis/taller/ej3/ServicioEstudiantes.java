package co.edu.uis.taller.ej3;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Ejercicio 3 - Lógica de negocio sobre estudiantes.
 * Depende de un RepositorioEstudiantes que recibe por el constructor
 * (inyección de dependencias): eso es lo que nos permite usar un mock.
 */
public class ServicioEstudiantes {

    public static final String NORMAL = "NORMAL";
    public static final String BAJO_RENDIMIENTO = "BAJO RENDIMIENTO";

    private final RepositorioEstudiantes repositorio;

    public ServicioEstudiantes(RepositorioEstudiantes repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Devuelve "NORMAL" si el promedio es >= 3.0, o "BAJO RENDIMIENTO" en otro caso.
     *
     * @throws NoSuchElementException si el estudiante no existe
     */
    public String consultarEstado(String codigo) {
        Estudiante estudiante = repositorio.buscarPorCodigo(codigo)
                .orElseThrow(() -> new NoSuchElementException("Estudiante no encontrado: " + codigo));
        return estudiante.promedio() >= 3.0 ? NORMAL : BAJO_RENDIMIENTO;
    }

    /**
     * Promedio de todos los estudiantes. Si no hay estudiantes devuelve 0.0
     */
    public double promedioGeneral() {
        List<Estudiante> todos = repositorio.listarTodos();
        if (todos.isEmpty()) {
            return 0.0;
        }
        double suma = 0;
        for (Estudiante e : todos) {
            suma += e.promedio();
        }
        return suma / todos.size();
    }

    /**
     * Registra un estudiante nuevo.
     *
     * @throws IllegalArgumentException si el código está vacío (y en ese caso NO guarda nada)
     */
    public void registrar(Estudiante estudiante) {
        if (estudiante.codigo() == null || estudiante.codigo().isBlank()) {
            throw new IllegalArgumentException("El código es obligatorio");
        }
        repositorio.guardar(estudiante);
    }
}
