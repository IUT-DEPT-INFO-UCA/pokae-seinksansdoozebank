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
    
    // Stats specifiques :
    public Stats statsSpecifiques;
    public int atk;
    public int def;
    public int vit;
    public int spe;
    public int pv;
    
    public int pvMax;

    // Valeur d'Effort == puissance suite aux combats
    public Stats statsEV;
    private int evAtq;
    private int evDef;
    private int evVit;
    private int evSpe;
    private int evPv;
    
    // Valeurs  determinantes == puissance native
    public Stats statsDV; //stats de naissance
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

	/////////////////////////////////////////////////////////////  
	
	
	
    //////////////// methodes de IPokemon ///////////////////////
	public IStat getStat() {
		// TODO Auto-generated method stub
		//ptetre on doit mettre les 5 stats dans un hashmap ? mais on 
		// pourait meme pas le return ici vu que ce serait pas le bon type
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
	public void vaMuterEn(IEspece esp) {
		this.espPoke=(Espece) esp;
	}

	public ICapacite[] getCapacitesApprises() {
		return this.listeCapacite;
	}
	@Override
	public void apprendCapacites(ICapacite[] caps) {
		// TODO Auto-generated method stub
		
	}
	public void remplaceCapacite(int i, ICapacite cap) throws Exception {
		this.listeCapacite[i]=(Capacite) cap;
	}

	@Override
	public void gagneExperienceDe(IPokemon pok) {
		//TODO faut checker ce calcul
    	double gainXp = (1.5 * pok.getNiveau() * pok.getEspece().getBaseExp()) / 7;
        double xpTemporaire = this.xp + gainXp ;
        double seuil = (Math.pow(this.niv, 3) * 0.8);
        if (xpTemporaire >= seuil) {
            augmenterNiveau();
            this.xp = xpTemporaire - seuil;
        } else {
            this.xp += gainXp;
        }
        // augmentation des EV
        //this.evAtq+=pok.espPoke.getGainAtk;
        //...
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
        //Les stats de base sont celles de l'espece actuelle du pokemon. 
        //Ainsi, si le pokemon a evolue, son espece a change juste avant donc les stats sont calculees sur les nouvelles stat de base.
        this.pvMax = (((2*(this.espPoke.pv + this.dvPv)+this.evPv/4)*this.niv ) /100 ) + this.niv + 10;
        this.atk = ((2*(this.espPoke.atq + this.dvAtq)+(evAtq/4)/100)+5);
        this.def = ((2*(this.espPoke.def + this.dvDef)+(evDef/4)/100)+5);
        this.vit = ((2*(this.espPoke.vit + this.dvVit)+(evVit/4)/100)+5);
        this.spe = ((2*(this.espPoke.spe + this.dvSpe)+(evSpe/4)/100)+5);
        this.pvMax = (((2*(this.getEspece().getBaseStat().getPV() + this.dvPv)+this.evPv/4)*this.niv ) /100 ) + this.niv + 10;
        this.atk = ((2*(this.getEspece().getBaseStat().getForce() + this.dvAtq)+(evAtq/4)/100)+5);
        this.def = ((2*(this.getEspece().getBaseStat().getDefense() + this.dvDef)+(evDef/4)/100)+5);
        this.vit = ((2*(this.getEspece().getBaseStat().getVitesse() + this.dvVit)+(evVit/4)/100)+5);
        this.spe = ((2*(this.getEspece().getBaseStat().getSpecial()+ this.dvSpe)+(evSpe/4)/100)+5);
    }
    public void evoluer(){
        // On modifie uniquement l'espece du pokemon. Le calcul des nouvelles stat se fait dans augmenterNiv
        this.vaMuterEn(this.getEspece().getEvolution(niv));
    }
    
    public float obtenirDefSur(Capacite c) {
    	if(!c.getCategorie().isSpecial()) {
    		return this.def;
    	}else {
    		return this.spe;
    	}
    }    
    public float obtenirAtqSur(Capacite c) {
    	if(!c.getCategorie().isSpecial()) {
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
