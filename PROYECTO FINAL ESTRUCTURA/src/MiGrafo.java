
public class MiGrafo<E extends Comparable<E>>{

	private MiListaEnlazada<NodoGF> nodos;
	
	public MiGrafo() {
		this.nodos=new MiListaEnlazada();
	}
	public void addNodo(NodoGF nodo) {
		this.nodos.insertarFin(nodo);
	}
	public MiListaEnlazada<NodoGF> getNodos(){
		return this.nodos;
	}
	public String toString() {
        return "Graph [nodes=" + nodos + "]";
    }
}
class NodoGF{
	private MiListaEnlazada<Limite> limites;
	private String nombre;
	private Vertice localizacion;
	public NodoGF(String nombre,Vertice loc) {
		this.nombre=nombre;
		this.localizacion=loc;
		this.limites=new MiListaEnlazada();
	}
	public void addLimite(Limite limite) {
		this.limites.insertarFin(limite);
	}
	public MiListaEnlazada<Limite> getLimites() {
		return this.limites;
	}
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Vertice getLocalizacion() {
		return this.localizacion;
	}
	public void setLocalizacion(Vertice localizacion) {
		this.localizacion = localizacion;  
	}
	 public String toString() {
	        return "\n \tNode [city=" + nombre + ", edges=" + limites + "]";
	    }
}
class Limite{
	private NodoGF origen,
					destino;
	private double distancia;
	private Arco arco;
	 public Limite(NodoGF origen, NodoGF destino, double distancia,Arco arco) {
	        this.origen = origen;
	        this.destino = destino;
	        this.distancia = distancia;
	        this.arco=arco;
	    }
	
	public Arco getArco() {
		return arco;
	}

	public void setArco(Arco arco) {
		this.arco = arco;
	}

	public NodoGF getOrigen() {
		return origen;
	}

	public void setOrigen(NodoGF origen) {
		this.origen = origen;
	}

	public NodoGF getDestino() {
		return destino;
	}

	public void setDestino(NodoGF destino) {
		this.destino = destino;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	 public String toString() {
	        return "\n Edge [origin=" + origen.getNombre() + ", destination=" + destino.getNombre() + ", distance="
	                + distancia + "]";
	    }
}
