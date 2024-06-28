package ar.edu.unlam.codeBurguers.dominio;

import ar.edu.unlam.codeBurguers.dominio.enums.TipoHamburguesa;

/**
 * El item de stock representa un elemento "stockeado" en el sistema.
 */
public class ItemDeStock {
    private TipoHamburguesa tipoHamburguesa;
    private String nombre;
    private long stock;
    private double precio;


    public ItemDeStock(TipoHamburguesa tipoHamburguesa, String nombre, long cantidad, double precio) {
        this.tipoHamburguesa = tipoHamburguesa;
        this.stock = cantidad;
        this.nombre = nombre;
        this.precio = precio;
    }

    public double obtenerSubtotal() {
        return this.stock * this.precio;
    }

    public ResultadoAccion decrementarStock(long cantidadAReservar) {
        if (cantidadAReservar > this.stock) {
            return new ResultadoAccion(false, "El pedido excede el stock actual.");
        }
        this.stock -= cantidadAReservar;
        if (this.stock == 0) {
            return new ResultadoAccion(false, "No queda más stock de: " + this.nombre);
        }
        return new ResultadoAccion(true, "El stock fue actualizado correctamente");
    }

    public TipoHamburguesa getTipoHamburguesa() {
        return tipoHamburguesa;
    }

    public void setTipoHamburguesa(TipoHamburguesa tipoHamburguesa) {
        this.tipoHamburguesa = tipoHamburguesa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public double getPrecio() {
        return this.precio;
    }

    @Override
    public String toString() {
        return Colores.ANSI_BLUE + "Nombre: " + Colores.ANSI_RESET + this.nombre +
                "\nCódigo: " + (this.tipoHamburguesa.ordinal() + 1) +
                Colores.ANSI_YELLOW + " Cantidad disponible: " + this.stock +
                Colores.ANSI_RESET + " Precio: " + this.precio;
    }

    public long incrementarStock(long cantidad) {
        this.stock += cantidad;
        return this.stock;
    }
}
