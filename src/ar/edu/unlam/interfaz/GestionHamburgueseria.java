package ar.edu.unlam.interfaz;

import ar.edu.unlam.codeBurguers.OpcionesPedido;
import ar.edu.unlam.codeBurguers.Pedido;
import ar.edu.unlam.codeBurguers.StockDeProductos;
import ar.edu.unlam.codeBurguers.TipoHamburguesa;

import java.util.Scanner;

public class GestionHamburgueseria {
    private static final long cantidadBase = 5L;
    private static final long precioBase = 10L;
    private final Scanner scanner;
    private StockDeProductos[] stocks;

    public GestionHamburgueseria(Scanner scanner) {
        this.scanner = scanner;
        inicializarStock();
    }

    private void inicializarStock() {
        this.stocks = new StockDeProductos[TipoHamburguesa.values().length];
        for (int i = 0; i < TipoHamburguesa.values().length; i++) {
            this.stocks[i] = new StockDeProductos(
                    TipoHamburguesa.values()[i],
                    TipoHamburguesa.values()[i].toString(),
                    cantidadBase,
                    precioBase
            );
        }
    }

    public void imprimirStockActual() {
        System.out.println("🍔STOCK ACTUAL🍔");
        for (StockDeProductos stock : stocks) {
            System.out.println(stock);
            System.out.println("---------------------------");
        }
        System.out.println("🍔🍔🍔");
    }

    public void completarPedido() {
        Pedido pedido = new Pedido(this.stocks);
        OpcionesPedido opcionElegida;
        do {
            opcionElegida = obtenerOpcionesPedido();
            switch (opcionElegida) {
                case AGREGAR_PRODUCTO:
                    pedirYAgregarProducto(pedido);
                    break;
                case FINALIZAR_PEDIDO:
                    finalizarPedido(pedido);
                    break;
                case SALIR:
            }
        } while (opcionElegida != OpcionesPedido.SALIR);
    }

    private void finalizarPedido(Pedido pedido) {
        pedido.imprimirPedido();
    }

    private OpcionesPedido obtenerOpcionesPedido() {
        int opcion;
        do {
            System.out.println("Elija una opción: ");
            for (int i = 0; i < OpcionesPedido.values().length; i++) {
                System.out.println("Opcion " + (i + 1) + ": " + OpcionesPedido.values()[i].toString());
            }
            opcion = Character.getNumericValue(scanner.next().charAt(0));
        } while (opcion > OpcionesPedido.values().length || opcion == 0);
        return OpcionesPedido.values()[opcion - 1];
    }

    private void pedirYAgregarProducto(Pedido pedido) {
        TipoHamburguesa tipoHamburguesa = this.obtenerTipoDeHamburguesa();
        int cantidad = this.obtenerCantidad();
        pedido.agregarProducto(tipoHamburguesa, cantidad);
    }

    private int obtenerCantidad() {
        System.out.println("Ingrese cantidad: ");
        return scanner.nextInt();
    }

    private TipoHamburguesa obtenerTipoDeHamburguesa() {
        int codigo;
        do {
            System.out.print("Ingrese el codigo de producto: ");
            codigo = Character.getNumericValue(scanner.next().toLowerCase().charAt(0));
        } while (codigo > TipoHamburguesa.values().length || codigo == 0);

        return TipoHamburguesa.values()[codigo - 1];
    }

}
