package gestionCombat;

import interfaces.ITour;

/**
 * Un objet Tour
 */
public class Tour implements ITour {
	/**
	 * l'identifiant du tour
	 */
	private int id;

	/**
	 * Le constructeur de Tour
	 * 
	 * @param nbTours l'identifiant du Tour
	 */
	public Tour(int nbTours) {
		this.setId(nbTours);
	}

	////////////////// méthode de ITour //////////////////
	@Override
	public void commence() {
		// TODO Auto-generated method stub

	}
	//////////////////////////////////////////////////////

	/**
	 * Cette fonction retourne l'id du Tour
	 * 
	 * @return L'identifiant du Tour.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Cette fonction définit l'identifiant du Tour sur l'identifiant passé en
	 * paramètre
	 * 
	 * @param id L'identifiant du Tour.
	 */
	public void setId(int id) {
		this.id = id;
	}

}
