package ar.edu.unlam.codeBurguers;

public class StockDeProductos {
    private TipoHamburguesa tipoHamburguesa;
    private String nombre;
    private long stock;
    private double precio;


    public StockDeProductos(TipoHamburguesa tipoHamburguesa, String nombre, long cantidad, double precio) {
        this.tipoHamburguesa = tipoHamburguesa;
        this.stock = cantidad;
        this.nombre = nombre;
        this.precio = precio;
    }


    public boolean decrementarStock(long cantidadAReservar) {
        if (cantidadAReservar > this.stock) {
            System.out.println("El pedido excede el stock actual. \n" + this);
            return false;
        }
        this.stock -= cantidadAReservar;
        if (this.stock == 0) {
            System.out.println("No queda más stock de: " + this.nombre);
        }
        return true;
    }

    @Override
    public String toString() {
        return "\nNombre: " + this.nombre +
                "\nCódigo: " + (this.tipoHamburguesa.ordinal() + 1) +
                "\nCantidad: " + this.stock +
                "\nPrecio: " + this.precio;
    }

    public long incrementarStock(long cantidad) {
        this.stock += cantidad;
        return this.stock;
    }

    public double getPrecio() {
        return this.precio;
    }
}
