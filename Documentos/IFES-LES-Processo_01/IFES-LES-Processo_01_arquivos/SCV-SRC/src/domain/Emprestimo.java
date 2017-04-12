package domain;

import java.util.ArrayList;
import java.util.Date;

public class Emprestimo {

    private int cdEmprestimo;

    private Date data;

    private float valor;

    private boolean pago;

    private Cliente cliente;

    //private ItemDeEmprestimo[] itemDeEmprestimo = new ItemDeEmprestimo [10];
    private ArrayList itemDeEmprestimo = new ArrayList();
    
    public Emprestimo() {
    }

    public void adicionarItemDeEmprestimo(ItemDeEmprestimo itemDeEmprestimo) {
        //this.itemDeEmprestimo[this.itemDeEmprestimo.length-1] = itemDeEmprestimo;
        this.itemDeEmprestimo.add(itemDeEmprestimo);
    }

    /**
     * @return the cdEmprestimo
     */
    public int getCdEmprestimo() {
        return cdEmprestimo;
    }

    /**
     * @param cdEmprestimo the cdEmprestimo to set
     */
    public void setCdEmprestimo(int cdEmprestimo) {
        this.cdEmprestimo = cdEmprestimo;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
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
     * @return the pago
     */
    public boolean getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(boolean pago) {
        this.pago = pago;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the itemDeEmprestimo
     */
    public ArrayList getItemDeEmprestimo() {
        return itemDeEmprestimo;
    }

    /**
     * @param itemDeEmprestimo the itemDeEmprestimo to set
     */
    public void setItemDeEmprestimo(ArrayList itemDeEmprestimo) {
        
        this.itemDeEmprestimo = itemDeEmprestimo;
    }

}
