package dao;

import domain.Emprestimo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmprestimoDao {

    private Connection connection;

    public EmprestimoDao() {
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection val) {
        this.connection = val;
    }

    public boolean alterarEmprestimo(Emprestimo emprestimo) throws SQLException {
        return true;
    }

    public boolean removerEmprestimo(Emprestimo emprestimo) throws SQLException {
        return true;
    }

    public Emprestimo selecionarEmprestimo(Emprestimo emprestimo) throws SQLException {
        return null;
    }

    public boolean inserirEmprestimo(Emprestimo emprestimo) throws SQLException {
        // A captura de exceções SQLException em Java é obrigatória para usarmos JDBC.
        // Utilizamos o método createStatement de con para criar o Statement
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO emprestimos (data, valor, pago, cdCliente) VALUES(?,?,?,?)");
        //Preenche valores
        java.sql.Date data = new java.sql.Date(new java.util.Date().getTime());
        stmt.setDate(1, data);
        stmt.setFloat(2, emprestimo.getValor());
        stmt.setBoolean(3, emprestimo.getPago());
        stmt.setInt(4, emprestimo.getCliente().getCdPessoa());

        stmt.execute();
        stmt.close();
        return true;
    }

    public ArrayList<Emprestimo> selecionarTodosEmprestimos() throws SQLException {
        return null;
    }

    public Emprestimo selecionarUltimoEmprestimo() throws SQLException {
        //PreparedStatement stmt = this.connection.prepareStatement("SELECT max(cdEmprestimo) FROM emprestimos");
        PreparedStatement stmt = this.connection.prepareStatement("SELECT max(cdEmprestimo) FROM emprestimos");
        ResultSet rs = stmt.executeQuery();
        rs.next();

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setCdEmprestimo(rs.getInt("max"));

        rs.close();
        stmt.close();
        return emprestimo;
    }

}
