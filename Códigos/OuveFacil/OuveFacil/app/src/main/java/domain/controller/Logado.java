package domain.controller;

import domain.model.Usuario;

/**
 * Created by Matheus on 26/06/2017.
 */

public class Logado {

    private static boolean EstaLogado;
    private static Usuario usuario;

    public static boolean isEstaLogado() {
        return EstaLogado;
    }

    public static void setEstaLogado(boolean estaLogado) {
        EstaLogado = estaLogado;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Logado.usuario = usuario;
    }
}
