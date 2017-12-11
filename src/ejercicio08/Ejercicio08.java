package ejercicio08;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Este programa inserta una fila en la tabla empleados si los datos son
 * correctos y si no no lo inserta
 * 
 * @author Joaquin Alonso Perianez
 *
 */
public class Ejercicio08 {
	public static void main(String[] args) {
		insertarEmpleado();
	}

	/**
	 * Inserta un empleado en una base de datos mysql Obtiene la conexion con
	 * {@link #getConnection(String)} Pide los datos necesarios con los métodos
	 * {@link #pideInt(String)} y {@link #pideString(String)}, Comprueba que estos
	 * datos esten introducidos correctamente obteniendo datos de
	 * {@link #selectNumDept()} y {@link #selectNumEmp()}
	 * 
	 * @see #getConnection(String)
	 * @see #selectNumDept()
	 * @see #selectNumEmp()
	 * @see #pideInt(String)
	 * @see #pideString(String)
	 */
	private static void insertarEmpleado() {
		int numero = 0;
		String apellido = "", oficio = "";
		int salario = 0, comision = 0, numDept = 0, dir = 0;
		Statement st = null;
		try {
			Connection connection = getConnection("mysql");
			st = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		numero = pideInt("numero de empleado");
		apellido = pideString("apellido");
		oficio = pideString("oficio");
		salario = pideInt("salario");
		comision = pideInt("comision");
		numDept = pideInt("numDept");
		dir = pideInt("director");
		ArrayList<Integer> departamentos = selectNumDept(), empleados = selectNumEmp();
		if (departamentos.contains(numDept)) {
			if (!empleados.contains(numero)) {
				if (empleados.contains(dir)) {
					if (salario > 0) {

						
						String insert = "INSERT INTO empleados (emp_no, apellido, oficio, dir, fecha_alt, salario, comision, dept_no) VALUES("
								+ numero + ",'" + apellido + "','" + oficio + "'," + dir + ",sysdate()," + (float)salario + ","
								+ (float)comision + "," + numDept + ");";
						try {
							int filas = st.executeUpdate(insert);
							System.out.println("Datos insertados " + filas + " filas afectadas");
						} catch (SQLException e) {
							System.out.println("Datos no insertados error de SQL");
							e.printStackTrace();
						}
					} else {
						System.out.println("Datos no insertados: El salario debe ser mayor que 0");
					}
				} else {
					System.out.println("Datos no insertados: El numero del director no existe");
				}
			} else {
				System.out.println("Datos no insertados: El numero de empleado ya existe");
			}
		} else {
			System.out.println("Datos no insertados: El numero de departamento no existe");
		}
	}

	/**
	 * Este metodo pide un String al usuario, recibe un String para indicar el tipo
	 * de dato esperado Si se da una excepcion se vuelve a pedir
	 * 
	 * @param string
	 *            dato esperadp
	 * @return dato solicitado
	 */
	private static String pideString(String string) {
		String palabra = "";
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce " + string);
		boolean correcto = false;
		do {
			try {
				palabra = sc.nextLine();
				correcto = true;
			} catch (Exception e) {
				System.out.println("Introduce de nuevo " + palabra);
			}
		} while (!correcto);
		return palabra;
	}

	/**
	 * Este metodo pide un entero al usuario por teclado Si se da una excepción se
	 * vuelve a pedir
	 * 
	 * @param tipo
	 *            indica el dato a introducir
	 * @return dato introducido
	 */
	public static int pideInt(String tipo) {
		int entero = 0;
		Scanner sc = new Scanner(System.in);
		boolean correcto = false;
		System.out.println("Introduce " + tipo);
		do {
			try {
				entero = sc.nextInt();
				correcto = true;
			} catch (NumberFormatException e) {
				System.out.println("Introduce de nuevo " + tipo);
			}
		} while (!correcto);
		return entero;
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
			ResultSet rs = st.executeQuery("SELECT dept_no FROM departamentos");

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
	 * Selecciona los numeros de empleado y los guarda en un ArrayList
	 * 
	 * @return numeros obtenidos
	 */
	private static ArrayList<Integer> selectNumEmp() {
		ArrayList<Integer> empleados = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/acadt", "root", "");
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT emp_no FROM empleados");

			while (rs.next()) {
				empleados.add(rs.getInt(1));
			}
			conexion.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empleados;
	}
}
