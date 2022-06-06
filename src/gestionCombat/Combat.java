package gestionCombat;

import gestionPokemon.Pokemon;
import interfaces.*;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Un objet Combat representant un duel entre 2 dresseurs
 *
 */
public class Combat implements ICombat {
	/**
	 * La chaine de caractère affichée au début du combat.
	 */
	static final String msgDebutCombat = "\nQue le combat commence !";
	/**
	 * Le nombre de tours du combat
	 */
	private int nbTours;
	/**
	 * Le premier dresseur participant du Combat
	 */
	private Dresseur dresseur1;
	/**
	 * Le Pokemon du premier dresseur
	 */
	private IPokemon pokemon1;
	/**
	 * Le deuxieme dresseur participant du Combat
	 */
	private Dresseur dresseur2;
	/**
	 * Le Pokemon du deuxième dresseur
	 */
	private IPokemon pokemon2;
	/**
	 * Une liste des tours de ce combat
	 */
	private List<ITour> tours = new ArrayList<ITour>();
	/**
	 * Le dresseur vainqueur du combat
	 */
	private Dresseur vainqueur;

	/**
	 * Cette fonction renvois le nombre de tours du combat
	 * 
	 * @return Le nombre de tours du combat
	 */
	public int getNbTours() {
		return this.nbTours;
	}

	/**
	 * Le constructeur de la classe Combat à partir des dresseurs qui s'affrontent
	 *
	 * @param d1 Le premier dresseur
	 * @param d2 Le deuxième dresseur
	 */
	public Combat(Dresseur d1, Dresseur d2) {
		this.nbTours = 0;
		this.dresseur1 = d1;
		this.dresseur2 = d2;
		this.dresseur1.setAdversaire(this.dresseur2);
		this.dresseur2.setAdversaire(this.dresseur1);
	}

	///////////////////// Méthodes de ICombat /////////////////////

	public void commence() {
		this.nbTours = 1;
		this.pokemon1 = this.dresseur1.choisitCombattant();
		System.out.println("");
		this.pokemon2 = this.dresseur2.choisitCombattant();
		System.out.println(msgDebutCombat);
	}

	public IDresseur getDresseur1() {
		return (IDresseur) this.dresseur1;
	}

	public IDresseur getDresseur2() {
		return (IDresseur) this.dresseur2;
	}

	public ITour nouveauTour(IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2) {
		this.nbTours++;
		return new Tour(this.nbTours);
	}

	public void termine() {
		dresseur1.soigneRanch();
		dresseur1.updateNiveau();
		dresseur2.soigneRanch();
		dresseur2.updateNiveau();
	}

	///////////////////////////////////////////////////////////////

	/**
	 * La fonction getVainqueur() est une fonction qui lance une bataille, puis
	 * exécute les actions des deux dresseurs tant que les 2 peuvent se battrent, et
	 * renvoie enfin le vainqueur de la bataille
	 * 
	 * @return Le Dresseur vainqueur de la bataille.
	 */
	public Dresseur getVainqueur() {
		this.commence();
		while (!this.estTermine()) {
			this.startTour();
			tours.add(this.nouveauTour(this.pokemon1, this.dresseur1.getActionChoisie(), this.pokemon2,
					this.dresseur2.getActionChoisie()));
			System.out.println("");
			this.executerActions();
		}
		this.termine();
		return this.vainqueur;
	}

	/**
	 * Si les deux dresseurs peuvent se battre, retournez faux.
	 * Si l'un des 2 ne peut plus se battre, on déclare l'autre comme vainqueur du
	 * combat, et on retourne vrai, le combat est terminé
	 * 
	 * @return un booleen indiquant si le combat es terminé ou non
	 */
	private boolean estTermine() {
		// System.out.println(dresseur1.pouvoirSeBattre());
		// System.out.println(dresseur2.pouvoirSeBattre());
		if (dresseur1.pouvoirSeBattre() && dresseur2.pouvoirSeBattre()) {
			return false;
		} else if (!dresseur1.pouvoirSeBattre()) {
			this.vainqueur = dresseur2;
		} else if (!dresseur2.pouvoirSeBattre()) {
			this.vainqueur = dresseur1;
		}
		return true;
	}

	/**
	 * La fonction startTour() permet de démarrer un nouveau tour en affiachant le
	 * pokemon actif de chacun des dresseurs et en leur permettant de choisir leur
	 * action pour ce tour
	 */
	private void startTour() {
		System.out.println("\n\n======================= Début du tour " + this.nbTours + " =======================\n");
		System.out.println(dresseur1.getNom() + "\t" + this.pokemon1/* .getNom()+" "+((Pokemon)pokemon1).getPVBar() */);
		System.out.println(dresseur2.getNom() + "\t" + this.pokemon2/* .getNom()+" "+((Pokemon)pokemon2).getPVBar() */);
		System.out.println("");
		// recuperation du choix d'action du dresseur1
		dresseur1.setActionChoisie(dresseur1.choisitAttaque(pokemon1, pokemon2));
		System.out.println("");
		// recuperation du choix d'action du dresseur2
		dresseur2.setActionChoisie(dresseur2.choisitAttaque(pokemon2, pokemon1));
	}

	/**
	 * C'ets la méthode principale de déroulement d'un tour. Si les 2 dresseur
	 * veulent changer de pokémon, l'ordre d'actino n'est pas important. Si l'un
	 * change de pokémon et l'autre attaque, alors c'est l'echange qui se fait en
	 * premier. Enfin si les 2 veulent attaquer, alors le pokémon le plus rapide
	 * attaque en premier
	 */
	private void executerActions() {
		// d1 echange...
		if (this.dresseur1.getActionChoisie() instanceof Echange) { // d1 echange
			dresseur1.getActionChoisie().utilise();
			pokemon1 = dresseur1.getPokemon();
			// ...et d2 echange
			if (this.dresseur2.getActionChoisie() instanceof Echange) {
				dresseur2.getActionChoisie().utilise();
				pokemon2 = dresseur2.getPokemon();
				// ...et d2 attaque
			} else {
				System.out.println("");
				pokemon1.subitAttaqueDe(pokemon2, dresseur2.getActionChoisie());
				this.testerPokAMisKOPok(dresseur2, pokemon2, dresseur1, pokemon1);
				System.out.println("");
				this.testerPokAMisKOPok(dresseur1, pokemon1, dresseur2, pokemon2);
			}
		} else if (this.dresseur2.getActionChoisie() instanceof Echange) { // d2 echange puis d1 attaque
			// d2 echange
			dresseur2.getActionChoisie().utilise();
			pokemon2 = dresseur2.getPokemon();
			System.out.println("");
			pokemon2.subitAttaqueDe(pokemon1, dresseur1.getActionChoisie());
			this.testerPokAMisKOPok(dresseur1, pokemon1, dresseur2, pokemon2);
			System.out.println("");
			this.testerPokAMisKOPok(dresseur2, pokemon2, dresseur1, pokemon1);
		} else {// d1 et d2 attaquent
			// d1 attaque avant
			if (((Pokemon) pokemon1).estPlusRapideQue((Pokemon) pokemon2)) {
				pokemon2.subitAttaqueDe(pokemon1, dresseur1.getActionChoisie());
				if (!this.testerPokAMisKOPok(dresseur1, pokemon1, dresseur2, pokemon2)) {
					this.testerPokAMisKOPok(dresseur2, pokemon2, dresseur1, pokemon1);
					System.out.println("");
					pokemon1.subitAttaqueDe(pokemon2, dresseur2.getActionChoisie());
					this.testerPokAMisKOPok(dresseur2, pokemon2, dresseur1, pokemon1);
					System.out.println("");
					this.testerPokAMisKOPok(dresseur1, pokemon1, dresseur2, pokemon2);
				} else {
					this.testerPokAMisKOPok(dresseur2, pokemon2, dresseur1, pokemon1);
				}
				// d2 attaque avant
			} else {
				pokemon1.subitAttaqueDe(pokemon2, dresseur2.getActionChoisie());
				if (!this.testerPokAMisKOPok(dresseur2, pokemon2, dresseur1, pokemon1)) {
					this.testerPokAMisKOPok(dresseur1, pokemon1, dresseur2, pokemon2);
					System.out.println("");
					pokemon2.subitAttaqueDe(pokemon1, dresseur1.getActionChoisie());
					this.testerPokAMisKOPok(dresseur1, pokemon1, dresseur2, pokemon2);
					System.out.println("");
					this.testerPokAMisKOPok(dresseur2, pokemon2, dresseur1, pokemon1);
				} else {
					this.testerPokAMisKOPok(dresseur1, pokemon1, dresseur2, pokemon2);
				}
			}
		}
	}

	/**
	 * Il vérifie si le pokémon est KO et si c'est le cas, le maitre de ce pokemon
	 * doit, si le combat n'est pas fini, envoyer un autre pokemon de son ranch au
	 * combat. Dans ce cas, le pokemon lanceur de l'attaque recoit de l'exp et s'il
	 * a gagné un niveau, son maitre peut peut-etre lui apprendre une capacitee
	 * 
	 * @param dresseurLanceur  l'entraîneur qui utilise le mouvement, et qui peut
	 *                         potentiellement apprendre une capacité a son pokemon
	 * @param lanceur          le pokémon qui attaque et qui peut gagner de l'exp si
	 *                         le receveur est KO
	 * @param dresseurReceveur le dresseur du pokemon qui reçoit l'attaque et qui
	 *                         est peut-être KO
	 * @param receveur         le pokémon qui reçoit l'attaque et qui est
	 *                         potentiellement KO
	 * @return Un booléen indiquant si le pokemon attaqué a été mis KO, auquel cas
	 *         le maître de ce pokemon ne pourra pas attaquer pendant ce tour
	 */
	public boolean testerPokAMisKOPok(IDresseur dresseurLanceur, IPokemon lanceur, IDresseur dresseurReceveur,
			IPokemon receveur) {
		// TODO DRY/KISS ca
		if (receveur.estEvanoui()) {
			System.out.println(receveur.getNom() + " est KO !");
			try {
				lanceur.gagneExperienceDe(receveur);
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			if (lanceur.aChangeNiveau()) {
				dresseurLanceur.enseigne(lanceur, lanceur.getCapacitesApprises());
				// ((Dresseur)dresseurLanceur).updateNiveau();
			}
			if (!this.estTermine()) { // si le combat n'est pas terminé, d2 envoie un autre pokemon
				/*
				 * receveur = dresseurReceveur.choisitCombattantContre(lanceur);
				 * System.out.println(dresseurReceveur.getNom()+" envoie "+receveur.getNom());
				 * pokemon1 = dresseur1.getPokemon();
				 * pokemon2 = dresseur2.getPokemon();
				 */
				if (receveur.equals(pokemon1)) {
					pokemon1 = dresseur1.choisitCombattantContre(pokemon2);
					System.out.println(dresseur1.getNom() + " envoie " + pokemon1.getNom());
					((Dresseur) dresseur1).setPokemon(pokemon1);
				} else if (receveur.equals(pokemon2)) {
					pokemon2 = dresseur2.choisitCombattantContre(pokemon1);
					System.out.println(dresseur2.getNom() + " envoie " + pokemon2.getNom());
					((Dresseur) dresseur2).setPokemon(pokemon2);
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Si le pokémon est KO et que le combat n'est pas fini, le dresseur maître de
	 * ce pokémon envoie un autre pokémon
	 * 
	 * @param pokKO           le pokémon qui est peut-être KO
	 * @param alreadyPrinted  booléen indiquant si l'evanouissement du pokemon a
	 *                        déjà
	 *                        été affiché, ce cas ce présente si cette methode est
	 *                        appelé par testerPokeAMisKOPok().
	 * @param pokAdv          Le pokemon adverse
	 * @param dresseurLanceur Le dresseur adverse
	 */
	// TODO virer cette methode
	public void switchIfKO(IPokemon pokAdv, IDresseur dresseurLanceur, IPokemon pokKO, boolean alreadyPrinted) {
		if (pokKO.estEvanoui()) {
			if (!alreadyPrinted) {
				System.out.println(pokKO.getNom() + " est KO !");
			}
			try {
				pokAdv.gagneExperienceDe(pokKO);
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			if (pokAdv.aChangeNiveau()) {
				dresseurLanceur.enseigne(pokAdv, pokAdv.getCapacitesApprises());
				// ((Dresseur)dresseurLanceur).updateNiveau();
			}
			if (!this.estTermine()) { // si le combat n'est pas terminé, d2 envoie un autre pokemon
				if (pokKO.equals(pokemon1)) {
					pokemon1 = dresseur1.choisitCombattantContre(pokemon2);
					System.out.println(dresseur1.getNom() + " envoie " + pokemon1.getNom());
					((Dresseur) dresseur1).setPokemon(pokemon1);
				}
				if (pokKO.equals(pokemon2)) {
					pokemon2 = dresseur2.choisitCombattantContre(pokemon1);
					System.out.println(dresseur2.getNom() + " envoie " + pokemon2.getNom());
					((Dresseur) dresseur2).setPokemon(pokemon2);
				}
			}
		}
	}

	/**
	 * Il vérifie si le fichier logs.txt existe
	 *
	 * @return Un booléen
	 */
	private static boolean testPresence() {
		File repertoire = new File("./dataSave/");
		String[] listeFichiers = repertoire.list();
		boolean testPresence = false;
		if (listeFichiers == null) {
			System.out.println("Mauvais répertoire");
		} else {
			int i = 0;
			while (!testPresence && i < listeFichiers.length) {
				if (listeFichiers[i].equals("logs.txt")) {
					testPresence = true;
				}
				i++;
			}
		}
		return testPresence;
	}

	/**
	 * Si le fichier existe, écrire le message en paramètre dans le fichier et la
	 * date à laquelle il a été écrit
	 *
	 * @param message Le message que vous souhaitez ajouter au fichier log.
	 */
	public static void addLog(String message) {
		if (testPresence()) {
			try {
				Calendar currentTime = Calendar.getInstance();
				FileWriter fw = new FileWriter("./dataSave/logs.txt", true);
				fw.write(currentTime.getTime() + "\t" + message + "\n");
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
