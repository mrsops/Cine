package hilos;

import java.util.ArrayList;

import controlador.ControlCine;
import modelo.Butaca;
import modelo.Pelicula;
import modelo.Sesion;

public class CompraEntradaPelicula extends Thread {
	private ControlCine cine;
	private String nSesion;
	
	
	

	public CompraEntradaPelicula(String nombreHilo,ControlCine cine, String nSesion, ArrayList<Butaca> butacas) {
		super();
		this.setName(nombreHilo);
		this.cine = cine;
		this.nSesion = nSesion;
		
	}




	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Sesion sesion;
		if (cine.buscarSesion(nSesion)!= null) {
			sesion = cine.buscarSesion(nSesion);
			
		}
				
		

	}

}
