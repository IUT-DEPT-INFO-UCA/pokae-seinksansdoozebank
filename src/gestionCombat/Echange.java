package gestionCombat;

import gestionPokemon.Pokemon;
import interfaces.IAttaque;
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
	private Pokemon newPokemon;
	
	/**
	 * Le constructeur de la classe Echange
	 * @param pok Le pokemon qui va être envoyé au combat
	 * @param dresseur Le dresseur à l'initiative de l'Echange
	 */
	public Echange(IPokemon pok, IDresseur dresseur) {
		this.newPokemon = (Pokemon) pok;
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
		return 0;
	}

	@Override
	public void utilise() {
		((Dresseur) this.dresseur).setPokemon(this.echangeCombattant());
	}

	@Override
	public void setPokemon(IPokemon pok) {
		this.newPokemon = (Pokemon) pok;

	}

	@Override
	public IPokemon echangeCombattant() {
		System.out.println(this.dresseur.getNom() + " rapelle "
				+ ((Dresseur) this.dresseur).getPokemon().getNom() 
				+ " et envoie " + this.newPokemon.getNom());
		((Dresseur) this.dresseur).getPokemon().incNbEchange();
		return this.newPokemon;
	}
	/////////////////////////////////////////////////////////////////////

	public IAttaque copy(Dresseur copy) {
		System.out.println("Debut de la copie d'un "+this.getClass().getSimpleName());
		return new Echange(((Pokemon) this.newPokemon).copy(),copy);
	}
}
