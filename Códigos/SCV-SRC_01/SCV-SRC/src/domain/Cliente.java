package domain;

import java.util.Date;

public class Cliente extends Pessoa {

    private float debito;

    private String telefone;

    private Date dataNascimento;

    private Reserva[] reserva;

    private Emprestimo[] emprestimo;

    /**
     * @return the debito
     */
    public float getDebito() {
        return debito;
    }

    /**
     * @param debito the debito to set
     */
    public void setDebito(float debito) {
        this.debito = debito;
    }

    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @param telefone the telefone to set
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * @return the dataNascimento
     */
    public Date getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * @return the reserva
     */
    public Reserva[] getReserva() {
        return reserva;
    }

    /**
     * @param reserva the reserva to set
     */
    public void setReserva(Reserva[] reserva) {
        this.reserva = reserva;
    }

    /**
     * @return the emprestimo
     */
    public Emprestimo[] getEmprestimo() {
        return emprestimo;
    }

    /**
     * @param emprestimo the emprestimo to set
     */
    public void setEmprestimo(Emprestimo[] emprestimo) {
        this.emprestimo = emprestimo;
    }

}
