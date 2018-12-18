package modelo;

import modelo.Butaca.Estado;

public class Butaca {
	private int numfila;
	private int numButaca;
	private Estado disponibilidad;
	public enum Estado {LIBRE, OCUPADO, RESERVANDO}
	
	
	public Butaca(int numfila, int numButaca) {
		this.numfila = numfila;
		this.numButaca = numButaca;
		this.disponibilidad = Estado.LIBRE;
	}


	public int getNumfila() {
		return numfila;
	}


	public void setNumfila(int numfila) {
		this.numfila = numfila;
	}


	public int getNumButaca() {
		return numButaca;
	}


	public void setNumButaca(int numButaca) {
		this.numButaca = numButaca;
	}


	public Estado getDisponibilidad() {
		return disponibilidad;
	}


	public void setDisponibilidad(Estado disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	
	
	public boolean reservaButaca() {
		
		if (verificaButaca()) {
			this.setDisponibilidad(Estado.RESERVANDO);
		}
		return false;
	}
	
	public void liberarButaca() {
		if(this.disponibilidad.equals(Estado.RESERVANDO)) {
			this.disponibilidad=Estado.LIBRE;
		}
	}
	
	public String iconoButaca( ) {
		if(this.disponibilidad.equals(Estado.LIBRE)) {
			return "[O]";
		}else if(this.disponibilidad.equals(Estado.OCUPADO)) {
			return "[X]";
		}else if(this.disponibilidad.equals(Estado.RESERVANDO)) {
			return "[?]";
		}else {
			return "";
		}
	}
	
	public boolean verificaButaca() {
		if(this.getDisponibilidad().equals(Estado.LIBRE)) {
			return true;
		}
		return false;
	}
	
	
	
	

}
