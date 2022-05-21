package gestionPokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import interfaces.IType;

<<<<<<< Updated upstream
/**
 * Un objet représentant un Type d'un Pokemon ou d'une Attaque
 */
public class Type implements IType {
	/**
	 * Liste statique des Types existant dans notre version du jeu
	 */
	public static String[] listeTypes = { "Combat", "Dragon", "Eau", "Électrik", "Feu", "Glace", "Insecte", "Normal",
			"Plante", "Poison", "Psy", "Roche", "Sol", "Spectre", "Vol" };

=======
public enum Type  implements IType {
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
	
	
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
	 * Créé un objet Type pour seul paramètre son nom
	 * 
	 * @param nom qui initialise le nom du pokémon
	 *            Les autres attributs sont initialisés grâce a des méthodes.
	 *            On affecte un id qui est égal au résultat de la méthode
	 *            getIndexOfType()
	 *            On affecte a initCoeff la position du fichier effecacites.csv
=======
	 * Constructeur d'un type à partir de son nom et de son id
	 * 
	 * @param nom Le nom du Type
	 * @param id le numero du type dans le tableau des types
>>>>>>> Stashed changes
	 */
	Type(String nom, int id) {
		this.nom=nom;
		this.id=id;
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
<<<<<<< Updated upstream
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
=======
	 * La methode getCoeffTotal renvoie le coefficient multiplicateur total de l'attaque en fonction des types du Pokemon
>>>>>>> Stashed changes
	 * 
	 * @param type1 est de type Type et correspond au premier type
	 * @param type2 est de type Type et correspond au second type
	 * @return un double correspondant aux coefficient avec lesquels les degats
	 *         seront calcules.
	 */
	public double getCoeffTotal(IType type1, IType type2) {
		System.out.println(this.getNom()+" -> "+type1.getNom()+" et "+type2.getNom());
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
		System.out.println(this.getNom()+" -> "+cible.getNom()+" = "+((Type) cible).tabCoeffEfficacite[this.id]);
		return ((Type) cible).tabCoeffEfficacite[this.id];
	}

<<<<<<< Updated upstream
	public void initCoeff(String fileName) {
=======
	/**
	 * Methode qui initialise le tableau des coefficients d'efficacités
	 * 
	 * @param fileName fichier csv stockant les coefficiants d'efficacité
	 */
	private void initCoeff(String fileName) {
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream

	///////////////// Methodes de debug /////////////////

	public static Type[] creerTypes(String fileName) {
		Type[] tab = new Type[15];
		for (int i = 0; i < 15; i++) {
			tab[i] = new Type(Type.listeTypes[i]);
			tab[i].initCoeff(fileName);
		}
		return tab;
	}

	public void afficherTab() {
		StringBuilder s = new StringBuilder();
		s.append(this.nom).append(" :");
		for (double d : this.tabCoeffEfficacite) {
			s.append(" ").append(d).append(",");
		}
		System.out.println(s);
	}

}
=======
}
>>>>>>> Stashed changes
