package gestionPokemon;

import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.ICategorie;
import interfaces.IPokemon;
import interfaces.IType;

public class Capacite implements ICategorie, IAttaque, ICapacite{
	//on met la categorie dans une enumeration ???
	public int id;
	public String nom;
	public Type type;
	public String categorie;
	public int puissance;
	public int precision;
	public int pp;
	public int ppBase;

	public Capacite(int id) {
		this.id = id;
	}

	public Capacite(String nom, Type type, String categorie, int puissance, int precision, int pp, int ppBase) {
		this.nom = nom;
		this.categorie = categorie;
		this.puissance = puissance;
		this.precision = precision;
		this.pp = pp;
		this.ppBase = ppBase;
	}
	 ///////////////////////metode de ICategorie ///////////////////////
	public boolean isSpecial() {
		return this.categorie=="Special";
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getCategoryName() {
		return this.categorie;
	}
	/////////////////////////////////////////////////////////////////////
	
	/////////////////////// methodes de IAttaque ///////////////////////
	
	public void utilise() {
		System.out.println("execution de public void utilise().");
	}

	@Override
	public int calculeDommage(IPokemon lanceur, IPokemon receveur) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double calculeDommage(Pokemon lanceur, Pokemon receveur) {
		if(this.touche()) {
			if(this.puissance>0) {
				return ((lanceur.niv*0.4+2)*lanceur.obtenirAtqSur(this)*this.puissance/(receveur.obtenirDefSur(this)*50))*calculerCM(lanceur, receveur);
			}else {
				switch(this.puissance) {
					case -1 : //one shot
						if(this.getEfficiencyOn(receveur)!=0) {
							return receveur.pvMax;
						}
					case -2 : //-20 sur les non spectre
						if(this.getEfficiencyOn(receveur)!=0) {
							return 20;
						}
					case -3 : //degat subit au tours precedent * 2 si capacite physique
						if(!lanceur.obtenirDerniereCapaUtilisee().isSpecial()) {
							return lanceur.obtenirDeniersDegatsSubits()*2;
						}
					case -4 : //degat = nivLanceur si la cible n'est pas imunise au type de l'attaque
						if(this.getEfficiencyOn(receveur)!=0) {
							return lanceur.niv;
						}
					case -5 : //40 degat sur type acier ou dragon sans prendre en compte les  stat de la cible
						return 40;
					case -6 : //degat = nivLanceur si la cible n'est pas imunise au type de l'attaque
						if(this.getEfficiencyOn(receveur)!=0) {
							return lanceur.niv;
						}
					case -7 : //impossible d' attaquer pendant 2 tours puis degat infligés = (2*les degat encaissé pendant les 2 tours) sans tenir compte des types
						lanceur.mettreNombreDeToursAvantAttaqueA(2);
						if(lanceur.obtenirNombreDeToursAvantAttaque()==0) {
							return (lanceur.obtenirAvantDeniersDegatsSubits()+lanceur.obtenirDeniersDegatsSubits())*2;
						}
				}
			}
		}
		return 0;
	}
	/////////////////////////////////////////////////////////////////////
	
	
	/////////////////////// methode de Icapacite ///////////////////////
	
	 // jsp comment ca marche les type interface
 
	@Override
	public ICategorie getCategorie() {
		return null;
	}

	@Override
	public IType getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/////////////////////////////////////////////////////////////////////
	
	public boolean touche() {
		double r = Math.random();
		System.out.println(""+r+"><"+this.precision);
		return Math.random()<=this.precision;
	}

	public double calculerCM(Pokemon attaquant, Pokemon defenseur){
		double stab = 0;
		double efficacite;
		if(attaquant.possedeLeType(this.type)) {
			stab = 1.5;
		}
		efficacite = this.getEfficiencyOn(defenseur);
		return stab*efficacite*(0.85*(Math.random()*0.15));
	}

	public double getEfficiencyOn(Pokemon defenseur) {
		return this.type.getCoeffDamageOn(defenseur);
	}


	@Override
	public double getPrecision() {
		return this.precision;
	}

	@Override
	public int getPuissance() {
		return this.puissance;
	}

	@Override
	public int getPP() {
		return this.ppBase;
	}

	@Override
	public void resetPP() {
		this.pp=this.ppBase;
	}


}