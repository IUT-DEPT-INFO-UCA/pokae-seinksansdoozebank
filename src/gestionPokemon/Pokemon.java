package gestionPokemon;

import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokemon;
import interfaces.IStat;

public class Pokemon implements IStat, IPokemon{
    public static int cptId = 0;
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

    public Pokemon(String nom,Espece espPoke) {
        this.setId(cptId);
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
		this.espPoke=espPoke;
    }

	public Pokemon(Espece espPoke) {
		this.setId(cptId);
		this.nom = espPoke.getNom();
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
		this.espPoke=espPoke;
	}
    
    //////////////// methodes de IStat ///////////////////////
	public int getPV() {
		return this.pv;
	}

	public int getForce() {
		return this.atk;
	}

	public int getDefense() {
		return this.def;
	}

	public int getSpecial() {
		return this.spe;
	}

	public int getVitesse() {
		return this.vit;
	}

	public void setPV(int pv) {
		this.pv = pv;
	}

	public void setForce(int atk) {
		this.atk = atk;
	}

	public void setDefense(int def) { 
		this.def = def;
	}

	public void setVitesse(int vit) {
		this.vit = vit;
		
	}

	public void setSpecial(int spe) {
		this.spe = spe;
	}

	//////////////////////////////////////////////////////////  
	
	
	
    //////////////// methodes de IPokemon ///////////////////////
	public IStat getStat() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getExperience() {
		return this.xp;
	}

	public int getNiveau() {
		return this.niv;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getNom() {
		return this.nom;
	}

	public double getPourcentagePV() {
		return this.pv*100*this.pvMax;
	}

	public IEspece getEspece() {
		return this.espPoke;
	}

	@Override
	public void vaMuterEn(IEspece esp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ICapacite[] getCapacitesApprises() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void apprendCapacites(ICapacite[] caps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remplaceCapacite(int i, ICapacite cap) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gagneExperienceDe(IPokemon pok) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subitAttaqueDe(IPokemon pok, IAttaque atk) {
		// TODO Auto-generated method stub
		
	}

	public boolean estEvanoui() {
        //si les pv sont inf a 0 
    	return this.pv <= 0;
	}

	@Override
	public boolean aChangeNiveau() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean peutMuter() {
		return this.espPoke.evolution!=null;
	}

	public void soigne() {
    	this.pv = this.pvMax;
    	this.resetPp();
	}

	//////////////////////////////////////////////////////////  
	
	private void setId(int cptId) {
		this.id = cptId;
		Pokemon.cptId ++;
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
    
    public void augmenterXP(int baseExpAdv, int nivAdv) {
    	double gainXp = (1.5 * nivAdv * baseExpAdv) / 7;
        double xpTemporaire = this.xp + gainXp ;
        double seuil = (Math.pow(this.niv, 3) * 0.8);
        if (xpTemporaire >= seuil) {
            augmenterNiveau();
            this.xp = xpTemporaire - seuil;
        } else {
            this.xp += gainXp;
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
    
    public float obtenirDefSur(Capacite c) {
    	if(!c.isSpecial()) {
    		return this.def;
    	}else {
    		return this.spe;
    	}
    }    
    public float obtenirAtqSur(Capacite c) {
    	if(!c.isSpecial()) {
    		return this.atk;
    	}else {
    		return this.spe;
    	}
    }
    
    public boolean possedeLeType(Type type) {
    	return this.espPoke.type1==type || this.espPoke.type2==type;
    }
    
    
    public void resetPp()
    {
    	for (Capacite c : this.listeCapacite) {
    		c.resetPP();
    	}
    }
	public boolean estPlusRapideQue(Pokemon other) {
		if(this.vit==other.vit) {
			return Math.random()>0.5;
		}else {
			return this.vit>other.vit;
		}
	}

}
