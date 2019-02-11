package server.hilos;

import java.util.ArrayList;

import server.controlador.ControlCine;
import server.modelo.Butaca;
import server.modelo.Sesion;

public class CompraEntradaPelicula extends Thread {
	private ControlCine cine;
	private String nSesion;
	private ArrayList<Butaca> butacas;
	
	
	

	public CompraEntradaPelicula(String nombreHilo,ControlCine cine, String nSesion, ArrayList<Butaca> butacas) {
		super();
		this.setName(nombreHilo);
		this.cine = cine;
		this.nSesion = nSesion;
		this.butacas = butacas;
		
	}




	@Override
	public void run() { //Cada hilo intentara reservar las butacas
		// TODO Auto-generated method stub
		super.run();
		Sesion sesion;
		if (cine.buscarSesion(nSesion)!= null) {
			sesion = cine.buscarSesion(nSesion);
			if (cine.reservarEntradas(sesion, butacas)){
				cine.comprarEntradas(sesion, butacas);
			}else{
				System.out.println("-------------------------------------------------\n" +
                        this.getName()+ "No ha sido posible reservar las entradas, las butacas estaban reservadas, ocupadas o no existen");
			}
			
		}
				
		

	}

}
