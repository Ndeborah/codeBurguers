package ar.edu.unlam.codeBurguers.dominio;

import ar.edu.unlam.codeBurguers.dominio.enums.TipoHamburguesa;

/**
 * El ítem de pedido representa un ítem en un pedido. Disminuirá el stock cuando se finalice el pedido.
 */
public class ItemDePedido {
    private final TipoHamburguesa tipoHamburguesa;
    private final double precio;
    private long cantidad = 0;

    public ItemDePedido(TipoHamburguesa tipoHamburguesa, double precio) {
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
