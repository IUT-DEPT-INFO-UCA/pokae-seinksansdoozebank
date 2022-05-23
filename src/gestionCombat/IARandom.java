package gestionCombat;

import gestionPokemon.Capacite;
import gestionPokemon.Pokemon;
import interfaces.IAttaque;
import interfaces.IPokemon;

public class IARandom extends Dresseur {

	public IARandom(String id, String mdp) {
		super(id, mdp);
	}
	
	@Override
	public IPokemon choisitCombattant() {
		int i = (int)(Math.random() * 6);
		Pokemon choosen = this.getEquipe()[i];
		this.setPokemon(choosen);
		return choosen;
	}
	
	@Override
	public IPokemon choisitCombattantContre(IPokemon pok) {
		int i = (int)(Math.random() * 6);
		Pokemon choosen = this.getEquipe()[i];
		this.setPokemonChoisi(choosen);
		return choosen;
	}

	@Override
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		int i = (int)(Math.random() * 4);
		this.setActionChoisie((Capacite) ((Pokemon)attaquant).getCapacitesApprises()[i]);
		this.getPokemon().setAttaqueChoisie(this.getActionChoisie());
		return this.getActionChoisie();
	}

}
