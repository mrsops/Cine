package server.controlador;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import server.modelo.Butaca;
import server.modelo.Butaca.Estado;
import server.modelo.Pelicula;
import server.modelo.Sala;
import server.modelo.Sesion;

public class ControlCine {
	private boolean turno;
	private ArrayList<Sala> salas;
	private ArrayList<Pelicula> peliculas;
	private ArrayList<Sesion> sesiones;
	public ControlCine() {
		this.salas = new ArrayList<>();
		this.peliculas = new ArrayList<>();
		this.sesiones = new ArrayList<>();
		this.turno=true;
	}

	public void nuevaSala(Sala s) {
		this.salas.add(s);
	}

	public void nuevaPelicula(Pelicula p){
		this.peliculas.add(p);
	}

	public void nuevaSesion(Sesion s) {
		this.sesiones.add(s);
	}

	public Sala buscarSala(int numSala) {
		for(Sala s:this.salas) {
			if(s.getNumSala()== numSala) {
				return s;
			}
		}
		return null;
	}

	public Pelicula buscarPelicula(String nombre) {
		for(Pelicula p: this.peliculas) {
			if (p.getNombrePelicula().equals(nombre)) {
				return p;
			}
		}
		return null;
	}

	public void mostrarSalas() {
		for (Sala s: this.salas) {
			System.out.println(s);
		}
	}

	public String mostrarSesiones() {
		String sesiones="";
		for (Sesion s: this.sesiones) {
			sesiones+=s+"\n";
		}
		return sesiones;
	}

	public void mostrarPeliculas() {
		for(Pelicula p:this.peliculas) {
			System.out.println(p);
		}
	}

	public Sesion buscarSesion(String nombreSesion) {
		for (Sesion s: this.sesiones) {
			if(s.getNombreSesion().equals(nombreSesion)) {
				return s;
			}
		}
		return null;
	}

	public ArrayList<Sala> getSalas() {
		return salas;
	}

	public void setSalas(ArrayList<Sala> salas) {
		this.salas = salas;
	}

	public ArrayList<Pelicula> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(ArrayList<Pelicula> peliculas) {
		this.peliculas = peliculas;
	}

	public ArrayList<Sesion> getSesiones() {
		return sesiones;
	}

	public void setSesiones(ArrayList<Sesion> sesiones) {
		this.sesiones = sesiones;
	}

	/**
	 *
	 * @param s Sala a borrar
	 * @return Si devuelve true, significa que la sala se elimino satisfactoriamente, si no, significa que fue imposible eliminarla debido a que existe reservas para esa sala en almenos una sesion
	 */
	public boolean borrarSala(Sala s) {
		if(validoModificarSala(s)) { //Buscamos si hay alguna reserva en cualquier sesion para esa sala
			for (Sesion sesion:this.sesiones) {
				if(sesion.getSala().equals(s)) { //Si hay alguna sesion que este creada, pero sin reservas, podemos borrar la sala
					sesion.borrarSala(); //Establecemos la sala de la sesion a Null para borrarla
				}
			}
			this.salas.remove(s); //Cuando terminamos de borrar la sala de todas las sesiones, la borramos de la lista de salas.
			return true; //Devolvemos true para indicar que se ha borrado la sala
		}


		return false; //Devolvemos false para indicar que no se pudo borrar la sala
	}


	private boolean validoModificarSala(Sala s) {
		for(Sesion sesion:this.sesiones) {
			Sala salaSesion = sesion.getSala();
			if(salaSesion!=null) {
				if(salaSesion.equals(s) && sesion.tieneReservas()) {
					return false;
				}
			}

		}
		return true;
	}

	public boolean validoModificarPelicula(Pelicula p) {
		for(Sesion s:this.sesiones) {
			Pelicula peliculaSesion = s.getPelicula();
			if(peliculaSesion!=null) {
				if(peliculaSesion.equals(p) && s.tieneReservas()) {
					return false;
				}
			}

		}
		return true;
	}

	public boolean modificarSesion(Sesion s) {
		//...
		// IMPLEMENTAR CODI ACÍ
		//...
		boolean omitir;

		if(s.getSala()!=null) { //Comprobamos que tenga una sala, si la tiene, luego comprobamos que tenga reservas
			if(s.tieneReservas()) {
				return false; //Si tiene reservas devolvemos false para no poder modificar la sesion
			}
		}

		String nombrePelicula;
		int numSala;
		Sala sala=null;;
		Pelicula pelicula=null;
		Scanner entrada = new Scanner(System.in);
		do {
			omitir=false;
			mostrarPeliculas();
			System.out.print("Introduce el nombre de la pelicula o 0 para omitir: ");
			nombrePelicula = entrada.nextLine();
			if(nombrePelicula.equals("0")) {
				omitir=true;
			}else {
				if(this.buscarPelicula(nombrePelicula)==null) {
					System.out.println("No existe la pelicula en el cine");
				}else {
					pelicula = this.buscarPelicula(nombrePelicula);
				}
			}

		}while(this.buscarPelicula(nombrePelicula) == null && omitir == false);

		do {
			omitir = false;
			mostrarSalas();
			System.out.print("Introduce el numero de la sala o 0 para omitir: ");
			numSala = entrada.nextInt();
			if(numSala==0) {
				omitir=true;
			}else {
				if(this.buscarSala(numSala)==null) {
					System.out.println("No existe la sala en el cine");
				}else {
					sala=this.buscarSala(numSala);
				}
			}


		}while(this.buscarSala(numSala) == null && omitir == false);

		Calendar horaSesion = Calendar.getInstance();

		System.out.print("Introduce la hora de la sesion: ");
		int hora = entrada.nextInt();
		entrada.nextLine();
		System.out.print("Introduce los minutos de la sesion: ");
		int minutos = entrada.nextInt();
		entrada.nextLine();
		System.out.print("Introduce el dia del mes: ");
		int diaMes = entrada.nextInt();
		entrada.nextLine();
		int mes=horaSesion.get(Calendar.MONTH);
		int año = horaSesion.get(Calendar.YEAR);
		if(diaMes<horaSesion.get(Calendar.DAY_OF_MONTH)) {
			if(mes==11) {
				mes=0;
				año++;
			}else {
				mes++;
			}
		}
		horaSesion.set(año, mes, diaMes, hora, minutos);
		System.out.print("Introduce el precio de la sesion: ");
		String entradaPrecio = entrada.nextLine();
		BigDecimal precio = new BigDecimal(entradaPrecio);
		s.modificaSessio(horaSesion, sala, precio, pelicula);
		return true;
	}


	public boolean modificarSala(Sala s) {
		if(validoModificarSala(s)) {
			Scanner entrada = new Scanner(System.in);
			boolean sala3D = false;
			System.out.print("Cuantas filas tiene: ");
			int numFilas = entrada.nextInt();
			entrada.nextLine();
			System.out.print("Cuantas butacas tiene por fila: ");
			int numButacas = entrada.nextInt();
			entrada.nextLine();


			System.out.print("¿La sala implementa funcionalidad 3D? : ");
			String respuesta3D = entrada.nextLine();
			if(respuesta3D.equals("s") ||respuesta3D.equals("S") || respuesta3D.equals("si") || respuesta3D.equals("SI")) {
				sala3D = true;
			}
			s.modificaSala(numFilas, numButacas, sala3D); //Le pasamos los datos a la sala para que los modifique
			return true; //Se han actualizado satisfactoriamente.
		}
		return false;

	}

	public boolean modificarPelicula(Pelicula p) {
		if (validoModificarPelicula(p)) {

			Scanner entrada = new Scanner(System.in);
			System.out.print("Introduce el nombre para la pelicula: ");
			String nombrePelicula = entrada.nextLine();
			if(validarNombrePelicula(nombrePelicula)) {
				System.out.print("Introduce nacionalidad de la pelicula: ");
				String nacionalidad = entrada.nextLine();
				System.out.print("Introduce la duracion en minutos de la pelicula: ");
				int duracion = entrada.nextInt();
				entrada.nextLine();
				System.out.print("Introduce el nombre del director: ");
				String director = entrada.nextLine();
				System.out.print("Introduce el nombre de los interpretes: ");
				String interpretes = entrada.nextLine();
				System.out.print("Introduce el argumento de la pelicula: ");
				String argumento = entrada.nextLine();
				System.out.print("Introduce el genero de la pelicula: ");
				String genero = entrada.nextLine();
				System.out.print("Introduce la clasificacion de la pelicula: ");
				String clasificacion = entrada.nextLine();
				p.modificaPelicula(nombrePelicula, nacionalidad, duracion, director, interpretes, argumento, genero, clasificacion);
				return true;
			}else {
				return false;
			}

		}
		return false;
	}

	private boolean validarNombrePelicula(String nombrePelicula) { //Si el nombre de la pelicula no existe en el listado devuelve true. Si existe o esta en blanco devuelve false
		if(nombrePelicula.equals("") || nombrePelicula==null)
			return false;
		for(Pelicula p:this.peliculas) {
			if(p.getNombrePelicula().equals(nombrePelicula)) {
				return false;
			}
		}

		return true;
	}


	public Pelicula crearPelicula(String nombrePelicula) {
		if(validarNombrePelicula(nombrePelicula)) {
			return new Pelicula();
		}else {
			return null;
		}
	}


	public Sala crearSala(int numSala) { //Si el num de sala pasado como parametro es valido, devolvemos una nueva sala, si no, devolvemos null
		if(validaNumSala(numSala)) {
			return new Sala();
		}else {
			return null;
		}
	}

	public Sesion crearSesion(String nombre) {
		if(validaNombreSesion(nombre)) {
			return new Sesion();
		}else {
			return null;
		}
	}

	private boolean validaNombreSesion(String nombre) { //Si podemos usar el nombre pasado como argumento, devolvemos true. Si no, devolvemos false
		for(Sesion s:this.sesiones) {
			if(s.getNombreSesion().equals(nombre)) {
				return false;
			}
		}
		return true;
	}

	private boolean validaNumSala(int numSala) { // Si el num de sala no existe aun, devuelve true, si existe, devuelve false;
		for(Sala s : this.salas) {
			if(s.getNumSala()==numSala) {
				return false;
			}
		}
		return true;
	}

	public boolean borrarSesion(Sesion s) {
		if(s.tieneReservas()) {
			return false;
		}else {
			this.sesiones.remove(s);
			return true;
		}
	}

	public boolean borrarPelicula(Pelicula p) {
		if(validoModificarPelicula(p)) {
			for(Sesion s:this.sesiones) {
				s.borrarPelicula();
			}
			this.peliculas.remove(p);
			return true;
		}
		return false;
	}

	public ArrayList<Sesion> sesionesSemana() {
		ArrayList<Sesion> sesionesSemana=new ArrayList<>();
		Calendar estaSemana = Calendar.getInstance();
		for(Sesion s:this.sesiones) {
			Calendar fechaSesion = s.getFecha();
			if(difDiasEntre2fechas(estaSemana, fechaSesion)<=7) { //Si esta en esta semana
				sesionesSemana.add(s);
			}
		}
		return sesionesSemana;
	}

	/**
	 *
	 * @param calInicio
	 * @param calFin
	 * @return Devuelve la diferencia de dias de las 2 fechas introducidas como parametros
	 */
	private int difDiasEntre2fechas(Calendar calInicio, Calendar calFin){
		Calendar c = Calendar.getInstance();
		//fecha inicio

		Calendar fechaInicio = new GregorianCalendar();
		fechaInicio.set(calInicio.get(Calendar.YEAR), calInicio.get(Calendar.MONTH), calInicio.get(Calendar.DAY_OF_MONTH));
		Calendar fechaFin = new GregorianCalendar();
		fechaFin.set(calFin.get(Calendar.YEAR), calFin.get(Calendar.MONTH), calFin.get(Calendar.DAY_OF_MONTH));

		c.setTimeInMillis(fechaFin.getTime().getTime() - fechaInicio.getTime().getTime());
		return c.get(Calendar.DAY_OF_YEAR);
	}


	public void generarTiquet(Sesion s) {
		int cantidad=0;
		for (int i = 0; i < s.getMapaSesion().length; i++) {
			for (int j = 0; j < s.getMapaSesion()[i].length; j++) {
				if(s.getMapaSesion()[i][j].getDisponibilidad().equals(Estado.RESERVANDO)) {
					s.getMapaSesion()[i][j].setDisponibilidad(Estado.OCUPADO);
					cantidad++;
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
		Date date = s.getFecha().getTime();
		String tiquet="";
		tiquet+="------------TIQUET-----------\n";
		tiquet+="Pelicula: "+s.getPelicula().getNombrePelicula()+"\n";
		tiquet+="Sala: "+s.getSala().getNumSala()+"\n";
		tiquet+="Fecha: "+sdf.format(date)+"\n";
		tiquet+="Precio: "+s.getPrecio().toString()+" x "+cantidad+" = "+multiplicadorPrecios(s.getPrecio(), cantidad)+"\n";
		tiquet+="-------------------------------\n\n";
		System.out.println(tiquet);

	}


	public void generarTiquet(Sesion s, ArrayList<Butaca> butacas){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
		Date date = s.getFecha().getTime();
		String tiquet="";
		tiquet+="----------  "+ Thread.currentThread().getName()+"   ---------------\n";
		tiquet+="---------------------------TIQUET-------------------------------\n";
		tiquet+="Pelicula: "+s.getPelicula().getNombrePelicula()+"\n";
		tiquet+="Sala: "+s.getSala().getNumSala()+"\n";
		tiquet+="Fecha: "+sdf.format(date)+"\n";
		tiquet+="Precio: "+s.getPrecio().toString()+" x "+butacas.size()+" = "+multiplicadorPrecios(s.getPrecio(), butacas.size())+"\n";
		tiquet+="--------------BUTACAS------------------\n";
		for (Butaca b: butacas) {
			tiquet+="Butaca: Fila-"+b.getNumfila()+"   Numero-"+b.getNumButaca()+"\n";
		}
		tiquet+="----------------------------------------------------------";
		System.out.println(tiquet);

	}

	/**
	 * Operacion para la multiplicacion entre un BigDecimal y un entero
	 * @param precio
	 * @param cantidad
	 * @return
	 */
	public BigDecimal multiplicadorPrecios(BigDecimal precio, int cantidad) {
		BigDecimal total= new BigDecimal("0");
		for (int i = 0; i < cantidad; i++) {
			total.add(precio);
		}
		return total;
	}

	public Butaca buscarButacaEnSesion(Sesion s, Butaca b) {
		if (this.sesiones.contains(s)) {
			if(b.getNumfila()<s.getMapaSesion().length && b.getNumButaca()<s.getMapaSesion()[0].length) {
				return s.getMapaSesion()[b.getNumfila()][b.getNumButaca()];
			}
		}
		return null;
	}

	public void comprarEntradas(Sesion s, ArrayList<Butaca> butacas) { //Si los server.hilos han conseguido reservar, no importa controlar quien compra primero, se supone tienen butacas distintas
		int cantidad = 0;
		for(Butaca b : butacas) {
			cantidad++;
			s.getMapaSesion()[b.getNumfila()][b.getNumButaca()].setDisponibilidad(Estado.OCUPADO);
		}
		generarTiquet(s, butacas);
	}

	public boolean reservarEntradas(Sesion s, ArrayList<Butaca> butacas) {
		while(!this.turno) { //controlamos que no puedan reservar varios server.hilos la misma butaca
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.turno=false; // Bloquea los recursos hasta que se termine de reservar las butacas para un hilo
		for(Butaca b: butacas) { //Comprobamos que todas las butacas que pide un hilo estan libres, si no, no podra reservar, y perdera su turno
			if(!s.getMapaSesion()[b.getNumfila()][b.getNumButaca()].verificaButaca()) {
				this.turno=true;
				return false;
			}
		}
		for(Butaca b: butacas) {
			s.getMapaSesion()[b.getNumfila()][b.getNumButaca()].reservaButaca(); //Reserva las butacas
		}
		this.turno=true; //Permitimos al siguiente hilo que pueda continuar
		return true;
	}
}
