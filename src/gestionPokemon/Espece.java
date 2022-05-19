package gestionPokemon;
//TODO LIST
/*

 getBaseStat
 getGainStat


*/














/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * Espece.java
 */

import java.util.HashMap;
import java.util.Map;
///Interfaces 
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IStat;
import interfaces.IType;

	public class Espece implements IEspece, IStat {
		/**
		 * @author Clement lefèvre, Antoine Fadda-Rodriguez, Thomas Gorisse
		 */
	    private int id;
	    public String nom;
	    public Type type1;
	    public Type type2;
	
	    //Stats specifiques :
	    public int force;
	    public int def;
	    public int vit;
	    public int spe;
	    public int pv;
	
	    public int nivDepart;
	    public int nivEvolution;
	    public Espece evolution;
	
	    private int expDeBase;
	
	    //Valeur d'Effort == puissance suite aux combats
	    private int gainForce;
	    private int gainDef;
	    private int gainVit;
	    private int gainSpe;
	    private int gainPv;

		//Liste des capacités
		private static HashMap<Capacite,Integer> capaciteSelonNiveau;
		

	    public Espece(int id) {
	        this.id = id;
	    }
public class Espece implements IEspece {
    private int id;
    public String nom;
    public Type type1;
    public Type type2;

    public int nivDepart;
    public int nivEvolution;
    public Espece evolution;
    private int expDeBase;

    //Stats specifiques :
    public Stats statsDeBase;
    /*
    public int atq;
    public int def;
    public int vit;
    public int spe;
    public int pv;*/
    
    //Valeur d'Effort == puissance suite aux combats
    public Stats statsGain;
    /*
    private int gainAtq;
    private int gainDef;
    private int gainVit;
    private int gainSpe;
    private int gainPv;*/


		@Override
		public IStat getBaseStat() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getNom() {
			return this.nom;
		}

		@Override
		public int getNiveauDepart() {
			return this.nivDepart;
		}

		@Override
		public int getBaseExp() {
			return this.expDeBase;
		}

		@Override
		public IStat getGainsStat() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ICapacite[] getCapSet() {
			return this.capaciteSelonNiveau;
		}

		public IEspece getEvolution(int niveau) {

			return this.evolution;
			if(this.evolution.nivDepart==niveau) {
				return this.evolution;
			}
			return null;
		}

		@Override
		public IType[] getTypes() {
			return {this.type1, this.type2};
		}
		///////////////////////////Méthodes de IStat///////////////////////////////////
		@Override
		public int getPV(){
			return this.pv;
		}
		@Override
		public int getForce(){
			return this.force;
		}
		@Override
		public int getDefense(){
			return this.def;
		}
		@Override
		public int getSpecial(){
			return this.spe;
		}
		@Override
		public int getVitesse(){
			return this.vit;
		}

		@Override
		public void setPV(int pv){
			this.pv = pv;
		}
		@Override
		public void setForce(int force){
			this.force = force;
		}
		@Override
		public void setDefense(int def){
			this.def = def;
		}
		@Override
		public void setVitesse(int vit){
			this.vit = vit;
		}
		@Override
		public void setSpecial(int spe){
			this.spe = spe;
		}

	    ///////////////////////////////////////////////////////////////////////////////
	    public void setExpDeBase(int expDeBase) {}

		//////////////////////////////////////////////////////////////
	    
	    public int getGainAtq() {
			return gainAtq;
		}

		public int getGainDef() {
			return gainDef;
		}

		public int getGainVit() {
			return gainVit;
		}

		public int getGainSpe() {
			return gainSpe;
		}

		public int getGainPv() {
			return gainPv;
		}

		public void setExpDeBase(int expDeBase) {
	        this.expDeBase = expDeBase;
	    }

	    public void setGainForce(int gainForce) {
	        this.gainAtq = gainForce;
	    }
	
	    public void setGainDef(int gainDef) {
	        this.gainDef = gainDef;
	    }
	
	    public void setGainVit(int gainVit) {
	        this.gainVit = gainVit;
	    }
	
	    public void setGainSpe(int gainSpe) {
	        this.gainSpe = gainSpe;
	    }
	
	    public void setGainPv(int gainPv) {
	        this.gainPv = gainPv;
	    }
	    
	    public int obtenirExpDeBase() {
	    	return this.expDeBase;
	    }

		public Capacite capaciteDispo(Pokemon pokemon){
			for (Map.Entry mapentry : capaciteSelonNiveau.entrySet()) {
				if (Integer.parseInt(mapentry.getKey().toString())<=pokemon.niv){
					return (Capacite) mapentry.getValue();
				}
			}
			return null;
		}
	
	    //Valeurs dï¿½terminantes == puissance native
	    // private int dvAtk;
	    // private int dvDef;
	    // private int dvVit;
	    // private int dvSpe;
	    // private int dvPV;

	private static HashMap<Capacite,Integer> capaciteSelonNiveau;
	
	
    public Espece(int id) {
        this.setId(id);
    }

	///////////////methode de IEspece/////////////////////////////////


	@Override
	public IStat getBaseStat() {
		// TODO Auto-generated method stub
		// return this.statsDeBase;
		return null;
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNiveauDepart() {
		return this.nivDepart;
	}

	@Override
	public int getBaseExp() {
		return this.expDeBase;
	}

	@Override
	public IStat getGainsStat() {
		return this.getGainsStat();
	}

	@Override
	public ICapacite[] getCapSet() {
		Capacite[] liste = new Capacite[Espece.capaciteSelonNiveau.size()];
		int i =0;
		for (Entry<Capacite, Integer> c : Espece.capaciteSelonNiveau.entrySet()) {
			liste[i] = (Capacite) c;
			i++;
		}
		return liste;
	}

	public IEspece getEvolution(int niveau) {
		if(this.evolution.nivDepart==niveau) {
			return this.evolution;
		}
		return null;
	}

	@Override
	public IType[] getTypes() {
		// TODO Auto-generated method stub
		return null;
	}
	//////////////////////////////////////////////////////////////

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setExpDeBase(int expDeBase) {
		this.expDeBase = expDeBase;
	}
    
    public int obtenirExpDeBase() {
    	return this.expDeBase;
    }

	public Capacite capaciteDispo(Pokemon pokemon){
		for (Entry<Capacite, Integer> c : Espece.capaciteSelonNiveau.entrySet()) {
			if (Integer.parseInt(c.getKey().toString())<=pokemon.niv){
				return ((Capacite)c);
			}
		}
		return null;
	}

}
