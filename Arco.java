package tarjan;

public class Arco {
	private Nodo nodo1, nodo2;

	public Arco(){            /*costruttore*/
		nodo1=nodo2=null;
	}
	
	public Arco(Nodo v1,Nodo v2) {    /*costruttore*/
		nodo1=v1;
		nodo2=v2;
	}

	public Nodo getNodo1() { 	/*restituisce il primo nodo*/
		return this.nodo1; 
	}

	public Nodo getNodo2() { 	/*restituisce il secondo nodo*/
		return this.nodo2;

	}

	public void setNodo1(Nodo v1) {  
		this.nodo1=v1;
	}
	
	public void setNodo2(Nodo v2) {
		this.nodo2=v2;
	}

}
