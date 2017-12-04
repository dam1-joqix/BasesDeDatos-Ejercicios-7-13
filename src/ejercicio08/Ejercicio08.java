package ejercicio08;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio08 {
	public static void main(String[] args) {
		insertarEmpleado();
	}

	private static void insertarEmpleado() {
		Connection connection=getConnection("mysql");
		
	}
	/**
	 * El metodo devuelve una conexion dado un string que será el nombre de la base
	 * de datos. Los nombres aceptados son <list>
	 * <li>oracle</li>
	 * <li>mysql</li>
	 * <li>sqlite</li> </list> <b>Si da error en alguno ojo a los getConnection
	 * </b><br>
	 * </br>
	 * 
	 * @param baseDatos
	 *            nombre de la base de datos
	 * @return conexion obtenida
	 */
	public static Connection getConnection(String baseDatos) {
		Connection conexion = null;

		String database = baseDatos.toLowerCase();
		try {

			switch (database) {
			case "oracle":
				Class.forName("oracle.jdbc.OracleDriver");
				conexion = DriverManager.getConnection("jdbc:oracle:thin:acadt/12345@//localhost/XE");
				break;
			case "mysql":
				Class.forName("com.mysql.jdbc.Driver");
				conexion = DriverManager.getConnection("jdbc:mysql://localhost/acadt", "root", "");

				break;
			case "sqlite":
				Class.forName("org.sqlite.JDBC");
				conexion = DriverManager.getConnection("jdbc:sqlite:src/sqlite/ejercicio1.db");
				break;
			default:
				System.out.println("No se puede establecer la conexión a la base de datos");
				break;
			}
		} catch (ClassNotFoundException e) {
			System.out.println("clase no encontrada");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("error sql");
			e.printStackTrace();
		}
		return conexion;
	}
	
	private static void select(String campo1, String campo2, String campo3, int num_dpto) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/acadt", "root", "");
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT " + campo1 + ", " + campo2 + ", " + campo3 + " FROM empleados where dept_no=" + num_dpto);
			while (rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getInt(3));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
