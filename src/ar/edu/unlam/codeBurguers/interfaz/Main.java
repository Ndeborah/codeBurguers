package ar.edu.unlam.codeBurguers.interfaz;

import ar.edu.unlam.codeBurguers.dominio.ControlDeStock;
import ar.edu.unlam.codeBurguers.dominio.ItemDeStock;
import ar.edu.unlam.codeBurguers.dominio.enums.TipoHamburguesa;
import ar.edu.unlam.codeBurguers.interfaz.enums.OpcionesMenu;
import ar.edu.unlam.codeBurguers.interfaz.enums.OpcionesPedido;

import java.util.Scanner;

public class Main {
    private static long cantidadBase = 10;
    private static long precioBase = 1000;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ItemDeStock[] itemDeStocks = inicializarStock();
        ControlDeStock controlDeStock = new ControlDeStock(itemDeStocks);
        GestionHamburgueseria gestor = new GestionHamburgueseria(scanner, controlDeStock);
        imprimirMenu(gestor);
    }

    private static ItemDeStock[] inicializarStock() {
        ItemDeStock[] items = new ItemDeStock[TipoHamburguesa.values().length];
        for (int i = 0; i < TipoHamburguesa.values().length; i++) {
            items[i] = new ItemDeStock(
                    TipoHamburguesa.values()[i],
                    TipoHamburguesa.values()[i].toString(),
                    cantidadBase,
                    precioBase
            );
        }
        return items;
    }

    private static void imprimirMenu(GestionHamburgueseria gestor) {
        imprimirPortada();
        selector(gestor);
    }

    private static OpcionesMenu obtenerOpcionesMenu() {
        int opcion;
        do {
            opcion = Herramientas.getCodigoDePantalla();
        } while (opcion > OpcionesMenu.values().length || opcion <= 0);

        return OpcionesMenu.values()[opcion - 1];
    }

    private static void selector(GestionHamburgueseria hamburgueseria) {
        OpcionesMenu opcionElegida;
        do {
            imprimirOpciones();
            opcionElegida = obtenerOpcionesMenu();
            switch (opcionElegida) {
                case INGRESAR_PEDIDO:
                    hamburgueseria.imprimirStockActual();
                    hamburgueseria.completarPedido();
                    break;
                case MODIFICAR_PRODUCTOS:
                    hamburgueseria.imprimirStockActual();
                    hamburgueseria.modificarProductos();
                    break;
                case CONTROLAR_STOCK:
                    hamburgueseria.imprimirStockActual();
                    break;
                case SALIR:
                    System.out.println("Gracias por elejirnos ❤️❤️❤️");
                    break;
                default:
                    opcionIncorrecta();

            }
        } while (opcionElegida != OpcionesMenu.SALIR);
    }

    private static void imprimirPortada() {
        System.out.println("🍔🍔CODE BURGERS🍔🍔");
    }

    private static void imprimirOpciones() {
        System.out.println(
                """
                        Ingrese el número de la opción deseada:
                        1. Ingresar pedido
                        2. Modificar stock de productos
                        3. Controlar stock
                        4. Salir
                        """
        );

    }

    private static void opcionIncorrecta() {
        System.out.println("La opción ingresada no es válida. Intenete nuevamente.");
    }
}
