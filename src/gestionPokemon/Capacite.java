package gestionPokemon;

import interfaces.ICapacite;
import interfaces.ICategorie;
import interfaces.IPokemon;
import interfaces.IType;

//import java.util.Objects;

/**
 * Un objet qui représente une capacité que les pokemon peuvent apprendre ou
 * utiliser.
 */
public class Capacite implements ICapacite {
	public int id;
	public String nom;
	public Type type;
	public CategorieAttaque categorie;
	public int puissance;
	public double precision;
	public int pp;
	public int ppBase;

	/**
	 * Créé un objet type 1 parametre
	 * 
	 * @param id Un itn qui initialise l'id du pokémon
	 *           Les autres attributs sont initialisés grâce a des méthodes.
	 */
	public Capacite(int id) {
		this.id = id;
	}

	/**
	 * Créé un objet type 1 parametre
	 * 
	 * @param nom       Un string qui sera le nom de la capacité
	 * @param type
	 * @param categorie
	 * @param puissance
	 * @param precision
	 * @param pp
	 * @param ppBase
	 */
	/*
	 * public Capacite(String nom, Type type, String categorie, int puissance, int
	 * precision, int pp, int ppBase) {
	 * this.nom = nom;
	 * this.categorie = Objects.equals(categorie, "Physique") ?
	 * CategorieAttaque.PHYSIQUE : CategorieAttaque.SPECIALE;
	 * this.puissance = puissance;
	 * this.precision = precision;
	 * this.pp = pp;
	 * this.ppBase = ppBase;
	 * }
	 */

	public String toString() {
		return "Capacite [id=" + id + "  nom :" + nom + "]";
	}

	/////////////////////// methodes de IAttaque ///////////////////////
	public int calculeDommage(IPokemon lanceur, IPokemon receveur) {
		double degats = 0;
		if (this.touche()) {
			if (this.puissance > 0) {
				degats = ((lanceur.getNiveau() * 0.4 + 2) * ((Pokemon) lanceur).obtenirAtqSur(this) * this.puissance
						/ (((Pokemon) receveur).obtenirDefSur(this) * 50))
						* calculerCM((Pokemon) lanceur, (Pokemon) receveur);
			} else {
				switch (this.puissance) {
					case -1: // one shot
						if (this.getEfficiencyOn((Pokemon) receveur) != 0) {
							degats = ((Pokemon) receveur).pvMax;
						}
					case -2: // -20 sur les non spectre
						if (this.getEfficiencyOn((Pokemon) receveur) != 0) {
							degats = 20;
						}
					case -3: // degat subit au tours precedent * 2 si capacite physique
						if (!((Pokemon) lanceur).obtenirDerniereCapaUtilisee().getCategorie().isSpecial()) {
							degats = ((Pokemon) lanceur).obtenirDeniersDegatsSubits() * 2;
						}
					case -4: // degat = nivLanceur si la cible n'est pas imunise au type de l'attaque
						if (this.getEfficiencyOn((Pokemon) receveur) != 0) {
							degats = lanceur.getNiveau();
						}
					case -5: // 40 degat sur type acier ou dragon sans prendre en compte les stat de la cible
						return 40;
					case -6: // degat = nivLanceur si la cible n'est pas imunise au type de l'attaque
						if (this.getEfficiencyOn((Pokemon) receveur) != 0) {
							degats = lanceur.getNiveau();
						}
					case -7: // impossible d' attaquer pendant 2 tours puis degat infligés = (2*les degat
								// encaissé pendant les 2 tours) sans tenir compte des types
						((Pokemon) lanceur).mettreNombreDeToursAvantAttaqueA(2);
						if (((Pokemon) lanceur).obtenirNombreDeToursAvantAttaque() == 0) {
							degats = (((Pokemon) lanceur).obtenirAvantDeniersDegatsSubits()
									+ ((Pokemon) lanceur).obtenirDeniersDegatsSubits()) * 2;
						}
				}
			}
		}
		return (int) degats;
	}

	public void utilise() {
		this.pp--;
	}
	/////////////////////////////////////////////////////////////////////

	/////////////////////// methode de Icapacite ///////////////////////
	public String getNom() {
		return this.nom;
	}

	public double getPrecision() {
		return this.precision;
	}

	public int getPuissance() {
		return this.puissance;
	}

	public int getPP() {
		return this.pp;
	}

	public void resetPP() {
		this.pp = this.ppBase;
	}

	public ICategorie getCategorie() {
		return this.categorie;
	}

	public IType getType() {
		return this.type;
	}
	/////////////////////////////////////////////////////////////////////

	/**
	 * Cette fonction retourne le PP de base de la capacité
	 * 
	 * @return Le PP de base du mouvement.
	 */
	public int getPPBase() {
		return this.ppBase;
	}

	/**
	 * Si le nombre aléatoire est inférieur ou égal à la précision, alors l'attaque
	 * atteint sa cible et la méthode retourne true
	 * 
	 * @return Une valeur booléenne.
	 */
	public boolean touche() {
		double r = Math.random();
		System.out.println("" + r + "><" + this.precision);
		return Math.random() <= this.precision;
	}

	/**
	 * Cette fonction calcule le coefficient multiplicateur d'une attaque
	 * 
	 * @param attaquant Le Pokémon attaquant
	 * @param defenseur Le Pokémon attaqué
	 * @return Le coefficient multiplicateur que l'attaque fera.
	 */
	public double calculerCM(Pokemon attaquant, Pokemon defenseur) {
		double stab = 0;
		double efficacite;
		if (attaquant.possedeLeType(this.type)) {
			stab = 1.5;
		}
		efficacite = this.getEfficiencyOn(defenseur);
		return stab * efficacite * (0.85 * (Math.random() * 0.15));
	}

	/**
	 * Cette fonction renvoie le multiplicateur de dégâts en fonction du type de
	 * l'attaque sur le défenseur
	 * 
	 * @param defenseur Le Pokémon attaqué
	 * @return L'efficacité de l'attaque sur le défenseur.
	 */
	public double getEfficiencyOn(Pokemon defenseur) {
		return this.type.getCoeffDamageOn(defenseur.getEspece().getTypes()[0])
				* this.type.getCoeffDamageOn(defenseur.getEspece().getTypes()[1]);
	}

}