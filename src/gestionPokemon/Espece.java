	package gestionPokemon;

	import java.util.HashMap;
	import java.util.Map;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IStat;
import interfaces.IType;

	public class Espece implements IEspece {
	    private int id;
	    public String nom;
	    public Type type1;
	    public Type type2;
	
	    //Stats specifiques :
	    public int atq;
	    public int def;
	    public int vit;
	    public int spe;
	    public int pv;
	
	    public int nivDepart;
	    public int nivEvolution;
	    public Espece evolution;
	
	    private int expDeBase;
	
	    //Valeur d'Effort == puissance suite aux combats
	    private int gainAtq;
	    private int gainDef;
	    private int gainVit;
	    private int gainSpe;
	    private int gainPv;

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
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getNiveauDepart() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getBaseExp() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public IStat getGainsStat() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ICapacite[] getCapSet() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IEspece getEvolution(int niveau) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IType[] getTypes() {
			// TODO Auto-generated method stub
			return null;
		}
		//////////////////////////////////////////////////////////////
	    
	    public void setExpDeBase(int expDeBase) {
	        this.expDeBase = expDeBase;
	    }
	
	    public void setGainAtq(int gainAtq) {
	        this.gainAtq = gainAtq;
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
