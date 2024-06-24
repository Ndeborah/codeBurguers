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
    
    
    public double pagarPedido(FormasDePago opcion) {

		double total= obtenerTotal();
		
		switch(opcion) {
		
		case EFECTIVO:
			total-=total*DESCUENTO_PAGO_EFECTIVO;
			break;
			
		case TARJETA_DE_DEBITO:
			total-=total*DESCUENTO_PAGO_TARJETA_DE_DEBITO;
			break;
			
		case TARJETA_DE_CREDITO:
			total-=total*DESCUENTO_PAGO_TARJETA_DE_CREDITO;
			break;
			
		case BILLETERA_VIRTUAL:
			total-=total*DESCUENTO_PAGO_BILLETERA_VIRTUAL;
			break;
		}
				
		return total;
	}
    
    public int costoPorZona(ZonasDeEnvio opcion) {

		int envio= 0;
		
		switch(opcion) {
		
		case SAN_JUSTO:
			envio+=50;
			break;
			
		case RAMOS_MEJIA:
		case LOMAS_DEL_MIRADOR:
		case VILLA_LUZURIAGA:
		case LA_TABLADA:
			envio+=80;
			break;
			
		case HAEDO:
		case MORON:
			envio+=100;
			break;
			
		}
				
		return envio;
	}
}
