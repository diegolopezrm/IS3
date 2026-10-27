# Taller de Pruebas Unitarias en Java — UIS

Proyecto Maven con **JUnit 5**, **Mockito** y **JaCoCo**.
Funciona con **Apache NetBeans** e **IntelliJ IDEA** (JDK 17 o superior).

## Cómo abrirlo

**IntelliJ IDEA:** `File > Open...` → selecciona la carpeta `taller-pruebas-unitarias` → `Trust Project`.
Espera a que termine la barra de progreso inferior (descarga de dependencias).

**NetBeans:** `File > Open Project...` (Ctrl+Shift+O) → selecciona la carpeta `taller-pruebas-unitarias`
(tiene el ícono de Maven) → `Open Project`. Si aparece "Resolve Problems", haz clic en `Resolve`/`Priming Build`.

## Cómo ejecutar TODAS las pruebas

| IntelliJ IDEA | NetBeans | Terminal |
|---|---|---|
| Clic derecho en `src/test/java` → `Run 'All Tests'` | Clic derecho en el proyecto → `Test` (Alt+F6) | `mvn test` |

## Estructura

```
src/main/java/co/edu/uis/taller/
  ej0/  Saludo                  -> verificar el entorno
  ej1/  CalculadoraNotas        -> patrón AAA, aserciones, valores límite (¡tiene un bug!)
  ej2/  ValidadorContrasena     -> excepciones y pruebas parametrizadas
  ej3/  ServicioEstudiantes     -> mocks con Mockito
  ej4/  CarritoCompras          -> TDD (clase vacía, la construyes tú)
  ej5/  Estadisticas            -> reto: 3 bugs escondidos
src/test/java/...               -> pruebas con marcas // TODO para completar
soluciones/                     -> ¡no mires hasta terminar cada ejercicio!
```

## Cobertura (Ejercicio 6)

Después de ejecutar las pruebas con Maven (`mvn test` o `Test` en NetBeans), abre:
`target/site/jacoco/index.html` en tu navegador.
En IntelliJ también puedes usar `Run 'All Tests' with Coverage`.

## Nota

Durante la primera apertura el IDE descarga JUnit, Mockito y JaCoCo desde Internet.
Si estás en una red sin acceso, pide al docente la carpeta `.m2` precargada.
