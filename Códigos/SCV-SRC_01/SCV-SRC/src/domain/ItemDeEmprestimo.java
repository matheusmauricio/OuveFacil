package domain;

import java.util.Date;

public class ItemDeEmprestimo {
 
	private int cdItemDeEmprestimo;
	 
	private int quantidade;
	 
	private float valor;
	 
	private Date dataDeEntrega;
	 
	private Emprestimo emprestimo;
	 
	private Fita fita;
	 
	public void setCdItemDeEmprestimo(int cdItemDeEmprestimo) {
	 
	}
	 
	public int getCdItemDeEmprestimo() {
		return 0;
	}
	 
	public void setQuantidade(int quantidade) {
	 
	}
	 
	public int getQuantidade() {
		return 0;
	}
	 
	public void setValor(float valor) {
	 
	}
	 
	public float getValor() {
		return 0;
	}
	 
	public void setFita(Fita fita) {
	 
	}
	 
	public void setDataDeEntrega(Date dataDeEntrega) {
	 
	}
	 
	public Date getDataDeEntrega() {
		return null;
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
	 
}
 
