package gestionPokemon;

import java.io.*;
//import java.util.Arrays;
import java.util.Objects;

import interfaces.IType;

/**
 * Un objet représentant un Type d'un Pokemon ou d'une Attaque
 */
public class Type implements IType {
	/**
	 * Liste statique des Types existant dans notre version du jeu
	 */
	public static String[] listeTypes = { "Combat", "Dragon", "Eau", "Électrik", "Feu", "Glace", "Insecte", "Normal",
			"Plante", "Poison", "Psy", "Roche", "Sol", "Spectre", "Vol" };

	/**
	 * L'id unique du type, correspondant à l'index du nom du type dans la liste
	 * statique
	 */
	public int id;

	/**
	 * Le nom du type
	 */
	public String nom;

	/**
	 * Le tableau regroupant l'efficacité des attque de type this sur chacun des
	 * types
	 */
	private double[] tabCoeffEfficacite = new double[15];

	/**
	 * Créé un objet Type pour seul paramètre son nom
	 * 
	 * @param nom qui initialise le nom du pokémon
	 *            Les autres attributs sont initialisés grâce a des méthodes.
	 *            On affecte un id qui est égal au résultat de la méthode
	 *            getIndexOfType()
	 *            On affecte a initCoeff la position du fichier effecacites.csv
	 */
	public Type(String nom) {
		this.nom = nom;
		// System.out.println(this.nom);
		this.id = getIndexOfType();
		this.initCoeff("./csv/efficacites.csv");
	}

	/**
	 * La methode getNom() renvoie le nom d'un Type
	 * 
	 * @return un string étant le nom du type
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * methode utilisee a la construction des types pour leur attribuer le bon id
	 * 
	 * @return un entier i si le type est trouvé ou -1 s'il ne l'est pas
	 */
	private int getIndexOfType() { // il faut throws une exeception si le pokémon n'est pas trouvé (si la méthode
									// return -1)
		int i = 0;
		while (i < listeTypes.length && !listeTypes[i].equals(this.nom)) {
			i++;
		}
		if (i == listeTypes.length) {
			return -1;
		}
		return i;
	}

	/**
	 * La methode getCoeffTotal renvoie le coefficient total en fonction du type
	 * 
	 * @param type1 est de type Type et correspond au premier type
	 * @param type2 est de type Type et correspond au second type
	 * @return un double correspondant aux coefficient avec lesquels les degats
	 *         seront calcules.
	 */
	public double getCoeffTotal(Type type1, Type type2) {
		return getCoeffDamageOn(type1) * getCoeffDamageOn(type2);
	}

	/**
	 * La methode getCoeffDamageOn() renvoie ???
	 * 
	 * @param cible est de type IType representant le type sur lequel on souhaite
	 *              determiner le coeff de l'attaque
	 * @return ?????
	 */
	public double getCoeffDamageOn(IType cible) {
		return this.tabCoeffEfficacite[((Type) cible).id];
	}

	/**
	 * Methode qui initialise le tableau des coefficients d'efficacités
	 * 
	 * @param fileName fichier csv stockant les coefficiants d'efficacité
	 */
	public void initCoeff(String fileName) {
		try {
			FileReader fichier = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(fichier);
			reader.readLine();
			// int i=0; unused askip
			while (reader.ready()) {

				String line = reader.readLine();
				String[] tab = line.split(";");
				if (Objects.equals(tab[0], this.nom)) {
					for (int j = 0; j < listeTypes.length; j++) {
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