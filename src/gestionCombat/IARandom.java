package gestionCombat;

import java.io.FileNotFoundException;

import gestionPokemon.Capacite;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import interfaces.IAttaque;
import interfaces.IPokemon;

public class IARandom extends Dresseur {
	private static final int delai = 500;
	
	public IARandom(String id, String mdp) {
		super(id, mdp);
	}
	
	public IARandom(String nom) {
		super(nom);
	}

	@Override
	public IPokemon choisitCombattant() {
		System.out.println(this.getNom()+"\n\tChoisi un pokemon a envoyer au combat...");
		try {
			Thread.sleep(delai);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = (int)(Math.random() * Pokedex.getNbPokemonParRanch());
		Pokemon choosen = this.getEquipe()[i];
		this.setPokemon(choosen);
		return choosen;
	}
	
	@Override
	public IPokemon choisitCombattantContre(IPokemon pok) {
		System.out.println(this.getNom()+"\n\tChoisi un pokemon a envoyer au combat...");
		try {
			Thread.sleep(delai);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = (int)(Math.random() * Pokedex.getNbPokemonParRanch());
		while(this.getEquipe()[i].estEvanoui()) { //verification que le pokemon n'est pas KO
			i = (int)(Math.random() * Pokedex.getNbPokemonParRanch());
		}
		Pokemon choosen = this.getEquipe()[i];
		this.setPokemonChoisi(choosen);
		return choosen;
	}

	@Override
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		if(attaquant.getCapacitesApprises().length>0) { //TODO ajouter le test des PP a 0 pour utiliser lutte
			System.out.println(this.getNom()+"\n\tChoisi une attaque a utiliser...");
			try {
				Thread.sleep(delai);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int i = (int)(Math.random() * attaquant.getCapacitesApprises().length);
			while(attaquant.getCapacitesApprises()[i].getPP()==0){ //verification que la capacite choisie a encore des PP
				i = (int)(Math.random() * attaquant.getCapacitesApprises().length);
			}
			this.setActionChoisie((Capacite) ((Pokemon)attaquant).getCapacitesApprises()[i]);
		}else {
			//utilisation de Lutte si aucune capacite n'est dispo
			try {
				this.setActionChoisie( Pokedex.createCapacite(((Capacite)Pokedex.getCapaciteStatic("Lutte")).id));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		this.getPokemon().setAttaqueChoisie(this.getActionChoisie());
		return this.getActionChoisie();
	}

	@Override
	public void selectAction(IPokemon p, IPokemon pAdv) {
		System.out.println(this.getNom()+"\n\ta "+p.getNom()+" sur le terrain. Il choisi quoi faire...");
		try {
			Thread.sleep(delai);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean echange = (int)(Math.random() * 10)<1;
		if(echange){
			p = this.choisitCombattantContre(pAdv);
			this.setActionChoisie(null);
		}else{
			this.choisitAttaque(p,pAdv);
		}
	}

}
