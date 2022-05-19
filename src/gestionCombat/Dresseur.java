package gestionCombat;
import gestionPokemon.*;
import interfaces.IEchange;
import interfaces.IPokemon;

public class Dresseur implements IEchange{
	private String identifiant;
	private String motDepasse;
	private String nom;
	private Pokemon[] equipe = new Pokemon[6];
	private int niveau;
	private Pokemon pokemon;
	private Pokemon pokemonChoisi;
	private Capacite actionChoisie;
	private String type; //joueur ou IA
	
	public Dresseur(String id, String mdp) {
		//on cree un dresseur en le recuperant dans le stockage
	}
	
	public Dresseur(String id, String mdp, String nom) {
		//on cree un dresseur en l'ajoutant au stockage
	}

	/////////////////////// methode de IEchange ///////////////////////
	public int calculeDommage(IPokemon lanceur, IPokemon receveur) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void utilise() {

	}

	
	public void setPokemon(IPokemon pok) {
		this.pokemonChoisi = (Pokemon) pok;
	}

	public IPokemon echangeCombattant() {
		Pokemon oldPokemonActif = this.pokemon;
		this.pokemon=this.pokemonChoisi;
		return oldPokemonActif;
	}
	/////////////////////////////////////////////////////////////////////
	
	public Pokemon getPokemon() {
		return this.pokemon;
	}

	public Pokemon getPokemonChoisi() {
		return pokemonChoisi;
	}
	
	public Pokemon[] getEquipe() {
		return this.equipe;
	}
	
	public void choisirPokemon() {
		System.out.println("Choisissez le pokemon à envoyer au combat : ");
		//affichage de l'equipe
		//scanner et tout
		Pokemon choosen = new Pokemon("carapute",new Espece(5));
		this.setPokemon(choosen);
	}

	public Capacite getActionChoisie() {
		return actionChoisie;
	}

	public void choisirAttaqueDe(Pokemon p) {
		this.actionChoisie = p.listeCapacite[1];
	}

	public void enseignerCapacite(Pokemon p){

	}
	
	public void attaquer(Dresseur other) {
		
	}
	
	public boolean pouvoirSeBattre() {
		boolean rep = false;
		int i=0;
		while (!rep && i<6) {
			rep = !this.equipe[i].estEvanoui();
		}
		return !rep;
	}
	
	public void soignerEquipe() {
		for (Pokemon p : this.equipe) {
			p.soigne();
		}
	}
}
