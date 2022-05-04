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

    public int calculEfficaciteSur(Pokemon cible){
        if(Math.random()>this.precision) {
        	
        }
        
        return 1;
    }
    
    public boolean touche() {
    	double r = Math.random();
    	System.out.println(""+r+"><"+this.precision);
        return Math.random()>this.precision;
    }
}