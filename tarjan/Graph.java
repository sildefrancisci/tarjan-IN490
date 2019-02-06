package tarjan;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Iterator;

public class Graph {
	private Map<Nodo,Set<Arco>> nodi;     /*ogni nodo è insieme di archi che incidono*/
	private Set<Nodo> Compcon=new LinkedHashSet<Nodo>();
	private Map<Integer,Set<Nodo>> numcompcon=new LinkedHashMap<Integer, Set<Nodo>>();

	public Map<Nodo,Set<Arco>> getNodi(){
		return nodi;
	}

	public void setNodi(Map<Nodo,Set<Arco>> nodi) {
		this.nodi=nodi;
	}

	public Set<Nodo> getCompCon() {
		return Compcon;
	}

	public void setCompCon(Set<Nodo> Compcon) {
		this.Compcon=Compcon;
	}

	public Map<Integer,Set<Nodo>> getNumCompCon(){
		return numcompcon;
	}

	public void setNumCompCon(Map<Integer,Set<Nodo>> numcompcon) {
		this.numcompcon=numcompcon;
	}

	

	public Graph() {                                   /*costruttore*/
		this.nodi= new LinkedHashMap<Nodo,Set<Arco>>();     /*Map è interfaccia non può essere istanzializzata*/
	}

	public Graph(Set<Nodo> insnodi,Set<Arco> insarchi){   /*costruttore*/
		this.nodi=new LinkedHashMap<Nodo,Set<Arco>>(); 
		while(!insnodi.isEmpty()){
			Nodo v=RandomNodo(insnodi);		
			Set<Arco> archi =new LinkedHashSet<Arco>();
			for(Arco a : insarchi){
				if(a.getNodo1()==v || a.getNodo2()==v)
					archi.add(a);
			}
			nodi.put(v,archi);
			insnodi.remove(v);
		}
	}

	public void AddArco(Arco a) {  /*aggiungere arco*/
		Nodo v=a.getNodo1();
		Nodo w=a.getNodo2();
		((this.nodi).get(v)).add(a);
		((this.nodi).get(w)).add(a);
	}

	public void RemoveArco(Arco a) {    /*rimuovere arco*/
		Nodo v=a.getNodo1();
		Nodo w=a.getNodo2();
		((this.nodi).get(v)).remove(a);
		((this.nodi).get(w)).remove(a);
	}

	public void AddNodo(Nodo v,Set<Arco> insarchi) { /*aggiungere arco*/
		this.nodi.put(v,insarchi);
		for(Nodo w : this.nodiAdiacenti(v)) {
			Arco a=new Arco(w,v);
			((this.nodi).get(w)).add(a);
		}
	}


	private Set<Arco> archiIncidenti(Nodo v) {        /*restituisce insieme di archi incidenti in v*/
		if((this.nodi).containsKey(v))
			return (this.nodi).get(v);
		else
			return null;
	}

	private Set<Nodo> nodiAdiacenti(Nodo v) {  /*restituisce insieme di nodi adiacenti a v */
		LinkedHashSet<Nodo> adj=new LinkedHashSet();		
		if((this.nodi).containsKey(v) && nodi.get(v)!=null) {
			for(Arco a : this.archiIncidenti(v)){
				if(a.getNodo1()!=v)
					adj.add(a.getNodo1());
				else
					adj.add(a.getNodo2());
			}
		}		
		return adj;
	}


	private Set<Nodo> getAllNodi() {          /*restituisce i nodi*/
    		return this.nodi.keySet();
  	}

	private Nodo RandomNodo(Set<Nodo> a) {     /*restituisce un nodo random da un inseme di nodi*/
		List<Nodo> k=new ArrayList<Nodo>(a);
		Random r= new Random();
		return k.get(r.nextInt(k.size()));
	}


	public void Visita(){		        
		if(getAllNodi().isEmpty())                                           /*controllo che il grafo non sia vuoto*/
			System.out.println("il grafo è vuoto");
		else{	
			Set<Nodo> nodes= new LinkedHashSet(getAllNodi());	
			while(!nodes.isEmpty()){                                       /*while su insieme di nodi che si modifica*/
				Set<Nodo> Compcon=new LinkedHashSet();
				Nodo v=RandomNodo(nodes);
				Integer i= numcompcon.size()+1;	
				if(v.isMarcatore()==false){          /*se il nodo non è mai stato visitato*/
					v.setMarcatore(true);
					Compcon.add(v);
					numcompcon.put(i,Compcon);     /*inserisco il nodo in una componente connessa*/
					i=Visita(v,i,numcompcon);         /*visito a partire dal nodo in esame*/
					nodes.removeAll(numcompcon.get(i));	/*rimuovo dal while i nodi che sono già in una componente*/
									
				}
				else {
					
					if(v.getNumero()>nodiAdiacenti(v).size()){          /*se nodo è stato visitato e ha meno nodi adiacenti rispetto a prima*/
						i=getInt(v);                                
						reset(numcompcon.get(i));                    
						nodes.addAll(numcompcon.get(i));         /*aggiungo i nodi della componente connessa di v al while*/
						ReCompcon(i);
					}
					else if(v.getNumero()<nodiAdiacenti(v).size()){  /*se nodo è stato già visitato e ha più nodi adiacenti rispetto a prima*/
						i=getInt(v);
						i=Visita(v,i,numcompcon);
						nodes.removeAll(numcompcon.get(i));   /*rimuovo dal while i nodi che sono già in una componente*/
					}
					else
						nodes.remove(v);             /*se nodo visitato e non sembra aver subito modifiche*/
				}				
			}			
		for(int c=1;c<=numcompcon.size();c++)
			System.out.println(numcompcon.get(c)+" ");     /*stampo le componenti connesse*/
		}
	}				
		
				
	
	private Integer Visita(Nodo v,Integer i,Map<Integer,Set<Nodo>> numcompcon){ /*visito a partire dal nodo in esame*/
		LinkedHashSet<Nodo> adj=new LinkedHashSet(nodiAdiacenti(v));
		Set<Nodo> Compcon=numcompcon.get(i);
		v.setNumero(adj.size());               /*modifico il numero del nodo a seconda di quanti nodi ha adiacenti*/
		adj.removeAll(Compcon);                /*non voglio fare la visita dei nodi che sono già nella componente connessa*/
		while(!adj.isEmpty()){
			v=RandomNodo(adj);
			if(v.isMarcatore()==true){      /*se v è stato visitato*/
				Integer c=getInt(v);
				if(c!=i){                /*se le componenti connesse dei due nodi adiacenti sono diverse*/ 
					Compcon.addAll(numcompcon.get(c));		
					numcompcon.put(c,Compcon);   /*pongo tutto come unica componente connessa*/
					ReCompcon(i);
					i=c;                     /*voglio farmi restituire dove ho salvato questa componente connessa*/
				}
				else {  /*se la componente connessa è la stessa*/
					Compcon.add(v);
					numcompcon.put(i,Compcon);
				}

			}
			else {  /*se v non è stato visitato*/
				v.setMarcatore(true);
				Compcon.add(v);
				numcompcon.put(i,Compcon);
				i=Visita(v,i,numcompcon);  /*lo visito*/
			}
			adj.removeAll(numcompcon.get(i));  
		}
		return i;
	}

	
	private void reset(Set<Nodo> Compcon) {    /*a tutti i nodi dell'insieme si pone il marcatore come false*/
		for(Nodo v : Compcon) 
			v.setMarcatore(false);
	}

	private Integer getInt(Nodo v) {            /*restituisce l'intero della componente connessa in cui si trova il nodo v*/
		Integer i=0;
		Set<Nodo> Compcon= new LinkedHashSet<Nodo>();
		for(int n=1;n<=numcompcon.size();n++){              
			Compcon=numcompcon.get(n); 
			if(Compcon.contains(v)){
				i=n;
				break;
			}
		}
		return i;
	}

	private void ReCompcon(int i){    /*rimuove la i-esima componente connessa e scala le successive*/
		numcompcon.remove(i);
		for(int n=i;n<=numcompcon.size();n++)
			numcompcon.put(n,numcompcon.get(n+1));
	}

			
}
