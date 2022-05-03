package gestionPokemon;
public class Espece {
    public String nom;
    
    //Stats spécifiques :
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
    //Valeurs déterminantes == puissance native
    // private int dvAtk;
    // private int dvDef;
    // private int dvVit;
    // private int dvSpe;
    // private int dvPV;    
    
  
}
