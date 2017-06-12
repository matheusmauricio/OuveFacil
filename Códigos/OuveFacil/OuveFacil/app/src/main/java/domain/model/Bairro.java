package domain.model;

import java.util.ArrayList;

public class Bairro {

    private int codBairro;
    private String nome;
    private ArrayList<Cidade> cidade = new ArrayList<>();

    public int getCodBairro() {
        return codBairro;
    }

    public void setCodBairro(int codBairro) {
        this.codBairro = codBairro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Cidade> getCidade() {
        return cidade;
    }

    public void setCidade(ArrayList<Cidade> cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return this.nome;
    }

}
