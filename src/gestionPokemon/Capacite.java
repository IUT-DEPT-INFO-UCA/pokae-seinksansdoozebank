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
    
    public int nivNecessaire;

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
        return "["+nom + ", " + this.getType().getNom()+"]";
        //return "["+nom + ", " + this.getType().getNom()+","+this.nivNecessaire+"]";
    }

    /////////////////////// methodes de IAttaque ///////////////////////
    public int calculeDommage(IPokemon lanceur, IPokemon receveur) {
    	//System.out.println(receveur);
		this.utilise();
        double degats = 0;
        if (this.touche()) {
            if (this.puissance > 0) {
                degats = (( (lanceur.getNiveau() * 0.4 + 2) * ((Pokemon) lanceur).getAtqSur(this) * this.puissance)
                        / (((Pokemon) receveur).getDefSur(this) * 50)+2) * calculerCM((Pokemon) lanceur, (Pokemon) receveur);
                if(this.id==110) { //gestion de Lutte qui fait perdre la moitié des dégâts qu'elle a infligés
                	((Pokemon)lanceur).subirDegats((int) Math.ceil(((Pokemon)lanceur).pvMax/4));
                }
            } else {
            	System.out.println("Calcul des degats pour une capacite particuliere");
                switch (this.puissance) {
                    case -1: // one shot
                        if (this.getEfficiencyOn((Pokemon) receveur) != 0) {
                            degats = ((Pokemon) receveur).pvMax;
                        }
                    case -2: // Sonic-Boom -20 sur les non spectre
                        if (this.getEfficiencyOn((Pokemon) receveur) != 0) {
                            degats = 20;
                        }
                    case -3: // Riposte : degat subits au tours precedent * 2 si capacite physique
                    	if(((Pokemon) receveur).getDerniereCapaciteEncaissee()!=null){
                    		if (!((Pokemon) receveur).getDerniereCapaciteEncaissee().getCategorie().isSpecial()) {
                            degats = ((Pokemon) lanceur).getDerniersDegatsSubits() * 2;
                        	}
                    	}
                    case -4: // Frappe-Atlas / Ombre Nocturne : degat = nivLanceur si la cible n'est pas imunise au type de l'attaque
                        if (this.getEfficiencyOn((Pokemon) receveur) != 0) {
                            degats = lanceur.getNiveau();
                        }
                    case -5: // Draco-Rage : 40 degat sur type acier ou dragon sans prendre en compte les stat de la cible
                    	degats = 40;
                    case -6: // degat = nivLanceur si la cible n'est pas imunise au type de l'attaque
                        if (this.getEfficiencyOn((Pokemon) receveur) != 0) {
                            degats = lanceur.getNiveau();
                        }
                    case -7: // Patience : impossible d' attaquer pendant 2 tours puis degat infligés = (2*les degats encaissé pendant les 2 tours) sans tenir compte des types
                        //TODO tester si Patience marche
                        if (((Pokemon) lanceur).getNombreDeToursAvantAttaque() == 0) {//si la capa chois est Patience et que le nombre de tour avant la fin est a 0
                        	((Pokemon) lanceur).setNombreDeToursAvantAttaque(2); // on met la duree a 2 tout
                        }else  if (((Pokemon) lanceur).getNombreDeToursAvantAttaque() == -1) { //-1 => Patience finie
                            degats = (((Pokemon) lanceur).getAvantDerniersDegatsSubits()+ ((Pokemon) lanceur).getDerniersDegatsSubits()) * 2;
                            ((Pokemon)lanceur).setNombreDeToursAvantAttaque(0); //reset du nombre pour reprendre un cycle de choix normal
                        }
                    case -8:
                    	degats = lanceur.getNiveau() * (Math.random() * + 0.5);
                    case -9 :
                    	degats = Math.ceil(receveur.getStat().getPV()/2);
                    ///Meteores n'est pas géré ici, nous n'avons aucune capacité qui modifie ce à quoi Meteores n'est pas sensible, donc elle n'est pas dans les cas particuliers
                }
            }
            //System.out.println(degats);
        }else {
        	System.out.println(receveur.getNom()+" esquive !");
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
		//System.out.println(defenseur.getType1().getNom()+" "+defenseur.getType2().getNom());
		double stab = 1;
		double efficacite = 1;
		if( this.id!=110) {
			if (attaquant.possedeLeType(this.type)) {
				stab = 1.5;
			}
			efficacite = attaquant.getAttaqueChoisie().getEfficiencyOn(defenseur);
		}
		double coeff =stab * efficacite * (Math.random() * (0.15) + 0.85);
		//System.out.println("Le CM vaut "+coeff);
		return coeff;
	}

	
	/**
	 * Cette fonction renvoie le multiplicateur de dégâts en fonctio1n du type de
	 * l'attaque sur le défenseur
	 * 
	 * @param defenseur Le Pokémon attaqué
	 * @return L'efficacité de l'attaque sur le défenseur.
	 */
	public double getEfficiencyOn(Pokemon defenseur) {
		double efficacite = ((Type)this.getType()).getCoeffTotal(defenseur.getType1(), defenseur.getType2());
		if(efficacite>1) {
			System.out.println("C'est super efficace !");
		}else if(efficacite==0) {
			System.out.println("Cela n'a aucun effet.");
		}else if (efficacite<1) {
			System.out.println("Ce n'est pas très efficace ...");
		}
		return efficacite;

	}

}