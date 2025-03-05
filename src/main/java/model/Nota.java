package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Nota {

	private Integer id;
	private String username;
	private String titulo;
	private String nota;
	private LocalDateTime fecha;
	private String fechaFormateada;

	public Nota(Integer id, String username, String titulo, String nota, LocalDateTime fecha) {
		this.id = id;
		this.username = username;
		this.titulo = titulo;
		this.nota = nota;
		this.fecha = fecha;
	}

	public void insert() {
		Connection conexion = NoteSphereDB.connect();

		try {
			Statement stmt = conexion.createStatement();
			String query = "INSERT INTO nota(username, titulo, nota, fecha) VALUES ('"
					+ this.username + "', '"
					+ this.titulo + "', '"
					+ this.nota + "', '"
					+ this.fecha.toString() + "');";
			System.out.println(query);
			stmt.execute(query);
			stmt.close();
			conexion.close();
		} catch (SQLException e) {
			System.err.println("Error al insertar los datos: " + e.getMessage());
		}

	}

	public static Nota getNotaById(Integer notaId) {
		Nota nota = null;
		Connection conexion = NoteSphereDB.connect();

		String sql = "SELECT id, username, titulo, nota, fecha FROM nota WHERE id = " + notaId;

		try (Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			if (rs.next()) {
				Integer id = rs.getInt("id");
				String username = rs.getString("username");
				String titulo = rs.getString("titulo");
				String notaC = rs.getString("nota");
				LocalDateTime fecha = rs.getTimestamp("fecha").toLocalDateTime();

				nota = new Nota(id, username, titulo, notaC, fecha);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		} finally {
			try {
				if (conexion != null) {
					conexion.close();
				}
			} catch (SQLException e) {
				System.err.println("Error al cerrar la conexión: " + e.getMessage());
			}
		}
		return nota;
	}

	public static ArrayList<Nota> getNotasDesde(String username, LocalDateTime time) {
		ArrayList<Nota> notas = new ArrayList<>();
		Connection conexion = NoteSphereDB.connect();
	
		try {
			Statement stmt = conexion.createStatement();
			String sql = "SELECT id, username, titulo, nota, fecha FROM nota WHERE username='" + username + "' AND fecha >= '" + Timestamp.valueOf(time) + "'";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
	
			while (rs.next()) {
				notas.add(new Nota(
						rs.getInt("id"),
						rs.getString("username"),
						rs.getString("titulo"),
						rs.getString("nota"),
						rs.getTimestamp("fecha").toLocalDateTime()));
			}
	
			rs.close(); // Cerramos el ResultSet
			stmt.close();
			conexion.close();
		} catch (SQLException e) {
			System.err.println("Error al obtener las notas a partir de la fecha: " + e.getMessage());
		}
		System.out.println(notas);;
		return notas;
	}
	
	@Override
	public String toString() {
		return "Nota [id=" + id + ", username=" + username + ", titulo=" + titulo + ", nota=" + nota + ", fecha="
				+ fecha + ", fechaFormateada=" + fechaFormateada + ", getId()=" + getId() + ", getUsername()="
				+ getUsername() + ", getTitulo()=" + getTitulo() + ", getNota()=" + getNota() + ", getFecha()="
				+ getFecha() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public static ArrayList<Nota> getNotas(String username) {
		ArrayList<Nota> notas = new ArrayList();
		Connection conexion = NoteSphereDB.connect();

		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT id, username, titulo, nota, fecha FROM nota WHERE username='" + username + "'");

			while (rs.next()) {
				notas.add(new Nota(rs.getInt("id"), rs.getString("username"), rs.getString("titulo"),
						rs.getString("nota"), rs.getTimestamp("fecha").toLocalDateTime()));
			}
			stmt.close();
			conexion.close();
		} catch (SQLException e) {
			System.err.println("Error al obtener las notas: " + e.getMessage());
		}
		return notas;
	}

	public void delete() {
		Connection conexion = NoteSphereDB.connect();
		try {
			Statement stmt = conexion.createStatement();
			stmt.execute("DELETE FROM nota WHERE id=" + this.id);
			stmt.close();
			conexion.close();
		} catch (SQLException e) {
			System.err.println("Error al borrar la nota: " + e.getMessage());

		}
	}

	public void update() {
		Connection conexion = NoteSphereDB.connect();
		try {
			Statement stmt = conexion.createStatement();
			stmt.execute("UPDATE nota SET titulo='" + this.titulo + "', nota='" + this.nota + "' WHERE id=" + this.id);
			stmt.close();
			conexion.close();
		} catch (SQLException e) {
			System.err.println("Error al editar la nota: " + e.getMessage());

		}
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNota() {
		return this.nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getFecha() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return fecha.format(formato);

	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

}