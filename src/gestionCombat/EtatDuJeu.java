package gestionCombat;

import interfaces.IDresseur;
import interfaces.IPokemon;

public class EtatDuJeu {
	private IDresseur dresseur1;
	private IDresseur dresseur2;
	private IPokemon pokemon1;
	private IPokemon pokemon2;
	
	
	
	public EtatDuJeu(IDresseur dresseur1,IDresseur dresseur2,IPokemon pokemon1,IPokemon pokemon2) {
		this.dresseur1=dresseur1;
		this.dresseur2=dresseur2;
		this.pokemon1=pokemon1;
		this.pokemon2=pokemon2;
	}
	
	public EtatDuJeu(EtatDuJeu edj) {
		this.dresseur1=edj.dresseur1;
		this.dresseur2=edj.dresseur2;
		this.pokemon1=edj.pokemon1;
		this.pokemon2=edj.pokemon2;
	}

	
	public IDresseur getDresseur1() {
		return dresseur1;
	}
	
	public void setDresseur1(IDresseur dresseur1) {
		this.dresseur1 = dresseur1;
	}
	
	public IDresseur getDresseur2() {
		return dresseur2;
	}
	
	public void setDresseur2(IDresseur dresseur2) {
		this.dresseur2 = dresseur2;
	}
	
	public IPokemon getPokemon1() {
		return pokemon1;
	}
	
	public void setPokemon1(IPokemon pokemon1) {
		this.pokemon1 = pokemon1;
	}
	
	public IPokemon getPokemon2() {
		return pokemon2;
	}
	
	public void setPokemon2(IPokemon pokemon2) {
		this.pokemon2 = pokemon2;
	}
	
	public boolean estTerminal() {
		return !((Dresseur) this.dresseur1).pouvoirSeBattre() || !((Dresseur)this.dresseur2).pouvoirSeBattre();
	}
}
