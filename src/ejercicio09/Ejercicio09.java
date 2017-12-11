package ejercicio09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Ejercicio09 {

	public static void main(String[] args) {
		select(ejercicio08.Ejercicio08.pideInt("Numero de departamento"));
	}

	public static void select(int departamento) {
		String sql = "SELECT * FROM empleados WHERE dept_no=?";
		Connection conexion = getConnection("mysql");
		ResultSet rs = null;
		PreparedStatement pst = null;
		boolean existe=false;
		int numEmpleados=0;
		try {
			pst = conexion.prepareStatement(sql);
			pst.setInt(1, departamento);
			rs = pst.executeQuery();
			System.out.println("Estos son los datos del departamento " + departamento);
			while (rs.next()) {
				existe=true;
				System.out.println("Numero: " + rs.getInt(1) + " Apellido: " + rs.getString(2) + " Oficio: "
						+ rs.getString(3) + " Director " + rs.getInt(4) + " Fecha alta: " + rs.getDate(5) + " Salario: "
						+ rs.getFloat(6) + " comision: " + rs.getFloat(7) + " numero de departamento: " + rs.getInt(8));
				numEmpleados++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (!existe) {
			System.out.println("El departamento no existe");
		} else {
			rs = null;
				System.out.println("Hay " + numEmpleados + " empleados en el departamento ");
			
			sql = "SELECT AVG(salario) FROM empleados WHERE dept_no="+departamento;
			pst = null;
			try {
				//TODO Corregir
				pst = conexion.prepareStatement(sql);
				pst.setInt(1, departamento);
				rs = pst.executeQuery();
				System.out.println("La media de sueldo del departamento es " + rs.getFloat(1));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Selecciona los numeros de departamento y los guarda en un ArrayList
	 * 
	 * @return numeros obtenidos
	 */
	private static ArrayList<Integer> selectNumDept() {
		ArrayList<Integer> departamentos = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/acadt", "root", "");
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT num_depto FROM depaqrtamentos");

			while (rs.next()) {
				departamentos.add(rs.getInt(1));
			}
			conexion.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return departamentos;
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
