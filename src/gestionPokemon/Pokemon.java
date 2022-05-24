/**
 *
 */
package gestionPokemon;

import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokemon;
import interfaces.IStat;
import java.util.Arrays;
import java.util.Objects;

/**
 * Une classe qui represente un Pokemon.
 */
public class Pokemon implements IPokemon {

    /** Le compteur d'id attribuant un id à chaque nouveau Pokemon */
    public static int cptId = 0;

    /**
     * L'identifiant unique du Pokémon
     */
    public int id = 0;
    /**
     * Le nom du Pokémon
     */
    public String nom;

    /**
     * Le niveau actuel du Pokémon
     */
    private int niv;
    
    private boolean aChangeNiveau = false;

    /**
     * La quantité d'expérience du Pokémon
     */
    public double xp;

    /** L'espèce du Pokémon */
    public Espece espPoke;

    /**
     * Le tableau des capacites que le Pokemon peut utiliser
     */
    private Capacite[] listeCapacite = new Capacite[4];

    /** L'ensemble des stats spécique du Pokemon */
    public Stats statsSpecifiques = new Stats();

    /** Le nombre maximum de PV que le Pokemon peut avoir */
    public int pvMax;

    /**
     * L'ensemble de stats des EV du Pokemon
     */
    public Stats statsEV = new Stats();

	/**
	 * L'ensemble de stats des EV du Pokemon
	 */
	public Stats statsDV = new Stats();
	
	private Capacite attaqueChoisie;

    /**
     * La capacité que le pokemon à utiliser en dernier
     */
    private Capacite derniereCapciteUtilisee;

    /**
     * La quantité de degat que le pokemon a subit lors du dernier tour
     */
    private double derniersDegatsSubits;

    /**
     * La quantité de degat que le pokemon a subit lors de l'avant-dernier tour
     */
    private double avantDerniersDegatsSubits;

    /**
     * Le nombre de tours avat que le Pokemon puisse à nouveau attaquer
     */
    private int nombreDeToursAvantAttaque;

    /**
     * Creer un objet Pokemon avec 2 parametres
     *
     * @param nom     le nom du Pokemon
     * @param espPoke l'espece du Pokemon
     */
    public Pokemon(String nom, Espece espPoke) {
        this.setId(cptId);
        this.nom = nom;
        this.statsEV.setForce(0);
        this.statsEV.setDefense(0);
        this.statsEV.setVitesse(0);
        this.statsEV.setSpecial(0);
        this.statsEV.setPV(0);
        this.statsDV.setForce((int) (Math.random() * (15) + 1));
        this.statsDV.setDefense((int) (Math.random() * (15) + 1));
        this.statsDV.setVitesse((int) (Math.random() * (15) + 1));
        this.statsDV.setSpecial((int) (Math.random() * (15) + 1));
        this.statsDV.setPV((int) (Math.random() * (15) + 1));
        this.espPoke = espPoke;
        this.statsSpecifiques=new Stats(this.espPoke.statsDeBase);
        this.espPoke.initCapaciteSelonNiveau();
        this.niv=espPoke.nivDepart;
        gagnerXp(this.espPoke.getExpDeBase());
        this.apprendCapacites(this.espPoke.capaciteDispo(this));
        calculPV();
        calculPVMax();
        calculDefense();
        calculSpecial();
        calculForce();
        calculVitesse();
    }

    /**
     * Creer un objet Pokemon avec 2 parametres
     *
     * @param espPoke l'espece du Pokemon
     */
    public Pokemon(Espece espPoke) {
        this.setId(cptId);
        this.nom = espPoke.getNom();
        this.statsEV.setForce(0);
        this.statsEV.setDefense(0);
        this.statsEV.setVitesse(0);
        this.statsEV.setSpecial(0);
        this.statsEV.setPV(0);
        this.statsDV.setForce((int) (Math.random() * (15) + 1));
        this.statsDV.setDefense((int) (Math.random() * (15) + 1));
        this.statsDV.setVitesse((int) (Math.random() * (15) + 1));
        this.statsDV.setSpecial((int) (Math.random() * (15) + 1));
        this.statsDV.setPV((int) (Math.random() * (15) + 1));
        this.espPoke = espPoke;
        this.statsSpecifiques=new Stats(this.espPoke.statsDeBase);
        this.espPoke.initCapaciteSelonNiveau();
        this.niv=espPoke.nivDepart;
        gagnerXp(this.espPoke.getExpDeBase());
        this.apprendCapacites(this.espPoke.capaciteDispo(this));
        calculPV();
        calculPVMax();
        calculDefense();
        calculSpecial();
        calculForce();
        calculVitesse();
    }

    /**
     * Il renvoie une représentation sous forme de chaîne de caractère de l'objet
     *
     * @return La méthode toString est renvoyée.
     */
    @Override
    public String toString() {
    	return "\nPokemon{" +
                "nom='" + nom + '\'' +
                ", niv=" + niv +
                ", "+ Arrays.toString(listeCapacite)+"}\n";
    	/*
        return "\nPokemon{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", niv=" + niv +
                ", xp=" + xp +
                ",\n\tespPoke=" + espPoke +
                ",\n\tlisteCapacite=" + Arrays.toString(listeCapacite) +
                ",\n\tstatsSpecifiques=" + statsSpecifiques +
                ",\n\tpvMax=" + pvMax +
                ",\n\tstatsEV=" + statsEV +
                ",\n\tstatsDV=" + statsDV +
                '}';
       */
    }

    //////////////// methodes de IPokemon ///////////////////////
    public IStat getStat() {
        return this.statsSpecifiques;
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
        return this.getStat().getPV() * 100 * this.pvMax;
    }

    public IEspece getEspece() {
        return this.espPoke;
    }
    
    public Capacite[] getCapacitesApprises() {//TODO chgande to ICapacite[]
    	//System.out.println("\taffichage de listeCapacite :");
    	int nb = 0;
    	for (Capacite c : this.listeCapacite) {
    		if(c!=null) {
    		nb++;
    		//System.out.println("\t\t"+c);
    		}
    	}
    	//System.out.println("initialisation de rep : ");
    	Capacite[] rep = new Capacite[nb];
    	for (int i=0;i<nb;i++) {
			//System.out.println("\t\t"+this.listeCapacite[i]);
    		rep[i]=this.listeCapacite[i];
    	}
        return rep;
    }

    @Override
    public void apprendCapacites(ICapacite[] caps) {
        for (int i = 0; i < Math.min(caps.length, 4); i++) {
            try {
                if(caps[i] != null) {
                    this.remplaceCapacite(i,new Capacite((Capacite)caps[i]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void remplaceCapacite(int i, ICapacite cap) throws Exception {
    	if(this.listeCapacite[i]!=null) {
    		System.out.println(this.getNom()+" oublie "+this.listeCapacite[i].getNom()+" et apprend "+cap.getNom());
    	}
        this.listeCapacite[i] = (Capacite) cap;
    }


    @Override
    public void gagneExperienceDe(IPokemon pok) {
        this.augmenterEV(pok);
        double gainXp = (1.5 * pok.getNiveau() * pok.getEspece().getBaseExp()) / 7;
        this.gagnerXp(gainXp);
    }

    public void vaMuterEn(IEspece especeEvolution) {
    	System.out.println(this.getNom()+" evolue !");
    	try {
			Thread.sleep(1000);
			System.out.println("...");
			Thread.sleep(900);
			System.out.println("...");
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	System.out.println(this.getNom()+" a evolue en "+especeEvolution.getNom()+".");
        if (Objects.equals(this.nom, this.espPoke.getNom())){
            this.nom=((Espece) especeEvolution).getNom();
        }
        this.espPoke = (Espece) especeEvolution;
        this.espPoke.initCapaciteSelonNiveau();
        this.niv=especeEvolution.getNiveauDepart();
        this.espPoke.showCapSet();
    }

    @Override
    public void subitAttaqueDe(IPokemon attaquant, IAttaque attaque) {
    	System.out.println(attaquant.getNom()+" utilise "+((Capacite)attaque).getNom()+" !");
    	this.subirDegats(attaque.calculeDommage(attaquant, this));
    }

    public boolean estEvanoui() {
        return this.getStat().getPV() <= 0;
    }

    @Override
    public boolean aChangeNiveau() {
    	if(this.aChangeNiveau) {
        	this.aChangeNiveau = !this.aChangeNiveau;
        	return !this.aChangeNiveau;
    	}
    		return false;
    }

    public boolean peutMuter() {
        return this.espPoke.evolution != null;
    }

    public void soigne() {
        this.getStat().setPV(this.pvMax);
        this.resetAllPp();
    }

    //////////////////////////////////////////////////////////

    /**
     * Cette fonction definit l'id du pokemon a la valeur du parametre cptId et
     * incremente la variable
     * statique cptId de 1
     *
     * @param cptId le nombre de Pokemon crees
     */
    private void setId(int cptId) {
        this.id = cptId;
        Pokemon.cptId++;
    }

    /**
     * Cette fonction renvoie la variable statsEV
     *
     * @return La variable statsEV est renvoyee.
     */
    public IStat getStatsEV() {
        return statsEV;
    }

    /**
     * Cette fonction retourne la variable statsDV
     *
     * @return L'objet statsDV.
     */
    public IStat getStatsDV() {
        return statsDV;
    }
    
    /**
     * Cette fonction renvoie le type1 du Pokemon.
     *
     * @return Le type1 du pokémon
     */
    public Type getType1() {
    	return this.espPoke.type1;
    }

    /**
     * Cette fonction renvoie le deuxième type de Pokémon.
     *
     * @return Le deuxième type de Pokémon.
     */
    public Type getType2() {
    	return this.espPoke.type2;
    }
    
    public Capacite getAttaqueChoisie() {
    	return this.attaqueChoisie;
    }
    
	/**
	 * > Cette fonction fixe la valeur de la variable `attaqueChoisie` à la valeur du paramètre `actionChoisie`
	 *
	 * @param actionChoisie L'action choisie par le joueur.
	 */
	public void setAttaqueChoisie(Capacite actionChoisie) {
		this.attaqueChoisie = actionChoisie;
	}
	
    /**
     * Cette fonction renvoie la derniere capacite utilisee par le joueur
     *
     * @return La derniere capacite utilisee.
     */
    public Capacite obtenirDerniereCapaUtilisee() {
        return this.derniereCapciteUtilisee;
    }

    /**
     * Cette fonction renvoie le montant des degâts subis par le joueur.
     *
     * @return Le montant des degâts subis par le joueur.
     */
    public double obtenirDeniersDegatsSubits() {
        return this.derniersDegatsSubits;
    }

    /**
     * Cette fonction retourne la valeur de la variable `avantDeniersDegatsSubits`
     *
     * @return La valeur de la variable avantDeniersDegatsSubits.
     */
    public double obtenirAvantDeniersDegatsSubits() {
        return this.avantDerniersDegatsSubits;
    }

    /**
     * Cette fonction renvoie le nombre de tours avant l'attaque
     *
     * @return Le nombre de tours avant l'attaque.
     */
    public int obtenirNombreDeToursAvantAttaque() {
        return nombreDeToursAvantAttaque;
    }

    /**
     * Cette fonction diminue le nombre de tours avant l'attaque d'un
     */
    public void updateNombreDeToursAvantAttaque() {
        if (this.nombreDeToursAvantAttaque > 0) {
            this.nombreDeToursAvantAttaque -= 1;
        }
    }

    /**
     * Cette fonction definit le nombre de tours avant l'attaque
     *
     * @param nombreDeToursAvantAttaque Le nombre de tours avant l'attaque
     */
    public void mettreNombreDeToursAvantAttaqueA(int nombreDeToursAvantAttaque) {
        this.nombreDeToursAvantAttaque = nombreDeToursAvantAttaque;
    }

    /**
     * Cette fonction est utilisee pour reduire la vie du joueur par la quantite de
     * degâts reçus
     *
     * @param degats les degâts a faire
     */
    public void subirDegats(int degats) {
    	System.out.println(this.getNom()+" subit "+degats+" degats.");
        this.getStat().setPV(this.getStat().getPV() - degats);
    	System.out.println("Il reste "+this.getStat().getPV()+" PV a "+this.getNom()+".");
        this.avantDerniersDegatsSubits = this.derniersDegatsSubits;
        this.derniersDegatsSubits = degats;
    }

    /**
     * Si l'expérience du joueur est supérieure au seuil, augmentez le niveau du joueur et soustrayez le seuil de
     * l'expérience du joueur. Sinon, ajoutez simplement l'expérience à l'expérience du joueur
     *
     * @param expAGagner la quantité d'expérience à acquérir
     */
    public void gagnerXp(double expAGagner) {
        double gainExp = expAGagner;
        double xpTemporaire = this.getExperience() + expAGagner;
        double seuil = (Math.pow(this.niv + 1, 3) * 0.8);
        //System.out.println((this.getNom()+" a gagne "+(int)expAGagner+" point d'experience."));
        if (xpTemporaire >= seuil) {
            while (xpTemporaire >= seuil) {
                augmenterNiveau();
                this.xp = gainExp - seuil;
                this.xp=Math.round(this.xp*100.0)/100.0;
                gainExp -= seuil;
                seuil = (Math.pow(this.niv + 1, 3) * 0.8);
                xpTemporaire = this.getExperience() + (gainExp-seuil);
            }
        } else {
            this.xp += expAGagner;
        }
    }

    /**
     * La fonction est appelee lorsqu'un pokemon monte de niveau. Il verifie si le pokemon peut
     * evoluer, et s'il le peut, il le fait evoluer. Puis il calcule les nouvelles stats du pokemon
     */
    public void augmenterNiveau() {
        this.niv++;
        this.aChangeNiveau = true;
        System.out.println("\n"+this.getNom()+" a atteint  le niveau "+this.getNiveau()+".");
        if (this.niv >= espPoke.nivEvolution && this.getEspece().getEvolution(this.niv) != null && this.espPoke.nivEvolution!=0) {
            this.vaMuterEn(this.getEspece().getEvolution(this.niv));
        }
        // Les stats de base sont celles de l'espece actuelle du pokemon. Ainsi, si le pokemon a evolue, son espece a change juste avant donc les stats sont calculees sur les nouvelles stat de base.
        calculPV();
        calculForce();
        calculDefense();
        calculVitesse();
        calculSpecial();
    }


    
    /**
     * Il calcule le PV maximum d'un Pokémon en fonction de sa vitesse de base, de ses statistiques DV et EV et de son niveau
     */
    public void calculPVMax(){
        this.pvMax = (((2 * (this.espPoke.getBaseStat().getPV() + this.getStatsDV().getPV())
                + this.getStatsEV().getPV() / 4) * this.getNiveau()) / 100) + this.getNiveau() + 10;
    }
    /**
     * Il calcule le PV d'un Pokémon en fonction de sa vitesse de base, de ses statistiques DV et EV et de son niveau
     */
    public void calculPV(){
        this.getStat().setPV((((2 * (this.espPoke.getBaseStat().getPV() + this.getStatsDV().getPV())
                + this.getStatsEV().getPV() / 4) * this.getNiveau()) / 100) + this.getNiveau() + 10);
    }
    /**
     * Cette fonction calcule la force d'un pokémon en fonction de sa vitesse de base, de ses statistiques DV et EV et de son niveau
     */
    public void calculForce(){
        this.getStat().setForce((2 * (this.getEspece().getBaseStat().getForce() + this.getStatsDV().getForce())
                + (this.getStatsEV().getPV() / 4) / 100) + 5);
    }
    /**
     * Cette fonction calcule la stat de défense d'un pokémon en fonction de sa vitesse de base, de ses statistiques DV et EV et de son niveau
     */
    public void calculDefense(){
        this.getStat().setDefense((2 * (this.getEspece().getBaseStat().getDefense() + this.getStatsDV().getDefense())
                + (this.getStatsEV().getDefense() / 4) / 100) + 5);
    }
    /**
     * Calcule la vitesse du pokémon en fonction de sa vitesse de base, de ses statistiques DV et EV et de son niveau
     */
    public void calculVitesse(){
        this.getStat().setVitesse((2 * (this.getEspece().getBaseStat().getVitesse() + this.getStatsDV().getVitesse())
                + (this.getStatsEV().getVitesse() / 4) / 100) + 5);
    }
    /**
     * Cette fonction calcule la stat spéciale d'un pokémon en fonction de sa vitesse de base, de ses statistiques DV et EV et de son niveau
     */
    public void calculSpecial(){
        this.getStat().setSpecial((2 * (this.getEspece().getBaseStat().getSpecial() + this.getStatsDV().getSpecial())
                + (this.getStatsEV().getSpecial() / 4) / 100) + 5);
    }

    /**
     * Cette fonction est utilisee pour augmenter les statistiques d'un pokemon
     * lorsqu'il bat un autre
     * pokemon
     *
     * @param vaincu Le pokemon qui a ete vaincu
     */
    public void augmenterEV(IPokemon vaincu) {
        this.getStatsEV().setForce(this.getStatsEV().getForce() + vaincu.getEspece().getGainsStat().getForce());
        this.getStatsEV().setDefense(this.getStatsEV().getDefense() + vaincu.getEspece().getGainsStat().getDefense());
        this.getStatsEV().setVitesse(this.getStatsEV().getVitesse() + vaincu.getEspece().getGainsStat().getVitesse());
        this.getStatsEV().setSpecial(this.getStatsEV().getSpecial() + vaincu.getEspece().getGainsStat().getSpecial());
        this.getStatsEV().setPV(this.getStatsEV().getPV() + vaincu.getEspece().getGainsStat().getPV());
    }
    /**
     * Cette fonction renvoie la stat de defense du pokemon si l'attaque n'est pas
     * speciale, sinon elle
     * renvoie la stat speciale
     *
     * @param capacite Le coup utilise
     * @return La defense ou la statistique speciale du pokemon.
     */
    public float obtenirDefSur(Capacite capacite) {
        if (!capacite.getCategorie().isSpecial()) {
            return this.getStat().getDefense();
        } else {
            return this.getStat().getSpecial();
        }
    }

    /**
     * Cette fonction renvoie la statistique d'attaque du Pokemon, en fonction du
     * type d'attaque
     *
     * @param capacite La capacite que le Pokemon utilise
     * @return La statistique d'attaque du Pokemon.
     */
    public float obtenirAtqSur(Capacite capacite) {
        if (!capacite.getCategorie().isSpecial()) {
            return this.getStat().getForce();
        } else {
            return this.getStat().getSpecial();
        }
    }

    /**
     * Cette fonction retourne vrai si le pokemon a le type passe en parametre
     *
     * @param type Le type cherché
     * @return un boolean indiquant la présence type parmis ceux du Pokemon
     */
    public boolean possedeLeType(Type type) {
        return this.espPoke.type1 == type || this.espPoke.type2 == type;
    }

    /**
     * Cette fonction reinitialise les PP de tous les mouvements du Pokemon
     */
    public void resetAllPp() {
        for (Capacite c : this.listeCapacite) {
            c.resetPP();
        }
    }

    /**
     * Si les vitesses des deux pokemons sont egales, alors retournez un booleen
     * aleatoire. Sinon, renvoie
     * vrai si la vitesse du pokemon actuel est superieure a la vitesse de l'autre
     * pokemon
     *
     * @param other Le Pokemon auquel comparer
     * @return un booleen indiquant si le Pokemon this est plus rapide que le
     *         Pokemon en parametre
     */
    public boolean estPlusRapideQue(Pokemon other) {
        if (this.getStat().getVitesse() == other.getStat().getVitesse()) {
            return Math.random() > 0.5;
        } else {
            return this.getStat().getVitesse() > other.getStat().getVitesse();
        }
    }

    /**
     * Cette fonction est utilisee pour utiliser une capacite specifique
     *
     * @param actionChoisie L'action que le joueur veut utiliser
     */
    public void utilise(Capacite actionChoisie) {
        actionChoisie.utilise();
    }

}
