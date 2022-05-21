package gestionCombat;
import java.util.Scanner;

import gestionPokemon.*;
import interfaces.ICapacite;
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
		this.getPokemon().utilise(this.getActionChoisie());
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
	
	
	public String getIdentifiant() {
		return identifiant;
	}

	public String getMotDepasse() {
		return motDepasse;
	}

	public String getNom() {
		return nom;
	}

	public int getNiveau() {
		return niveau;
	}

	public String getType() {
		return type;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public void setMotDepasse(String motDepasse) {
		this.motDepasse = motDepasse;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void choisirPokemon() {
		System.out.println("Choisissez le pokemon à envoyer au combat : ");
		for(Pokemon p: this.getEquipe()) {
			System.out.print(p+"     ");
		}
		System.out.println("Choississez le numéro du pokemon à envoyer au combat : ");
		try (Scanner sc = new Scanner(System.in)) {
			int input = sc.nextInt();
			Pokemon choosen = this.getEquipe()[input+1];
			this.setPokemon(choosen);
		}
	}

	public Capacite getActionChoisie() {
		return actionChoisie;
	}

	public void choisirAttaqueDe(Pokemon p) {
		for(ICapacite c : this.getPokemon().getCapacitesApprises()) {
			System.out.print(c+"     ");
		}
		System.out.println("Choississez le numéro de l'attaque à utiliser : ");
		try (Scanner sc = new Scanner(System.in)) {
			int input = sc.nextInt();
			this.actionChoisie = p.listeCapacite[input+1];
			this.pokemon.setAttaqueChoisie(this.actionChoisie);
		}
	}

	public void enseignerCapacite(Pokemon p){

	}
	
	public void attaquer(Dresseur other) {
		other.getPokemon().subitAttaqueDe(this.getPokemon(), this.actionChoisie);
		this.utilise();
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
