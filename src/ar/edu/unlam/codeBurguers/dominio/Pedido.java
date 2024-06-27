package ar.edu.unlam.codeBurguers.dominio;

import ar.edu.unlam.codeBurguers.enums.FormasDePago;
import ar.edu.unlam.codeBurguers.enums.TipoHamburguesa;
import ar.edu.unlam.codeBurguers.enums.ZonasDeEnvio;

public class Pedido {
    private final static double DESCUENTO_PAGO_EFECTIVO = 0.15;
    private final static double DESCUENTO_PAGO_TARJETA_DE_CREDITO = 0.20;
    private final static double DESCUENTO_PAGO_TARJETA_DE_DEBITO = 0.25;
    private final static double DESCUENTO_PAGO_BILLETERA_VIRTUAL = 0.10;

    private final StockDeProductos[] stock;
    private final Item[] productPedidos;
    private double costoInicial = 0;
    private double costoEnvio = 0;
    private double costoConDescuento = 0;

    public Pedido(StockDeProductos[] stock) {
        this.stock = stock;
        this.productPedidos = new Item[stock.length];
        inicializarPedido();
    }

    private void inicializarPedido() {
        for (int i = 0; i < TipoHamburguesa.values().length; i++) {
            this.productPedidos[i] = new Item(
                    TipoHamburguesa.values()[i],
                    this.stock[i].getPrecio()
            );
        }
    }

    public void agregarProducto(TipoHamburguesa tipoHamburguesa, long cantidad) {
        if (stock[tipoHamburguesa.ordinal()].decrementarStock(cantidad)) {
            productPedidos[tipoHamburguesa.ordinal()].incrementarCantidad(cantidad);
        }
    }

    public void actualizarCosto() {
        this.costoInicial = calcularCostoInicial();
        this.costoConDescuento = costoInicial;
    }

    private double calcularCostoInicial() {
        double total = 0.0;
        for (Item item : productPedidos) {
            total += item.obtenerSubtotal();
        }

        return total;
    }

    public String toString() {
        String texto = "";
        texto = texto + ANSI_CYAN + "\nPedido Actual:" + ANSI_RESET;

        for (Item item : productPedidos) {
            if (item.getCantidad() > 0) {
                texto = texto + "\n" + item;
            }

        }
        texto = texto + "\n" + ANSI_GREEN + "TOTAL PRODUCTOS: " + ANSI_RESET + this.costoInicial;
        texto = texto + "\n" + ANSI_GREEN + "TOTAL CON DESCUENTO: " + ANSI_RESET + this.costoConDescuento;
        texto = texto + "\n" + ANSI_GREEN + "TOTAL ENVIO: " + ANSI_RESET + this.costoEnvio;
        texto = texto + "\n" + ANSI_GREEN + "TOTAL: " + ANSI_RESET + (this.costoConDescuento + this.costoEnvio);

        return texto;
    }

    public void realizarEnvio(ZonasDeEnvio zona) {
        this.costoEnvio = this.costoPorZona(zona);
    }

    public void pagarPedido(FormasDePago formaDePago) {
        this.costoConDescuento = this.calcularTotalConDescuento(formaDePago);
    }

    private double calcularTotalConDescuento(FormasDePago opcion) {
        double total = this.costoInicial;

        switch (opcion) {

            case EFECTIVO:
                total -= total * DESCUENTO_PAGO_EFECTIVO;
                break;

            case TARJETA_DE_DEBITO:
                total -= total * DESCUENTO_PAGO_TARJETA_DE_DEBITO;
                break;

            case TARJETA_DE_CREDITO:
                total -= total * DESCUENTO_PAGO_TARJETA_DE_CREDITO;
                break;

            case BILLETERA_VIRTUAL:
                total -= total * DESCUENTO_PAGO_BILLETERA_VIRTUAL;
                break;
        }

        return total;
    }

    private int costoPorZona(ZonasDeEnvio opcion) {

        int envio = 0;

        switch (opcion) {

            case SAN_JUSTO:
                envio += 50;
                break;

            case RAMOS_MEJIA:
            case LOMAS_DEL_MIRADOR:
            case VILLA_LUZURIAGA:
            case LA_TABLADA:
                envio += 80;
                break;

            case HAEDO:
            case MORON:
                envio += 100;
                break;

        }

        return envio;
    }

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RESET = "\u001B[0m";
}
