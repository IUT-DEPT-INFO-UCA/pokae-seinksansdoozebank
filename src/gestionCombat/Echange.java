package gestionCombat;

import interfaces.IDresseur;
import interfaces.IEchange;
import interfaces.IPokemon;
/**
 * La classe représentant en echange de Pokemon qu'un dresseur peut faire durant un combat
 * @author fadda
 *
 */
public class Echange implements IEchange {
	/**
	 * Le dresseur à l'initiative de l'Echange
	 */
	private IDresseur dresseur;
	/**
	 * Le Pokemon qui va être envoyé au combat
	 */
	private IPokemon newPokemon;
	
	/**
	 * Le constructeur de la classe Echange
	 * @param pok Le pokemon qui va être envoyé au combat
	 * @param dresseur Le dresseur à l'initiative de l'Echange
	 */
	public Echange(IPokemon pok, IDresseur dresseur) {
		this.newPokemon = pok;
		this.dresseur = dresseur;
	}
	
	/**
	 * Methode retournant une représentation textuelle de l'Echange
	 * @return Une représentation textuelle de l'Echange
	 */
	public String toString() {
		return dresseur.getNom() + " veut envoyer " + newPokemon.getNom();
	}

	/////////////////// methode de IEchange (et IAttaque) ///////////////////
	@Override
	public int calculeDommage(IPokemon lanceur, IPokemon receveur) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void utilise() {
		((Dresseur) this.dresseur).setPokemon(this.echangeCombattant());
	}

	@Override
	public void setPokemon(IPokemon pok) {
		this.newPokemon = pok;

	}

	@Override
	public IPokemon echangeCombattant() {
		System.out.println(((Dresseur) this.dresseur).getNom() + " rapelle "
				+ ((Dresseur) this.dresseur).getPokemon().getNom() + " et envoie " + this.newPokemon.getNom());
		return this.newPokemon;
	}
	/////////////////////////////////////////////////////////////////////
}
