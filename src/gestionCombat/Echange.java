package gestionCombat;

import interfaces.IDresseur;
import interfaces.IEchange;
import interfaces.IPokemon;

public class Echange implements IEchange {
	private IDresseur dresseur;
	private IPokemon oldPokemon;
	private IPokemon newPokemon;
	
	public Echange(IPokemon p, IDresseur dresseur) {
		this.newPokemon = p;
		this.dresseur=dresseur;
	}
	
	public String toString() {
		return dresseur.getNom()+" veut envoyer "+newPokemon.getNom();
	}

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
		System.out.println(((Dresseur)this.dresseur).getNom()+" rapelle "+((Dresseur)this.dresseur).getPokemon().getNom()+" et envoie "+this.newPokemon.getNom());
		return this.newPokemon;
	}

}
