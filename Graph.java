package tarjan;

import java.util.concurrent.ConcurrentHashMap;
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
	private Map<Nodo,Set<Nodo>> numcompcon=new ConcurrentHashMap<Nodo, Set<Nodo>>();

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

	public Map<Nodo,Set<Nodo>> getNumCompCon(){
		return numcompcon;
	}

	public void setNumCompCon(Map<Nodo,Set<Nodo>> numcompcon) {
		this.numcompcon=numcompcon;
	}

	

	public Graph() {                                   /*costruttore*/
		this.nodi= new ConcurrentHashMap<Nodo,Set<Arco>>();     /*Map è interfaccia non può essere istanzializzata*/
	}

	public Graph(Set<Nodo> insnodi,Set<Arco> insarchi){   /*costruttore*/
		this.nodi=new ConcurrentHashMap<Nodo,Set<Arco>>(); 
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
		if(!getAllNodi().contains(v))
			v=getCC(v);
		if(!getAllNodi().contains(w))
			w=getCC(w);
		((this.nodi).get(v)).add(a);
		((this.nodi).get(w)).add(a);		
	}

	public void RemoveArco(Arco a) {    /*rimuovere arco*/
		Nodo v=a.getNodo1();
		Nodo w=a.getNodo2();
		((this.nodi).get(v)).remove(a);
		((this.nodi).get(w)).remove(a);
	}

	public void AddNodo(Nodo v,Set<Arco> insarchi) { /*aggiungere nodo*/
		this.nodi.put(v,insarchi);
		for(Arco a : insarchi) {
			AddArco(a);
		}
	}

	public void RemoveNodo(Nodo v) {
		for(Nodo w : this.nodiAdiacenti(v)) {
			Arco a=new Arco(v,w);
			(this.nodi.get(w)).remove(a);
		}
		for(Nodo w : this.nodiIncidenti(v)) {
			  Arco a=new Arco(w,v);
			(this.nodi.get(w)).remove(a);
		}                                             
		this.nodi.remove(v);
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
				if(a.getNodo1()==v)
					adj.add(a.getNodo2());
			}
		}		
		return adj;
	}

	private Set<Nodo> nodiIncidenti(Nodo v) {  /*restituisce insieme di nodi incidenti a v */
		LinkedHashSet<Nodo> inc=new LinkedHashSet();		
		if((this.nodi).containsKey(v) && nodi.get(v)!=null) {
			for(Arco a : this.archiIncidenti(v)){
				if(a.getNodo2()==v)
					inc.add(a.getNodo1());
			}
		}		
		return inc;
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
		else {	
			Set<Nodo> nodes= new LinkedHashSet(getAllNodi());	
			for(Nodo v : nodes) {                                       /*while su insieme di nodi che si modifica*/
				List<Nodo> Compcon1=new ArrayList<Nodo>();	
				if(v.isMarcatore()==false){          /*se il nodo non è mai stato visitato*/
					v.setMarcatore(true);
					Compcon1.add(v);
					Visita(v,Compcon1);         /*visito a partire dal nodo in esame*/				
				}				
			}			
		    	resetMarc();
		}
		for(Nodo w : numcompcon.keySet())
			System.out.println(numcompcon.get(w)+" ");
	}				
		
				
	
	private void Visita(Nodo w,List<Nodo> Compcon1){ /*visito a partire dal nodo in esame*/
		LinkedHashSet<Nodo> adj=new LinkedHashSet(nodiAdiacenti(w));
		Set<Nodo> Compcon=new LinkedHashSet();
		LinkedHashSet<Nodo> inc=new LinkedHashSet(nodiIncidenti(w));
		if(inc.isEmpty() || adj.isEmpty()){
			Compcon1.remove(w);
			w.setMarcatore(true);                
		}
		else {		
			for(Nodo v : adj){		
				if(v.isMarcatore()==true){      /*se v è stato visitato*/
					if(Compcon1.contains(v)) {
						int c=Compcon1.indexOf(v);
						Compcon.add(v);
						LinkedHashSet<Arco> ar=new LinkedHashSet(archiIncidenti(v));
						for(int j=c+1;j<=Compcon1.size();j++) {
							Nodo h=Compcon1.get(j);
							System.out.println(h + "");
							Compcon.add(h);
							if(!archiIncidenti(h).isEmpty()) {
								for(Arco a : archiIncidenti(h)) {
									if(a.getNodo2()==h){
										RemoveArco(a);
										a.setNodo2(v);
									}
									else {
										RemoveArco(a);
										a.setNodo1(v);
									}	
									ar.add(a);
								}
								this.nodi.remove(h);
								Compcon1.remove(h);
							}
						}
						numcompcon.put(v,Compcon); 
						this.nodi.remove(v);
						AddNodo(v,ar);	
						Visita(v,Compcon1);			
					}
				}
				else {  /*se v non è stato visitato*/
					v.setMarcatore(true);
					Compcon1.add(v);
					Visita(v,Compcon1);  /*lo visito*/
				}
			}	
		}
	}

	
	private void resetMarc() {    
		for(Nodo v : getAllNodi()) 
			v.setMarcatore(false);
	}

	private Nodo getCC(Nodo v) {            
		Set<Nodo> Compcon= new LinkedHashSet<Nodo>();
		for(Nodo w : numcompcon.keySet()) {              
			Compcon=numcompcon.get(w); 
			if(Compcon.contains(v))
				return w;
		}
		return null;
	}			
}
