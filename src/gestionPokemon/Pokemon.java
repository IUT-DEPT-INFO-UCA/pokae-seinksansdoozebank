package gestionPokemon;
public class Pokemon {
    static int cptId=0;
    public int id=0; //identifiant unique
    public String nom; //Nom du pokemon
    public int niv; 
    public int xp; //Points d'expériences
    //Stats spécifiques :
    public int atk;
    public int def;
    public int vit;
    public int spe;
    public int pv;
    
    public Capacite[] listeCapacite;
    //Valeur d'Effort == puissance suite aux combats
    private int evAtk;
    private int evDef;
    private int evVit;
    private int evSpe;
    private int evPV;
    //Valeurs déterminantes == puissance native
    private int dvAtk;
    private int dvDef;
    private int dvVit;
    private int dvSpe;
    private int dvPV;
    public Pokemon(String nom) {
        this.setId(cptId);
        this.nom = nom;
        evAtk=0;
        evDef=0;
        evVit=0;
        evSpe=0;
        evPV=0;
        dvAtk=(int)(Math.random()*((15)+1));
        dvDef=(int)(Math.random()*((15)+1));
        dvVit=(int)(Math.random()*((15)+1));
        dvSpe=(int)(Math.random()*((15)+1));
        dvPV=(int)(Math.random()*((15)+1));
        
    }
    public static int getId() {
        return id;
    }
    public static void setId(int id) {
        Pokemon.id = id;
    }

}
