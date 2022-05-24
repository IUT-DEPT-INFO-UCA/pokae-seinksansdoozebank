package gestionPokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import interfaces.IType;

/**
 * Enumeration Type qui implemente IType qui sert a definir tout les types et leurs coefficients
 */
public enum Type implements IType {
	/**
	 * Enum Combat : Type
	 */
	COMBAT("Combat",0),
	/**
	 * Enum Dragon : Type
	 */
	DRAGON("Dragon",1),
	/**
	 * Enum Eau : Type
	 */
	EAU("Eau",2),
	/**
	 * Enum Électrik : Type
	 */
	ELECTRIK("Electrik",3),
	/**
	 * Enum Feu : Type
	 */
	FEU("Feu",4),
	/**
	 * Enum Glace : Type
	 */
	GLACE("Glace",5),
	/**
	 * Enum Insecte : Type
	 */
	INSECTE("Insecte",6),
	/**
	 * Enum Normal : Type
	 */
	NORMAL("Normal",7),
	/**
	 * Enum Plante : Type
	 */
	PLANTE("Plante",8),
	/**
	 * Enum Poison : Type
	 */
	POISON("Poison",9),
	/**
	 * Enum Psy : Type
	 */
	PSY("Psy",10),
	/**
	 * Enum Roche : Type
	 */
	ROCHE("Roche",11),
	/**
	 * Enum Sol : Type
	 */
	SOL("Sol",12),
	/**
	 * Enum Spectre : Type
	 */
	SPECTRE("Spectre",13),
	/**
	 * Enum Vol : Type
	 */
	VOL("Vol",14);
	
	 /**
	 * Liste statique des Types existant dans notre version du jeu
	 */
	public static Type[] listeTypes = {COMBAT, DRAGON, EAU,	ELECTRIK, FEU, GLACE, INSECTE, NORMAL, PLANTE, POISON, PSY, ROCHE, SOL, SPECTRE, VOL};
	
	/**
	 * L'id unique du type, correspondant à l'index du nom du type dans la liste
	 * statique
	 */
	public int id;

    /**
     * Le nom de du type
     */
	public String nom;

	/**
	 * Le tableau regroupant l'efficacité des attque de type this sur chacun des
	 * types
	 */
	private double[] tabCoeffEfficacite = new double[15];
	
	/**
	 * Constructeur d'un type à partir de son nom et de son id
	 * 
	 * @param nom Le nom du Type
	 * @param id le numero du type dans le tableau des types
	 */
	Type(String nom, int id) {
		this.nom=nom;
		this.id=id;
		this.initCoeff("./csv/efficacites.csv");
	}

	/**
	 * Renvoie le nom d'un Type
	 * 
	 * @return le nom du type
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * Retourne la liste des types disponibles dans le jeu
	 * 
	 * @return la liste des types
	 */
	public static Type[] getListe() {
		return listeTypes;
	}

	/**
	 * La methode getCoeffTotal renvoie le coefficient multiplicateur total de l'attaque en fonction des types du Pokemon
	 * 
	 * @param type1 le premier type du Pokemon
	 * @param type2 le deuxieme type du Pokemon
	 * @return un double correspondant aux coefficient avec lesquels les degats
	 *         seront calcules.
	 */
	public double getCoeffTotal(IType type1, IType type2) {
		//System.out.println(this.getNom()+" -> "+type1.getNom()+" et "+type2.getNom());
		return this.getCoeffDamageOn(type1) * this.getCoeffDamageOn(type2);
	}

	/**
	 * Calcule le coefficient multipicateur correspondant aux force du type de l'attaque sur les types du Pokemon
	 * 
	 * @param cible est de type IType representant le type sur lequel on souhaite
	 *              determiner le coeff de l'attaque
	 * @return Le tableau regroupant l'efficacité des attques de type this sur chacun des
	 * types
	 */
	public double getCoeffDamageOn(IType cible) {
		if(cible!=null) {
			//System.out.println(this.getNom()+" -> "+cible.getNom()+" = "+((Type) cible).tabCoeffEfficacite[this.id]);
			return ((Type) cible).tabCoeffEfficacite[this.id];
		}
		return 1;
	}

	/**
	 * Methode qui initialise le tableau des coefficients d'efficacités
	 * 
	 * @param fileName fichier csv stockant les coefficiants d'efficacité
	 */
	private void initCoeff(String fileName) {
		try {
			FileReader fichier = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(fichier);
			reader.readLine();
			// int i=0; unused askip
			while (reader.ready()) {
				String line = reader.readLine();
				String[] tab = line.split(";");
				if (Objects.equals(tab[0], this.nom)) {
					for (int j = 0; j <15; j++) {
						this.tabCoeffEfficacite[j] = Double.parseDouble(tab[j + 1]);
					}
				}
				// i++;
			}
			// System.out.println(this.nom+" tab =
			// "+Arrays.toString(this.tabCoeffEfficacite));
			reader.close();
			fichier.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
