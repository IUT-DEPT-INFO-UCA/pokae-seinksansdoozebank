package gestionPokemon;

import interfaces.IStat;

public class Stats implements IStat {
	/**
	 * La stat de Force de l'ensemble.
	 */
	public int force;
	/**
	 * La stat de Defense de l'ensemble.
	 */
	public int defense;
	/**
	 * La stat de Vitesse de l'ensemble.
	 */
	public int vitesse;
	/**
	 * La stat de Special de l'ensemble.
	 */
	public int special;
	/**
	 * La stat de Point de Vie de l'ensemble.
	 */
	public int pv;


	/**Creer un objet Stat avec 5 valeurs.
	 * @param f La stat de Force de l'ensemble.
	 * @param d La stat de Defense de l'ensemble.
	 * @param v La stat de Vitesse de l'ensemble.
	 * @param s La stat Special de l'ensemble.
	 * @param pv La stat de Point de Vie de l'ensemble.
	 */
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
