/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IEspece.java
 */
package interfaces;

/**Présente les méthodes pour utiliser une espece de pokemon.
 * @author Leo Donati
 *
 */
public interface IEspece {
	/**Récupère l'ensemble de stats de base.
	 * @return L'ensemble des stats de base.
	 */
	public IStat getBaseStat();
	
	/**Récupère le nom de l'espece.
	 * @return Un string représentant le nom de l'espèce.
	 */
	public String getNom();
	
	/**Récupère le niveau de départ de l'espèce.
	 * @return Un int représentant le niveau de départ de l'espèce.
	 */
	public int getNiveauDepart();
	
	/**Récupère l'expérience de départ de l'espèce.
	 * @return Un int représentant l'expérience de départ de l'espèce.
	 */
	public int getBaseExp();
	
	/**Récupère l'ensemble de stats de gain, ceux qui seront gagnés en battant un membre de cette espece.
	 * @return L'ensemble des stats de gain.
	 */
	public IStat getGainsStat();
	
	/**Récupère le tableau des capacités qu'une espèce peut apprendre.
	 * @return Un tableau de Capacite que peut apprendre l'espece.
	 */
	public ICapacite[] getCapSet();			//ensemble des capacités disponibles pour cette espèce
	
	/**Récupère l'espece en laquelle l'espece peut evoluer.
	 * @param niveau Un int représantant le niveau actuel du pokemon.
	 * @return Une espece réprésentant l'espece en laquelle l'espece peut evoluer à son niveau actuel, null s'il ne peut pas évoluer à son niveau actuel.
	 */
	public IEspece getEvolution(int niveau);
	
	/**Récupère le tableau des types de l'espece.
	 * @return Un tableau réprésentant les types de l'espece.
	 */
	public IType[] getTypes();				//une espece de pokemon peut avoir un ou deux types
}
