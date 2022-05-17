package gestionPokemon;

import interfaces.IStat;

public class Stats implements IStat {
	
	public int force;
    public int defense;
    public int vitesse;
    public int special;
    public int pv;
    

	public Stats(int f, int d, int v, int s, int pv) {
		this.force=f;
		this.defense=d;
		this.vitesse=v;
		this.special=s;
		this.pv=pv;
	}
	
	public int getForce() {
		return force;
	}
	public int getDefense() {
		return defense;
	}
	public int getVitesse() {
		return vitesse;
	}
	public int getSpecial() {
		return special;
	}
	public int getPV() {
		return pv;
	}
	public void setForce(int force) {
		this.force = force;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}
	public void setSpecial(int special) {
		this.special = special;
	}
	public void setPV(int pv) {
		this.pv = pv;
	}
    
    

}
