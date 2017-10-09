
package domain.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database{

    private static final String url = "http://177.234.153.2/u883301547_bdmm";
    private static final String usuario = "u883301547_mm";
    private static final String senha = "mmboladao";

    private Connection conn;


    public static Connection obtemConexao() throws SQLException {

        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:mysql://192.168.1.105/bancoouvefacil", "root", "");

        //return DriverManager.getConnection(url, usuario, senha);

    }

}
