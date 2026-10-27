package co.edu.uis.taller.ej4;

import java.util.ArrayList;
import java.util.List;

/**
 * SOLUCIÓN - Ejercicio 4 (versión final, después del último REFACTOR).
 */
public class CarritoCompras {

    public static final long UMBRAL_DESCUENTO = 100_000;
    public static final int PORCENTAJE_DESCUENTO = 10;

    /** Una línea del carrito. Extraída durante un paso de REFACTOR. */
    private record Item(String nombre, long precioUnitario, int cantidad) {
        long subtotal() {
            return precioUnitario * cantidad;
        }
    }

    private final List<Item> items = new ArrayList<>();

    public int cantidadProductos() {
        return items.size();
    }

    public void agregar(String nombre, long precioUnitario, int cantidad) {
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }
        items.add(new Item(nombre, precioUnitario, cantidad));
    }

    public long total() {
        long suma = 0;
        for (Item item : items) {
            suma += item.subtotal();
        }
        if (suma > UMBRAL_DESCUENTO) {
            suma = suma * (100 - PORCENTAJE_DESCUENTO) / 100;
        }
        return suma;
    }
}
