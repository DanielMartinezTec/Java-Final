//A01633182 Braulio Vargas Camargo
//MyHashTable.java
//21 de Marzo del 2019
//Comentarios:...
import java.util.NoSuchElementException;

public class MyHashTable<K,V> {

	private MiListaEnlazada<MyNodoHash<K,V>>[]tabla;
	private int size;
	private static final double LOAD_FACTOR=0.75;
	
	public MyHashTable() {
		this.tabla=(MiListaEnlazada<MyNodoHash<K,V>>[])new MiListaEnlazada[11];
		for(int i=0;i<this.tabla.length;i++) {
			tabla[i]=new MiListaEnlazada<>();
		}
	}
	
	//En caso de pasar una llave que ya exista en la tabla entonces se sobreescribe que corresponde a la entrada con la llave
	public void put(K llave, V valor) {
		int pos=Math.abs(llave.hashCode()%this.tabla.length);
		this.tabla[pos].insertarFin(new MyNodoHash<>(llave,valor));
		this.size++;
		if((double)this.size/this.tabla.length>MyHashTable.LOAD_FACTOR) {
			rehashing();
		}
	}
	public void rehashing() {
		//Incrementar la tabla a tamaño doble + 1 y hacer el reacomodo de los elementos en la nueva tabla
		MiListaEnlazada<MyNodoHash<K,V>>[]tmp=this.tabla;
		this.tabla=(MiListaEnlazada<MyNodoHash<K,V>>[])new MiListaEnlazada[tmp.length*2+1];
		this.size=0;
		for(int i=0;i<this.tabla.length;i++) {
			tabla[i]=new MiListaEnlazada<>();
		}
		for(int i=0;i<tmp.length;i++) {
			if(tmp[i].inicio!=null) {
				NodoLE<MyNodoHash<K, V>>current=tmp[i].inicio;
				while(current!=null) {
					this.put(current.getDato().getLlave(), current.getDato().getValor());
					current=current.getNext();
				}
			}
		}
	}
	//Regresar valor asociado con la llave, si no está esta llave arroja un NoSuchElementException
	public V get(K llave) {
		for(int i=0;i<this.tabla.length;i++) {
			if(this.tabla[i].inicio!=null) {
				NodoLE<MyNodoHash<K, V>>current=this.tabla[i].inicio;
				while(current!=null) {
					if(current.getDato().getLlave().equals(llave)) {
						return current.getDato().getValor();
					}
					current=current.getNext();
				}
			}
		}
		throw new NoSuchElementException("No es encuentra un valor con esa llave o es incorrecta");
	}
	//Borrar la entrada que tenga la llave que se paso como parametro en caso de no encontrarse el elemento NoSuchElementException
	public V delete(K llave) {
		for(int i=0;i<this.tabla.length;i++) {
			if(this.tabla[i].inicio!=null) {
				NodoLE<MyNodoHash<K, V>>current=this.tabla[i].inicio;
				while(current!=null) {
					if(current.getDato().getLlave().equals(llave)) {
						V valor=current.getDato().getValor();
						if(current.getNext()!=null) {
							current.setDato(current.getNext().getDato());
							current.setNext(current.getNext().getNext());
						}
						else{
							current=null;
						}
						this.size--;
						return valor;
					}
					current=current.getNext();
				}
			}
		}
		throw new NoSuchElementException("No es encuentra un valor con esa llave o es incorrecta");
	}
	//Regresa true si la llave esta en la tabla caso contrario regrese false
	public boolean containsKey(K llave) {
		for(int i=0;i<this.tabla.length;i++) {
			if(this.tabla[i].inicio!=null) {
				NodoLE<MyNodoHash<K, V>>current=this.tabla[i].inicio;
				while(current!=null) {
					if(current.getDato().getLlave().equals(llave)) {
						return true;
					}
					current=current.getNext();
				}
			}
		}
		return false;
	}
	//Reinicia la tabla como estaba en el inicio
	public void clear() {
		this.tabla=(MiListaEnlazada<MyNodoHash<K,V>>[])new MiListaEnlazada[11];
		this.size=0;
		for(int i=0;i<this.tabla.length-1;i++) {
			tabla[i]=new MiListaEnlazada<>();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyHashTable e=new MyHashTable();
		System.out.println(e.tabla.length);
		e.put("A01633182", "Braulio Vargas");
		e.put("A01633183", "Daniel Martinez");
		for(int i=0;i<100;i++) {
			e.put("A"+i, "Don V"+i);
		}
		System.out.println(e.get("A01633183"));
		System.out.println(e.containsKey("A01633182"));
		System.out.println(e.get("A49"));
		System.out.println(e.containsKey("A34"));
		System.out.println(e.tabla.length);
		System.out.println(e.containsKey("A100"));
		System.out.println(e.containsKey("A99"));
		e.delete("deded");
	}

}

class MyNodoHash<K,V>{
	K llave;
	V valor;
	public MyNodoHash(K llave,V valor) {
		this.llave=llave;
		this.valor=valor;
	}
	public K getLlave() {
		return llave;
	}
	public V getValor() {
		return valor;
	}
	public void setValor(V valor) {
		this.valor = valor;
	}
	
}