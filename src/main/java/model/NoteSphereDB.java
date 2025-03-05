package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class NoteSphereDB {

    private static final String SERVER = "localhost";
    private static final String DATABASE = "NoteSphere";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Método para establecer conexión con la base de datos
    public static Connection connect() {
        Connection connection = null;
        try {
            // Construir la URL de conexión
            String url = "jdbc:mysql://" + SERVER + ":3306/" + DATABASE;

            // Establecer la conexión
            connection = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("¡Conexión exitosa!");
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return connection; // Devuelve la conexión (puede ser null si hubo error)
    }
}