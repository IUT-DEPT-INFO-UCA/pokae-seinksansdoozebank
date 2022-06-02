package gestionCombat;

import java.io.FileNotFoundException;

import gestionPokemon.Capacite;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IPokemon;

/**
 * Permet de gérer un objet Joueur (un dresseur controlé par un humain via
 * l'entrée standard) hériant de Dresseur
 * 
 */
public class Joueur extends Dresseur {

	static final String strChoixCombattant = "\tChoix du Pokemon à envoyer au combat :";
	
	/**
	 * La chaine de caractère indiquant au joueur qu'il doit choisir une action à
	 * faire
	 */
	static final String strChoixAction = " est sur le terrain. Choix de l'action : ";
	
	static final String strListeChoixAction = "\t1- Attaquer\n"
										  + "\t\t2- Changer de pokemon";
	
	static final String strDernierPokemon = " est votre dernier pokemon, vous devez attaquer !";

	static final String strChoixAttaque = "\tChoix de l'attaque à utiliser : " ;
	
	private static boolean nextStep = false;
	
	public Joueur() {
		super();
	}
	
	/**
	 * le constructeur d'un joueur lorsqu'il se connecte
	 * 
	 * @param id  l'identifiant unique du joueur
	 * @param mdp le mot de passe du joueur
	 */
	public Joueur(String id) {
		super(id);
	}

	/**
	 * le constructeur d'un Joueur lorsqu'un joueur s'inscrit
	 * 
	 * @param id  identifiant unique de l'utilisateur
	 * @param mdp le mot de passe de l'utilisateur
	 * @param nom le nom du dresseur que l'utilisateur créé sint dresseur
	 */
	public Joueur(String id, String mdp, String nom) {
		super(id, mdp, nom);
	}

	public IPokemon choisitCombattant() {
		System.out.println(this.getNom() + strChoixCombattant);
		for (int i = 0; i < this.getEquipe().length; i++) {
			System.out.println("\t\t" + (i + 1) + "- " + this.getEquipe()[i]);
		}
		//System.out.println("\tEntrer le numéro du pokemon à envoyer au combat : ");
		int input = InputViaScanner.getInputInt(1, 6);
		Pokemon choosen = this.getEquipe()[input - 1];
		this.setPokemon(choosen);
		return choosen;
	}

	@Override
	public void selectAction(IPokemon p, IPokemon pAdv) {
		System.out.println(this.getNom() + "\t" + p.getNom() + Joueur.strChoixAction);
		if(this.getNbPokemonAlive()>1) {
			nextStep = false;
			while(!nextStep) {
				//System.out.println("nextStep = "+nextStep);
				System.out.println("\t" + strListeChoixAction);
				int input = InputViaScanner.getInputInt(1, 2);
				if (input == 1) {
					//System.out.println("JUSTE AVANT DE L'APPEL DE CHOISIATTAQUE()");
					this.choisitAttaque(p, pAdv);
				} else if(input == 2){
					p = this.choisitCombattantContre(pAdv);
					this.setActionChoisie(null);
				}
				if(!nextStep) {
					System.out.println(this.getNom() + "\t" + p.getNom() + Joueur.strChoixAction);
				}
			}
		}else {
			System.out.println(this.getNom() + "\t"+p.getNom()+strDernierPokemon);
			//System.out.println("JUSTE AVANT DE L'APPEL DE CHOISIATTAQUE()");
			this.choisitAttaque(p, pAdv);
		}
	}

	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		// TODO choix "annuler" pour revenir au choix des actions
		if (((Pokemon) attaquant).getNombreDeToursAvantAttaque() == 0) { // dans le cas ou patience a ete utilisee
			if (((Pokemon) attaquant).getCapacitesUtilisables().length > 0) {
				System.out.println(strChoixAttaque);
				ICapacite[] caps = attaquant.getCapacitesApprises();
				for (int i = 0; i < caps.length; i++) {
					System.out.println("\t\t" + (i + 1) + "- " + caps[i]);
				}
				System.out.println("\t\t0- Retour");
				int input = InputViaScanner.getInputIntCapacite(0, attaquant.getCapacitesApprises().length,attaquant.getCapacitesApprises());
				if(input!=0) {
					this.setActionChoisie((Capacite) ((Pokemon) attaquant).getCapacitesApprises()[input - 1]);
					nextStep = true;
				}else {
					System.out.println("retour");
				}
			} else {// utilisation de Lutte si aucune capacite n'est dispo
				try {
					this.setActionChoisie(Pokedex.createCapacite(((Capacite) Pokedex.getCapaciteStatic("Lutte")).id));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		} else {// utilisation de patience
			((Pokemon) attaquant).updateNombreDeToursAvantAttaque();// on decremente la duree avant la fin de Patience
			if (((Pokemon) attaquant).getNombreDeToursAvantAttaque() == 0) {// si Patience est prete
				((Pokemon) attaquant).setNombreDeToursAvantAttaque(-1); // on met le nb de tour a -1 pour que dans
																		// calculDommage() le nb nne soit pas remis a 2
			}

		}
		// System.out.println(attaquant.getNom()+" va utiliser "+this.actionChoisie);
		((Pokemon) attaquant).setAttaqueChoisie(this.getActionChoisie());
		return this.getActionChoisie();

	}


	public IPokemon choisitCombattantContre(IPokemon pok) {
		// TODO choix "annuler" pour revenir au choix des actions
		System.out.println(strChoixCombattant);
		for (int i = 0; i < this.getEquipe().length; i++) {
			if (!this.getEquipe()[i].estEvanoui())
				System.out.println("\t\t" + (i + 1) + "- " + this.getEquipe()[i]);
			else {
				System.out.println("\t\t" + "KO " + this.getEquipe()[i]);
			}
		}
		System.out.println("\t\t0- Retour");
		int input = InputViaScanner.getInputIntPokemon(0, 6, this.getEquipe());
		if(input!=0) {
			while (this.getEquipe()[input - 1].equals(this.getPokemon())) {
				System.out.println(this.getPokemon().getNom() + " est déjà au combat."); //TODO
				input = InputViaScanner.getInputIntPokemon(0, 6, this.getEquipe());
				if(input==0) {
					return this.getPokemon();
				}
			}
			Pokemon choosen = this.getEquipe()[input - 1];
			nextStep = true;
			this.setPokemonChoisi(choosen);
			return choosen;
		}else {
			return this.getPokemon();
		}
	}
	
	@Override
	public void enseigne(IPokemon pok, ICapacite[] caps) {
		Capacite capaciteAApprendre = this.canTeachAMove();
		if (capaciteAApprendre != null) {
			if (caps.length < 4) {
				// System.out.println(pok.getNom()+" peut apprendre
				// "+capaciteAApprendre.getNom()+" et il peut le faire seul.");
				try {
					this.getPokemon().remplaceCapacite(caps.length, capaciteAApprendre);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("\t" + pok.getNom() + " a appris " + capaciteAApprendre.getNom() + " !");
			} else {
				System.out.println("\t" + pok.getNom() + " veut apprendre " + capaciteAApprendre.getNom() + ".");
				System.out.println("\t1 - Oublier une capacité\n"
								 + "\t2 - Abandonner "+capaciteAApprendre.getNom());
				int inputChoix = InputViaScanner.getInputInt(1, 2);
				if (inputChoix == 1) {
					System.out.println("\tChoix de la capacité à oublier :");
					((Pokemon) pok).showCapaciteApprise();
					int inputCapacite = InputViaScanner.getInputInt(1, this.getPokemon().getCapacitesApprises().length);
					try {
						pok.remplaceCapacite(inputCapacite - 1, capaciteAApprendre);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("\t" + pok.getNom() + " n'a pas appris " + capaciteAApprendre.getNom() + ".");
				}
			}
		} else {
			// System.out.println(pok.getNom()+" n'a aucune capacite a apprendre au niveau
			// "+pok.getNiveau());
		}
	}

	
}
