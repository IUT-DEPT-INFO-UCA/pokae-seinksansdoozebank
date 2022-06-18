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
	 * entier représentant la durée en ms des pauses du thread pendant les choix de
	 * L'IARandom
	 */
	private static final int delai = 0; //800;

	/**
	 * Le constructeur d'un IARandom 
	 */
	public IARandom(boolean empty) {
		super(empty);
	}

	/////////////////////// methode abstraites de Dresseur ///////////////////////
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
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		System.out.println(this.getNom() + "\ta " + attaquant.getNom() + " sur le terrain. Il choisi quoi faire...");
		try {
			Thread.sleep(delai);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean echange = (int) (Math.random() * 2) < 1;
		if (echange && this.getNbPokemonAlive() > 1) {
			return new Echange(this.choisitCombattantContre(defenseur), this);
		} else {
			return this.choisiCapacite(attaquant);
		}
	}

	@Override
	public IPokemon choisitCombattantContre(IPokemon pok) {
		try {
			Thread.sleep(delai);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int i = (int) (Math.random() * Pokedex.getNbPokemonParRanch());
		while (this.getEquipe()[i].estEvanoui() ||  !((Pokemon)this.getEquipe()[i]).echangePossible() || this.getPokemon() == this.getEquipe()[i]) { // verification que le pokemon n'est pas KO
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
			} else {
				int inputChoix = InputViaScanner.getInputInt(1, 2);
				if (inputChoix == 1) {
					int inputCapacite = (int) (Math.random() * caps.length);
					try {
						pok.remplaceCapacite(inputCapacite - 1, capaciteAApprendre);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	/////////////////////////////////////////////////////////////////////

	/**
	 * Methode permettant à l'IA de choisr la capacité à utiliser
	 * @param attaquant Le Pokémon actuellement au combat du Dresseur courant
	 * @return La capacité à utilier sous forme d'un IAttaque
	 */
	private IAttaque choisiCapacite(IPokemon attaquant) {
		if (((Pokemon) attaquant).getNombreDeToursAvantAttaque() == 0) { // dans le cas ou patience a ete utilisee
			if (((Pokemon) attaquant).getCapacitesUtilisables().length > 0) {
				try {
					Thread.sleep(delai);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int i = (int) (Math.random() * attaquant.getCapacitesApprises().length);
				while (attaquant.getCapacitesApprises()[i].getPP() == 0) { // re-génération de l'input si la capacite choisi n'aa plus de PP
					i = (int) (Math.random() * attaquant.getCapacitesApprises().length);
				}
				// input valide
				this.setActionChoisie(attaquant.getCapacitesApprises()[i]);
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
		this.getPokemon().setAttaqueChoisie((Capacite) this.getActionChoisie());
		return this.getActionChoisie();
	}

	@Override
	protected Joueur copy() {
		System.out.println("Debut de la copie d'une "+this.getClass().getSimpleName());
		Joueur copy = new Joueur(true);
		if(this.getActionChoisie() instanceof Echange) {
			copy.setActionChoisie(((Echange)this.getActionChoisie()).copy(copy));
		}else if(this.getActionChoisie() instanceof Capacite) {
			copy.setActionChoisie(((Capacite)this.getActionChoisie()).copy());
		}else {
			copy.setActionChoisie(null);
		}
		for(int i=0;i<Pokedex.getNbPokemonParRanch();i++) {
			copy.setPokemon(i,(Pokemon) ((Pokemon) this.getPokemon(i)).copy());
		}
		copy.setIdentifiant(this.getIdentifiant());
		copy.setMotDepasse(this.getMotDepasse());
		copy.updateNiveau();
		copy.setNom(this.getNom());
		copy.setPokemon(this.getPokemon());
		copy.setPokemonChoisi(this.getPokemonChoisi());
		return copy;
	}
}
