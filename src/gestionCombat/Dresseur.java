package gestionCombat;

import java.io.FileNotFoundException;
import java.io.FileWriter;


import java.io.IOException;

import gestionPokemon.*;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IEchange;
import interfaces.IPokemon;
import interfaces.IStrategy;
import org.json.simple.parser.ParseException;
/**
 * Un objet représenant un dresseur
 *
 */
public abstract class Dresseur implements IDresseur, IEchange, IStrategy {
	/**
	 * L'identifiat unique du dresseur
	 */
	private String identifiant;
	/**
	 * le mot de passe du dresseur
	 */
	private String motDepasse;
	/**
	 * le nom du dresseur
	 */
	private String nom;
	/**
	 * le ranch de 6 pokemons du dresseur
	 */
	private Pokemon[] equipe = new Pokemon[Pokedex.getNbPokemonParRanch()];
	/**
	 * le niveau du dresseur, soit la somme des niveaux des pokemons de son ranch
	 */
	private int niveau;
	/**
	 * le pokemon actif du dresseur
	 */
	private Pokemon pokemon;
	/**
	 * le pokemon choisi par le dresseur, celui qui va etre envoyé au combat
	 */
	private Pokemon pokemonChoisi;
	/**
	 * l'action choisi par le dresseur pour le tour actuel
	 */
	private Capacite actionChoisie;

	/**
	 * le constructeur d'un dresseur pour une IARandom
	 * 
	 * @param nom le nom du dresseur
	 */
	public Dresseur(String nom) {
		this.nom = nom;
		try {
			this.equipe = (Pokemon[]) Pokedex.engendreRanchStatic();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		this.updateNiveau();
		this.pokemon = this.equipe[0];
		saveData(identifiant, motDepasse);

	}
	public void saveData(String id, String mdp){
		//FileWriter fichier = new FileWriter("")
	}
	public void connection(String nom, String mdp){
/*
Ecrire un systeme de sauvegarde de données
Algo :
Parcours le fichier Excel ou JSON ou une base de donnée
On recherche l'identifiant de l'utilisateur.
	Si non trouvé => erreur
	Sinon :
		On compare avec le mot de passe saisi :
			Si incorrect => erreur
			Sinon connected = true et dans tous nos getters de pokémon on met :
				Si connected alors tu peux return sinon return null
 */
		if(this.nom == nom && this.identifiant == mdp){
			System.out.println("Vous etes connecté");
		}
	}

	public String toSring() {
		return this.getNom();


	/**
	 * le constructeur du Dresseur lorsqu'un joueur se connecte
	 * 
	 * @param id  identifiant unqiue de l'utilisateur
	 * @param mdp le mot de passe de l'utilisateur
	 */
	public Dresseur(String id, String mdp) {
		// on cree un dresseur en le recuperant dans le stockage
	}

	/**
	 * le constructeur du Dresseur lorsqu'un joueur s'inscrit
	 * 
	 * @param id  identifiant unqiue de l'utilisateur
	 * @param mdp le mot de passe de l'utilisateur
	 * @param nom le nom du dresseur que l'utilisateur créé sint dresseur
	 */
	public Dresseur(String id, String mdp, String nom) {
		// on cree un dresseur en l'ajoutant au stockage
		this.identifiant = id;
		this.motDepasse = mdp;
		this.nom = nom;
		try {
			this.equipe = (Pokemon[]) Pokedex.engendreRanchStatic();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		this.updateNiveau();
		this.pokemon = this.equipe[0];
	}

	/**
	 * Definition de l'affichage d'un dresseur par son nom
	 */
	public String toString() {
		return this.nom;

	}
	/////////////////////// methode de IDresseur (et IStrategy) ///////////////////////

	public IPokemon getPokemon(int i) {
		return this.equipe[i];
	}

	@Override
	public abstract void enseigne(IPokemon pok, ICapacite[] caps);

	@Override
	public void soigneRanch() {
		for (Pokemon p : this.equipe) {
			p.soigne();
		}
	}

	public abstract IPokemon choisitCombattant();

	public abstract IPokemon choisitCombattantContre(IPokemon pok);

	public abstract IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur);

	/////////////////////// methode de IEchange ///////////////////////

	public int calculeDommage(IPokemon lanceur, IPokemon receveur) {
		// On return 0 puisque echange ne fait aucun degat
		// de toute facons on appelle jamais cette méthode puisque le calcul des dommage
		// d'un dresseur n'a pas de sens
		return 0;
	}

	public void utilise() {
		this.getPokemon().utilise(this.getActionChoisie());
	}

	public void setPokemon(IPokemon pok) {
		System.out.println(this.getNom() + " envoie " + pok.getNom() + " au combat.");
		this.pokemon = (Pokemon) pok;
	}

	public IPokemon echangeCombattant() {
		Pokemon oldPokemonActif = this.pokemon;
		System.out.print(this.getNom() + " rapelle " + oldPokemonActif.getNom() + " et ");
		this.pokemon = this.pokemonChoisi;
		System.out.println("envoie " + this.getPokemon().getNom());
		return oldPokemonActif;
	}
	/////////////////////////////////////////////////////////////////////

	/**
	 * La fonction prend deux objets IPokemon, et le dresseur du premier objet
	 * IPokemon sélectionnera une action à effectuer : une capacité qui touchera le
	 * Pokemon pAdv ou un changement de Pokemon
	 * 
	 * @param p    Le pokémon qui utilise le mouvement
	 * @param pAdv Le pokémon de l'adversaire
	 */
	public abstract void selectAction(IPokemon p, IPokemon pAdv);

	/**
	 * Retourne le pokémon actif du dresseur
	 * 
	 * @return L'objet pokémon actif du dresseur.
	 */
	public Pokemon getPokemon() {
		return this.pokemon;
	}

	/**
	 * Cette fonction renvoie l'attribut pokemonChoisi (le pokemonChoisi est le
	 * pokemon qui sera envoyé au combat lors de l'appel de la méthode
	 * echangeCombattant() )
	 * 
	 * @return Le pokemonChoisi par le dresseur.
	 */
	public Pokemon getPokemonChoisi() {
		return pokemonChoisi;
	}

	/**
	 * Défini le pokemon que le dresseur veut envoyer au combat dans l'attribut
	 * pokemonChoisi
	 * 
	 * @param pokemonChoisi le pokémon que le dresseur veut envoyer au combat
	 */
	public void setPokemonChoisi(IPokemon pokemonChoisi) {
		this.pokemonChoisi = (Pokemon) pokemonChoisi;
	}

	/**
	 * Cette fonction renvoie le ranch du ranch du Dresseur sous forme de tableau de
	 * Pokemon
	 * 
	 * @return Le tableau équipe.
	 */
	public Pokemon[] getEquipe() {
		return this.equipe;
	}

	/**
	 * Cette fonction renvoie l'identifiant du dresseur
	 * 
	 * @return L'identifiant du dresseur
	 */
	public String getIdentifiant() {
		return identifiant;
	}

	/**
	 * Cette fonction renvoie le mot de passe du dresseur
	 * 
	 * @return La méthode renvoie la valeur de la variable motDepasse.
	 */
	public String getMotDepasse() {
		return motDepasse;
	}

	/**
	 * Cette fonction renvoie le nom du du dresseur
	 * 
	 * @return Le nom du du dresseur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Cette fonction renvoie le niveau du dresseur
	 * 
	 * @return La valeur de la variable niveau.
	 */
	public int getNiveau() {
		return niveau;
	}

	/**
	 * Cette fonction fixe la valeur de l'attribut identifiant du dresseur à la
	 * valeur du paramètre
	 * identifiant
	 * 
	 * @param identifiant L'identifiant unique du dresseur.
	 */
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	/**
	 * Cette fonction définit le mot de passe du dresseur
	 * 
	 * @param motDepasse Le mot de passe du dresseur
	 */
	public void setMotDepasse(String motDepasse) {
		this.motDepasse = motDepasse;
	}

	/**
	 * Cette fonction fixe la valeur de la variable nom à la valeur du paramètre nom
	 * 
	 * @param nom Le nom du paramètre.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Cette fonction calcule le niveau total du dresseur en fonction du niveau des
	 * pokemons de son ranch
	 */
	public void updateNiveau() {
		int s=0;
		for (Pokemon p : this.getEquipe()) {
			s += p.getNiveau();
		}
		this.niveau = s;
	}

	/**
	 * Cette fonction renvoie l'action choisie par le dresseur
	 * 
	 * @return L'action choisie par le dresseur.
	 */
	public Capacite getActionChoisie() {
		return actionChoisie;
	}

	/**
	 * Cette fonction définit l'action choisie par le dresseur
	 * 
	 * @param actionChoisie L'action que le dresseur a choisi d'utiliser.
	 */
	public void setActionChoisie(Capacite actionChoisie) {
		this.actionChoisie = actionChoisie;
	}

	/**
	 * Cette fonction retourne vrai si le joueur peut combattre, faux si tout ces
	 * pokemons sont KO
	 * 
	 * @return Une valeur booléenne.
	 */
	public boolean pouvoirSeBattre() {
		return this.getNbPokemonAlive()>0;
	}
	
	public int getNbPokemonAlive() {
		int nb=0;
		for(Pokemon p : this.getEquipe()) {
			if(!p.estEvanoui()) {
				nb++;
			}
		}
		return nb;
	}
		

	/**
	 * Il renvoie le mouvement que le pokémon peut apprendre à son niveau actuel
	 * 
	 * @return La méthode renvoie le mouvement apprenable du pokemon.
	 */
	public Capacite canTeachAMove() {
		// System.out.println("appel de getLearnableMove().");
		return this.getPokemon().espPoke.getLearnableMove(this.getPokemon().getNiveau());
	}

	/**
	 * Cette fonction affiche l'équipe du joueur, utile lorsqu'on veut qu'il
	 * choisisse un pokemon
	 */
	public void showTeam() {
		for (Pokemon p : this.getEquipe()) {
			System.out.println(p);
		}
	}
	/**
	 * Permet d'afficher les statistique d'un dresseur comme son niveau et la composition de son équipe
	 */
	public void afficherStat(){
    	System.out.println(this.getNom()+" est niveau "+this.getNiveau() + " et son equipe est composé de :");
    	this.showTeam();
	}
}
