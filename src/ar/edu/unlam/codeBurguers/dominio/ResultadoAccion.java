package ar.edu.unlam.codeBurguers.dominio;

public class ResultadoAccion {
    private boolean exito;
    private String mensaje;

    public ResultadoAccion(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
    }

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }
}
