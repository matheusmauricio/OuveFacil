package dao;

import domain.UF;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UFDao {

    private Connection connection;

    public UFDao() {
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection val) {
        this.connection = val;
    }

    public boolean inserirUF(UF uf) throws SQLException {
        // A captura de exceções SQLException em Java é obrigatória para usarmos JDBC.
        // Utilizamos o método createStatement de con para criar o Statement
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO ufs (sigla, nome) VALUES(?,?)");
        //Preenche valores
        stmt.setString(1, uf.getSigla());
        stmt.setString(2, uf.getNome());

        stmt.execute();
        stmt.close();
        return true;
    }

    public boolean alterarUF(UF uf) throws SQLException {
        return true;
    }

    public boolean removerUF(UF uf) throws SQLException {
        return true;
    }

    public UF selecionarUF(UF uf) throws SQLException {
        return null;
    }

    public ArrayList<UF> selecionarTodasUFs() throws SQLException {
        // A captura de exceções SQLException em Java é obrigatória para usarmos JDBC.
        // Para termos acesso ao objeto con, ele deve ter um escopo mais amplo que o bloco try
        ArrayList<UF> ufs = new ArrayList();
        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM ufs");

        // Executa um select
        ResultSet rs = stmt.executeQuery();

        // Itera no ResultSet
        while (rs.next()) {
            UF uf = new UF();

            uf.setSigla(rs.getString("sigla"));
            uf.setNome(rs.getString("nome"));

            ufs.add(uf);
        }
        return ufs;
    }
}
