/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IEchange.java
 */
package interfaces;

/**Présente les méthodes pour changer de pokemon en combat
 * @author Leo Donati
 * C'est un autre type d'attaque
 * Correspond à l'échange du Pokemon du combat avec un autre Pokemon du ranch
 */
public interface IEchange extends IAttaque {
	/**Défini le pokemon qui va entrer en combat lors du changement
	 * @param pok Le pokemon qui va entrer en combat
	 */
	public void setPokemon(IPokemon pok); //choisit le Pokemon remplaçant
	
	/**Execute le changement de pokemon en mettant le pokemon qui va entrer en combat comme nouveau pokemon actif
	 * @return Le pokemon qui etait au combat
	 */
	public IPokemon echangeCombattant();  //active le remplacement (et renvoie l'ancien pokemon)	
}
