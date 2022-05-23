/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * ICombat.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface ICombat {
	public void commence();
	public IDresseur getDresseur1();
	public IDresseur getDresseur2();
	public ITour nouveauTour(IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2); 
	public void termine();
}
