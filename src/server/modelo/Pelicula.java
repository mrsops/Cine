package server.modelo;

public class Pelicula {

	private String nombrePelicula;
	private String nacionalidad;
	private int duracion;
	private String director;
	private String interpretes;
	private String argumento;
	private String genero;
	private String clasificacion;
	
	
	
	
	
	public Pelicula(String nombrePelicula, String nacionalidad, int duracion, String director, String interpretes, String argumento, String genero, String clasificacion) {
		this.nombrePelicula = nombrePelicula;
		this.nacionalidad = nacionalidad;
		this.duracion = duracion;
		this.director = director;
		this.interpretes = interpretes;
		this.argumento = argumento;
		this.genero = genero;
		this.clasificacion = clasificacion;
	}


	public Pelicula() {
		super();
	}
	public String getNombrePelicula() {
		return nombrePelicula;
	}
	public void setNombrePelicula(String nombrePelicula) {
		this.nombrePelicula = nombrePelicula;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getInterpretes() {
		return interpretes;
	}
	public void setInterpretes(String interpretes) {
		this.interpretes = interpretes;
	}
	public String getArgumento() {
		return argumento;
	}
	public void setArgumento(String argumento) {
		this.argumento = argumento;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getClasificacion() {
		return clasificacion;
	}
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}	
	
	
	public void modificaPelicula(String nombrePelicula, String nacionalidad, int duracion, String director, String interpretes,
			String argumento, String genero, String clasificacion) {
		this.nombrePelicula = nombrePelicula;
		this.nacionalidad = nacionalidad;
		this.duracion = duracion;
		this.director = director;
		this.interpretes = interpretes;
		this.argumento = argumento;
		this.genero = genero;
		this.clasificacion = clasificacion;
	}


	@Override
	public String toString() {
		return "\tPelicula ["+
				"\n\t\tNombre=" + this.nombrePelicula + 
				"\n\t\tNacionalidad="+ this.nacionalidad + 
				"\n\t\tDuracion=" + this.duracion + 
				"\n\t\tDirector="+ this.director + 
				"\n\t\tInterpretes=" + this.interpretes + 
				"\n\t\tArgumento="+ this.argumento + 
				"\n\t\tGenero=" + this.genero + 
				"\n\t\tClasificacion="+ this.clasificacion +  
				"\n\t]";
	}
	
	
	
}
