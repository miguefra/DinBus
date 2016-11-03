package autobusDinamico;

public class Autobus {

	private int id;
	private Coordenadas paradas[];
	private Usuario usuarios[];
	private Coordenadas destinoActual;
	private Coordenadas ubicacionActual;
	
	public Coordenadas[] getParadas() {
		return paradas;
	}
	public void setParadas(Coordenadas[] paradas) {
		this.paradas = paradas;
	}
	public Usuario[] getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Usuario[] usuarios) {
		this.usuarios = usuarios;
	}
	public Coordenadas getDestinoActual() {
		return destinoActual;
	}
	public void setDestinoActual(Coordenadas destino) {
		this.destinoActual = destino;
	}
	
	public void setDestinoActual(double x, double y) {
		this.destinoActual.setX(x);
		this.destinoActual.setY(y);
	}
	public Coordenadas getUbicacionActual() {
		return ubicacionActual;
	}
	public void setUbicacionActual(Coordenadas ubicacionActual) {
		this.ubicacionActual = ubicacionActual;
	}
	public void addParada(int i, Coordenadas parada){
		this.paradas[i] = parada;
	}
	
    public Autobus(Autobus autobuses[], Coordenadas ubicacion, Coordenadas destino, int id) {
    	this.setParadas(new Coordenadas[100]);
    	this.setUsuarios(new Usuario[100]);
    	this.setDestinoActual(destino);
    	this.setUbicacionActual(ubicacion);
    	this.id = id;
    	autobuses[autobuses.length - 1] = this;
    }
	@Override
	public String toString() {
		return "Autobus [id=" + id + "]";
	}
    
    
}
