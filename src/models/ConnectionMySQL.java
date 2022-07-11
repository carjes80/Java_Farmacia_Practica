package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionMySQL {
    private String database_name = "farmacia";
    private String user = "root";
    private String password = "admin";
    private String url = "jdbc:mysql://localhost:3306/" + database_name; 
    Connection conn = null;
    
    public Connection getConnection()
    {
        try{
            //Obtener el valor del driver (buscar qué es esto)
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            
        }catch(ClassNotFoundException e){
            System.err.println("Ha ocurrido un ClassNotFoundException "+e.getMessage());
            
            }catch(SQLException e){
                System.out.println("Ha ocurrido un SQLException "+ e.getMessage());
                
            }
        return conn;
    }
    
    
}
