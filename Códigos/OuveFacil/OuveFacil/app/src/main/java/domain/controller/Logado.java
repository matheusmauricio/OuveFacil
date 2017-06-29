package domain.controller;

/**
 * Created by Matheus on 26/06/2017.
 */

public class Logado {

    private static boolean EstaLogado;

    public static boolean isEstaLogado() {
        return EstaLogado;
    }

    public static void setEstaLogado(boolean estaLogado) {
        EstaLogado = estaLogado;
    }
}
