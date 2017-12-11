package ejercicio11;

import java.util.Scanner;

public class Ejercicio11 {
	public static void main(String[] args) {
		menu();
	}

	private static void menu() {
		int opcion=0;
		do {
			System.out.println("---MENU---");
			System.out.println("1.-Introducir productos");
			System.out.println("2.-Introducir clientes");
			System.out.println("3.-Salir");
			opcion=pideInt("opcion");
			switch (opcion) {
			case 1:
				introducirProducto();
				break;
			case 2:
				introducirCliente();
				break;
			case 3:
				System.out.println("FIN DEL PROGRAMA");
				break;

			default:
				System.out.println("Te has equivocado");
				break;
			}
		}while(opcion!=3);
		
	}
	private static void introducirCliente() {
		// TODO Auto-generated method stub
		
	}

	private static void introducirProducto() {
		// TODO Auto-generated method stub
		
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
}
