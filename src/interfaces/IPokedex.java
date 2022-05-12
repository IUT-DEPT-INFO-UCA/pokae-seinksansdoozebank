/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IPokedex.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface IPokedex {
	public IPokemon[] engendreRanch();
	public IEspece getInfo(String nomEspece);
	public Double getEfficacite(IType attaque, IType defense);
	public ICapacite getCapacite(String nom);
	public ICapacite getCapacite(int n);
}
