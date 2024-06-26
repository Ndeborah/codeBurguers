package ar.edu.unlam.codeBurguers;

public class ProductStock {
    private TipoHamburguesa tipoHamburguesa;
    private String nombre;
    private long stock;
    private double precio;


    public ProductStock(TipoHamburguesa tipoHamburguesa, String nombre, long cantidad, double precio) {
        this.tipoHamburguesa = tipoHamburguesa;
        this.stock = cantidad;
        this.nombre = nombre;
        this.precio = precio;
    }

    public double obtenerSubtotal() {
        return this.stock * this.precio;
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
        return ANSI_BLUE + "Nombre: " + ANSI_RESET + this.nombre +
                "\nCódigo: " + (this.tipoHamburguesa.ordinal() + 1) +
                ANSI_YELLOW + " Cantidad disponible: " + this.stock +
                ANSI_RESET + " Precio: " + this.precio;
    }

    public long incrementarStock(long cantidad) {
        this.stock += cantidad;
        return this.stock;
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
