package mundo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.JOptionPane;

/**
 *
 * @author docentes
 */
public class MySql 
{ // Atributos
     private Connection connection;
     private ResultSet rst;
     private Statement statement;
	    	     
  // Constantes  
     private static final String driver   = "com.mysql.cj.jdbc.Driver";
     private static final String user     = "root";
     private static final String password = "";     
     private static final String url      = "jdbc:mysql://localhost:3306/FACTURACION";
             
  // Constructor
     public MySql()
     {        
     }   
     
     public Connection connect()
     { this.connection = null;
     //PARTE DONDE HACE LA CONEXIÓN
       try
       { Class.forName("com.mysql.cj.jdbc.Driver");
         this.connection = DriverManager.getConnection(this.url, this.user, this.password);   
       }
       catch (Exception e)
       { System.out.println("Mysql() :: " + e.getMessage());           
       }    
       return this.connection;
     } 
     //DEVUELVE LA CONEXION
     public Connection getConnection() { return this.connection; }
     public void closeConnection() throws SQLException 
     { connection.close();  
     }
     
     
    /* Métodos de comportamiento CRUD */
     
    /**
     * DML: Utilizada para INSERT, UPDATE, o DELETE. 
     * @param  sql
     * @return reg Número de registros afectados.
     * @throws java.sql.SQLException
     */
     public int update( String sql ) throws SQLException
     { //System.out.println(sql);
         this.statement  = this.connection.createStatement();
       return statement.executeUpdate( sql );
     }		

    /**
     * Retorna el resultado de la consulta ejecutada.
     * @param  sql Consulta a realizar.
     * @return rst Resultado de la consulta
     */
     public ResultSet select( String sql ) throws SQLException 
     { this.statement  = this.connection.createStatement();
       this.rst = statement.executeQuery( sql );
       return rst;
     }		     
}
