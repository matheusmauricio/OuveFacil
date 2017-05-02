package domain;

public class Fita {
 
	private int cdFita;
	 
	private boolean danificada;
	 
	private boolean disponivel;
	 
	private ItemDeEmprestimo[] itemDeEmprestimo;
	 
	private Filme filme;
	 
	private Reserva[] reserva;

    /**
     * @return the cdFita
     */
    public int getCdFita() {
        return cdFita;
    }

    /**
     * @param cdFita the cdFita to set
     */
    public void setCdFita(int cdFita) {
        this.cdFita = cdFita;
    }

    /**
     * @return the danificada
     */
    public boolean isDanificada() {
        return danificada;
    }

    /**
     * @param danificada the danificada to set
     */
    public void setDanificada(boolean danificada) {
        this.danificada = danificada;
    }

    /**
     * @return the disponivel
     */
    public boolean isDisponivel() {
        return disponivel;
    }

    /**
     * @param disponivel the disponivel to set
     */
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
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

    /**
     * @return the filme
     */
    public Filme getFilme() {
        return filme;
    }

    /**
     * @param filme the filme to set
     */
    public void setFilme(Filme filme) {
        this.filme = filme;
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
	 
}
 
