/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IDresseur.java
 */
package interfaces;

/**
 * Représente les méthodes à utiliser les objets Dresseur
 * 
 * @author Leo Donati
 *
 */
public interface IDresseur {
	/**
	 * Il enseigne à un pokémon un ensemble de mouvements
	 * 
	 * @param pok  Le Pokemon à qui le Dresseur enseigne un attaque
	 * @param caps Les ICapacite que le Pokemon connait déjà
	 */
	public void enseigne(IPokemon pok, ICapacite[] caps);

	/**
	 * Cette fonction guérit tout les Pokemon du ranch.
	 */
	public void soigneRanch();

	/**
	 * Il renvoie un objet IPokemon choisi par le dresseur
	 * 
	 * @return Un Pokémon
	 */
	public IPokemon choisitCombattant();

	/**
	 * Il renvoie un objet IPokemon choisi par le dresseur
	 * 
	 * @param pok Le pokémon de l'adversaire
	 * @return La méthode renvoie un objet IPokemon.
	 */
	public IPokemon choisitCombattantContre(IPokemon pok);

	/**
	 * Cette fonction choisit une attaque que le Pokémon attaquant utilisera contre
	 * le Pokémon défenseur.
	 * 
	 * @param attaquant le pokémon attaquant
	 * @param defenseur Le pokémon défenseur
	 * @return La méthode renvoie un objet IAttaque.
	 */
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur);

	/**
	 * Il renvoie le niveau du joueur.
	 * 
	 * @return Le niveau du joueur.
	 */
	public int getNiveau();

	/**
	 * Il renvoie le nom du Dresseur.
	 * 
	 * @return Le nom du Dresseur.
	 */
	public String getNom();

	/**
	 * Cette fonction renvoie un objet Pokemon à l'index donné parmis le ranch du
	 * Dresseur.
	 * 
	 * @param i L'index du pokémon que vous souhaitez obtenir.
	 * @return L'objet Pokemon à l'index donné.
	 */
	public IPokemon getPokemon(int i);
}
