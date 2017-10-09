package domain.model;

import java.util.ArrayList;

public class Cidade {

    private int codCidade;
    private String nome;
    private ArrayList<UF> uf = new ArrayList<>();

    public int getCodCidade() {
        return codCidade;
    }

    public void setCodCidade(int codCidade) {
        this.codCidade = codCidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<UF> getUf() {
        return uf;
    }

    public void setUf(ArrayList<UF> uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
