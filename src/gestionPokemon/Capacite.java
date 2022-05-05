package gestionPokemon;

public class Capacite {
    private String nom;
    private Type type;
    private String categorie;
    private int puissance;
    private int precision;
    private int pp;
    private int ppBase;
    
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
        return Math.random()>this.precision;
    }
    
    public double calculerCM(Pokemon attaquant, Pokemon defenseur){
        double stab = 0;
        double efficacite;
        if(attaquant.espPoke.type1==this.type || attaquant.espPoke.type2==this.type) {
        	stab = 1.5;
        }
        efficacite = this.type.obtenirCoeffDegatSur(defenseur);
        return stab*efficacite*(0.85*(Math.random()*0.15));
    }
    
    public double calculerDegat(Pokemon attaquant, Pokemon defenseur) {
    	if(this.touche()) {
    		double degat = ((attaquant.niv*0.4+2)*attaquant.obtenirAtkSur(this.categorie)*this.puissance/(defenseur.obtenirDefSur(this.categorie)*50))*calculerCM(attaquant, defenseur);
        	return degat;
    	}
    	return 0;
    }
    
    
}
