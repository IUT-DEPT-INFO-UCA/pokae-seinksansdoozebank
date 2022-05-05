package gestionPokemon;

public class Espece {
    public String nom;
    public Type type1;
    public Type type2;
    //Stats spï¿½cifiques :
    public int atk;
    public int def;
    public int vit;
    public int spe;
    public int pv;
    
    

    public int niv;
    public int nivEvolution;
    public Espece prochaineEvolution;
    public Capacite[] listeCapacite;


    private int expDeBase;
    //Valeur d'Effort == puissance suite aux combats
    private int evAtk;
    private int evDef;
    private int evVit;
    private int evSpe;
    private int evPV;
    //Valeurs dï¿½terminantes == puissance native
    // private int dvAtk;
    // private int dvDef;
    // private int dvVit;
    // private int dvSpe;
    // private int dvPV;    
    
  
}
