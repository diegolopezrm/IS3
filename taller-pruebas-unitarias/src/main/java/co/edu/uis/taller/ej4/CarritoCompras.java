package co.edu.uis.taller.ej4;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejercicio 4 - Carrito construido con TDD.
 *
 * Esta es la versión después del refactor final: los contadores sueltos se
 * reemplazaron por una lista de ítems y los números mágicos por constantes.
 * Las 10 pruebas siguieron en verde sin tocar ni una, que es la señal de que
 * el refactor cambió el diseño y no el comportamiento.
 */
public class CarritoCompras {

    /** Por encima de este total se aplica descuento. Es un "mayor que", no un "mayor o igual". */
    public static final long UMBRAL_DESCUENTO = 100_000;
    public static final int PORCENTAJE_DESCUENTO = 10;

    private record Item(String nombre, long precioUnitario, int cantidad) {
        long subtotal() {
            return precioUnitario * cantidad;
        }
    }

    private final List<Item> items = new ArrayList<>();

    public void agregar(String nombre, long precioUnitario, int cantidad) {
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }
        items.add(new Item(nombre, precioUnitario, cantidad));
    }

    public int cantidadProductos() {
        return items.size();
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
