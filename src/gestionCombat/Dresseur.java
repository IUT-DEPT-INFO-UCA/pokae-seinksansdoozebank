package gestionCombat;
import gestionPokemon.Pokemon;

public class Dresseur {
	private String nom;
	private Pokemon[] equipe = new Pokemon[6];
	private int niv=0;
	
	public Dresseur(String nom) {
		this.nom = nom;
	}
	
	public Dresseur() {
		this.nom = "Ash";
	}

	public void enseignerCapacite(Pokemon p){
		
	}
	
}
