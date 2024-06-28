package ar.edu.unlam.codeBurguers.dominio;

import ar.edu.unlam.codeBurguers.dominio.enums.TipoHamburguesa;

public class ControlDeStock {
    private final ItemDeStock[] stock;

    public ControlDeStock(ItemDeStock[] stockInicial) {
        this.stock = stockInicial;
    }

    public long agregarCantidadAUnProducto(TipoHamburguesa tipoHamburguesa, long cantidad) {
        ItemDeStock itemDeStock = obtenerProductoSegunElTipo(tipoHamburguesa);
        itemDeStock.incrementarStock(cantidad);
        return itemDeStock.getStock();
    }

    public boolean cambiarPrecioDeUnProducto(TipoHamburguesa tipoHamburguesa, double precioNuevo) {
        ItemDeStock itemDeStock = obtenerProductoSegunElTipo(tipoHamburguesa);
        if (itemDeStock == null) {
            return false;
        }
        itemDeStock.setPrecio(precioNuevo);
        return true;
    }

    public ItemDeStock obtenerProductoSegunElTipo(TipoHamburguesa tipo) {
        return this.stock[tipo.ordinal()];
    }

    public ItemDePedido[] crearItemsParaPedir() {
        ItemDePedido[] items = new ItemDePedido[stock.length];
        for (int i = 0; i < TipoHamburguesa.values().length; i++) {
            items[i] = new ItemDePedido(
                    TipoHamburguesa.values()[i],
                    this.stock[i].getPrecio()
            );
        }

        return items;
    }

    public ResultadoAccion decrementarStock(TipoHamburguesa tipo, long cantidad) {
        return this.stock[tipo.ordinal()].decrementarStock(cantidad);
    }

    public ItemDeStock[] getStockOrdenado() {
        return this.ordenarProductosPorStockMayorAMenor(this.stock);
    }

    private ItemDeStock[] ordenarProductosPorStockMayorAMenor(ItemDeStock[] stocks) {
        ItemDeStock auxiliar;
        ItemDeStock[] stocksOrdenados = stocks.clone();
        for (int i = 0; i < stocksOrdenados.length; i++) {
            for (int j = 0; j < stocksOrdenados.length - (i + 1); j++) {
                if (stocksOrdenados[j] != null && stocksOrdenados[j + 1] != null && stocksOrdenados[j].getStock() < stocksOrdenados[j + 1].getStock()) {
                    auxiliar = stocksOrdenados[j];
                    stocksOrdenados[j] = stocksOrdenados[j + 1];
                    stocksOrdenados[j + 1] = auxiliar;
                }
            }
        }
        return stocksOrdenados;
    }

}
