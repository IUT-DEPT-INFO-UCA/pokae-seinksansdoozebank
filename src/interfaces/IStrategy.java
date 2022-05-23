/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IStrategy.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface IStrategy {
	public IPokemon choisitCombattant();
	public IPokemon choisitCombattantContre(IPokemon pok);
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur);
}
