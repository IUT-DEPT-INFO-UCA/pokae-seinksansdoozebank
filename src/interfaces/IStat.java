/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IStat.java
 */
package interfaces;

/**Presente les methodes pour utiliser un ensemble de stats
 * @author Leo Donati
 *
 */
public interface IStat {
	/**Recupère la stat de Force.
	 * @return Un int représentant stat de Force de l'ensemble.
	 */
	public int getForce();

	/**Le getter de la stat de Défense.
	 * @return Un int représentant stat de Defense de l'ensemble.
	 */
	public int getDefense();

	/**Recupère la stat de Vitesse.
	 * @return Un int représentant stat de Vitesse de l'ensemble.
	 */
	public int getVitesse();

	/**Recupère la stat de Special.
	 * @return Un int représentant stat de Special de l'ensemble.
	 */
	public int getSpecial();

	/**Recupère la stat de Point de Vie.
	 * @return Un int représentant stat de Point de Vie de l'ensemble.
	 */
	public int getPV();

	
	/**Change la stat de Force.
	 * @param force La nouvelle valeur de la stat de Force.
	 */
	void setForce(int force);

	/**Change la stat de Defense.
	 * @param defense La nouvelle valeur de la stat de Defense.
	 */
	void setDefense(int defense);

	/**Change la stat de Vitesse.
	 * @param vitesse La nouvelle valeur de la stat de Vitesse.
	 */
	void setVitesse(int vitesse);

	/**Change la stat Special.
	 * @param special La nouvelle valeur de la stat Special.
	 */
	void setSpecial(int special);

	/**Change la stat de Point de Vie.
	 * @param pv La nouvelle valeur de la stat de Point de Vie.
	 */
	void setPV(int pv);
}
