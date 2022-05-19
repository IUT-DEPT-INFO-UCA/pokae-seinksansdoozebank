/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * ICapacite.java
 */
package interfaces;

/**Présente les méthodes pour utiliser une capacité.
 * @author Leo Donati
 *	Une capacité est un type d'attaque qu ele pokemon peut utilser
 */
public interface ICapacite extends IAttaque {
	
	/**Récupère le nom de la capacité.
	 * @return Un string représentant le nom de la capacité.
	 */
	String getNom();
	
	/**Récupère la précision de la capacité.
	 * @return Un double représentant la précision de la capacité.
	 */
	double getPrecision();
	
	/**Récupère la puissance de la capacité.
	 * @return Un int représentant la puissance de la capacité.
	 */
	int getPuissance();
	
	/**Récupère le nombre de point de pouvoir restant de la capacité.
	 * @return Un int représentant le nombre de point de pouvoir restant de la capacité.
	 */
	int getPP();	
	
	/**Réinitialise le nombre de point de pouvoir de la capacité, en mettant le nombre de points de pouvoir au nombre de points de pouvoirs max de la capacité.
	 */
	void resetPP();
	
	/**Récupère la catégorie de la capacité.
	 * @return Une Categorie représentant la catégorie de la capacité.
	 */
	ICategorie getCategorie();
	
	/**Récupère le type de la capacité.
	 * @return Un Type représentant le type de la capacité.
	 */
	IType getType();			//Type de la capacité (la liste des types est la même que pour le pokemon)
}
