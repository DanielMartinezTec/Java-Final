//A01633182 Braulio Vargas Camargo
//MiListaEnlazada.java
//24 de Abril del 2019 
//Comentarios:...
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MiListaEnlazada<E> implements Iterable<E>{
	
	protected NodoLE<E> inicio,	
						fin;
	protected int size;
	
	public MiListaEnlazada() {
		this.inicio=this.fin=null;
		this.size=0;
	}
	public MiListaEnlazada(E[]datos) {
		//Inicializa la lista con los elementos que tiene el arreglo
		for(int i=0;i<datos.length;i++) {
			this.insertarFin(datos[i]);
		}
	}
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<E>(){
			private NodoLE<E> current=inicio;
			private boolean callRemove=false;
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return current!=null?true:false;
			}
			@Override
			public E next() {
				// TODO Auto-generated method stub
				callRemove=true;
				E dato=current.getDato();
				current=current.getNext();
				return dato;
			}
			public void remove() {
				if(callRemove) {
					NodoLE<E> tmp=inicio;
					while(!tmp.getNext().equals(current)) {
						tmp=tmp.getNext();
					}
					tmp.setDato(tmp.getNext().getDato());
					tmp.setNext(tmp.getNext().getNext());
					size--;
				}
				callRemove = false;
			}
		};
	}
	public E inicio() {
		try {
			return this.inicio.getDato();
		}
		catch(NullPointerException e) {
			throw new NoSuchElementException("No se puede regresar el primer elemento de una lista vacia");
		}
	}
	public E fin() {
		try {
			return this.fin.getDato();
		}
		catch(NullPointerException e) {
			throw new NoSuchElementException("No se puede regresar el ultimo elemento de una lista vacia");
		}
	}
	public boolean estaVacia() {
		return this.size==0;
	}
	public int size() {
		return this.size;
	}
	public void insertarInicio(E dato) {
		NodoLE<E> nvo=new NodoLE<>(dato,inicio);
		this.inicio=nvo;
		if(estaVacia()) {
			this.fin=nvo;
		}
		this.size++;
	}
	public void insertarFin(E dato) {
		if(this.estaVacia()) {
			this.insertarInicio(dato);
		}
		else {
			NodoLE<E> nvo=new NodoLE<>(dato);
			this.fin.setNext(nvo);
			this.fin=nvo;
			this.size++;
		}		
	}
	public void insertarEn(E dato,int pos) {
		if(pos<0||pos>size) {
			throw new IllegalStateException("No se puede "
					+ "insertar,ingrese una posición valida");
		}
		else if(pos==0) {
			this.insertarInicio(dato);
		}
		else if(pos==size) {
			this.insertarFin(dato);
		}
		else {
			NodoLE<E> nvo=new NodoLE<>(dato);
			NodoLE<E>current=this.inicio;
			for(int i=0;i<pos-1;i++) {
				current=current.getNext();
			}
			nvo.setNext(current.getNext());
			current.setNext(nvo);
			this.size++;
		}
	}
	public E borrarInicio()	{
		try {
			E res=this.inicio();
			this.inicio=this.inicio.getNext();
			size--;
			if(this.size==0) {
				this.fin=null;
			}
			return res;
		}
		catch (NullPointerException|NoSuchElementException ex) {
			throw new IndexOutOfBoundsException("No se puede borrar el inicio de una lista vacia");
		}
	}
	public E borrarFin() {
		try {
			E dato=this.fin();
			NodoLE<E> current=this.inicio;
			for(int i=0;i<size-2;i++) {
				current=current.getNext();
			}
			//Quiero que current se detenga uno antes de fin
			current.setNext(null);
			this.fin=current;
			this.size--;
			return dato;
		}
		catch(IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("No se puede borrar el fin de una lista vacia");
		}
	}
	public E borrarEn(int pos) throws IndexOutOfBoundsException {
		E dato;
		if(pos==this.size-1) {
			return this.borrarFin();
		}
		else if(pos==0) {
			return this.borrarInicio();
		}
		else if(pos>=1&&pos<this.size-1) {
			NodoLE<E> current=this.inicio.getNext();
			for(int i=0;i<pos-2;i++) {
				current=current.getNext();
			}
			dato=current.getNext().getDato();
			current.setNext(current.getNext().getNext());
			this.size--;
			return dato;
		}
		else {
			throw new IndexOutOfBoundsException("No se puede borrar el elemento en la posicón "+pos+" en una lista de tamaño "+this.size);
		}
	}
	public void flush() {
		this.inicio=this.fin=null;
		size=0;
		System.gc();
	}
	public E getEn(int pos) {
		if(pos>=0&&pos<this.size) {
			NodoLE<E> current=this.inicio;
			for(int i=0;i<pos;i++) {
				current=current.getNext();
			}
			return current.getDato();
		}
		else {
			throw new IndexOutOfBoundsException("No se puede regresar el elemento en la posicón "+pos+" en una lista de tamaño "+this.size);
		}
	}
	public void setEn(E dato,int pos) {
		if(pos==this.size-1) {
			this.fin.setDato(dato);
		}
		else if(pos>=0&&pos<this.size) {
			NodoLE<E> current=this.inicio;
			for(int i=0;i<pos;i++) {
				current=current.getNext();
			}
			current.setDato(dato);
		}
		else {
			throw new IndexOutOfBoundsException("No se puede regresar el elemento en la posicón "+pos+" en una lista de tamaño "+this.size);
		}
	}
	public String toString() {
		String res="";
		NodoLE<E>current=this.inicio;
		for(int i=0;i<this.size;i++) {
			res+=current.getDato()+",";
			current=current.getNext();
		}
		return res;
	}
}
class NodoLE<E>{
	private E dato;
	private NodoLE<E>next;
	public NodoLE(E dato, NodoLE<E> next) {
		super();
		this.dato = dato;
		this.next = next;
	}
	public NodoLE(E dato) {
		this(dato,null);
	}
	public E getDato() {
		return dato;
	}
	public void setDato(E dato2) {
		this.dato = dato2;
	}
	public NodoLE<E> getNext() {
		return next;
	}
	public void setNext(NodoLE<E> next) {
		this.next = next;
	}
}
//FINAL