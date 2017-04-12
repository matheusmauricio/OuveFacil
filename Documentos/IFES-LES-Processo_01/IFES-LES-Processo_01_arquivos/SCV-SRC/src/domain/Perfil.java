package domain;

public class Perfil {

    private String cdPerfil;

    private String perfil;

    private Funcionario[] funcionario;

    public Perfil() {
    }

    /**
     * @return the cdPerfil
     */
    public String getCdPerfil() {
        return cdPerfil;
    }

    /**
     * @param cdPerfil the cdPerfil to set
     */
    public void setCdPerfil(String cdPerfil) {
        this.cdPerfil = cdPerfil;
    }

    /**
     * @return the perfil
     */
    public String getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    /**
     * @return the funcionario
     */
    public Funcionario[] getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(Funcionario[] funcionario) {
        this.funcionario = funcionario;
    }

}
