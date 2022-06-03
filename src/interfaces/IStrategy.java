/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IStrategy.java
 */
package interfaces;

/**
 * Représente les méthodes utiliser par les dresseurs pour mener un combat
 * (Joueurs, IARandom, IAElaboree)
 * 
 * @author Leo Donati
 *
 */
public interface IStrategy {
	/**
	 * Il renvoie un objet IPokemon choisi par un dresseur parmis les Pokemon de son
	 * equipe
	 * 
	 * @return Un Pokémon
	 */
	public IPokemon choisitCombattant();

	/**
	 * Cette fonction renvoie le pokémon, en connaissant le pokémon adverse, choisi
	 * par un dresseur parmis les Pokemon de son ranch.
	 * 
	 * @param pok Le pokémon de l'adversaire
	 * @return La méthode renvoie un objet IPokemon.
	 */
	public IPokemon choisitCombattantContre(IPokemon pok);

	/**
	 * Il renvoie l'attaque que le joueur veut utiliser
	 * 
	 * @param attaquant le pokémon attaquant
	 * @param defenseur Le pokémon attaqué.
	 * @return Une attaque
	 */
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur);
}
