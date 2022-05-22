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

    /**
     * L'identifiant unique de la capacite
     */
    public int id;

    /**
     * Le nom de la capacite
     */
    public String nom;

    /**
     * Le Type de la capacite
     */
    public Type type;

    /**
     * La Categorie de la capacite
     */
    public CategorieAttaque categorie;

    /**
     * La puissance brute de la capacite
     */
    public int puissance;

    /**
     * La précision de la capacite (entre 0 et 1)
     */
    public double precision;

    /**
     * Le nombre de points de pouvoir actuels de la capacite
     */
    public int pp;

    /**
     * Le nombre de points de pouvoir maximum de la capacite
     */
    public int ppBase;

	/**
	 * Creer un objet Type avec son identifiant comme seul parametre
	 * 
	 * @param id Un int qui initialise l'id du pokémon,  les autres attributs sont initialisés grâce a des méthodes.
	 */
	public Capacite(int id) {
		this.id = id;
	}
	

    /**
	 * @param capacite La capacite à copier
	 */
	public Capacite(Capacite capacite) {
		this.id = capacite.id;
		this.nom = capacite.nom;
		this.type = capacite.type;
		this.categorie = capacite.categorie;
		this.puissance = capacite.puissance;
		this.precision = capacite.precision;
		this.pp = capacite.pp;
		this.ppBase = capacite.ppBase;
	}



	/**
     * La méthode toString() renvoie la représentation sous forme de chaîne de
     * l'objet
     *
     * @return Un texte contenant l'identifiant et le nom de la capacité.
     */
    public String toString() {
        return "Capacite [id=" + id + "  nom :" + nom + " type : " + this.getType().getNom() + "]";
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
		efficacite = attaquant.getAttaqueChoisie().getEfficiencyOn(defenseur);
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

		return ((Type)this.getType()).getCoeffTotal(defenseur.getType1(), defenseur.getType2());

	}

}