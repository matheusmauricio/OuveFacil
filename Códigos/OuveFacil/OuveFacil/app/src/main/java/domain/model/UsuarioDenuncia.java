package domain.model;

/**
 * Created by Matheus on 11/09/2017.
 */

public class UsuarioDenuncia {

    private int codUsuarioDenuncia;
    private Usuario usuario;
    private Denuncia denuncia;
    private String colaboracao;

    public int getCodUsuarioDenuncia() {
        return codUsuarioDenuncia;
    }

    public void setCodUsuarioDenuncia(int codUsuarioDenuncia) {
        this.codUsuarioDenuncia = codUsuarioDenuncia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Denuncia getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
    }

    public String getColaboracao() {
        return colaboracao;
    }

    public void setColaboracao(String colaboracao) {
        this.colaboracao = colaboracao;
    }
}
