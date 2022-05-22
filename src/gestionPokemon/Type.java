package gestionPokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import interfaces.IType;

public enum Type implements IType {
	COMBAT("Combat",0),
	DRAGON("Dragon",1),
	EAU("Eau",2),
	ELECTRIK("Électrik",3),
	FEU("Feu",4),
	GLACE("Glace",5),
	INSECTE("Insecte",6),
	NORMAL("Normal",7),
	PLANTE("Plante",8),
	POISON("Poison",9),
	PSY("Psy",10),
	ROCHE("Roche",11),
	SOL("Sol",12),
	SPECTRE("Spectre",13),
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
		//System.out.println(this.getNom()+" -> "+cible.getNom()+" = "+((Type) cible).tabCoeffEfficacite[this.id]);
		return ((Type) cible).tabCoeffEfficacite[this.id];
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
