package ar.edu.unlam.interfaz;

import ar.edu.unlam.codeBurguers.OpcionesMenu;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        imprimirMenu();
    }

    private static void imprimirMenu() {
        imprimirPortada();
        GestionHamburgueseria hamburgueseria = new GestionHamburgueseria(scanner);
        selector(hamburgueseria);
    }

    private static void selector(GestionHamburgueseria hamburgueseria) {
        OpcionesMenu opcionElegida;
        do {
            imprimirOpciones();
            int opcionIngresada = ingresarNumero();
            opcionElegida = obtenerOpcion(opcionIngresada - 1);
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

    private static OpcionesMenu obtenerOpcion(int opcion) {
        return OpcionesMenu.values()[opcion];
    }

    private static int ingresarNumero() {
        return scanner.nextInt();
    }

    private static void opcionIncorrecta() {
        System.out.println("La opción ingresada no es válida. Intenete nuevamente.");
    }
}
