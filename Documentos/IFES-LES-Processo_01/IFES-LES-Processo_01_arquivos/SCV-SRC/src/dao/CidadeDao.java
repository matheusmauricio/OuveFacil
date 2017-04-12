package dao;

import domain.Cidade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CidadeDao {

    private Connection connection;

    public CidadeDao() {
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection val) {
        this.connection = val;
    }

    public boolean inserirCidade(Cidade cidade) throws SQLException {
        // A captura de exceções SQLException em Java é obrigatória para usarmos JDBC.
        // Utilizamos o método createStatement de con para criar o Statement
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO cidades (nome, sigla) VALUES(?,?)");
        //Preenche valores
        stmt.setString(1, cidade.getNome());
        stmt.setString(2, cidade.getUF().getSigla());

        stmt.execute();
        stmt.close();
        return true;
    }
}
