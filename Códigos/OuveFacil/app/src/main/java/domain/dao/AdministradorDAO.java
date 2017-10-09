/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.dao;

public class AdministradorDAO {

    /*
    public boolean inserir(Administrador administrador) {

        try {

            String sql = "INSERT INTO administrador(nome, login, senha, cpfCnpj) VALUES ('?','?','?','?')";
            PreparedStatement stmt;

            Connection conn = Database.obtemConexao();

            stmt = conn.prepareStatement(sql);
            //stmt.setInt(1,4);
            stmt.setString(1, administrador.getNome());
            stmt.setString(2, administrador.getLogin());
            stmt.setString(3, administrador.getSenha());
            stmt.setString(4, administrador.getCpfCnpj());
            stmt.executeUpdate();

            conn.close();


        } catch (SQLException ex) {
            //Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }

        return true;
    }
*/
    /*public boolean alterar(Administrador administrador) {
        String sql = "UPDATE administrador SET nome=?, cpf=?, telefone=?,"
                + " email=? WHERE matricula_administrador=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, administrador.getNome());
            stmt.setString(2, administrador.getCpf());
            stmt.setString(3, administrador.getTelefone());
            stmt.setString(4, administrador.getEmail());
            stmt.setInt(5, administrador.getMatricula());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    } */

   /* public boolean remover(Administrador administrador) {
        String sql = "DELETE FROM administrador WHERE matricula_administrador=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, administrador.getMatricula());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    } */
/*
    public List<Administrador> listar() {
        String sql = "SELECT * FROM administrador";
        List<Administrador> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Administrador administrador = new Administrador();
                administrador.setNome(resultado.getString("nome"));
                administrador.setLogin(resultado.getString("login"));
                administrador.setSenha(resultado.getString("senha"));
                administrador.setCpfCnpj(resultado.getString("cpfCnpj"));
                retorno.add(administrador);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
*/

    /*public Administrador buscar(Administrador administrador) {
        String sql = "SELECT * FROM administrador WHERE matricula_administrador=?";
        Administrador retorno = new Administrador();
        //Categoria categoria = new Categoria();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, administrador.getMatricula());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                administrador.setMatricula(resultado.getInt("matricula_administrador"));
                administrador.setNome(resultado.getString("nome"));
                administrador.setCpf(resultado.getString("cpf"));
                administrador.setTelefone(resultado.getString("telefone"));
                administrador.setEmail(resultado.getString("email"));
                //categoria.setCdCategoria(resultado.getInt("cdCategoria"));
                //retorno.setCategoria(categoria);
                retorno = administrador;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    } */

}
