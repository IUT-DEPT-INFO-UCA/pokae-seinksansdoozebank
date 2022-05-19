package gestionPokemon;

import java.io.*;
//import java.util.Arrays;
import java.util.Objects;

import interfaces.IType;

public class Type implements IType{
	public static String[] listeTypes = {"Combat","Dragon","Eau","Électrik","Feu","Glace","Insecte","Normal","Plante","Poison","Psy","Roche","Sol","Spectre","Vol"};
	public int id;
	public String nom;
	private double [] tabCoeffEfficacite = new double[15];

	/**Créé un objet type 1 parametre 
	 *	@param nom qui initialise le nom du pokémon
	 * Les autres attributs sont initialisés grâce a des méthodes.
	 * On affecte un id qui est égal au résultat de la méthode getIndexOfType()
	 * On affecte a initCoeff la position du fichier effecacites.csv 
	 */
	public Type(String nom){
		this.nom = nom;
		//System.out.println(this.nom);
		this.id=getIndexOfType();
		this.initCoeff("./csv/efficacites.csv");
	}
	/**
	 Peut-etre a supprimer
	public Type(int id, String nom) {
		this.id = id;
		this.nom = nom;
	}
	*/
	/** La methode getNom() renvoie le nom d'un Type
	 * @return un string étant le nom du type
	 */
	public String getNom() {
		return this.nom;
	}
	/**methode utilisee a la construction des types pour leur attribuer le bon id
	* @return un entier i si le type est trouvé ou -1 s'il ne l'est pas
	*/
	private int getIndexOfType(){ // il faut throws une exeception si le pokémon n'est pas trouvé (si la méthode return -1)
		int i=0;
		while (i<listeTypes.length && !listeTypes[i].equals(this.nom)) {
			i++;
		}
		if(i==listeTypes.length) {
			return -1;
		}
		return i;
	}
	/**La methode getCoeffTotal renvoie le coefficient total en fonction du type
	 * @param type1 est de type Type et correspond au premier type
	 * @param type2 est de type Type et correspond au second type 
	 * @return un double correspondant aux coefficient avec lesquels les degats seront calcules. 
	 */
	public double getCoeffTotal(Type type1,Type type2) {
		return getCoeffDamageOn(type1)*getCoeffDamageOn(type2);
	}
	/** La methode getCoeffDamageOn() renvoie ???
	 * @param cible est de type IType representant le type sur lequel on souhaite determiner le coeff de l'attaque
	 * @return ?????
	 */
	public double getCoeffDamageOn(IType cible) {
		return this.tabCoeffEfficacite[((Type)cible).id];
	}

	public void initCoeff(String fileName){
		try{
			FileReader fichier = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(fichier);
			reader.readLine();
			//int i=0; unused askip
			while(reader.ready()){

				String line = reader.readLine();
				String[] tab = line.split(";");
				if(Objects.equals(tab[0], this.nom)) {
					for (int j=0;j< listeTypes.length;j++){
						this.tabCoeffEfficacite[j] = Double.parseDouble(tab[j+1]);
					}
				}
				//i++;
			}
//			System.out.println(this.nom+" tab = "+Arrays.toString(this.tabCoeffEfficacite));
			reader.close();
			fichier.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	
	

	///////////////// Methodes de debug /////////////////


	public static Type[] creerTypes(String fileName) {
		Type[] tab = new Type[15];
		for (int i=0;i<15;i++) {
			tab[i]=new Type(Type.listeTypes[i]);
			tab[i].initCoeff(fileName);
		}
		return tab;
	}

	public void afficherTab() {
		StringBuilder s  = new StringBuilder();
		s.append(this.nom).append(" :");
		for(double d : this.tabCoeffEfficacite) {
			s.append(" ").append(d).append(",");
		}
		System.out.println(s);
	}

}