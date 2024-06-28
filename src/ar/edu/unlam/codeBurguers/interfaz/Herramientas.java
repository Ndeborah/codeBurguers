package ar.edu.unlam.codeBurguers.interfaz;

import java.util.Scanner;

public class Herramientas {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getCodigoDePantalla() {
        String next = scanner.next().toLowerCase();
        if (next.length() > 1) {
            return 0;
        }

        return Character.getNumericValue(next.charAt(0));
    }
}
