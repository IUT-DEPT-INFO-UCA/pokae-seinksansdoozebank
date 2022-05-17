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

	public String getNom() {
		return this.categorie;
	}
	/////////////////////////////////////////////////////////////////////
	
	
	/////////////////////// methodes de IAttaque ///////////////////////
	
	public void utilise() {
		this.pp--;
	}

	@Override
	public int calculeDommage(IPokemon lanceur, IPokemon receveur) {
		double degats = 0;
		if(this.touche()) {
			if(this.puissance>0) {
				degats = ((lanceur.getNiveau()*0.4+2)*((Pokemon) lanceur).obtenirAtqSur(this)*this.puissance/(((Pokemon) receveur).obtenirDefSur(this)*50))*calculerCM((Pokemon) lanceur, (Pokemon) receveur);
			}else {
				switch(this.puissance) {
					case -1 : //one shot
						if(this.getEfficiencyOn((Pokemon) receveur)!=0) {
							degats = ((Pokemon)receveur).pvMax;
						}
					case -2 : //-20 sur les non spectre
						if(this.getEfficiencyOn((Pokemon) receveur)!=0) {
							degats = 20;
						}
					case -3 : //degat subit au tours precedent * 2 si capacite physique
						if(!((Pokemon) lanceur).obtenirDerniereCapaUtilisee().isSpecial()) {
							degats = ((Pokemon) lanceur).obtenirDeniersDegatsSubits()*2;
						}
					case -4 : //degat = nivLanceur si la cible n'est pas imunise au type de l'attaque
						if(this.getEfficiencyOn((Pokemon) receveur)!=0) {
							degats = lanceur.getNiveau();
						}
					case -5 : //40 degat sur type acier ou dragon sans prendre en compte les  stat de la cible
						return 40;
					case -6 : //degat = nivLanceur si la cible n'est pas imunise au type de l'attaque
						if(this.getEfficiencyOn((Pokemon) receveur)!=0) {
							degats = lanceur.getNiveau();
						}
					case -7 : //impossible d' attaquer pendant 2 tours puis degat infligés = (2*les degat encaissé pendant les 2 tours) sans tenir compte des types
						((Pokemon) lanceur).mettreNombreDeToursAvantAttaqueA(2);
						if(((Pokemon) lanceur).obtenirNombreDeToursAvantAttaque()==0) {
							degats = (((Pokemon) lanceur).obtenirAvantDeniersDegatsSubits()+((Pokemon) lanceur).obtenirDeniersDegatsSubits())*2;
						}
				}
			}
		}
		return (int) degats;
	}

	public int calculeDommage(Pokemon lanceur, Pokemon receveur) {
		double degats = 0;
		if(this.touche()) {
			if(this.puissance>0) {
				degats = ((lanceur.niv*0.4+2)*lanceur.obtenirAtqSur(this)*this.puissance/(receveur.obtenirDefSur(this)*50))*calculerCM(lanceur, receveur);
			}else {
				switch(this.puissance) {
					case -1 : //one shot
						if(this.getEfficiencyOn(receveur)!=0) {
							degats = receveur.pvMax;
						}
					case -2 : //-20 sur les non spectre
						if(this.getEfficiencyOn(receveur)!=0) {
							degats = 20;
						}
					case -3 : //degat subit au tours precedent * 2 si capacite physique
						if(!lanceur.obtenirDerniereCapaUtilisee().isSpecial()) {
							degats = lanceur.obtenirDeniersDegatsSubits()*2;
						}
					case -4 : //degat = nivLanceur si la cible n'est pas imunise au type de l'attaque
						if(this.getEfficiencyOn(receveur)!=0) {
							degats = lanceur.niv;
						}
					case -5 : //40 degat sur type acier ou dragon sans prendre en compte les  stat de la cible
						return 40;
					case -6 : //degat = nivLanceur si la cible n'est pas imunise au type de l'attaque
						if(this.getEfficiencyOn(receveur)!=0) {
							degats = lanceur.niv;
						}
					case -7 : //impossible d' attaquer pendant 2 tours puis degat infligés = (2*les degat encaissé pendant les 2 tours) sans tenir compte des types
						lanceur.mettreNombreDeToursAvantAttaqueA(2);
						if(lanceur.obtenirNombreDeToursAvantAttaque()==0) {
							degats = (lanceur.obtenirAvantDeniersDegatsSubits()+lanceur.obtenirDeniersDegatsSubits())*2;
						}
				}
			}
		}
		return (int) degats;
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
		return this.type.getCoeffDamageOn(defenseur.getEspece().getTypes()[0]) * this.type.getCoeffDamageOn(defenseur.getEspece().getTypes()[1]);
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