package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Usuario {
    private String username;
    private String password;

    private static final String ALGORITHM = "AES";
    private static final String KEY = "1234567890123456"; // Debe ser de 16 caracteres para AES

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void insert() {
        Connection conexion = NoteSphereDB.connect();

        try {
            Statement stmt = conexion.createStatement();
            String encryptedPassword = encrypt(this.password); // Encriptar la contraseña
            String query = "INSERT INTO usuario(username, password) VALUES ('" + this.username + "', '" + encryptedPassword + "');";
            stmt.execute(query);
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al insertar los datos: " + e.getMessage());
        }
    }

    public static Usuario getUsuarioByUsername(String username) {
        Connection conexion = NoteSphereDB.connect();
        try {
            Statement stmt = conexion.createStatement();
            String query = "SELECT username, password FROM usuario WHERE username='" + username + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String encryptedPassword = rs.getString("password");
                String decryptedPassword = decrypt(encryptedPassword); // Desencriptar la contraseña
                return new Usuario(rs.getString("username"), decryptedPassword);
            }

            stmt.close();
            conexion.close();
            return null;
        } catch (SQLException e) {
            System.err.println("Usuario no encontrado: " + e.getMessage());
        }

        return null;
    }

    // Encriptar el texto
    private static String encrypt(String data) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return java.util.Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar", e);
        }
    }

    // Desencriptar el texto
    private static String decrypt(String encryptedData) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedData = cipher.doFinal(java.util.Base64.getDecoder().decode(encryptedData));
            return new String(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar", e);
        }
    }

    public String getPassword() {
        return this.password;
    }
    
    public String getUsername() {
        return this.username;
    }
}
