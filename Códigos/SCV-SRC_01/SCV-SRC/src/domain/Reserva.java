package domain;

import java.util.Date;

public class Reserva {
 
	private int cdReserva;
	 
	private Date data;
	 
	private float valor;
	 
	private Cliente cliente;
	 
	private Fita fita;

    /**
     * @return the cdReserva
     */
    public int getCdReserva() {
        return cdReserva;
    }

    /**
     * @param cdReserva the cdReserva to set
     */
    public void setCdReserva(int cdReserva) {
        this.cdReserva = cdReserva;
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
 
