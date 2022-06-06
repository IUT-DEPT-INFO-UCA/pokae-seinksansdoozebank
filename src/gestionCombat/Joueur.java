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

	/**
	 * Une chaîne constante stockant le message à écrire dans logs quand un
	 * utilisateur se connecte
	 */
	private static final String strLogConnexion = "s'est connecté.";
	/**
	 * Une chaîne constante stockant le message à écrire dans logs quand un
	 * utilisateur s'inscrit
	 */
	private static final String strLogIinscription = "s'est inscrit.";
	/**
	 * Une chaîne constante stockant le message annonçant au joueur qu'il doit
	 * choisir une action
	 */
	private static final String strChoixAction = " est sur le terrain. Choix de l'action : ";
	/**
	 * Une chaîne constante stockant le message annonçant au joueur qu'il doit
	 * choisir un Pokemon
	 */
	private static final String strChoixCombattant = "\tChoix du Pokemon à envoyer au combat :";
	/**
	 * Une chaîne constante stockant le message annonçant au joueur que son pokemon
	 * est le dernier de son ranch a pouvoir se battre
	 */
	private static final String strDernierPokemon = " est votre dernier pokemon, vous devez attaquer !";
	/**
	 * Une chaîne constante stockant le message annonçant au joueur qu'il
	 * doitchoisir une capacité à utiliser
	 */
	private static final String strChoixAttaque = "\tChoix de l'attaque à utiliser : ";
	/**
	 * Une chaîne constante stockant le message annonçant au joueur la liste des
	 * action qu'il peut faire
	 */
	private static final String strListeChoixAction = "\t1- Attaquer\n" + "\t\t2- Changer de pokemon";
	/**
	 * Un booléen inndiquant si lors d'un choix d'action, l'utilisateur a souhaité validé son choix ou s'il est plutot revenu au menu précédent
	 */
	private boolean nextStep = false;

	/**
	 * Constructeur d'un joueur qui l'instancie avec Dresseur
	 */
	public Joueur() {
		super();
	}

	/**
	 * le constructeur d'un joueur lorsqu'il se connecte
	 * 
	 * @param id l'identifiant unique du joueur
	 */
	public Joueur(String id) {
		super(id);
		Combat.addLog(id + " " + strLogConnexion);
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
		Combat.addLog(id + " " + strLogIinscription);
	}

	/////////////////////// methode abstraites de Dresseur ///////////////////////
	@Override
	public IPokemon choisitCombattant() {
		System.out.println(this.getNom() + strChoixCombattant);
		for (int i = 0; i < this.getEquipe().length; i++) {
			System.out.println("\t\t" + (i + 1) + "- " + this.getEquipe()[i]);
		}
		// System.out.println("\tEntrer le numéro du pokemon à envoyer au combat : ");
		int input = InputViaScanner.getInputInt(1, 6);
		Pokemon choosen = this.getEquipe()[input - 1];
		this.setPokemon(choosen);
		return choosen;
	}

	@Override
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		System.out.println(this.getNom() + "\t" + attaquant.getNom() + Joueur.strChoixAction);
		if (this.getNbPokemonAlive() > 1) {
			nextStep = false;
			while (!nextStep) {
				System.out.println("\t" + strListeChoixAction);
				int input = InputViaScanner.getInputInt(1, 2);
				if (input == 1) {
					IAttaque tmp = this.choisitCapacite(attaquant);
					if (nextStep) {
						return tmp;
					}
				} else if (input == 2) {
					IAttaque tmp = new Echange(this.choisitCombattantContre(defenseur), this);
					if (nextStep) {
						// attaquant = this.choisitCombattantContre(defenseur);
						return tmp;
					}
				}
				if (!nextStep) {
					System.out.println(this.getNom() + "\t" + attaquant.getNom() + Joueur.strChoixAction);
				}
			}
		} else {
			System.out.println(this.getNom() + "\t" + attaquant.getNom() + strDernierPokemon);
			// System.out.println("JUSTE AVANT DE L'APPEL DE CHOISIATTAQUE()");
			return this.choisitCapacite(attaquant);
		}
		return null;
	}

	@Override
	public IPokemon choisitCombattantContre(IPokemon pok) {
		boolean avecRetour = !this.getPokemon().estEvanoui();
		System.out.println(strChoixCombattant);
		for (int i = 0; i < this.getEquipe().length; i++) {
			if (!this.getEquipe()[i].estEvanoui())
				System.out.println("\t\t" + (i + 1) + "- " + this.getEquipe()[i]);
			else {
				System.out.println("\t\t" + "KO " + this.getEquipe()[i]);
			}
		}
		int inputMin;
		if (avecRetour) {
			System.out.println("\t\t0- Retour");
			inputMin = 0;
		} else {
			inputMin = 1;
		}
		int input = InputViaScanner.getInputIntPokemon(inputMin, 6, this.getEquipe());
		if (input != 0) {
			while (this.getEquipe()[input - 1].equals(this.getPokemon())) {
				System.out.println(this.getPokemon().getNom() + " est déjà au combat.");
				input = InputViaScanner.getInputIntPokemon(inputMin, 6, this.getEquipe());
				if (input == 0) {
					return this.getPokemon();
				}
			}
			Pokemon choosen = this.getEquipe()[input - 1];
			nextStep = true;
			this.setPokemonChoisi(choosen);
			return choosen;
		} else {
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
						+ "\t2 - Abandonner " + capaciteAApprendre.getNom());
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
	/////////////////////////////////////////////////////////////////////


	/**
	 * Methode permettant à l'utilisateur de choisr la capacité à utiliser
	 * @param attaquant Le Pokémon actuellement au combat du Dresseur courant
	 * @return La capacité à utiliser sous forme d'un IAttaque
	 */
	public IAttaque choisitCapacite(IPokemon attaquant) {
		if (((Pokemon) attaquant).getNombreDeToursAvantAttaque() == 0) { // dans le cas ou patience a ete utilisee
			if (((Pokemon) attaquant).getCapacitesUtilisables().length > 0) {
				System.out.println(strChoixAttaque);
				ICapacite[] caps = attaquant.getCapacitesApprises();
				for (int i = 0; i < caps.length; i++) {
					System.out.println("\t\t" + (i + 1) + "- " + caps[i]);
				}
				System.out.println("\t\t0- Retour");
				int input2 = InputViaScanner.getInputIntCapacite(0, attaquant.getCapacitesApprises().length,
						attaquant.getCapacitesApprises());
				// input valide
				if (input2 != 0) {// si on fait pas retour
					// on set l'action
					this.setActionChoisie((Capacite) ((Pokemon) attaquant).getCapacitesApprises()[input2 - 1]);
					nextStep = true; // et on valide la sortie de la boucle
				} else {
					// System.out.println("retour");
				}
			} else {// utilisation de Lutte si aucune capacite n'est dispo
				ICapacite cap = null;
				try {
					cap = Pokedex.createCapacite(((Capacite) Pokedex.getCapaciteStatic("Lutte")).id);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				System.out.println(strChoixAttaque);
				System.out.println("\t\t1- " + cap);
				System.out.println("\t\t0- Retour");
				int input3 = InputViaScanner.getInputInt(0, 1);
				if (input3 == 1) {
					this.setActionChoisie(cap);
					nextStep = true;
				} else {
					// System.out.println("retour");
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
		this.getPokemon().setAttaqueChoisie((Capacite) this.getActionChoisie());
		return this.getActionChoisie();
	}
}
