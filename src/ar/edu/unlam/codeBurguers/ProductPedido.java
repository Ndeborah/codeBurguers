package ar.edu.unlam.codeBurguers;

public class ProductPedido {
    private final TipoHamburguesa tipoHamburguesa;
    private final double precio;
    private long cantidad = 0;

    public ProductPedido(TipoHamburguesa tipoHamburguesa, double precio) {
        this.tipoHamburguesa = tipoHamburguesa;
        this.precio = precio;
    }

    public void incrementarCantidad(long cantidad) {
        this.cantidad += cantidad;
    }

    public double obtenerSubtotal() {
        return cantidad * precio;
    }

    public long getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return  "Tipo De Hamburguesa: " + tipoHamburguesa +
                "\nPrecio: " + precio +
                " Cantidad: " + cantidad +
                "\nSubtotal: " + obtenerSubtotal();
    }
}
