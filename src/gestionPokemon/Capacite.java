package gestionPokemon;

public class Capacite {
    private String nom;
    private Type type;
    private String categorie;
    private int puissance;
    private int precision;
    private int pp;
    private int ppBase;
    private Boolean ohko;
    public Capacite(String nom, Type type, String categorie, int puissance, int precision, int pp, int ppBase,
            Boolean ohko) {
        this.nom = nom;
        this.type = type;
        this.categorie = categorie;
        this.puissance = puissance;
        this.precision = precision;
        this.pp = pp;
        this.ppBase = ppBase;
        this.ohko = ohko;
    }

    public int calculEfficaciteSur(Type type){
        
        
        return 1;
    }
}
