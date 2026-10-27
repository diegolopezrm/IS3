package co.edu.uis.taller.ej4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SOLUCIÓN - Ejercicio 4
 */
class CarritoComprasTest {

    private CarritoCompras carrito;

    // Agregado en un REFACTOR de las pruebas: todas creaban un carrito nuevo
    @BeforeEach
    void setUp() {
        carrito = new CarritoCompras();
    }

    // CICLO 1
    @Test
    @DisplayName("Un carrito nuevo está vacío y su total es 0")
    void carritoNuevoEstaVacio() {
        assertEquals(0, carrito.cantidadProductos());
        assertEquals(0, carrito.total());
    }

    // CICLO 2
    @Test
    @DisplayName("Agregar 2 cuadernos de $5.000 da un total de $10.000")
    void agregarUnProductoCalculaElTotal() {
        carrito.agregar("Cuaderno", 5_000, 2);

        assertEquals(1, carrito.cantidadProductos());
        assertEquals(10_000, carrito.total());
    }

    // CICLO 3
    @Test
    @DisplayName("El total es la suma de los subtotales")
    void agregarVariosProductosSumaLosSubtotales() {
        carrito.agregar("Cuaderno", 5_000, 2);   // 10.000
        carrito.agregar("Lápiz", 1_500, 4);      //  6.000

        assertEquals(2, carrito.cantidadProductos());
        assertEquals(16_000, carrito.total());
    }

    // CICLO 4
    @Test
    @DisplayName("Un precio negativo lanza IllegalArgumentException")
    void precioNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> carrito.agregar("Error", -100, 1));
    }

    @Test
    @DisplayName("Una cantidad de 0 lanza IllegalArgumentException")
    void cantidadCeroLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> carrito.agregar("Error", 1_000, 0));
    }

    // CICLO 5
    @Test
    @DisplayName("$120.000 con 10% de descuento es $108.000")
    void totalMayorA100milTieneDescuento() {
        carrito.agregar("Calculadora científica", 120_000, 1);

        assertEquals(108_000, carrito.total());
    }

    @Test
    @DisplayName("Límite: un total de $100.000 NO tiene descuento")
    void totalDeExactamente100milNoTieneDescuento() {
        carrito.agregar("Libro", 50_000, 2);

        assertEquals(100_000, carrito.total());
    }
}
