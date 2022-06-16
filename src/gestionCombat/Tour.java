package gestionCombat;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import gestionPokemon.Pokemon;
import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IPokemon;
import interfaces.ITour;

/**
 * Un objet Tour
 */
public class Tour implements ITour {
	/**
	 * l'identifiant du tour
	 */
	private int id;
	private EtatDuJeu edj;
	private ICapacite cap1;
	private ICapacite cap2;
	

	/**
	 * Le constructeur de Tour
	 * 
	 * @param nbTours l'identifiant du Tour
	 */
	public Tour(int nbTours, EtatDuJeu edj) {
		this.setId(nbTours);
		this.edj = edj;
	}

	////////////////// méthode de ITour //////////////////
	@Override
	public void commence() {
		this.executerActions();

	}
	//////////////////////////////////////////////////////

	/**
	 * Cette fonction retourne l'id du Tour
	 * 
	 * @return L'identifiant du Tour.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Cette fonction définit l'identifiant du Tour sur l'identifiant passé en
	 * paramètre
	 * 
	 * @param id L'identifiant du Tour.
	 */
	public void setId(int id) {
		this.id = id;
	}

	public EtatDuJeu getEdj() {
		return edj;
	}

	public void setEdj(EtatDuJeu edj) {
		this.edj = edj;
	}

	/**
	 * C'ets la méthode principale de déroulement d'un tour. Si les 2 dresseur
	 * veulent changer de pokémon, l'ordre d'actino n'est pas important. Si l'un
	 * change de pokémon et l'autre attaque, alors c'est l'echange qui se fait en
	 * premier. Enfin si les 2 veulent attaquer, alors le pokémon le plus rapide
	 * attaque en premier
	 */
	private void executerActions() {
		Dresseur dresseur1= (Dresseur)this.edj.getDresseur1();
		IPokemon pokemon1=(Pokemon)this.edj.getPokemon1();
		Dresseur dresseur2=(Dresseur)this.edj.getDresseur2();
		IPokemon pokemon2=(Pokemon)edj.getPokemon2();
		
		// d1 echange...
		if (dresseur1.getActionChoisie() instanceof Echange) { // d1 echange
			dresseur1.getActionChoisie().utilise();
			pokemon1 = dresseur1.getPokemon();
			// ...et d2 echange
			if (dresseur2.getActionChoisie() instanceof Echange) {
				dresseur2.getActionChoisie().utilise();
				pokemon2 = dresseur2.getPokemon();
				// ...et d2 attaque
			} else {
				System.out.println("");
				pokemon1.subitAttaqueDe(pokemon2, dresseur2.getActionChoisie());
				this.testerPokAMisKOPok(dresseur2, pokemon2, pokemon1);
				System.out.println("");
				this.testerPokAMisKOPok(dresseur1, pokemon1, pokemon2);
			}
		} else if (dresseur2.getActionChoisie() instanceof Echange) { // d2 echange puis d1 attaque
			// d2 echange
			dresseur2.getActionChoisie().utilise();
			pokemon2 = dresseur2.getPokemon();
			System.out.println("");
			pokemon2.subitAttaqueDe(pokemon1, dresseur1.getActionChoisie());
			this.testerPokAMisKOPok(dresseur1, pokemon1, pokemon2);
			System.out.println("");
			this.testerPokAMisKOPok(dresseur2, pokemon2, pokemon1);
		} else {// d1 et d2 attaquent
			// d1 attaque avant
			if (((Pokemon) pokemon1).estPlusRapideQue((Pokemon) pokemon2)) {
				doubleAttaque(pokemon2, pokemon1, dresseur1, dresseur2);
				// d2 attaque avant
			} else {
				doubleAttaque(pokemon1, pokemon2, dresseur2, dresseur1);
			}
		}
	}

	private void doubleAttaque(IPokemon pokemon2, IPokemon pokemon1, Dresseur dresseur1, Dresseur dresseur2) {
		pokemon2.subitAttaqueDe(pokemon1, dresseur1.getActionChoisie());
		if (!this.testerPokAMisKOPok(dresseur1, pokemon1, pokemon2)) {
			this.testerPokAMisKOPok(dresseur2, pokemon2, pokemon1);
			System.out.println("");
			pokemon1.subitAttaqueDe(pokemon2, dresseur2.getActionChoisie());
			this.testerPokAMisKOPok(dresseur2, pokemon2, pokemon1);
			System.out.println("");
			this.testerPokAMisKOPok(dresseur1, pokemon1, pokemon2);
		} else {
			this.testerPokAMisKOPok(dresseur2, pokemon2, pokemon1);
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
	 * @param receveur         le pokémon qui reçoit l'attaque et qui est
	 *                         potentiellement KO
	 * @return Un booléen indiquant si le pokemon attaqué a été mis KO, auquel cas
	 *         le maître de ce pokemon ne pourra pas attaquer pendant ce tour
	 */
	public boolean testerPokAMisKOPok(IDresseur dresseurLanceur, IPokemon lanceur,
									  IPokemon receveur) {
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
			if (!this.edj.estTerminal()) { // si le combat n'est pas terminé, d2 envoie un autre pokemon
				if (receveur.equals(edj.getPokemon1())) {
					edj.setPokemon1(edj.getDresseur1().choisitCombattantContre(edj.getPokemon2()));
					System.out.println(edj.getDresseur1().getNom() + " envoie " + edj.getPokemon1().getNom());
					((Dresseur) edj.getDresseur1()).setPokemon(edj.getPokemon1());
				} else if (receveur.equals(edj.getPokemon2())) {
					edj.setPokemon2(edj.getDresseur2().choisitCombattantContre(edj.getPokemon1()));
					System.out.println(edj.getDresseur2().getNom() + " envoie " + edj.getPokemon2().getNom());
					((Dresseur) edj.getDresseur2()).setPokemon(edj.getPokemon2());
				}
			}
			return true;
		}
		return false;
	}
	
}
