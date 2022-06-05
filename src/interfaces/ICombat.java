/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * ICombat.java
 */
package interfaces;

/**
 * Représente les méthodes qui gère un combat
 * 
 * @author Leo Donati
 *
 */
public interface ICombat {

	/**
	 * Cette méthode fait choisir aux dresseurs s'affrontant le premier Pokemon
	 * qu'ils veulent envoyer au combat
	 */
	public void commence();

	/**
	 * Il renvoie le premier dresseur.
	 * 
	 * @return Le premier dresseur.
	 */
	public IDresseur getDresseur1();

	/**
	 * Il renvoie le deuxième dresseur.
	 * 
	 * @return Le deuxième dresseur.
	 */
	public IDresseur getDresseur2();

	/**
	 * Il crée un nouvel objet ITour qui compose le Combat
	 * 
	 * @param pok1 Le premier pokémon
	 * @param atk1 L'attaque utilisée par le premier pokémon
	 * @param pok2 Le deuxième pokémon
	 * @param atk2 L'attaque utilisée par le deuxième pokémon
	 * @return Un objet ITour.
	 */
	public ITour nouveauTour(IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2);

	/**
	 * Cette fonction est appelée lorsque le combat est terminé et soigne les ranch
	 * des 2 dresseur pour qu'ils soient prêts à faire un autre Combat
	 */
	public void termine();
}
