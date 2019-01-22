package tarjan;

public class Nodo {
 	private String nome;
	private boolean marcatore;
	private int numero;
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero=numero;
	}

	public boolean isMarcatore() {
		return marcatore;
	}

	public void setMarcatore(boolean marcatore) {
		this.marcatore=marcatore;
	} 


	public Nodo(String nome) {
		this.nome=nome;
	}

	/*private String getNome() {
		return nome;
	}*/

	/*public void setNome(String nome) {   non serve
		this.nome= nome;
	}*/

	public String toString() {
		return " " +nome;  /*per non farsi stampare l'hashcode*/
	}
}
