package ar.edu.unlam.codeBurguers.interfaz.enums;

public enum OpcionesMenu {
    INGRESAR_PEDIDO,//el recepcionista ingresa el pedido(revisar stock), la zona a entregar(revisar zona), el medio de pago(revisar formas de pago)
    // segun la zona podes tomar el pedido o no.
    MODIFICAR_PRODUCTOS,//abm de productos, hamburguesa, hay stock se pone, no hay no se pone.
    CONTROLAR_STOCK,//ver si hay productos necesarios para la venta.
    SALIR;//salis del programa.
}
