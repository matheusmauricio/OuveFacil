package dao;

import domain.Filme;
import domain.TipoDeFilme;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilmeDao {

    private Connection connection;

    public FilmeDao() {
    }

    public boolean inserirFilme(Filme filme) throws SQLException {
        return true;
    }

    public boolean alterarFilme(Filme filme) throws SQLException {
        return true;
    }

    public boolean removerFilme(Filme filme) throws SQLException {
        return true;
    }

    public Filme selecionarFilme(Filme filme) throws SQLException {
        // A captura de exceções SQLException em Java é obrigatória para usarmos JDBC.
        // Para termos acesso ao objeto con, ele deve ter um escopo mais amplo que o bloco try
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM filmes WHERE cdFilme = ?");
        stmt.setInt(1, filme.getCdFilme());

        // Executa um select
        ResultSet rs = stmt.executeQuery();

        // Itera no ResultSet
        if (rs.next()) {
            TipoDeFilme tipoDeFilme = new TipoDeFilme();
            filme.setCdFilme(rs.getInt("cdFilme"));
            filme.setGenero(rs.getString("genero"));
            filme.setTempoDuracao(rs.getFloat("tempoDuracao"));
            tipoDeFilme.setCdTipoDeFilme(rs.getInt("cdTipoDeFilme"));
            filme.setTipoDeFilme(tipoDeFilme);
            filme.setTitulo(rs.getString("titulo"));
        }
        return filme;
    }

    public ArrayList<Filme> selecionarTodosFilmes() throws SQLException {
        // A captura de exceções SQLException em Java é obrigatória para usarmos JDBC.
        // Para termos acesso ao objeto con, ele deve ter um escopo mais amplo que o bloco try
        ArrayList<Filme> filmes = new ArrayList();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM filmes");

        // Executa um select
        ResultSet rs = stmt.executeQuery();

        // Itera no ResultSet
        while (rs.next()) {
            Filme filme = new Filme();
            TipoDeFilme tipoDeFilme = new TipoDeFilme();

            filme.setCdFilme(rs.getInt("cdFilme"));
            filme.setGenero(rs.getString("genero"));
            filme.setTempoDuracao(rs.getFloat("tempoDuracao"));
            tipoDeFilme.setCdTipoDeFilme(rs.getInt("cdTipoDeFilme"));
            filme.setTipoDeFilme(tipoDeFilme);
            filme.setTitulo(rs.getString("titulo"));

            filmes.add(filme);
        }
        return filmes;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection val) {
        this.connection = val;
    }

}
