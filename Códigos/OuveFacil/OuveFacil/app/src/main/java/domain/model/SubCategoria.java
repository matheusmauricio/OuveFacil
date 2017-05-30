package domain.model;

public class SubCategoria {

    private int codSubCategoria;
    private String nome;
    private Categoria categoria;

    public int getCodSubCategoria() {
        return codSubCategoria;
    }

    public void setCodSubCategoria(int codSubCategoria) {
        this.codSubCategoria = codSubCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
