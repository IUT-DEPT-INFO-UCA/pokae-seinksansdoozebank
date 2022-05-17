package gestionPokemon;

import interfaces.ICategorie;

public enum CategorieAttaque implements ICategorie{
	PHYSIQUE("physique"), SPECIALE("special");
	
	private String nom;

	CategorieAttaque(String nom) {
		this.nom=nom;
	}

	public String getNom() {
		return nom;
	}

	public boolean isSpecial() {
		return this==SPECIALE;
	}
	
	
}
