package dao;

import domain.Cliente;
import java.util.ArrayList;
import domain.Filme;
import domain.Fita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FitaDao {

    private Connection connection;

    public Fita selecionarFita(Fita fita) throws SQLException {
        return null;
    }

    public boolean removerFita(Cliente cliente) throws SQLException {
        return true;
    }

    public boolean alterarFita(Fita fita) throws SQLException {
        return true;
    }

    public boolean inserirFita(Fita fita) throws SQLException {
        return true;
    }

    public ArrayList<Fita> selecionarTodasFitas() throws SQLException {
        // A captura de exceções SQLException em Java é obrigatória para usarmos JDBC.
        // Para termos acesso ao objeto con, ele deve ter um escopo mais amplo que o bloco try
        ArrayList<Fita> fitas = new ArrayList();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM fitas");

        // Executa um select
        ResultSet rs = stmt.executeQuery();

        // Itera no ResultSet
        while (rs.next()) {
            Fita fita = new Fita();
            Filme filme = new Filme();

            fita.setCdFita(rs.getInt("cdFita"));
            fita.setDanificada(rs.getBoolean("danificada"));
            fita.setDisponivel(rs.getBoolean("disponivel"));
            filme.setCdFilme(rs.getInt("cdFilme"));
            fita.setFilme(filme);

            fitas.add(fita);
        }
        return fitas;
    }

    public ArrayList<Fita> selecionarFitasPorFilme(Filme filme) throws SQLException {
        // A captura de exceções SQLException em Java é obrigatória para usarmos JDBC.
        // Para termos acesso ao objeto con, ele deve ter um escopo mais amplo que o bloco try
        ArrayList<Fita> fitas = new ArrayList();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM fitas WHERE cdFilme = ?");

        stmt.setInt(1, filme.getCdFilme());
        // Executa um select
        ResultSet rs = stmt.executeQuery();

        // Itera no ResultSet
        while (rs.next()) {
            Fita fita = new Fita();

            fita.setCdFita(rs.getInt("cdFita"));
            fita.setDanificada(rs.getBoolean("danificada"));
            fita.setDisponivel(rs.getBoolean("disponivel"));
            filme.setCdFilme(rs.getInt("cdFilme"));
            fita.setFilme(filme);

            fitas.add(fita);
        }
        return fitas;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection val) {
        this.connection = val;
    }

}
