package domain;

import java.util.Date;

public class ItemDeEmprestimo {

    private int cdItemDeEmprestimo;

    private int quantidade;

    private float valor;

    private Date dataDeEntrega;

    private Emprestimo emprestimo;

    private Fita fita;
    
    public ItemDeEmprestimo() {
    }

    /**
     * @return the cdItemDeEmprestimo
     */
    public int getCdItemDeEmprestimo() {
        return cdItemDeEmprestimo;
    }

    /**
     * @param cdItemDeEmprestimo the cdItemDeEmprestimo to set
     */
    public void setCdItemDeEmprestimo(int cdItemDeEmprestimo) {
        this.cdItemDeEmprestimo = cdItemDeEmprestimo;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the valor
     */
    public float getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(float valor) {
        this.valor = valor;
    }

    /**
     * @return the dataDeEntrega
     */
    public Date getDataDeEntrega() {
        return dataDeEntrega;
    }

    /**
     * @param dataDeEntrega the dataDeEntrega to set
     */
    public void setDataDeEntrega(Date dataDeEntrega) {
        this.dataDeEntrega = dataDeEntrega;
    }

    /**
     * @return the emprestimo
     */
    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    /**
     * @param emprestimo the emprestimo to set
     */
    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    /**
     * @return the fita
     */
    public Fita getFita() {
        return fita;
    }

    /**
     * @param fita the fita to set
     */
    public void setFita(Fita fita) {
        this.fita = fita;
    }

}
