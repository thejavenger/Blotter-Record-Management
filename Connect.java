
package blotterrecordmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Naps
 */
public class Connect {
    
    String derbyembeddeddriver = "org.apache.derby.jdbc.EmbeddedDriver";
    String derbyclientdriver = "org.apache.derby.jdbc.ClientDriver";
    String odbcdriver = "sun.jdbc.odbc.JdbcOdbcDriver";
    
    String strDriver;
    
    String host;
    String uName;
    String uPass;
    
    public Connection doConnection(Connection conn, String username, String password ){
        try {
           
            
            //String strDriver = "org.apache.derby.jdbc.ClientDriver";
            //Class.forName(strDriver);

            strDriver = derbyclientdriver;
            
            Class.forName(strDriver);
            
            //host = "jdbc:derby://localhost:1527/TAWCDB;create=true";
            host = "jdbc:derby://localhost:1527/blotter_db;create=true";
            uName = "EllaJazelle";
            uPass = "BlotterManagement";

            conn = DriverManager.getConnection(host,uName,uPass);
                
            
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conn;
    }
}
