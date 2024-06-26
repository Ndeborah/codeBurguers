package ar.edu.unlam.codeBurguers;

public class Pedido {
    private final static double DESCUENTO_PAGO_EFECTIVO = 0.15;
    private final static double DESCUENTO_PAGO_TARJETA_DE_CREDITO = 0.20;
    private final static double DESCUENTO_PAGO_TARJETA_DE_DEBITO = 0.25;
    private final static double DESCUENTO_PAGO_BILLETERA_VIRTUAL = 0.10;

    private final ProductStock[] stock;
    private final ProductPedido[] productPedidos;
    private double costoInicial = 0;
    private double costoEnvio = 0;
    private double costoConDescuento = 0;

    public Pedido(ProductStock[] stock) {
        this.stock = stock;
        this.productPedidos = new ProductPedido[stock.length];
        inicializarPedido();
    }

    private void inicializarPedido() {
        for (int i = 0; i < TipoHamburguesa.values().length; i++) {
            this.productPedidos[i] = new ProductPedido(
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

    public void finalizarCarga() {
        this.costoInicial = calcularCostoInicial();
        this.costoConDescuento = costoInicial;
        this.imprimirPedido();
    }

    private double calcularCostoInicial() {
        double total = 0.0;
        for (ProductPedido item : productPedidos) {
            total += item.obtenerSubtotal();
        }

        return total;
    }

    public void imprimirPedido() {
        System.out.println(ANSI_CYAN + "\nPedido Actual:" + ANSI_RESET);

        for (ProductPedido item : productPedidos) {
            if (item.getCantidad() > 0) {
                System.out.println(item);
            }

        }

        System.out.println(ANSI_GREEN + "TOTAL PRODUCTOS: " + ANSI_RESET + this.costoInicial);
        System.out.println(ANSI_GREEN + "TOTAL CON DESCUENTO: " + ANSI_RESET + this.costoConDescuento);
        System.out.println(ANSI_GREEN + "TOTAL ENVIO: " + ANSI_RESET + this.costoEnvio);
        System.out.println(ANSI_GREEN + "TOTAL: " + ANSI_RESET + (this.costoConDescuento + this.costoEnvio));
    }

    public void realizarEnvio(ZonasDeEnvio zona) {
        this.costoEnvio = this.costoPorZona(zona);
        this.imprimirPedido();
    }

    public void pagarPedido(FormasDePago formaDePago) {
        this.costoConDescuento = this.calcularTotalConDescuento(formaDePago);
        this.imprimirPedido();
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
