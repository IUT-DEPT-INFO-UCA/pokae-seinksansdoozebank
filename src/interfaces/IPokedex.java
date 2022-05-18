/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IPokedex.java
 */
package interfaces;

/**Présente les méthodes pour utiliser un pokedex.
 * @author Leo Donati
 *
 */
public interface IPokedex {
<<<<<<< Updated upstream
	public IPokemon[] engendreRanch();			//Renvoie un tableau de 6 Pokemon au hasard
	public IEspece getInfo(String nomEspece);	//Renvoie une instance de l'espèce de Pokemon dont on fournit le nom
	public Double getEfficacite(IType attaque, IType defense); //Calcule l'efficacité d'un type d'attaque sur un type de cible
	public ICapacite getCapacite(String nomCapacite);   //Renvoie une instance de la capacité appelée nomCapacite
	public ICapacite getCapacite(int nunCapacite);
=======
	
	/**Génère un tableau de 6 pokemon choisi au hasard.
	 * @return Un tableau de 6 pokemons.
	 */
	public IPokemon[] engendreRanch();

	/**Récupère une espece à partir de son nom.
	 * @param nomEspece Un string représentant le nom de l'espece cherchée.
	 * @return Une espece réprésentant celle correspondant au nom en paramètre.
	 */
	public IEspece getInfo(String nomEspece);
	
	/**Récupère l'efficacité d'une attaque sur un type de pokemon.
	 * @param attaque Un type représentant le type de l'attaque.
	 * @param defense Un type représentant le type du pokemon cible.
	 * @return Un double représentant le coefficiant d'efficacité de l'attaque.
	 */
	public Double getEfficacite(IType attaque, IType defense);

	/**Récupère une capacité à partir de son nom.
	 * @param nomCapacite Un string représentant le nom de la capacité cherchée.
	 * @return Une capacité réprésentant celle correspondant au nom en paramètre.
	 */
	public ICapacite getCapacite(String nomCapacite);

	/**Récupère une capacité à partir de son numero.
	 * @param numCapacite Un int représentant le numéro de la capacité cherchée.
	 * @return Une capacité réprésentant celle correspondant au numéro en paramètre.
	 */
	public ICapacite getCapacite(int numCapacite);
>>>>>>> Stashed changes
}
