package gestionCombat;

import java.io.FileNotFoundException;

import gestionPokemon.Capacite;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IPokemon;

/**
 * Permet de gérer un objet IARandom (un dresseur controlé aléatoirement)
 * hériant de Dresseur
 * 
 */
public class IARandom extends Dresseur {
	/**
	 * entier représentant la durée en ms des pauses du thread pendant les choix de L'IARandom
	 */
	private static final int delai = 800;

	/**
	 * Le constructeur d'un IARandom en indiquant son nom
	 * 
	 * @param nom le nom du dresseur créé
	 */
	public IARandom() {
		super();
	}

	@Override
	public IPokemon choisitCombattant() {
		System.out.println(this.getNom() + "\tchoisi un pokemon a envoyer au combat...");
		try {
			Thread.sleep(delai);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int i = (int) (Math.random() * Pokedex.getNbPokemonParRanch());
		Pokemon choosen = this.getEquipe()[i];
		this.setPokemon(choosen);
		return choosen;
	}

	@Override
	public void selectAction(IPokemon p, IPokemon pAdv) {
		System.out.println(this.getNom() + "\ta " + p.getNom() + " sur le terrain. Il choisi quoi faire...");
		try {
			Thread.sleep(delai);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean echange = (int) (Math.random() * 10) < 1;
		if (echange && this.getNbPokemonAlive()>1) {
			p = this.choisitCombattantContre(pAdv);
			this.setActionChoisie(null);
		} else {
			this.choisitAttaque(p, pAdv);
		}
	}

	@Override
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		if (((Pokemon) attaquant).getNombreDeToursAvantAttaque() == 0) { // dans le cas ou patience a ete utilisee
			if (((Pokemon) attaquant).getCapacitesUtilisables().length > 0) {
				//System.out.println(this.getNom() + "\tchoisi une attaque a utiliser...");
				try {
					Thread.sleep(delai);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int i = (int) (Math.random() * attaquant.getCapacitesApprises().length);
				while (attaquant.getCapacitesApprises()[i].getPP() == 0) { // re-génération de l'input si la capacite
																			// choisi n'aa plus de PP
					i = (int) (Math.random() * attaquant.getCapacitesApprises().length);
				}
				this.setActionChoisie((Capacite) ((Pokemon) attaquant).getCapacitesApprises()[i]);
			} else {
				// utilisation de Lutte si aucune capacite n'est dispo
				try {
					this.setActionChoisie(Pokedex.createCapacite(((Capacite) Pokedex.getCapaciteStatic("Lutte")).id));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		} else {// utilisation de patience
			((Pokemon) attaquant).updateNombreDeToursAvantAttaque();// on decremente la duree avant la fin de Patience
			if (((Pokemon) attaquant).getNombreDeToursAvantAttaque() == 0) {// si Patiece est prete
				((Pokemon) attaquant).setNombreDeToursAvantAttaque(-1); // on met le nb de tour a -1 pour que dans
																		// calculDommage() le nb nne soit pas remis a 2
			}

		}
		this.getPokemon().setAttaqueChoisie(this.getActionChoisie());
		return this.getActionChoisie();
	}

	@Override
	public IPokemon choisitCombattantContre(IPokemon pok) {
		//System.out.println(this.getNom() + "\tchoisi un pokemon a envoyer au combat...");
		try {
			Thread.sleep(delai);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int i = (int) (Math.random() * Pokedex.getNbPokemonParRanch());
		while (this.getEquipe()[i].estEvanoui() || this.getPokemon()==this.getEquipe()[i]) { // verification que le pokemon n'est pas KO
			i = (int) (Math.random() * Pokedex.getNbPokemonParRanch());
		}
		Pokemon choosen = this.getEquipe()[i];
		this.setPokemonChoisi(choosen);
		return choosen;
	}

	@Override
	public void enseigne(IPokemon pok, ICapacite[] caps) {
		Capacite capaciteAApprendre = this.canTeachAMove();
		if (capaciteAApprendre != null) {
			if (caps.length < 4) {
				try {
					this.getPokemon().remplaceCapacite(caps.length, capaciteAApprendre);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//System.out.println("\t" + pok.getNom() + " a appris " + capaciteAApprendre.getNom() + " !");
			} else {
				//System.out.println("\t" + pok.getNom() + " veut apprendre " + capaciteAApprendre.getNom() + ".");
				//System.out.println("\tVoulez vous lui faire oublier une des ses capacités (1) ou ne pas l'apprendre (2) ?");
				int inputChoix = InputViaScanner.getInputInt(1, 2);
				if (inputChoix == 1) {
					//((Pokemon) pok).showCapaciteApprise();
					//System.out.println("\tEntrer le numéro de la capacité à oublier (ou 0 pour annuler) :");
					int inputCapacite = (int) (Math.random() * caps.length);
					try {
						pok.remplaceCapacite(inputCapacite - 1, capaciteAApprendre);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					//System.out.println("\t" + pok.getNom() + " n'a pas appris " + capaciteAApprendre.getNom() + ".");
				}
			}
		} else {
			// System.out.println(pok.getNom()+" n'a aucune capacite a apprendre au niveau
			// "+pok.getNiveau());
		}
	}

}
