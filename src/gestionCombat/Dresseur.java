package gestionCombat;
import java.io.IOException;

import gestionPokemon.*;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IEchange;
import interfaces.IPokemon;
import interfaces.IStrategy;
import org.json.simple.parser.ParseException;

public abstract class Dresseur implements IDresseur,IEchange, IStrategy{
	private String identifiant;
	private String motDepasse;
	private String nom;
	private Pokemon[] equipe = new Pokemon[6];
	private int niveau;
	private Pokemon pokemon;
	private Pokemon pokemonChoisi; 

	private Capacite actionChoisie;

	private String type; //joueur ou sIA
	
	public Dresseur(String nom) {
		//constructeur pour les ia car pas besoin d'id ou de mdp
		this.nom = nom;
		try {
			this.equipe = (Pokemon[]) Pokedex.engendreRanchStatic();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		this.setNiveau();
		this.pokemon = this.equipe[0];
	}
	
	public Dresseur(String id, String mdp) {
		//on cree un dresseur en le recuperant dans le stockage
	}
	
	public Dresseur(String id, String mdp, String nom){
		//on cree un dresseur en l'ajoutant au stockage
		this.identifiant=id;
		this.motDepasse = mdp;
		this.nom = nom;
		try {
			this.equipe = (Pokemon[]) Pokedex.engendreRanchStatic();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		this.setNiveau();
		this.pokemon = this.equipe[0];
	}
	
	public String toString() {
		return this.nom;
	}
	/////////////////////// methode de IDresseur ///////////////////////
	

	public IPokemon getPokemon(int i) {
		return this.equipe[i];
	}
	

	@Override
	public void enseigne(IPokemon pok, ICapacite[] caps) {
		Capacite capaciteAApprendre = this.canTeachAMove();
		if(capaciteAApprendre!=null) {
			if(caps.length<4) {
				//System.out.println(pok.getNom()+" peut apprendre "+capaciteAApprendre.getNom()+" et il peut le faire seul.");
				try {
					this.getPokemon().remplaceCapacite(caps.length, capaciteAApprendre);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("\t"+pok.getNom()+" a appris "+capaciteAApprendre.getNom()+" !");
			}else {
				System.out.println("\t"+pok.getNom()+" veut apprendre "+capaciteAApprendre.getNom()+".");
				System.out.println("\tVoulez vous lui faire oublier une des ses capacités (1) ou ne pas l'apprendre (2) ?");
				int inputChoix = InputViaScanner.getInputInt(1, 2);
				if(inputChoix==1) {
					((Pokemon)pok).showCapaciteApprise();
					System.out.println("\tEntrer le numéro de la capacité à oublier (ou 0 pour annuler) :");
					int inputCapacite = InputViaScanner.getInputInt(1, this.getPokemon().getCapacitesApprises().length);
						try {
							pok.remplaceCapacite(inputCapacite-1, capaciteAApprendre);
						} catch (Exception e) {
							e.printStackTrace();
						}
				}else {
					System.out.println("\t"+pok.getNom()+" n'a pas appris "+capaciteAApprendre.getNom()+".");
				}
			}
		}else {
			//System.out.println(pok.getNom()+" n'a aucune capacite a apprendre au niveau "+pok.getNiveau());
		}
		
	}

	@Override
	public void soigneRanch() {
		for (Pokemon p : this.equipe) {
			p.soigne();
		}
	}

	public abstract	IPokemon choisitCombattant();

	public abstract IPokemon choisitCombattantContre(IPokemon pok);

	public abstract IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur);

	
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
		System.out.println(this.getNom()+" envoie "+pok.getNom()+" au combat.");
		this.pokemon = (Pokemon) pok;
	}
	

	public IPokemon echangeCombattant() {
		Pokemon oldPokemonActif = this.pokemon;
		System.out.print(this.getNom()+" rapelle "+oldPokemonActif.getNom()+" et ");
		this.pokemon=this.pokemonChoisi;
		System.out.println("envoie "+this.getPokemon().getNom());
		return oldPokemonActif;
	}
	/////////////////////////////////////////////////////////////////////
	
	public abstract void selectAction(IPokemon p, IPokemon pAdv);
	
	
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
	/*
	public void attaquer(Dresseur other) {
		other.getPokemon().subitAttaqueDe(this.getPokemon(), this.actionChoisie);
		this.utilise();
	}
	*/
	public boolean pouvoirSeBattre() {
		boolean peutSeBattre = false;
		int i=0;
		while (!peutSeBattre && i<Pokedex.getNbPokemonParRanch()) {
			peutSeBattre = !this.equipe[i].estEvanoui();
			i++;
		}
		return peutSeBattre;
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
