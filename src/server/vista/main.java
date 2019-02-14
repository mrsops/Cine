package server.vista;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import server.controlador.ControlCine;
import server.hilos.CompraEntradaPelicula;
import server.hilos.ServerListener;
import server.hilos.ServerThread;
import server.modelo.Butaca;
import server.modelo.Pelicula;
import server.modelo.Sala;
import server.modelo.Sesion;

public class main {
	private static ControlCine cine;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner entrada = new Scanner(System.in);
		int numSala;
		Sesion sesion;
		Sala sala;
		Pelicula pelicula;
		cine = new ControlCine();
		ServerListener serverListener=new ServerListener(cine);
		String nombreSesion, nombrePelicula;

		int opcio;
		//...
		// IMPLEMENTAR CODI ACÍ
		//...

		do{
			opcio = menu();

			switch(opcio) {

				case 1: //Crear SALA
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					System.out.print("Introduce el numero de la sala: ");
					numSala = entrada.nextInt();
					entrada.nextLine();
					if (numSala == 0 || numSala > 5) {
						System.out.println("Numero de sala incorrecto, solo salas de 1 a 5");
						break;
					}
					sala = cine.crearSala(numSala);//Creamos una nueva sala
					if (sala != null) {
						sala.setNumSala(numSala);
						if (cine.modificarSala(sala)) { //Si nos permite crear la sala (todos los datos correctos)
							cine.nuevaSala(sala);  //Añadimos la sala al cine
							System.out.println("Sala creada satisfactoriamente");
						} else { // Si nos salimos del menu para la creacion de la sala, mostramos mensaje
							System.out.println("No se ha podido crear la nueva sala");
						}
					} else { //Si existe una sala con el mismo numero mostramos error
						System.out.println("Ya existe una sala con la misma numeracion");
					}
					break;

				case 2:    //Modificar SALA
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					cine.mostrarSalas();
					System.out.print("Intoduce el numero de la sala a modificar: ");
					numSala = entrada.nextInt();
					entrada.nextLine();
					if (cine.buscarSala(numSala) != null) {
						sala = cine.buscarSala(numSala);
						cine.modificarSala(sala);
					} else {
						System.out.println("No se ha encontrado la sala especificada");
					}
					break;

				case 3: //Esborrar SALA
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					cine.mostrarSalas();
					System.out.print("Intoduce el numero de la sala a borrar: ");
					numSala = entrada.nextInt();
					entrada.nextLine();
					if (cine.buscarSala(numSala) != null) {
						sala = cine.buscarSala(numSala);
						if (cine.borrarSala(sala)) {
							System.out.println("Se ha borrado la sala satisfactoriamente");
						} else {
							System.out.println("No se pudo borrar la sala por que existen reservas para esta sala en al menos una sesion");
						}
					} else {
						System.out.println("No se ha encontrado la sala especificada");
					}
					break;
				case 4:    //Crear SESSIO
					//...
					// IMPLEMENTAR CODI ACÍ
					//...

					System.out.print("Introduce el nombre para la sesion: ");
					nombreSesion = entrada.nextLine();
					sesion = cine.crearSesion(nombreSesion);
					if (sesion != null) { //Si se ha podido crear con exito...
						sesion.setNombreSesion(nombreSesion);
						if (cine.modificarSesion(sesion)) { //Si nos permite crear la sesion (todos los datos correctos)
							cine.nuevaSesion(sesion);  //Añadimos la sesion al cine
						} else {
							System.out.println("No se ha podido crear la nueva sesion");
						}
					} else {
						System.out.println("El nombre de la sesion ya existe");
					}

					break;

				case 5: //Modifica SESSIO
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					System.out.println(cine.mostrarSesiones());
					System.out.print("Introduce el nombre de sesion a modificar: ");
					nombreSesion = entrada.nextLine();
					if (cine.buscarSesion(nombreSesion) != null) { //Si existe la sesion, la modificamos
						cine.modificarSesion(cine.buscarSesion(nombreSesion));
					} else { // Si no existe mostramos error
						System.out.println("La sesion introducida no existe");
					}
					break;

				case 6: //Esborrar SESSSIO
					//...
					// IMPLEMENTAR CODI ACÍ
					//...

					cine.mostrarSesiones(); //Mostramos las sesiones
					System.out.print("Introduce el nombre de sesion a eliminar: ");
					nombreSesion = entrada.nextLine();
					if (cine.buscarSesion(nombreSesion) != null) { //Si existe la sesion y no tiene reservas, la elimina;
						if (cine.borrarSesion(cine.buscarSesion(nombreSesion))) {
							System.out.println("Se ha eliminado la sesion satisfactoriamente");
						} else {
							System.out.println("No se pudo eliminar debido a que existen reservas para esta sesion");
						}
					} else { // Si no existe mostramos error
						System.out.println("La sesion introducida no existe");
					}
					break;


				case 7: //Crear PELICULA
					//...
					// IMPLEMENTAR CODI ACÍ
					//...


					pelicula = new Pelicula();  //Creamos la pelicula
					if (cine.modificarPelicula(pelicula)) { //Si nos permite crear la pelicula (Al menos el nombre correcto)
						cine.nuevaPelicula(pelicula);  //Añadimos la pelicula al cine
					} else {
						System.out.println("No se ha podido crear la nueva pelicula");
					}


					break;

				case 8: //Modifica PELICULA
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					cine.mostrarPeliculas();
					System.out.print("Introduce el nombre de la pelicula a modificar: ");
					nombrePelicula = entrada.nextLine();
					if (cine.buscarPelicula(nombrePelicula) != null) {
						cine.modificarPelicula(cine.buscarPelicula(nombrePelicula));
					} else {
						System.out.println("No se ha encontrado la pelicula con dicho nombre");
					}


					break;

				case 9: //Esborrar PELICULA
					//...
					// IMPLEMENTAR CODI ACÍ
					//...

					cine.mostrarPeliculas(); //Mostramos las peliculas
					System.out.print("Introduce el nombre de sesion a eliminar: ");
					nombrePelicula = entrada.nextLine();
					if (cine.buscarPelicula(nombrePelicula) != null) { //Si existe la pelicula y no tiene reservas, la elimina;
						if (cine.borrarPelicula(cine.buscarPelicula(nombrePelicula))) {
							System.out.println("Se ha eliminado la pelicla satisfactoriamente");
						}
					} else { // Si no existe mostramos error
						System.out.println("La pelicula introducida no existe");
					}
					break;

				case 10: //Associar PELICULA a SESSIO
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					String decision;
					System.out.println(cine.mostrarSesiones());
					System.out.print("Introduce el nombre de sesion que quieres asociar: ");
					nombreSesion = entrada.nextLine();
					if (cine.buscarSesion(nombreSesion) != null) {
						sesion = cine.buscarSesion(nombreSesion);
						if (!sesion.tieneReservas()) {
							System.out.print("Deseas asociar una pelicula a la sesion?: ");
							decision = entrada.nextLine();
							if (decision.equals("s") || decision.equals("S") || decision.equals("SI") || decision.equals("si")) {
								cine.mostrarPeliculas();
								System.out.print("Introduce el nombre de la pelicula a asociar: ");
								nombrePelicula = entrada.nextLine();
								if (cine.buscarPelicula(nombrePelicula) != null) {
									sesion.setPelicula(cine.buscarPelicula(nombrePelicula));
								} else {
									System.out.println("Nombre de pelicula incorrecto");
								}
							}

							System.out.print("Deseas asociar una sala a la sesion?: ");
							decision = entrada.nextLine();
							if (decision.equals("s") || decision.equals("S") || decision.equals("SI") || decision.equals("si")) {
								cine.mostrarSalas();
								System.out.print("Introduce el numero de la sala a asociar: ");
								numSala = entrada.nextInt();
								if (cine.buscarSala(numSala) != null) {
									sesion.setSala(cine.buscarSala(numSala));
								}
							}
						} else {
							System.out.println("Esta sesion no se puede modificar debido a que ya se han producido reservas");
						}
					} else {
						System.out.println("Sesion no encontrada");
					}


					break;



				case 11: //Introducir datos de demo
					Sala salaDemo = new Sala(1, 10, 10, true);
					cine.nuevaSala(salaDemo);
					Pelicula peliculaDemo = new Pelicula("Avatar", "Americana", 120, "James Cameron", "Zoe Saldana", "Americanadas en el espacio", "Ciencia Ficcion", "+13");
					cine.nuevaPelicula(peliculaDemo);
					Calendar fecha = Calendar.getInstance();
					int diaMes = fecha.get(Calendar.DAY_OF_MONTH)+1;
					int mes=fecha.get(Calendar.MONTH);
					int año = fecha.get(Calendar.YEAR);
					fecha.set(año, mes, diaMes, 10, 15);
					BigDecimal precio = new BigDecimal(5);
					Sesion sesionDemo = new Sesion("sesion1", fecha, salaDemo, precio, peliculaDemo);
					cine.nuevaSesion(sesionDemo);
					System.out.println("Terminamos de añadir las demos");





					break;

				case 12: //Poner el servidor a la espera de aceptar clientes

					if (!serverListener.isAlive()){
						serverListener.start();
						System.out.println("Se ha movido  el servicio de escucha de peticiones");
					}else if(!serverListener.isPausaFlag()){
						serverListener.pause();
						System.out.println("Se ha detenido el servicio de escucha de peticiones");
					}else if(serverListener.isPausaFlag()){
						serverListener.unpause();
						System.out.println("Se ha continuado con la escucha del servicio");
					}

					break;
				default:
					//...
					// IMPLEMENTAR CODI ACÍ
					//...

			}
		}while(opcio!=0);

	}



	//*********************************************************
	//PAGAMENT D'UNA ENTRADA
	//static boolean pagamentEntrada(float preu){
	//...
	// IMPLEMENTAR CODI ACÍ
	//...
	//}


	//*********************************************************
	//VISUALITZA EL MENU PRINCIPAL
	public static int menu(){
		int opcio;
		Scanner s = new Scanner(System.in);

		do{
			System.out.println("MENU Aplicació CINE:");
			System.out.println("====================");
			System.out.println("1.  Crear SALA");
			System.out.println("2.  Modificar SALA");
			System.out.println("3.  Eliminar SALA");
			System.out.println();
			System.out.println("4.  Crear SESION");
			System.out.println("5.  Modificar SESION");
			System.out.println("6.  Eliminar SESION");
			System.out.println();
			System.out.println("7.  Crear PELICULA");
			System.out.println("8.  Modificar PELICULA");
			System.out.println("9.  Eliminar PELICULA");
			System.out.println();
			System.out.println("10. Associar PELICULA a SESSIO");
			System.out.println("11. Introducir datos de demo");
			System.out.println("12. Mover o parar servicio de excucha de conexiones de  cliente");

			System.out.println();
			System.out.println("0. Salir de la Aplicación CINE");

			String stropcio = s.nextLine();
			opcio=Integer.parseInt(stropcio);
			if (opcio <0 || opcio >12) {
				limpiar();
				System.out.println("Opcion Incorrecta...");
				System.out.println();
			}
		}while (opcio < 0 || opcio > 12);

		return opcio;
	}



	public static void limpiar() {
		for (int i =0; i<50; i++) {
			System.out.println();
		}
	}


	public static Butaca seleccionarButaca() {
		Scanner entrada = new Scanner(System.in);
		System.out.print("Introduce la fila de la butaca (Empezando por arriba): ");
		int fila = entrada.nextInt();
		entrada.nextLine();
		System.out.print("Introduce la butaca de la fila (Empezando por la izquierda): ");
		int numButaca = entrada.nextInt();
		entrada.nextLine();
		return new Butaca(fila, numButaca);

	}

	public static ArrayList<Butaca> seleccionarVariasButacas(){
		Scanner entrada = new Scanner(System.in);
		String decision="";
		ArrayList<Butaca> butacas = new ArrayList<>();
		do {
			System.out.print("Desea seleccionar una nueva butaca?: ");
			decision = entrada.nextLine();
			if(decision.equals("s") || decision.equals("S") || decision.equals("si") || decision.equals("SI")) {
				Butaca butaca = seleccionarButaca();
				butacas.add(butaca);
			}else {
				if(butacas.size()>0) {
					System.out.println("Se han seleccionado estas butacas");
					for (Butaca butaca : butacas) {
						System.out.println("Fila: "+butaca.getNumfila()+ "  NumButaca: "+butaca.getNumButaca());
					}
				}
			}
		}while(decision.equals("s") || decision.equals("si") || decision.equals("S") || decision.equals("SI"));
		return butacas;
	}

	public static CompraEntradaPelicula llenarHiloDatos(ControlCine cine, String nSesion, ArrayList<Butaca> butacas) {
		Scanner entrada = new Scanner(System.in);
		System.out.print("Introduce el nombre del hilo: ");
		String nombreHilo = entrada.nextLine();
		return new CompraEntradaPelicula(nombreHilo, cine , nSesion, butacas);


	}

	public static void pararServicioEscucha(ServerListener st){
		Socket scli=null;
		try {
			st.interrupt();
			scli =  new Socket("localhost", 9000);
			scli.close();
		}catch (IOException e){
			System.out.println("Deteniendo servicio");
		}

	}


}
