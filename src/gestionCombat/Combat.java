package gestionCombat;

import interfaces.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

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
	
	private EtatDuJeu edj;
	/**
	 * Une liste des tours de ce combat
	 */
	private List<ITour> tours = new ArrayList<>();
	/**
	 * Le dresseur vainqueur du combat
	 */
	private IDresseur vainqueur;
	/**
	 * Variable de log pour le combat
	 */
	public static Logger logger = Logger.getLogger("logger");
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
		this.edj=new EtatDuJeu(d1, d2, d1.getPokemon(0), d2.getPokemon(0));
		((Dresseur) this.edj.getDresseur1()).setAdversaire((Dresseur) this.edj.getDresseur2());
		((Dresseur) this.edj.getDresseur2()).setAdversaire((Dresseur) this.edj.getDresseur1());
	}

	///////////////////// Méthodes de ICombat /////////////////////

	public void commence() {
		this.nbTours = 1;
		this.edj.setPokemon1(this.edj.getDresseur1().choisitCombattant());
		System.out.println("");
		this.edj.setPokemon2(this.edj.getDresseur2().choisitCombattant());
		System.out.println(msgDebutCombat);
	}

	public IDresseur getDresseur1() {
		return this.edj.getDresseur1();
	}

	public IDresseur getDresseur2() {
		return this.edj.getDresseur2();
	}

	public ITour nouveauTour(IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2) {
		this.nbTours++;
		return new Tour(this.nbTours,this.edj, atk1, atk2);
	}

	public void termine() {
		this.edj.getDresseur1().soigneRanch();
		((Dresseur) this.edj.getDresseur1()).updateNiveau();
		this.edj.getDresseur2().soigneRanch();
		((Dresseur) this.edj.getDresseur2()).updateNiveau();
	}

	///////////////////////////////////////////////////////////////

	/**
	 * La fonction getVainqueur() est une fonction qui lance une bataille, puis
	 * exécute les actions des deux dresseurs tant que les 2 peuvent se battrent, et
	 * renvoie enfin le vainqueur de la bataille
	 * 
	 * @return Le Dresseur vainqueur de la bataille.
	 */
	public IDresseur getVainqueur() {
		this.commence();
		while (!this.estTermine()) {
			this.startTour();
			ITour currentTour = this.nouveauTour(this.edj.getPokemon1(), ((Dresseur) this.getDresseur1()).getActionChoisie(), this.edj.getPokemon2(),((Dresseur) this.getDresseur2()).getActionChoisie());
			System.out.println("");
			currentTour.commence();
			tours.add(currentTour);
			
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
		if (((Dresseur) this.edj.getDresseur1()).pouvoirSeBattre() && ((Dresseur) this.edj.getDresseur2()).pouvoirSeBattre()) {
			return false;
		} else if (!((Dresseur) this.edj.getDresseur1()).pouvoirSeBattre()) {
			this.vainqueur = this.edj.getDresseur2();
		} else if (!((Dresseur) this.edj.getDresseur2()).pouvoirSeBattre()) {
			this.vainqueur = this.edj.getDresseur1();
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
		System.out.println(edj.getDresseur1().getNom() + "\t" + edj.getPokemon1()/* .getNom()+" "+((Pokemon)pokemon1).getPVBar() */);
		System.out.println(edj.getDresseur2().getNom() + "\t" + edj.getPokemon2()/* .getNom()+" "+((Pokemon)pokemon2).getPVBar() */);
		System.out.println("");
		// recuperation du choix d'action du dresseur1
		((Dresseur) edj.getDresseur1()).setActionChoisie(edj.getDresseur1().choisitAttaque(edj.getPokemon1(), edj.getPokemon2()));
		System.out.println("");
		// recuperation du choix d'action du dresseur2
		((Dresseur) edj.getDresseur2()).setActionChoisie(edj.getDresseur2().choisitAttaque(edj.getPokemon2(), edj.getPokemon1()));
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
	 * Il crée un logger, définit son niveau sur INFO, lui ajoute un FileHandler sur logs.txt et supprime le ConsoleHandler pour clear le logger
	 *
	 * @param message le message à enregistrer
	 */
	public static void addLog(String message) {
		try {
			logger.setLevel(Level.INFO);
			logger.config("");
			logger.setUseParentHandlers(false);
			FileHandler fileHandler = new FileHandler("dataSave/logs.txt");
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
			int i = 0;
			boolean trouve = false;
			while (i < logger.getHandlers().length && !trouve) {
				if (logger.getHandlers()[i] instanceof ConsoleHandler) {
					logger.removeHandler(logger.getHandlers()[i]);
					trouve = true;
				}
				i++;
			}
			logger.info(message);
		}catch(Exception e){
			System.out.println("Erreur lors de l'écriture du fichier log");
		}
	}

}
