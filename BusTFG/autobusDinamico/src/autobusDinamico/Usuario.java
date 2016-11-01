package autobusDinamico;

public class Usuario {

	private Coordenadas origen;
	private Coordenadas destino;
	private double tiempoEspera;
	
	public Usuario(Coordenadas origen, Coordenadas destino) {
		super();
		this.origen = origen;
		this.destino = destino;
	}
	public Coordenadas getOrigen() {
		return origen;
	}
	public void setOrigen(Coordenadas origen) {
		this.origen = origen;
	}
	public void setOrigen(double x, double y) {
		this.origen.setX(x);
		this.origen.setY(y);
	}
	
	public Coordenadas getDestino() {
		return destino;
	}
	public void setDestino(Coordenadas destino) {
		this.destino = destino;
	}
	public void setDestino(double x, double y) {
		this.destino.setX(x);
		this.destino.setY(y);
	}
	
	public double getTiempoEspera() {
		return tiempoEspera;
	}
	public void setTiempoEspera(double tiempoEspera) {
		this.tiempoEspera = tiempoEspera;
	}
	
}
