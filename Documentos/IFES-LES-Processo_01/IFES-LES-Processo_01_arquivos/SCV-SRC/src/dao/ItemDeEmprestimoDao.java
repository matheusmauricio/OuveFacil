package dao;

import domain.ItemDeEmprestimo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDeEmprestimoDao {
    
    private Connection connection;

    public boolean inserirItemDeEmprestimo(ItemDeEmprestimo itemDeEmprestimo) throws SQLException {
        // A captura de exceções SQLException em Java é obrigatória para usarmos JDBC.
        // Utilizamos o método createStatement de con para criar o Statement
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO itensdeemprestimo (dataDeEntrega, valor, cdFita, cdEmprestimo) VALUES(?,?,?,?)");

        java.sql.Date data = new java.sql.Date(itemDeEmprestimo.getDataDeEntrega().getTime());
        stmt.setDate(1, data);
        stmt.setFloat(2, itemDeEmprestimo.getValor());
        stmt.setInt(3, itemDeEmprestimo.getFita().getCdFita());
        stmt.setInt(4, itemDeEmprestimo.getEmprestimo().getCdEmprestimo());

        stmt.execute();
        stmt.close();
        return true;
    }

    public boolean alterarItemDeEmprestimo(ItemDeEmprestimo itemDeEmprestimo) throws SQLException {
        return true;
    }

    public boolean removerItemDeEmprestimo(ItemDeEmprestimo itemDeEmprestimo) throws SQLException {
        return true;
    }

    public ItemDeEmprestimo selecionarItemDeEmprestimo(ItemDeEmprestimo itemDeEmprestimo) throws SQLException {
        return null;
    }

    public ArrayList<ItemDeEmprestimo> selecionarTodosItensDeEmprestimo() throws SQLException {
        return null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection val) {
        this.connection = val;
    }

}
