package gestionCombat;

import interfaces.ITour;

public class Tour implements ITour {
	private int id;
		
	public Tour(int nbTours) {
		this.setId(nbTours);
	}
	////////////////// méthode de ITour //////////////////
	@Override
	public void commence() {
		// TODO Auto-generated method stub

	}
	//////////////////////////////////////////////////////
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
