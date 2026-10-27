package co.edu.uis.taller.ej2;

/**
 * Ejercicio 2 - Validador de contraseñas.
 *
 * Una contraseña es válida si cumple TODAS estas reglas:
 *  1. No es nula.
 *  2. Tiene al menos 8 caracteres.
 *  3. Tiene al menos una letra mayúscula.
 *  4. Tiene al menos un número.
 */
public class ValidadorContrasena {

    public static final String MSG_NULA = "La contraseña no puede ser nula";
    public static final String MSG_LONGITUD = "Debe tener al menos 8 caracteres";
    public static final String MSG_MAYUSCULA = "Debe tener al menos una mayúscula";
    public static final String MSG_NUMERO = "Debe tener al menos un número";

    /**
     * Valida la contraseña.
     *
     * @return true si la contraseña es válida
     * @throws IllegalArgumentException con un mensaje que indica la PRIMERA regla incumplida
     */
    public boolean validar(String contrasena) {
        if (contrasena == null) {
            throw new IllegalArgumentException(MSG_NULA);
        }
        if (contrasena.length() < 8) {
            throw new IllegalArgumentException(MSG_LONGITUD);
        }
        if (!contrasena.chars().anyMatch(Character::isUpperCase)) {
            throw new IllegalArgumentException(MSG_MAYUSCULA);
        }
        if (!contrasena.chars().anyMatch(Character::isDigit)) {
            throw new IllegalArgumentException(MSG_NUMERO);
        }
        return true;
    }

    /**
     * Igual que validar(), pero en lugar de lanzar excepción devuelve false.
     */
    public boolean esValida(String contrasena) {
        try {
            return validar(contrasena);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
