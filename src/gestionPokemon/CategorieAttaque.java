package gestionPokemon;

import interfaces.ICategorie;

/**
 * Énumération permettant la gestion des catégories des attaques : SPECIAL ou
 * PHYSIQUE
 */
public enum CategorieAttaque implements ICategorie {
	PHYSIQUE("physique"), SPECIALE("special");

	/**
	 * Nom de la catégorie
	 */
	private String nom;

	/**
	 * Constructeur d'une catégorie à partir de son nom
	 * 
	 * @param nom nom de la catégorie
	 */
	CategorieAttaque(String nom) {
		this.nom = nom;
	}

	/**
	 * Cette fonction renvoie le nom de la catégorie
	 * 
	 * @return Le nom de la personne.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Si celui-ci est égal à SPECIALE, renvoie vrai, sinon renvoie faux.
	 * 
	 * @return La valeur booléenne de l'expression.
	 */
	public boolean isSpecial() {
		return this == SPECIALE;
	}

}
