package gestionCombat;
import java.io.IOException;
import java.util.Scanner;

import gestionPokemon.*;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IEchange;
import interfaces.IPokemon;
import interfaces.IStrategy;
import org.json.simple.parser.ParseException;

public class Dresseur implements IDresseur,IEchange, IStrategy{
	private String identifiant;
	private String motDepasse;
	private String nom;
	public Pokemon[] equipe = new Pokemon[6]; //TODO set to private
	private int niveau;
	private Pokemon pokemon; //TODO set to private
	private Pokemon pokemonChoisi;

	private Capacite actionChoisie;

	private String type; //joueur ou IA
	
	public Dresseur(String id, String mdp) {
		//on cree un dresseur en le recuperant dans le stockage
	}
	
	public Dresseur(String id, String mdp, String nom) throws IOException, ParseException {
		//on cree un dresseur en l'ajoutant au stockage
		this.identifiant=id;
		this.motDepasse = mdp;
		this.nom = nom;
		this.equipe = (Pokemon[]) Pokedex.engendreRanchStatic();
		this.setNiveau();
		this.pokemon = this.equipe[0];
	}
	
	public String toSring() {
		return this.getNom();
	}
	/////////////////////// methode de IDresseur ///////////////////////
	

	public IPokemon getPokemon(int i) {
		return this.equipe[i];
	}
	

	@Override
	public void enseigne(IPokemon pok, ICapacite[] caps) {
			////////////////TODO :
			/*
			En gros, on veux faire l'algo suivant :
			On test si le niveau actuel nous donne une capacitée a apprendre :
			SI NON : 
				aucune capacitée a apprendre
			SI OUI :
				0--> On ne souhaite pas apprendre la capacitée
				
				1--> On souhaite l'apprendre
				=> SI 0 Alors on abandonne la capacitée et on garde notre tableau de capacité comme il est.
				=> SI 1 Alors, on parcours les capacitées. 
					=> SI on trouve un null alors on le remplace par la nouvelle capacitée a apprendre
					=> SI on ne trouve pas de null alors on demande a l'utilisateur de renseigner un entier compris entre 0 et 4 
						=> SI >4 ou <0 erreur
						=> SINON on remplace la capacitée de l'entier
		*/
			
		Capacite capaciteAApprendre = this.canTeachAMove();
		if(capaciteAApprendre!=null) {
			if(caps.length<4) {
				//System.out.println(pok.getNom()+" peut apprendre "+capaciteAApprendre.getNom()+" et il peut le faire seul.");
				try {
					this.getPokemon().remplaceCapacite(caps.length, capaciteAApprendre);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(pok.getNom()+" a appris "+capaciteAApprendre.getNom()+" !");
			}else {
				System.out.println(pok.getNom()+" veut apprendre "+capaciteAApprendre.getNom()+".");
				System.out.println("Voulez vous lui faire oublier une des ses capacités (1) ou ne pas l'apprendre (0) ?");
				try (Scanner scChoix = new Scanner(System.in)) {
					int inputChoix = scChoix.nextInt();
					if(inputChoix==1) {
						for(int i=0;i<pok.getCapacitesApprises().length;i++) {
							System.out.println(i+1+" - "+pok.getCapacitesApprises()[i]);
						}
						System.out.println("Entrer le numéro de la capacité à oublier (ou 0 pour annuler) :");
						try (Scanner scCapacite = new Scanner(System.in)) {
							int inputCapacite = scCapacite.nextInt()-1;
							try { //TODO reparer le scanner
								pok.remplaceCapacite(inputCapacite, capaciteAApprendre);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}else {
						System.out.println(pok.getNom()+" n'a pas appris "+capaciteAApprendre.getNom()+".");
					}

				}
			}
		}else {
			System.out.println(pok.getNom()+" n'a aucune capacite a apprendre au niveau "+pok.getNiveau());
		}
		
	}

	@Override
	public void soigneRanch() {
		for (Pokemon p : this.equipe) {
			p.soigne();
		}
	}

	@Override
	public IPokemon choisitCombattant() {
		System.out.println("Choisissez le pokemon à envoyer au combat : ");
		for(int i=0;i<this.getEquipe().length;i++) {
			System.out.println(i+1+" - "+this.getEquipe()[i]);
		}
		System.out.println("Entrer le numéro du pokemon choisi : ");
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		sc.close();
		Pokemon choosen = this.getEquipe()[input];
		this.setPokemon(choosen);
		return choosen;
	}

	@Override
	public IPokemon choisitCombattantContre(IPokemon pok) {
		System.out.println("Choisissez le pokemon à envoyer au combat : ");
		for(Pokemon p: this.getEquipe()) {
			System.out.print(p+"     ");
		}
		System.out.println("Choississez le numéro du pokemon à envoyer au combat : ");
		try (Scanner sc = new Scanner(System.in)) {
			int input = sc.nextInt();
			Pokemon choosen = this.getEquipe()[input+1];
			this.setPokemonChoisi(choosen);
			return choosen;
		}
	}

	@Override
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		for(ICapacite c : this.getPokemon().getCapacitesApprises()) {
			System.out.print(c+"     ");
		}
		System.out.println("Choississez le numéro de l'attaque à utiliser : ");
		try (Scanner sc = new Scanner(System.in)) {
			int input = sc.nextInt();
			this.actionChoisie = (Capacite) ((Pokemon)attaquant).getCapacitesApprises()[input+1];
			this.pokemon.setAttaqueChoisie(this.actionChoisie);
			return this.actionChoisie;
		}
	}

	
	/////////////////////// methode de IEchange ///////////////////////
	
	public int calculeDommage(IPokemon lanceur, IPokemon receveur) {
		//On return 0 puisque echange ne fait aucun degat
		// de toute facons on appelle jamais cette méthode puisque le calcul des dommage d'un dresseur n'a pas de sens
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

	public void setPokemonChoisi(IPokemon pokemonChoisi) {
		this.pokemonChoisi = (Pokemon) pokemonChoisi;
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

	public void setNiveau() {
		for(Pokemon p : this.getEquipe()) {
			this.niveau+=p.getNiveau();
		}
	}

	public void setType(String type) {
		this.type = type;
	}

	public Capacite getActionChoisie() {
		return actionChoisie;
	}

	public void setActionChoisie(Capacite actionChoisie) {
		this.actionChoisie = actionChoisie;
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
	
	public Capacite canTeachAMove() {
		//System.out.println("appel de getLearnableMove().");
		return this.getPokemon().espPoke.getLearnableMove(this.getPokemon().getNiveau());
	}
	
	public void showTeam() {
		for(Pokemon p : this.getEquipe()) {
			System.out.println(p);
		}
	}
}
