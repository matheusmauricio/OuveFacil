package domain.model;

public class Status {

    private int codStatus;
    private String nome;

    public int getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(int codStatus) {
        this.codStatus = codStatus;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
