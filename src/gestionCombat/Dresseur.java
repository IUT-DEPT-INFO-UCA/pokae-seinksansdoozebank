package gestionCombat;
import gestionPokemon.*;

public class Dresseur {
	private String identifiant;
	private String motDepasse;
	private String nom;
	private Pokemon[] equipe = new Pokemon[6];
	private int niveau;
	private Pokemon pokemonActif;
	private Pokemon pokemonChoisi;
	private Capacite actionChoisie;
	private String type; //joueur ou IA
	
	public Dresseur(String id, String mdp) {
		//on cree un dresseur en le recuperant dans le stockage
	}
	
	public Dresseur(String id, String mdp, String nom) {
		//on cree un dresseur en l'ajoutant au stockage
	}
	
	public Pokemon obtenirPokemonActif() {
		return this.pokemonActif;
	}
	public void mettrePokemonActifA(Pokemon pokemonActif) {
		this.pokemonActif = pokemonActif;
	}

	public Pokemon obtenirPokemonChoisi() {
		return pokemonChoisi;
	}
	public void choisirPokemon() {
		this.pokemonChoisi = this.equipe[1];
	}

	public Capacite obtenirActionChoisie() {
		return actionChoisie;
	}

	public void choisirAttaqueDe(Pokemon p) {
		this.actionChoisie = p.listeCapacite[1];
	}
	
	public void attaquer(Dresseur other) {
		
	}
	
	public boolean pouvoirSeBattre() {
		boolean rep = false;
		int i=0;
		while (!rep && i<6) {
			rep = !this.equipe[i].estMort();
		}
		return !rep;
	}
	
	public void soignerEquipe() {
		for (Pokemon p : this.equipe) {
			p.soigner();
		}
	}
	
}
