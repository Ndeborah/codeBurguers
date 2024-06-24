package ar.edu.unlam.interfaz;

import ar.edu.unlam.codeBurguers.*;

import java.util.Scanner;

public class GestionHamburgueseria {
    private static final long cantidadBase = 5L;
    private static final long precioBase = 10L;
    private final Scanner scanner;
    private ProductStock[] stocks;
    private double totalDeCostoDelPedido=0;

    public GestionHamburgueseria(Scanner scanner) {
        this.scanner = scanner;
        inicializarStock();
    }

    private void inicializarStock() {
        this.stocks = new ProductStock[TipoHamburguesa.values().length];
        for (int i = 0; i < TipoHamburguesa.values().length; i++) {
            this.stocks[i] = new ProductStock(
                    TipoHamburguesa.values()[i],
                    TipoHamburguesa.values()[i].toString(),
                    cantidadBase,
                    precioBase
            );
        }
    }

    public void imprimirStockActual() {
        System.out.println("🍔STOCK ACTUAL🍔");
        for (ProductStock stock : stocks) {
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
                	System.out.println("\n");
                    finalizarPedido(pedido);
                    System.out.println("\n");
                	pagarPedido(pedido);
                    break;
                case ENVIO_A_DOMICILIO:
                	costoDeEnvio(pedido);
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
    
    private FormasDePago obtenerTipoDePago() {
        int opcion;
        do {
            System.out.print("Ingrese la opcion de pago: \n");
            for (int i = 0; i < FormasDePago.values().length; i++) {
                System.out.println("Opcion " + (i + 1) + ": " + FormasDePago.values()[i].toString());
            }
            opcion = Character.getNumericValue(scanner.next().toLowerCase().charAt(0));
        } while (opcion > FormasDePago.values().length || opcion == 0);

        return FormasDePago.values()[opcion- 1];
    }
    private void pagarPedido(Pedido pedido) {
    	FormasDePago formaDePago = this.obtenerTipoDePago();
        double totalAPagar = pedido.pagarPedido(formaDePago);
        this.totalDeCostoDelPedido = totalAPagar;
        System.out.println("Descuento de "+ (pedido.obtenerTotal()-totalAPagar) + " pesos\n");
        System.out.println("Pago total con descuento :" + totalAPagar + "\n");
        
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
    
    private void costoDeEnvio(Pedido pedido) {
    	ZonasDeEnvio precioPorZona = this.zonasDeEnvio();
        double totalAPagar = (pedido.costoPorZona(precioPorZona) + this.totalDeCostoDelPedido);
        System.out.println("Valor del envio a la zona: "+ pedido.costoPorZona(precioPorZona));
        System.out.println("\nPrecio total con el envio :" + totalAPagar + "\n");
        
    }
    
}
