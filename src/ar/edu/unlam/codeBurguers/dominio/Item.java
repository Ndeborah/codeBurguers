package ar.edu.unlam.codeBurguers.dominio;

import ar.edu.unlam.codeBurguers.enums.TipoHamburguesa;

public class Item {
    private final TipoHamburguesa tipoHamburguesa;
    private final double precio;
    private long cantidad = 0;

    public Item(TipoHamburguesa tipoHamburguesa, double precio) {
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
