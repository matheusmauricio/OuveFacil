
package dao;

import domain.Bairro;
import java.util.ArrayList;
import domain.Cliente;
import domain.Endereco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ClienteDao {

    private Connection connection;

    public ArrayList selecionarTodosClientes() throws SQLException {

        ArrayList<Cliente> clientes = new ArrayList();
        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM clientes");

        // Executa um select
        ResultSet rs = stmt.executeQuery();

        // Itera no ResultSet
        while (rs.next()) {
            Cliente cliente = new Cliente();
            Endereco endereco = new Endereco();
            Bairro bairro = new Bairro();

            cliente.setCdPessoa(rs.getInt("cdCliente"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setDataNascimento(rs.getDate("dataNascimento"));
            cliente.setDebito(rs.getFloat("debito"));
            cliente.setNome(rs.getString("nome"));
            cliente.setTelefone(rs.getString("telefone"));
            endereco.setRua(rs.getString("rua"));
            endereco.setNumero(rs.getInt("numero"));
            bairro.setCdBairro(rs.getInt("cdBairro"));
            endereco.setBairro(bairro);
            cliente.setEndereco(endereco);
            
            Number number;
            number = (java.lang.Number)rs.getObject("cpfnumeric");

            clientes.add(cliente);
        }
        return clientes;
    }

    public boolean inserirCliente(Cliente cliente) throws SQLException{
        return false;
    }

    public boolean alterarCliente(Cliente cliente) {
        return false;
    }

    public boolean removerCliente(Cliente cliente) {
        return false;
    }

    public Cliente selecionarCliente(Cliente cliente) {
        return null;
    }

    public ArrayList selecionarClientesPorBairro() {
        return null;
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
