package ar.edu.unlam.codeBurguers.interfaz;

import ar.edu.unlam.codeBurguers.dominio.*;
import ar.edu.unlam.codeBurguers.enums.FormasDePago;
import ar.edu.unlam.codeBurguers.enums.OpcionesPedido;
import ar.edu.unlam.codeBurguers.enums.TipoHamburguesa;
import ar.edu.unlam.codeBurguers.enums.ZonasDeEnvio;

import java.util.Scanner;

public class GestionHamburgueseria {
    private static long cantidadBase = 10;
    private static long precioBase = 1000;
    private final Scanner scanner;
    private StockDeProductos[] stocks;

    public GestionHamburgueseria(Scanner scanner) {
        this.scanner = scanner;
        inicializarStock();
    }

    public static void setCantidadBase(long cantidadBase) {
        GestionHamburgueseria.cantidadBase = cantidadBase;
    }

    public static void setPrecioBase(long precioBase) {
        GestionHamburgueseria.precioBase = precioBase;
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
        ordenarProductosPorStockMayorAMenor(stocks);
        System.out.println("🍔STOCK ACTUAL🍔\n");
        for (StockDeProductos stock : stocks) {
            System.out.println(stock);
            System.out.println("------------------------------------------------------");
        }
        System.out.println("🍔🍔🍔");
    }

    public StockDeProductos[] ordenarProductosPorStockMayorAMenor(StockDeProductos[] stocks) {

        StockDeProductos auxiliar=null;

        for (int i = 0; i < stocks.length; i++) {
            for (int j = 0; j < stocks.length - (i + 1); j++) {
                if (stocks[j] != null && stocks[j + 1] != null && stocks[j].getStock() < stocks[j + 1].getStock()) {
                    auxiliar = stocks[j];
                    stocks[j] = stocks[j + 1];
                    stocks[j + 1] = auxiliar;
                }
            }
        }return stocks;
    }

    public void completarPedido() {
        Pedido pedido = new Pedido(this.stocks);
        OpcionesPedido opcionElegida;
        do {
            opcionElegida = obtenerOpcionesPedido();
            switch (opcionElegida) {
                case AGREGAR_PRODUCTO:
                    pedirYAgregarProducto(pedido);
                    pedido.actualizarCosto();
                    System.out.println(pedido);
                    break;
                case FINALIZAR_PEDIDO:
                    opcionDeEnvio(pedido);
                    System.out.println(pedido);
                    pagarPedido(pedido);
                    System.out.println(pedido);
                    return;
                case SALIR:
            }
        } while (opcionElegida != OpcionesPedido.SALIR);
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


    private ZonasDeEnvio zonasDeEnvio() {
        int opcion;
        do {
            System.out.println("Elija su lugar de envio: ");
            for (int i = 0; i < ZonasDeEnvio.values().length; i++) {
                System.out.println("Opcion " + (i + 1) + ": " + ZonasDeEnvio.values()[i].toString());
            }
            opcion = Character.getNumericValue(scanner.next().charAt(0));
        } while (opcion > ZonasDeEnvio.values().length || opcion == 0);
        return ZonasDeEnvio.values()[opcion - 1];
    }

    public void opcionDeEnvio(Pedido pedido) {
        char opcion;

        System.out.println("¿Desea realizar envio a domicilio? S / N");
        do {
            opcion = scanner.next().toLowerCase().charAt(0);
            switch (opcion) {
                case 's':
                    pedido.realizarEnvio(zonasDeEnvio());
                    return;
                case 'n':
                    return;
                default:
                    System.out.println("La opción ingresada es incorrecta, intente nuevamente.");
            }
        } while (true);
    }

    private FormasDePago obtenerTipoDePago() {
        int opcion;
        do {
            System.out.print("Ingrese la opcion de pago: \n");
            for (int i = 0; i < FormasDePago.values().length; i++) {
                System.out.println("Opcion " + (i + 1) + ": " + FormasDePago.values()[i].toString());
            }
            opcion = Character.getNumericValue(scanner.next().toLowerCase().charAt(0));
        } while (opcion > FormasDePago.values().length || opcion == 0);

        return FormasDePago.values()[opcion - 1];
    }

    private void pagarPedido(Pedido pedido) {
        FormasDePago formasDePago = obtenerTipoDePago();
        pedido.pagarPedido(formasDePago);
    }

    private StockDeProductos obtenerProductoStockSegunElTipo(TipoHamburguesa tipoHamburguesa) {
        for (StockDeProductos prod : this.stocks) {
            if (prod.getTipoHamburguesa().equals(tipoHamburguesa)) {
                return prod;
            }
        }

        return null;
    }
    public void modificarProductos(){
        char opcion;

        System.out.println("¿Qué modificación desea realizar?");
        System.out.println("P. para cambiar precios");
        System.out.println("C. para agregar cantidad");
        do {
            opcion = scanner.next().toLowerCase().charAt(0);
            switch (opcion) {
                case 'p':
                    cambiarPrecio();
                    System.out.println(ANSI_GREEN + "¡El precio se modificó con exito!" +ANSI_RESET );
                    return;
                case 'c':
                    agregarCantidadAStock();
                    System.out.println(ANSI_GREEN + "¡La cantidad se modificó con exito!" +ANSI_RESET );
                    return;
                default:
                    System.out.println("La opción ingresada es incorrecta, intente nuevamente.");
            }
        } while (true);
    }
    public void agregarCantidadAStock() {
        TipoHamburguesa tipo = this.obtenerTipoDeHamburguesa();
        long cant = obtenerCantidad();
        StockDeProductos productoStock = this.obtenerProductoStockSegunElTipo(tipo);
        if (productoStock == null) {
            System.out.println("No se puede agregar el producto a stock.");
        } else {
            productoStock.incrementarStock(cant);
        }
    }
    public void cambiarPrecio() {
        TipoHamburguesa tipo = this.obtenerTipoDeHamburguesa();
        long precioNuevo = obtenerCantidad();
        StockDeProductos productoStock = this.obtenerProductoStockSegunElTipo(tipo);
        if (productoStock == null) {
            System.out.println("No se encuentra el producto en el stock");
        } else {
            productoStock.setPrecio(precioNuevo);
        }
    }
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
}
