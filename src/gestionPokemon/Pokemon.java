package gestionPokemon;

public class Pokemon {
    static int cptId = 0;
    public int id = 0; // identifiant unique
    public String nom; // Nom du pokemon
    public int niv;
    public double xp; // Points d'exp�riences
    public Espece espPoke;
    public Capacite[] listeCapacite = new Capacite[4];

    // Stats sp�cifiques :
    public int atk;
    public int def;
    public int vit;
    public int spe;
    public int pv;
    public int pvMax;

    // Valeur d'Effort == puissance suite aux combats
    private int evAtq;
    private int evDef;
    private int evVit;
    private int evSpe;
    private int evPv;

    // Valeurs  determinantes == puissance native
    private int dvAtq;
    private int dvDef;
    private int dvVit;
    private int dvSpe;
    private int dvPv;

    //historique et effet des capacite
    private Capacite derniereCapciteUtilisee;
    private double deniersDegatsSubits;
    private double avantDeniersDegatsSubits;
    private int nombreDeToursAvantAttaque;

    public Pokemon(String nom) {
        this.mettreId(cptId);
        this.nom = nom;
        evAtq = 0;
        evDef = 0;
        evVit = 0;
        evSpe = 0;
        evPv = 0;
        dvAtq = (int) (Math.random() * ((15) + 1));
        dvDef = (int) (Math.random() * ((15) + 1));
        dvVit = (int) (Math.random() * ((15) + 1));
        dvSpe = (int) (Math.random() * ((15) + 1));
        dvPv = (int) (Math.random() * ((15) + 1));

    }

    public int obtenirId() {
        return id;
    }

    public void mettreId(int id) {
        this.id = id;
    }

    public Capacite obtenirDerniereCapaUtilisee() {
        return this.derniereCapciteUtilisee;
    }

    public double obtenirDeniersDegatsSubits() {
        return this.deniersDegatsSubits;
    }
    public double obtenirAvantDeniersDegatsSubits() {
        return this.avantDeniersDegatsSubits;
    }

    public int obtenirNombreDeToursAvantAttaque() {
        return nombreDeToursAvantAttaque;
    }

    public void updateNombreDeToursAvantAttaque() {
        if(this.nombreDeToursAvantAttaque>0) {
            this.nombreDeToursAvantAttaque-=1;
        }
    }

    public void mettreNombreDeToursAvantAttaqueA(int nombreDeToursAvantAttaque) {
        this.nombreDeToursAvantAttaque = nombreDeToursAvantAttaque;
    }

    public void subirDegats(int degats) {
        this.pv-=degats;
    }

    public boolean estMort() {
        //si les pv sont inf � 0
        return this.pv <= 0;
    }


    public void augmenterXP(int baseExpAdv, int nivAdv) {
        double xpTemporaire = this.xp + (1.5 * nivAdv * baseExpAdv) / 7;
        double seuil = (Math.pow(this.niv, 3) * 0.8);
        if (xpTemporaire >= seuil) {
            augmenterNiveau();
            this.xp = xpTemporaire - seuil;
        } else {
            this.xp += (1.5 * nivAdv * baseExpAdv) / 7;
        }
    }

    public void augmenterNiveau() {
        this.niv++;
        if(this.niv>=espPoke.nivEvolution){
            evoluer();
        }
        //Les stats de base sont celles de l'espece actuelle du pok�mon.
        //Ainsi, si le pok�mon a �volu�, son espece a chang� juste avant donc les stats sont calcul�es sur les nouvelles stat de base.
        this.pvMax = (((2*(this.espPoke.pv + this.dvPv)+this.evPv/4)*this.niv ) /100 ) + this.niv + 10;
        this.atk = ((2*(this.espPoke.atq + this.dvAtq)+(evAtq/4)/100)+5);
        this.def = ((2*(this.espPoke.def + this.dvDef)+(evDef/4)/100)+5);
        this.vit = ((2*(this.espPoke.vit + this.dvVit)+(evVit/4)/100)+5);
        this.spe = ((2*(this.espPoke.spe + this.dvSpe)+(evSpe/4)/100)+5);
    }
    public void evoluer(){
        // On modifie uniquement l'espece du pok�mon. Le calcul des nouvelles stat se fait dans augmenterNiv
        this.espPoke=this.espPoke.evolution;
    }

    public float obtenirDefSur(String categorieCapa) {
        if(categorieCapa == "Physique") {
            return this.def;
        }else {
            return this.spe;
        }
    }
    public float obtenirAtqSur(String categorieCapa) {
        if(categorieCapa == "Physique") {
            return this.atk;
        }else {
            return this.spe;
        }
    }

    public boolean possedeLeType(Type type) {
        return this.espPoke.type1==type || this.espPoke.type2==type;
    }


}