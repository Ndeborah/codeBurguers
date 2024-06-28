package ar.edu.unlam.codeBurguers.interfaz;

import ar.edu.unlam.codeBurguers.dominio.*;
import ar.edu.unlam.codeBurguers.dominio.enums.FormasDePago;
import ar.edu.unlam.codeBurguers.interfaz.enums.OpcionesPedido;
import ar.edu.unlam.codeBurguers.dominio.enums.TipoHamburguesa;
import ar.edu.unlam.codeBurguers.dominio.enums.ZonasDeEnvio;

import java.util.Scanner;

import static ar.edu.unlam.codeBurguers.dominio.Colores.ANSI_GREEN;
import static ar.edu.unlam.codeBurguers.dominio.Colores.ANSI_RESET;

public class GestionHamburgueseria {

    private final Scanner scanner;
    private final ControlDeStock controlDeStock;

    public GestionHamburgueseria(Scanner scanner, ControlDeStock controlDeStock) {
        this.scanner = scanner;
        this.controlDeStock = controlDeStock;
    }

    public void imprimirStockActual() {
        System.out.println("🍔STOCK ACTUAL🍔\n");
        for (ItemDeStock stock : this.controlDeStock.getStockOrdenado()) {
            System.out.println(stock);
            System.out.println("------------------------------------------------------");
        }
        System.out.println("🍔🍔🍔");
    }

    public void completarPedido() {
        Pedido pedido = new Pedido(this.controlDeStock);
        OpcionesPedido opcionElegida;
        do {
            opcionElegida = obtenerOpcionesPedido();
            switch (opcionElegida) {
                case AGREGAR_PRODUCTO:
                    pedirYAgregarProducto(pedido);
                    System.out.println(pedido);
                    break;
                case FINALIZAR_PEDIDO:
                    opcionDeEnvio(pedido);
                    System.out.println(pedido);
                    pagarPedido(pedido);
                    System.out.println(pedido);
                    System.out.println("Pedido finalizado exitosamente! 💸🍔");
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
            opcion = Herramientas.getCodigoDePantalla();
        } while (opcion > OpcionesPedido.values().length || opcion == 0);
        return OpcionesPedido.values()[opcion - 1];
    }

    private void pedirYAgregarProducto(Pedido pedido) {
        TipoHamburguesa tipoHamburguesa = this.obtenerTipoDeHamburguesa();
        int cantidad = this.obtenerCantidad();
        ResultadoAccion resultado = pedido.agregarProducto(tipoHamburguesa, cantidad);
        System.out.println(resultado.getMensaje());
    }

    private int obtenerCantidad() {
        System.out.println("Ingresar: ");
        return scanner.nextInt();
    }

    private TipoHamburguesa obtenerTipoDeHamburguesa() {
        int codigo;
        do {
            System.out.print("Ingrese el codigo de producto: ");
            codigo = Herramientas.getCodigoDePantalla();
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
            opcion = Herramientas.getCodigoDePantalla();
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
            opcion = Herramientas.getCodigoDePantalla();;
        } while (opcion > FormasDePago.values().length || opcion == 0);

        return FormasDePago.values()[opcion - 1];
    }

    private void pagarPedido(Pedido pedido) {
        FormasDePago formasDePago = obtenerTipoDePago();
        pedido.pagarPedido(formasDePago);
    }

    public void modificarProductos() {
        char opcion;

        System.out.println("¿Qué modificación desea realizar?");
        System.out.println("P. para cambiar precios");
        System.out.println("C. para agregar cantidad");
        do {
            opcion = scanner.next().toLowerCase().charAt(0);
            switch (opcion) {
                case 'p':
                    cambiarPrecio();
                    System.out.println(ANSI_GREEN + "¡El precio se modificó con exito!" + ANSI_RESET);
                    return;
                case 'c':
                    agregarCantidadAStock();
                    System.out.println(ANSI_GREEN + "¡La cantidad se modificó con exito!" + ANSI_RESET);
                    return;
                default:
                    System.out.println("La opción ingresada es incorrecta, intente nuevamente.");
            }
        } while (true);
    }

    public void agregarCantidadAStock() {
        TipoHamburguesa tipo = this.obtenerTipoDeHamburguesa();
        long cant = obtenerCantidad();
        long nuevaCantidad = this.controlDeStock.agregarCantidadAUnProducto(tipo, cant);
        System.out.println("Cantidad añadida correctamente, la cantidad actual es de: " + nuevaCantidad);
    }

    public void cambiarPrecio() {
        TipoHamburguesa tipo = this.obtenerTipoDeHamburguesa();
        long precioNuevo = obtenerCantidad();
        if (this.controlDeStock.cambiarPrecioDeUnProducto(tipo, precioNuevo)) {
            System.out.println("El precio se ha agregado correctamente: \n Precio nuevo: " + precioNuevo);
        } else {
            System.out.println("El precio no se pudo modificar, producto no encontrado");
        }
    }
}
