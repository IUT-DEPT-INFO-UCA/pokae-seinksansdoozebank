package gestionPokemon;

public class Espece {
    private int id;
    public String nom;
    public Type type1;
    public Type type2;

    //Stats specifiques :
    public int atq;
    public int def;
    public int vit;
    public int spe;
    public int pv;

    public int nivDepart;
    public int nivEvolution;
    public Espece evolution;

    private int expDeBase;

    //Valeur d'Effort == puissance suite aux combats
    private int evAtq;
    private int evDef;
    private int evVit;
    private int evSpe;
    private int evPv;

    //Valeurs dï¿½terminantes == puissance native
    // private int dvAtk;
    // private int dvDef;
    // private int dvVit;
    // private int dvSpe;
    // private int dvPV;


}