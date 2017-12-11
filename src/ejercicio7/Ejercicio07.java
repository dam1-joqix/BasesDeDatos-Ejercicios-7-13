package ejercicio7;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Este programa muestra las tablas que tiene la tabla empleados de la base de
 * datos creada con sqlite Para ello llama al metodo
 * {@link #getColumnaTabla(String, String, String)}: Este metodo llama a
 * {@link #getConnection(String)} para conectarse a la base de datos sqlite y
 * despues muestra la informacion de las columnas de la tabla
 * 
 * @author Joaquin Alonso Perianez
 *
 */
public class Ejercicio07 {

	public static void main(String[] args) {
		getColumnaTabla("sqlite", null, "empleados");
	}

	/**
	 * Muestra las columnas que tiene una tabla de una base de datos Obtendra la
	 * conexion con el metodo {@link #getConnection(String)}
	 * 
	 * 
	 * @param bd
	 *            sistema gestor de bases de datos usado para crear la base de datos
	 * @param esquema
	 *            esquema de la base de datos
	 * @param tabla
	 *            tabla de la que queremos obtener información
	 * @see #getConnection(String)
	 */
	public static void getColumnaTabla(String bd, String esquema, String tabla) {
		Connection conection = getConnection(bd);
		try {
			DatabaseMetaData dbmd = conection.getMetaData();
			ResultSet columnas = null;
			columnas = dbmd.getColumns(null, esquema, tabla, null);
			System.out.println("Estas son las columnas para la tabla " + tabla);
			System.out.println("------------------------------------------");
			while (columnas.next()) {
				String nombreCol = columnas.getString("COLUMN_NAME");
				String tipoCol = columnas.getString("TYPE_NAME");
				String tamanyoCol = columnas.getString("COLUMN_SIZE");
				String nula = columnas.getString("IS_NULLABLE");
				if (nula.equalsIgnoreCase("YES")) {
					nula = "SÍ";
				} else {
					nula = "NO";
				}
				System.out.println("Columna: " + nombreCol + ", Tipo: " + tipoCol + ", Tamaño: " + tamanyoCol
						+ ", ¿Puede ser Nula? " + nula);
			}
			conection.close();
		} catch (SQLException e) {
			System.out.println("Error de SQL");
			e.printStackTrace();
		}
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
}
