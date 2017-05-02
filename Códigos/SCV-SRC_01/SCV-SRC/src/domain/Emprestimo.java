package domain;

import java.util.Date;

public class Emprestimo {
 
	private int cdEmprestimo;
	 
	private Date data;
	 
	private float valor;
	 
	private boolean pago;
	 
	private Cliente cliente;
	 
	private ItemDeEmprestimo[] itemDeEmprestimo;
	 
	public void adicionarItemDeEmprestimo(ItemDeEmprestimo itemDeEmprestimo) {
	 
	}
	 
	public void setCdEmprestimo(int cdEmprestimo) {
	 
	}
	 
	public int getCdEmprestimo() {
		return 0;
	}
	 
	public void setData(Date data) {
	 
	}
	 
	public Date getData() {
		return null;
	}
	 
	public void setValor(float valor) {
	 
	}
	 
	public float getValor() {
		return 0;
	}
	 
	public void setPago(boolean pago) {
	 
	}
	 
	public boolean isPago() {
		return false;
	}
	 
	public void setCliente(Cliente cliente) {
	 
	}

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @return the itemDeEmprestimo
     */
    public ItemDeEmprestimo[] getItemDeEmprestimo() {
        return itemDeEmprestimo;
    }

    /**
     * @param itemDeEmprestimo the itemDeEmprestimo to set
     */
    public void setItemDeEmprestimo(ItemDeEmprestimo[] itemDeEmprestimo) {
        this.itemDeEmprestimo = itemDeEmprestimo;
    }
	 
}
 
