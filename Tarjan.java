package tarjan;

import java.util.HashSet;
import java.util.Set;

public class Tarjan{

	public static void main(String[] args) {
		Set<Nodo> insnodi=new HashSet<Nodo>();
		Set<Arco> insarchi=new HashSet<Arco>();
		Nodo v1= new Nodo("v1");
		Nodo v2= new Nodo("v2");
		Nodo v3= new Nodo("v3");
		Nodo v4= new Nodo("v4");
		Nodo v5=new Nodo("v5");
		Arco arco1=new Arco(v1,v2);
		Arco arco2=new Arco(v1,v3);
		insnodi.add(v1);
		insnodi.add(v2);
		insnodi.add(v3);
		insnodi.add(v4);
		insarchi.add(arco1);
		insarchi.add(arco2);
		Graph g=new Graph(insnodi,insarchi);
		g.Visita();
		Arco arco3=new Arco(v5,v4);
		Set<Arco> ins=new HashSet<Arco>();
		ins.add(arco3);
		g.AddNodo(v5,ins);
		g.Visita();
		Arco arco4=new Arco(v3,v4);
		g.RemoveArco(arco1);
		g.Visita();
	}
}
