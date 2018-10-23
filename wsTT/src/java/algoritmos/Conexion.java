package algoritmos;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author benja
 */
public class Conexion {
            private String password;
            private String url;
            private Connection conn;
            private Statement stmt;
            private ResultSet rs;    
            
            public Conexion(String base, String user, String pass){
                password = pass;
                url = "jdbc:mysql://localhost/"+base+"?user="+user+"&password="+pass+"&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                conn = null;
                stmt = null;
                rs = null;
            }
            
            public boolean conecta(){
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                    conn= DriverManager.getConnection(url);
                    stmt=conn.createStatement();
                    return true;
                }catch(Exception error){
                    System.out.print(error.toString());
                    return false;
                }
            }
            
            public ResultSet consulta(String query){
                try {
                    rs = stmt.executeQuery(query);
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }
                return rs;
            }
            
            public boolean actualizacion(String query){           
                try {
                    stmt.executeUpdate(query);
                    return true;
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                    return false;
                }
            }
            
            public void desconecta(){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
}
