package modelo;

import java.util.Scanner;

public class Sala {
	private int numSala;
	private int filas;
	private int butacas;
	private boolean sala3D;
	

	public Sala(int numSala, int filas, int butacas, boolean sala3D) {
		this.filas=filas;
		this.butacas = butacas;
		
		
		this.numSala = numSala;
		this.sala3D = sala3D;
	}
	
	public Sala(int numSala, int filas, int butacas) {
		this.filas=filas;
		this.butacas = butacas;
		this.numSala = numSala;
	}

	public Sala() {
		super();
	}


	public boolean isSala3D() {
		return sala3D;
	}

	public void setSala3D(boolean sala3d) {
		sala3D = sala3d;
	}

	public int getNumSala() {
		return numSala;
	}

	public void setNumSala(int numSala) {
		this.numSala = numSala;
	}

	public int getFilas() {
		return filas;
	}

	public void setFilas(int filas) {
		this.filas = filas;
	}

	public int getButacas() {
		return butacas;
	}

	public void setButacas(int butacas) {
		this.butacas = butacas;
	}

	@Override
	public String toString() {
		String valor3D="";
		if(this.sala3D) {
			valor3D="Si";
		}else {
			valor3D="No";
		}
		return "Sala [numSala=" + numSala + ", filas=" + filas + ", butacas=" + butacas + ", sala3D=" + valor3D + "]";
	}
	
	
	//*********************************************************
	//MODIFICA LES DADES DE LA SALA
	public void modificaSala(int filas, int butacas, boolean sala3D) {
		this.filas=filas;
		this.butacas=butacas;
		this.sala3D=sala3D;
	}

	
	
	
	
	
	
	
	
}
