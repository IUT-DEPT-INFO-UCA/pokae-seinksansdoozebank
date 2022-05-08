package gestionPokemon;

public class Capacite {
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
		this.type = type;
		this.categorie = categorie;
		this.puissance = puissance;
		this.precision = precision;
		this.pp = pp;
		this.ppBase = ppBase;
	}

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
		efficacite = this.calculerEfficacite(defenseur);
		return stab*efficacite*(0.85*(Math.random()*0.15));
	}

	public double calculerEfficacite(Pokemon defenseur) {
		return this.type.obtenirCoeffDegatSur(defenseur);
	}

	public double calculerDegats(Pokemon attaquant, Pokemon defenseur) {
		if(this.touche()) {
			if(this.puissance>0) {
				return ((attaquant.niv*0.4+2)*attaquant.obtenirAtqSur(this.categorie)*this.puissance/(defenseur.obtenirDefSur(this.categorie)*50))*calculerCM(attaquant, defenseur);
			}else {
				switch(this.puissance) {
					case -1 : //one shot
						if(this.calculerEfficacite(defenseur)!=0) {
							return defenseur.pvMax;
						}
					case -2 : //-20 sur les non spectre
						if(this.calculerEfficacite(defenseur)!=0) {
							return 20;
						}
					case -3 : //degat subit au tours precedent * 2 si capacite physique
						if(attaquant.obtenirDerniereCapaUtilisee().categorie == "Physique") {
							return attaquant.obtenirDeniersDegatsSubits()*2;
						}
					case -4 : //degat = nivLanceur si la cible n'est pas imunise au type de l'attaque
						if(this.calculerEfficacite(defenseur)!=0) {
							return attaquant.niv;
						}
					case -5 : //40 degat sur type acier ou dragon sans prendre en compte les  stat de la cible
						return 40;
					case -6 : //degat = nivLanceur si la cible n'est pas imunise au type de l'attaque
						if(this.calculerEfficacite(defenseur)!=0) {
							return attaquant.niv;
						}
					case -7 : //impossible d' attaquer pendant 2 tours puis degat infligés = (2*les degat encaissé pendant les 2 tours) sans tenir compte des types
						attaquant.mettreNombreDeToursAvantAttaqueA(2);
						if(attaquant.obtenirNombreDeToursAvantAttaque()==0) {
							return (attaquant.obtenirAvantDeniersDegatsSubits()+attaquant.obtenirDeniersDegatsSubits())*2;
						}
				}
			}
		}
		return 0;
	}
	public void resetPp(){
		this.pp=this.ppBase;
	}

}