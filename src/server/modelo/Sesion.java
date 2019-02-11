package server.modelo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import server.modelo.Butaca.Estado;

public class Sesion {
	private Butaca[][] mapaSesion;
	private String nombreSesion;
	private Calendar fecha;
	private  Sala sala;
	private BigDecimal precio;
	private Pelicula pelicula;
	
	
	
	public Sesion() {
		super();
	}
	
	
	public Sesion(String nombreSesion, Calendar data, Sala sala, BigDecimal precio, Pelicula pelicula) {
		this.mapaSesion = null;
		this.nombreSesion = nombreSesion;
		this.fecha = data;
		this.sala = sala;
		this.precio = precio;
		this.pelicula = pelicula;
		if(sala!=null) {
			iniciarButacas();
		}
		
	}
	
	private void iniciarButacas() {
		this.mapaSesion = new Butaca[sala.getFilas()][sala.getButacas()];
		for (int i=0;i<this.mapaSesion.length;i++) {
			for(int j=0;j<mapaSesion[0].length;j++) {
				this.mapaSesion[i][j]= new Butaca(i,j);
			}
		}
	}



	public String getNombreSesion() {
		return nombreSesion;
	}
	public void setNombreSesion(String nombreSesion) {
		this.nombreSesion = nombreSesion;
	}

	public void setData(Calendar data) {
		this.fecha = data;
	}
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
		iniciarButacas();
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public Pelicula getPelicula() {
		return pelicula;
	}
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}
	
	


	public Butaca[][] getMapaSesion() {
		return mapaSesion;
	}


	public void setMapaSesion(Butaca[][] mapaSesion) {
		this.mapaSesion = mapaSesion;
	}


	public Calendar getFecha() {
		return fecha;
	}


	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
		Date date = this.fecha.getTime();
		return "Sesion ["+
				 "\n\tNombre sesion: "+this.nombreSesion+
				 "\n\tFecha: "+sdf.format(date)+
				 "\n\tPrecio: "+this.precio.toString()+
				 "\n\tSala: "+this.sala+
				 "\n\tPelicula: "+this.pelicula+
				 "\n\t]";
	}
	
	public boolean tieneReservas() {
		if(this.sala!=null) {
			for (int i = 0; i < mapaSesion.length; i++) {
				for (int j = 0; j < mapaSesion[i].length; j++) {
					if(!mapaSesion[i][j].getDisponibilidad().equals(Estado.LIBRE)) {
						return true;
					}
				
				}
			}
		}
		
		return false;
		
	}
	
	public String mostrarMapa() {
		
		if(this.sala!=null) {
			String mapa="---MAPA DE BUTACAS---\n";
			for (int i = 0; i < mapaSesion.length; i++) {
				String linea="";
				for (int j = 0; j < mapaSesion[i].length; j++) {
					linea+=mapaSesion[i][j].iconoButaca();
				}
				mapa+=linea+"\n";
			}
			return mapa;
		}else {
			System.out.println("No se ha assignado ninguna sala");
			return null;
		}
	}
	
	
	//*********************************************************
	//MODIFICA DADES DE LA SESSIO
	public void modificaSessio(Calendar data, Sala sala, BigDecimal precio, Pelicula pelicula) {
			this.mapaSesion = null;
			this.fecha = data;
			this.sala = sala;
			this.precio = precio;
			this.pelicula = pelicula;
			if(sala!=null) {
				iniciarButacas();
			}
			
		
	}
	
	public void borrarSala() {
		this.sala=null;
	}

	
	public void borrarPelicula() {
		this.pelicula=null;
	}
	
	
	
	
	
}
