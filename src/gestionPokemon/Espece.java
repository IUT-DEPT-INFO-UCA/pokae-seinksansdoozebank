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

		///////////////methode de IEspece/////////////////////////////////

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

		@Override
		public IEspece getEvolution(int niveau) {
			return this.evolution;
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
	
	
	}
