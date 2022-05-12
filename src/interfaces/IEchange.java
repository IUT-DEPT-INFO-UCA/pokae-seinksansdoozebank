/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IEchange.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface IEchange extends IAttaque {
	public void setPokemon(IPokemon pok);
	public IPokemon echangeCombattant(); 
}
