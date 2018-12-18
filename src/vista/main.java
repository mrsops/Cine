package vista;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import controlador.ControlCine;
import hilos.CompraEntradaPelicula;
import modelo.Butaca;
import modelo.Butaca.Estado;
import modelo.Pelicula;
import modelo.Sala;
import modelo.Sesion;

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
		String nombreSesion, nombrePelicula;
		
		int opcio;
			//...
			// IMPLEMENTAR CODI ACÍ
			//...

			do{
				opcio = menu();

				switch(opcio){

				case 1: //Crear SALA
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					System.out.print("Introduce el numero de la sala: ");
					numSala = entrada.nextInt();
					entrada.nextLine();
					if(numSala==0||numSala>5) {
						System.out.println("Numero de sala incorrecto, solo salas de 1 a 5");
						break;
					}
					sala = cine.crearSala(numSala);//Creamos una nueva sala
					if(sala!=null) {
						sala.setNumSala(numSala);
						if(cine.modificarSala(sala)) { //Si nos permite crear la sala (todos los datos correctos)
							cine.nuevaSala(sala);  //Añadimos la sala al cine
							System.out.println("Sala creada satisfactoriamente");
						}else { // Si nos salimos del menu para la creacion de la sala, mostramos mensaje
							System.out.println("No se ha podido crear la nueva sala");
						}
					}else { //Si existe una sala con el mismo numero mostramos error
						System.out.println("Ya existe una sala con la misma numeracion");
					}
					break;

				case 2:	//Modificar SALA
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					cine.mostrarSalas();
					System.out.print("Intoduce el numero de la sala a modificar: ");
					numSala = entrada.nextInt();
					entrada.nextLine();
					if (cine.buscarSala(numSala)!=null) {
						sala = cine.buscarSala(numSala);
						cine.modificarSala(sala);
					}else {
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
					if (cine.buscarSala(numSala)!=null) {
						sala = cine.buscarSala(numSala);
						if(cine.borrarSala(sala)) {
							System.out.println("Se ha borrado la sala satisfactoriamente");
						}else {
							System.out.println("No se pudo borrar la sala por que existen reservas para esta sala en al menos una sesion");
						}
					}else {
						System.out.println("No se ha encontrado la sala especificada");
					}
					break;
				case 4:	//Crear SESSIO
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					
					System.out.print("Introduce el nombre para la sesion: ");
					nombreSesion = entrada.nextLine();
					sesion = cine.crearSesion(nombreSesion);
					if(sesion!=null) { //Si se ha podido crear con exito...
						sesion.setNombreSesion(nombreSesion);
						if(cine.modificarSesion(sesion)) { //Si nos permite crear la sesion (todos los datos correctos)
							cine.nuevaSesion(sesion);  //Añadimos la sesion al cine
						}else {
							System.out.println("No se ha podido crear la nueva sesion");
						}
					}else {
						System.out.println("El nombre de la sesion ya existe");
					}
	
					break;

				case 5: //Modifica SESSIO
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					cine.mostrarSesiones();
					System.out.print("Introduce el nombre de sesion a modificar: ");
					nombreSesion = entrada.nextLine();
					if(cine.buscarSesion(nombreSesion)!=null) { //Si existe la sesion, la modificamos
						cine.modificarSesion(cine.buscarSesion(nombreSesion));
					}else { // Si no existe mostramos error
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
					if(cine.buscarSesion(nombreSesion)!=null) { //Si existe la sesion y no tiene reservas, la elimina;
						if(cine.borrarSesion(cine.buscarSesion(nombreSesion))) {
							System.out.println("Se ha eliminado la sesion satisfactoriamente");
						}else {
							System.out.println("No se pudo eliminar debido a que existen reservas para esta sesion");
						}
					}else { // Si no existe mostramos error
						System.out.println("La sesion introducida no existe");
					}
					break;


				case 7: //Crear PELICULA
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					
					
					pelicula = new Pelicula();  //Creamos la pelicula
					if(cine.modificarPelicula(pelicula)) { //Si nos permite crear la pelicula (Al menos el nombre correcto)
						cine.nuevaPelicula(pelicula);  //Añadimos la pelicula al cine
					}else {
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
					if(cine.buscarPelicula(nombrePelicula)!=null) {
						cine.modificarPelicula(cine.buscarPelicula(nombrePelicula));
					}else {
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
					if(cine.buscarPelicula(nombrePelicula)!=null) { //Si existe la pelicula y no tiene reservas, la elimina;
						if(cine.borrarPelicula(cine.buscarPelicula(nombrePelicula))) {
							System.out.println("Se ha eliminado la pelicla satisfactoriamente");
						}
					}else { // Si no existe mostramos error
						System.out.println("La pelicula introducida no existe");
					}
					break;

				case 10: //Associar PELICULA a SESSIO 
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					String decision;
					cine.mostrarSesiones();
					System.out.print("Introduce el nombre de sesion que quieres asociar: ");
					nombreSesion = entrada.nextLine();
					if(cine.buscarSesion(nombreSesion)!=null) {
						sesion=cine.buscarSesion(nombreSesion);
						if(!sesion.tieneReservas()) {
						System.out.print("Deseas asociar una pelicula a la sesion?: ");
						decision = entrada.nextLine();
						if(decision.equals("s") || decision.equals("S") || decision.equals("SI") || decision.equals("si")) {
							cine.mostrarPeliculas();
							System.out.print("Introduce el nombre de la pelicula a asociar: ");
							nombrePelicula = entrada.nextLine();
							if(cine.buscarPelicula(nombrePelicula)!=null) {
								sesion.setPelicula(cine.buscarPelicula(nombrePelicula));
							}else {
								System.out.println("Nombre de pelicula incorrecto");
							}
						}
						
						System.out.print("Deseas asociar una sala a la sesion?: ");
						decision = entrada.nextLine();
						if(decision.equals("s") || decision.equals("S") || decision.equals("SI") || decision.equals("si")) {
							cine.mostrarSalas();
							System.out.print("Introduce el numero de la sala a asociar: ");
							numSala = entrada.nextInt();
							if(cine.buscarSala(numSala)!=null) {
								sesion.setSala(cine.buscarSala(numSala));
							}
						}
					}else {
						System.out.println("Esta sesion no se puede modificar debido a que ya se han producido reservas");
					}
					}else {
						System.out.println("Sesion no encontrada");
					}
					
					
					break;

				case 11: //Comprar ENTRADA
					//...
					// IMPLEMENTAR CODI ACÍ
					//...
					int fila;
					int numButaca;
					Calendar horaActual = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
					Date date = horaActual.getTime();
					System.out.println("Hora actual: "+sdf.format(date)); //Mostramos la hora actual
					
					ArrayList<Sesion> sesionesSemana = cine.sesionesSemana();
					for(Sesion s: cine.getSesiones()) { //Mostramos todas las sesiones de la semana
						System.out.println(s);
					}
					
					if(sesionesSemana.size()>0) {
						System.out.print("Selecione la sesion para comprar su entrada: ");
						nombreSesion = entrada.nextLine();
						if(cine.buscarSesion(nombreSesion)!=null) {
							sesion = cine.buscarSesion(nombreSesion);
							
							//MOSTRAMOS EL MAPA DE BUTACAS
							String selecionarButaca;
							do {
							sesion.mostrarMapa();
							System.out.print("Desea seleccionar una butaca? (s/n): ");
							selecionarButaca = entrada.nextLine();
							if(selecionarButaca.equals("s") ||selecionarButaca.equals("si") ||selecionarButaca.equals("S") ||selecionarButaca.equals("SI")) {
								Butaca butaca = seleccionarButaca();
								butaca = cine.buscarButacaEnSesion(sesion, butaca);
								
								//Butaca butaca = sesion.getMapaSesion()[fila][numButaca];
								if(butaca.verificaButaca()) {
									System.out.print("Desea reservar esta butaca? (s/n): ");
									String reservarButaca = entrada.nextLine();
									if(selecionarButaca.equals("s") ||selecionarButaca.equals("si") ||selecionarButaca.equals("S") ||selecionarButaca.equals("SI")) {
										butaca.reservaButaca();
									}
								}else {
									if(butaca.getDisponibilidad().equals(Estado.RESERVANDO)) {
										System.out.print("La butaca esta en proceso de reserva. Desea liberar esta butaca? (s/n): ");
										String liberarButaca = entrada.nextLine();
										if(liberarButaca.equals("s") ||liberarButaca.equals("si") ||liberarButaca.equals("S") ||liberarButaca.equals("SI")) {
											butaca.liberarButaca();
										}

									}else {
										System.out.println("La butaca esta ocupada");
									}
								}
							}else { //No se quiso seleccionar una nueva butaca
								ArrayList<Butaca> butacasSeleccionadas =new ArrayList<>();
								for (int i = 0; i < sesion.getMapaSesion().length; i++) {
									for (int j = 0; j < sesion.getMapaSesion()[i].length; j++) {
										if(sesion.getMapaSesion()[i][j].getDisponibilidad().equals(Estado.RESERVANDO)) {
											butacasSeleccionadas.add(sesion.getMapaSesion()[i][j]);
										}
									}
								}
								
								if(butacasSeleccionadas.size()>0) {
									sesion.mostrarMapa();
									System.out.print("Desea comprar estas entradas: ");
									String comprarEntradas = entrada.nextLine();
									if(comprarEntradas.equals("s") ||comprarEntradas.equals("si") ||comprarEntradas.equals("S") ||comprarEntradas.equals("SI")) {
										cine.generarTiquet(sesion);
								}

								}
								
							}
							
							
							}while(selecionarButaca.equals("s") || selecionarButaca.equals("si") ||selecionarButaca.equals("S") ||selecionarButaca.equals("SI"));
						}else {
							System.out.println("La sesion introducida no se encuentra");
						}
					}else {
						System.out.println("No hay sesiones esta semana");
					}
					
					System.out.println("");
					
					break;
					
				case 12: // Declaramos los datos para cada hilo y al finalizar, los lanzamos todos de golpe
					
					ArrayList<CompraEntradaPelicula> listaHilos = new ArrayList<>(); //Lista de los hilos que luego intentaran comprar las entradas
					//Recojer los datos para un hilo
					String nSesion = seleccionarSession(); //La misma sesion para todos los hilos
					decision ="";
					do {
						System.out.print("Desea crear nuevo hilo?: ");
						decision=entrada.nextLine();
						if(decision.equals("s") || decision.equals("S") ||decision.equals("si") ||decision.equals("SI")) {
							CompraEntradaPelicula hilo = llenarHiloDatos(cine, nSesion, seleccionarVariasButacas());
							listaHilos.add(hilo);
						}
					}while(decision.equals("s") ||decision.equals("S") ||decision.equals("si") ||decision.equals("SI"));
					
					for (CompraEntradaPelicula compraHilo : listaHilos) { //Arrancamos todos los hilos de golpe
						compraHilo.start();
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
				System.out.println("11. Comprar ENTRADA");
				System.out.println("12. Compra ENTRADAS HILOS ()");
				System.out.println();
				System.out.println("0. Salir de la Aplicación CINE");

				String stropcio = s.nextLine();
				opcio=Integer.parseInt(stropcio);
				if (opcio <0 || opcio >11) {
					limpiar();
					System.out.println("Opcion Incorrecta...");
					System.out.println();
				}
			}while (opcio < 0 || opcio > 11);

			return opcio;
		}
		
		
		
		public static void limpiar() {
			for (int i =0; i<50; i++) {
				System.out.println();
			}
		}
		
		public static String seleccionarSession() {
			Scanner entrada = new Scanner(System.in);
			System.out.print("Selecione la sesion para comprar su entrada: ");
			String nombreSesion = entrada.nextLine();
			entrada.close();
			return nombreSesion;
		}
		
		public static Butaca seleccionarButaca() {
			Scanner entrada = new Scanner(System.in);
			System.out.print("Introduce la fila de la butaca (Empezando por arriba): ");
			int fila = entrada.nextInt();
			entrada.nextLine();
			System.out.print("Introduce la butaca de la fila (Empezando por la izquierda): ");
			int numButaca = entrada.nextInt();
			entrada.nextLine();
			entrada.close();
			return new Butaca(fila, numButaca);
			
		}
		
		public static ArrayList<Butaca> seleccionarVariasButacas(){
			Scanner entrada = new Scanner(System.in);
			String decision="";
			ArrayList<Butaca> butacas = new ArrayList<>();
			do {
				System.out.print("Desea seleccionar una nueva butaca?: ");
				decision = entrada.nextLine();
				if(entrada.equals("s") || entrada.equals("S") || entrada.equals("si") || entrada.equals("SI")) {
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
			entrada.close();
			return butacas;
		}
		
		public static CompraEntradaPelicula llenarHiloDatos(ControlCine cine, String nSesion, ArrayList<Butaca> butacas) {
			Scanner entrada = new Scanner(System.in);
			System.out.print("Introduce el nombre del hilo: ");
			String nombreHilo = entrada.nextLine();
			return new CompraEntradaPelicula(nombreHilo, cine , nSesion, butacas);
			
			
		}
	}
