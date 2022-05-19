/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * ICategory.java
 */
package interfaces;

/**Présente les methodes pour utiliser les catégorie d'une attaque
 * @author Leo Donati
 * Il s'agit de la catégorie d'une capacité :
 *  - soit Physique
 *  - soit Special
 */
public interface ICategorie {
	/**Récupère l'ete Special d'une attaque
	 * @return Un booléen qui vaut true si l'attaque est speciale, faux sinon
	 */
	boolean isSpecial();
	
	/**Récupère le nom de la catégorie de l'attaque
	 * @return Un String représentant le nom de la catégorie
	 */
	String getNom();
}
