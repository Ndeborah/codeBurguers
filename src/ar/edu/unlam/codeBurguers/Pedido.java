package ar.edu.unlam.codeBurguers;

public class Pedido {
    private final static double DESCUENTO_PAGO_EFECTIVO = 0.15;
    private final static double DESCUENTO_PAGO_TARJETA_DE_CREDITO = 0.20;
    private final static double DESCUENTO_PAGO_TARJETA_DE_DEBITO = 0.25;
    private final static double DESCUENTO_PAGO_BILLETERA_VIRTUAL = 0.10;

    private final ProductStock[] stock;
    private final ProductPedido[] productPedidos;

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

    public double obtenerTotal() {
        double total = 0.0;
        for (ProductPedido item : productPedidos) {
            total += item.obtenerSubtotal();
        }

        return total;
    }
    public void imprimirPedido(){
    System.out.println("Pedido Actual:");

    for (ProductPedido item : productPedidos) {
         if (item.getCantidad()>0){
             System.out.println(item);
         }

     }
     System.out.println("TOTAL: " + obtenerTotal());
    }
}
