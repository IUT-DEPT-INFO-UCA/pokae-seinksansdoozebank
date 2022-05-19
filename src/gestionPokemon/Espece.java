package gestionPokemon;

import java.util.HashMap;
import java.util.Map.Entry;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IStat;
import interfaces.IType;

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

    public int atq;
    public int def;
    public int vit;
    public int spe;
    public int pv;

	//Valeur d'Effort == puissance suite aux combats
	public Stats statsGain;
    /*
    private int gainAtq;
    private int gainDef;
    private int gainVit;
    private int gainSpe;
    private int gainPv;*/

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