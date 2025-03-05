package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class NoteSphereDB {

    private static final String SERVER = "dpg-cv498v8gph6c73acmorg-a.frankfurt-postgres.render.com"; // Cambia a tu host
    private static final String DATABASE = "notesphere_r2hm"; // Nombre de la base de datos
    private static final String USER = "notesphere_r2hm_user"; // Tu usuario
    private static final String PASSWORD = "rDHKMUSZglM6kniqZjC1KEGBItSQedZq"; // Tu contraseña

    // Método para establecer conexión con la base de datos
    public static Connection connect() {
        Connection connection = null;
        try {
            // Construir la URL de conexión para PostgreSQL
            String url = "jdbc:postgresql://" + SERVER + ":5432/" + DATABASE;

            // Establecer la conexión
            connection = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("¡Conexión exitosa!");

        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return connection; // Devuelve la conexión (puede ser null si hubo error)
    }
}
