package domain.model;

import java.util.ArrayList;

public class Categoria {

    private int codCategoria;
    private String nome;
    private ArrayList<SubCategoria> subCategoria = new ArrayList();

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<SubCategoria> getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(ArrayList<SubCategoria> subCategoria) {
        this.subCategoria = subCategoria;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
