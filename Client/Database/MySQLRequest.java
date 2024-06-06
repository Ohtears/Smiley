package Client.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLRequest {
 
    String url = "jdbc:mysql://localhost:3306/mydatabase";
    String user = "tears";
    String password = "traboat12";

    public void EstablishConnection(){

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to MySQL database.");
        }
         
        catch (SQLException e) {
            System.out.println("Connection to MySQL database failed.");
            e.printStackTrace();
        }

    }

}
