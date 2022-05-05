package gestionPokemon;

public class Pokemon {
    static int cptId = 0;
    public int id = 0; // identifiant unique
    public String nom; // Nom du pokemon
    public int niv;
    public double xp; // Points d'expï¿½riences
    public Espece espPoke= new Espece();
    
    // Stats spï¿½cifiques :
    public int atk;
    public int def;
    public int vit;
    public int spe;
    public int pv;
    public int pvMax;

    // public Capacite[] listeCapacite;
    // Valeur d'Effort == puissance suite aux combats
    private int evAtk;
    private int evDef;
    private int evVit;
    private int evSpe;
    private int evPV;
    // Valeurs  determinantes == puissance native
    private int dvAtk;
    private int dvDef;
    private int dvVit;
    private int dvSpe;
    private int dvPV;

    public Pokemon(String nom) {
        this.setId(cptId);
        this.nom = nom;
        evAtk = 0;
        evDef = 0;
        evVit = 0;
        evSpe = 0;
        evPV = 0;
        dvAtk = (int) (Math.random() * ((15) + 1));
        dvDef = (int) (Math.random() * ((15) + 1));
        dvVit = (int) (Math.random() * ((15) + 1));
        dvSpe = (int) (Math.random() * ((15) + 1));
        dvPV = (int) (Math.random() * ((15) + 1));

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void subirDegats(int degats) {
        this.pv-=degats;
    }
    public boolean estMort() {
        //si les pv sont inf ï¿½ 0 
        if (this.pv <= 0) {
        
            // mort
            return true;
        }
        return false;
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
        //Les stats de base sont celles de l'espece actuelle du pokï¿½mon. 
        //Ainsi, si le pokï¿½mon a ï¿½voluï¿½, son espece a changï¿½ juste avant donc les stats sont calculï¿½es sur les nouvelles stat de base.
        this.pvMax = (((2*(this.espPoke.pv + this.dvPV)+this.evPV/4)*this.niv ) /100 ) + this.niv + 10;
        this.atk = ((2*(this.espPoke.atk + this.dvAtk)+(evAtk/4)/100)+5);
        this.def = ((2*(this.espPoke.def + this.dvDef)+(evDef/4)/100)+5);
        this.vit = ((2*(this.espPoke.vit + this.dvVit)+(evVit/4)/100)+5);
        this.spe = ((2*(this.espPoke.spe + this.dvSpe)+(evSpe/4)/100)+5);
    }
    public void evoluer(){
        // On modifie uniquement l'espece du pokï¿½mon. Le calcul des nouvelles stat se fait dans augmenterNiv
        this.espPoke=this.espPoke.prochaineEvolution;
    }
    
    public float obtenirDefSur(String categorieCapa) {
    	if(categorieCapa == "Physique") {
    		return this.def;
    	}else {
    		return this.spe;
    	}
    }    
    public float obtenirAtkSur(String categorieCapa) {
    	if(categorieCapa == "Physique") {
    		return this.atk;
    	}else {
    		return this.spe;
    	}
    }

}
