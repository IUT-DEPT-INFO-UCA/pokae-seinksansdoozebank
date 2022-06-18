/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IPokemon.java
 */
package interfaces;

import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Présente les méthodes pour utiliser un pokemon.
 * 
 * @author Leo Donati
 *
 */
public interface IPokemon {
	/**
	 * Récupère l'ensemble de stats spécifiques du pokemon.
	 * 
	 * @return L'ensemble des stats spécifiques du pokemon.
	 */
	public IStat getStat();

	/**
	 * Récupère l'expérience du pokemon.
	 * 
	 * @return Un int représentant l'expérience du pokemon.
	 */
	public double getExperience();

	/**
	 * Récupère l'expérience du pokemon.
	 * 
	 * @return Un int représentant l'expérience du pokemon.
	 */
	public int getNiveau();

	/**
	 * Récupère l'identifiant du pokemon.
	 * 
	 * @return Un int représentant l'identifiant du pokemon.
	 */
	public int getId();

	/**
	 * Récupère le nom du pokemon.
	 * 
	 * @return Un string représentant le nom du pokemon.
	 */
	public String getNom();

	/**
	 * Récupère le pourcentage de point de vie restant du pokemon.
	 * 
	 * @return Un int représentant le pourcentage de point de vie restant du
	 *         pokemon.
	 */
	public double getPourcentagePV();

	/**
	 * Récupère l'espèce du pokemon.
	 * 
	 * @return Une Espece représentant l'espèce du pokemon.
	 */
	public IEspece getEspece();

	/**
	 * Fait évoluer le pokemon en changeant son espèce.
	 * 
	 * @param esp Une Espece représentant la nouvelle espece en laquelle le pokemon
	 *            évolue;
	 * @throws IOException    une exception
	 * @throws ParseException une autre exception
	 */
	public void vaMuterEn(IEspece esp) throws IOException, ParseException;

	/**
	 * Récupère le tableau des capacités que le pokemon connait.
	 * 
	 * @return Un tableau de Capacite que le pokemon connait.
	 */
	public ICapacite[] getCapacitesApprises();

	/**
	 * Défini le tableaux des attaques que le pokemon pourra utiliser à partir d'un
	 * tableau de capacités.
	 * 
	 * @param caps Un tableau de Capacite que le pokemon va apprendre.
	 */
	public void apprendCapacites(ICapacite[] caps); // Enseigne les capacités au Pokemon

	/**
	 * Change une capacité du tableau des capacité que le pokemon peut utiliser par
	 * une autre.
	 * 
	 * @param i   Un int représentant l'indice de la capacité à oublier.
	 * @param cap Une Capacite représenant la capacité à apprendre.
	 * @throws Exception Une exception gérant l'éventuel cas où i n'est pas un
	 *                   indice du tableau.
	 */
	public void remplaceCapacite(int i, ICapacite cap) throws Exception;

	/**
	 * Ajoute l'expérience gagnée au pokemonen fonction des stats du pokemon qu'il a
	 * vaincu.
	 * 
	 * @param pok Un Pokemon représentant le pokemon vaincu.
	 * @throws IOException une exception
	 * @throws ParseException une autre exception
	 */
	public void gagneExperienceDe(IPokemon pok) throws IOException, ParseException;

	/**
	 * Met à jour les stats du pokemon en fonction du pokemon attaquant et l'attaque
	 * qu'il a utilisé.
	 * 
	 * @param pok Un Pokemon représentant le pokemon attaquant.
	 * @param atk Une Capacite repésentant la capacité utilisée par le pokemon
	 *            attaquant.
	 */
	public void subitAttaqueDe(IPokemon pok, IAttaque atk);

	/**
	 * Récupère l'état évanoui du pokemon.
	 * 
	 * @return Un booléen représentant l'état évanoui du pokemon, true s'il l'est,
	 *         false sinon.
	 */
	public boolean estEvanoui();

	/**
	 * Récupère un booléen indiquant si le pokemon vient de changer de niveau.
	 * 
	 * @return Un booléen valant true si le pokémon vient de changer de niveau,
	 *         false sinon.
	 */
	public boolean aChangeNiveau(); // renvoie true si le Pokemon vient de changer de niveau

	/**
	 * Récupère la possbilité d'évoluer d'un pokemon.
	 * 
	 * @return Un booléen valant true si le pokemon peut évoluer, false sinon.
	 */
	public boolean peutMuter();

	/**
	 * Soigne un pokemon en remettant ces PV à leur maximum et les PP de chacun de
	 * ses attaques à leur maximum.
	 */
	public void soigne(); // Remet les PV au maximum

}
