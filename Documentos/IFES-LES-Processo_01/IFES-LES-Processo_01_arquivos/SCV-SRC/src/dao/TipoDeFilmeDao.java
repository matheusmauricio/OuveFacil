package dao;

import domain.TipoDeFilme;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TipoDeFilmeDao {

    private Connection connection;

    public TipoDeFilmeDao() {
    }

    public boolean inserirTipoDeFilme(TipoDeFilme tipoDeFilme) throws SQLException {
        return true;
    }

    public boolean alterarTipoDeFilme(TipoDeFilme tipoDeFilme) throws SQLException {
        return true;
    }

    public boolean removerTipoDeFilme(TipoDeFilme tipoDeFilme) throws SQLException {
        return true;
    }

    public TipoDeFilme selecionarTipoDeFilme(TipoDeFilme tipoDeFilme) throws SQLException {
        // A captura de exceções SQLException em Java é obrigatória para usarmos JDBC.
        // Para termos acesso ao objeto con, ele deve ter um escopo mais amplo que o bloco try
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tiposdefilmes WHERE cdTipoDeFilme = ?");
        stmt.setInt(1, tipoDeFilme.getCdTipoDeFilme());

        // Executa um select
        ResultSet rs = stmt.executeQuery();

        // Itera no ResultSet
        if (rs.next()) {
            tipoDeFilme.setCdTipoDeFilme(rs.getInt("cdTipoDeFilme"));
            tipoDeFilme.setDiasDePrazo(rs.getInt("diasDePrazo"));
            tipoDeFilme.setNome(rs.getString("nome"));
            tipoDeFilme.setPreco(rs.getFloat("preco"));
        }
        return tipoDeFilme;
    }

    public ArrayList<TipoDeFilme> selecionarTodosTiposDeFilme() throws SQLException {
        return null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection val) {
        this.connection = val;
    }
}
