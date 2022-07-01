/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IAttaque.java
 */
package interfaces;



/**
 * Présente les methodes pour utiliser une attaque.
 * 
 * @author Leo Donati
 *         Une attaque est une action du Pokemon durant une bataille.
 *         Il y a deux types d'attaques :
 *         - les capacités (interface ICapacity)
 *         - les échanges (interface IEchange)
 */
public interface IAttaque {
	/**
	 * Calcule la quantité de dégats que va recevoir un pokemon, soit le nombre de
	 * Points de Vie qu'il va perdre.
	 * 
	 * @param lanceur  Le pokemon qui utilise l'attaque.
	 * @param receveur Le pokemon qui subit l'attaque.
	 * @return Un int représentant le nombre de dégats que va recevoir le recevoir.
	 */
	int calculeDommage(IPokemon lanceur, IPokemon receveur);

	/**
	 * Décrémente le nombre de Points de Pouvoir de la capacité utilisée ou actionne l'echange
	 * 
	 */
	void utilise();
}
