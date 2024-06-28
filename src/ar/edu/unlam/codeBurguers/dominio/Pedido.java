package ar.edu.unlam.codeBurguers.dominio;

import ar.edu.unlam.codeBurguers.dominio.enums.FormasDePago;
import ar.edu.unlam.codeBurguers.dominio.enums.TipoHamburguesa;
import ar.edu.unlam.codeBurguers.dominio.enums.ZonasDeEnvio;

import static ar.edu.unlam.codeBurguers.dominio.Colores.*;

public class Pedido {
    private final static double DESCUENTO_PAGO_EFECTIVO = 0.15;
    private final static double DESCUENTO_PAGO_TARJETA_DE_CREDITO = 0.20;
    private final static double DESCUENTO_PAGO_TARJETA_DE_DEBITO = 0.25;
    private final static double DESCUENTO_PAGO_BILLETERA_VIRTUAL = 0.10;

    private final ControlDeStock stock;
    private final ItemDePedido[] productPedidos;
    private double costoInicial = 0;
    private double costoEnvio = 0;
    private double costoConDescuento = 0;

    public Pedido(ControlDeStock controlDeStock) {
        this.stock = controlDeStock;
        this.productPedidos = controlDeStock.crearItemsParaPedir();
    }

    public ResultadoAccion agregarProducto(TipoHamburguesa tipoHamburguesa, long cantidad) {
        ResultadoAccion resultadoAccion = this.stock.decrementarStock(tipoHamburguesa, cantidad);
        if (resultadoAccion.isExito()) {
            productPedidos[tipoHamburguesa.ordinal()].incrementarCantidad(cantidad);
            this.actualizarCosto();
            return resultadoAccion;
        }
        return resultadoAccion;
    }

    private void actualizarCosto() {
        this.costoInicial = calcularCostoInicial();
        this.costoConDescuento = costoInicial;
    }


    public String toString() {
        String texto = "";
        texto = texto + ANSI_CYAN + "\nPedido Actual:" + ANSI_RESET;

        for (ItemDePedido itemDePedido : productPedidos) {
            if (itemDePedido.getCantidad() > 0) {
                texto = texto + "\n" + itemDePedido;
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

    private double calcularCostoInicial() {
        double total = 0.0;
        for (ItemDePedido itemDePedido : productPedidos) {
            total += itemDePedido.obtenerSubtotal();
        }

        return total;
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
}
