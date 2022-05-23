/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IDresseur.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface IDresseur {
	public void enseigne(IPokemon pok, ICapacite[] caps);
	public void soigneRanch();
	
	public IPokemon choisitCombattant();
	public IPokemon choisitCombattantContre(IPokemon pok);
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur);
	
	public int getNiveau();
	public String getNom();
	public IPokemon getPokemon(int i);
}
