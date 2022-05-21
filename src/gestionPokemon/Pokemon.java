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
     * Le nivea uactuel du Pokémon
     */
    public int niv;

    /**
     * La quantité d'expérience du Pokémon
     */
    public double xp;

    /** L'espèce du Pokémon */
    public Espece espPoke;

    /**
     * Le tableau des capacites que le Pokemon peut utiliser
     */
    public Capacite[] listeCapacite = new Capacite[4];

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

    /**
     * La capacité que le pokemon à utiliser en dernier
     */
    private Capacite derniereCapciteUtilisee;

    /**
     * La quantité de degat que le pokemon a subit lors du dernier tour
     */
    private double deniersDegatsSubits;

    /**
     * La quantité de degat que le pokemon a subit lors de l'avant-dernier tour
     */
    private double avantDeniersDegatsSubits;

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
        this.statsDV.setForce((int) (Math.random() * ((15) + 1)));
        this.statsDV.setDefense((int) (Math.random() * ((15) + 1)));
        this.statsDV.setVitesse((int) (Math.random() * ((15) + 1)));
        this.statsDV.setSpecial((int) (Math.random() * ((15) + 1)));
        this.statsDV.setPV((int) (Math.random() * ((15) + 1)));
        this.espPoke = espPoke;
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
        this.statsDV.setForce((int) (Math.random() * ((15) + 1)));
        this.statsDV.setDefense((int) (Math.random() * ((15) + 1)));
        this.statsDV.setVitesse((int) (Math.random() * ((15) + 1)));
        this.statsDV.setSpecial((int) (Math.random() * ((15) + 1)));
        this.statsDV.setPV((int) (Math.random() * ((15) + 1)));
        this.espPoke = espPoke;
        this.espPoke.initCapaciteSelonNiveau();
        this.apprendCapacites(this.espPoke.capaciteDispo(this));
    }

    /**
     * Il renvoie une représentation sous forme de chaîne de caractère de l'objet
     *
     * @return La méthode toString est renvoyée.
     */
    @Override
    public String toString() {
        return "\nPokemon{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", niv=" + niv +
                ", xp=" + xp +
                ", espPoke=\n\t" + espPoke +
                ", \nlisteCapacite=" + Arrays.toString(listeCapacite) +
                ", statsSpecifiques=" + statsSpecifiques +
                ", pvMax=" + pvMax +
                ", statsEV=" + statsEV +
                ", statsDV=" + statsDV +
                '}';
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

    public void vaMuterEn(IEspece esp) {
        this.espPoke = (Espece) esp;
    }

    public ICapacite[] getCapacitesApprises() {
        return this.listeCapacite;
    }

    @Override
    public void apprendCapacites(ICapacite[] caps) {
        for (int i = 0; i < Math.min(caps.length, 4); i++) {
            try {
                this.remplaceCapacite(i, caps[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void remplaceCapacite(int i, ICapacite cap) throws Exception {
        this.listeCapacite[i] = (Capacite) cap;
    }

    @Override
    public void gagneExperienceDe(IPokemon pok) {
        this.augmenterEV(pok);
        double gainXp = (1.5 * pok.getNiveau() * pok.getEspece().getBaseExp()) / 7;
        double xpTemporaire = this.getExperience() + gainXp;
        double seuil = (Math.pow(this.niv, 3) * 0.8);
        if (xpTemporaire >= seuil) {
            augmenterNiveau();
            this.xp = xpTemporaire - seuil;
        } else {
            this.xp += gainXp;
        }
    }

    @Override
    public void subitAttaqueDe(IPokemon pok, IAttaque attaque) {
        this.getStat().setPV(attaque.calculeDommage(pok, this));
    }

    public boolean estEvanoui() {
        return this.getStat().getPV() <= 0;
    }

    @Override
    public boolean aChangeNiveau() {
        return this.getNiveau() != this.getEspece().getNiveauDepart();
    }

    public boolean peutMuter() {
        return this.espPoke.evolution != null;
    }

    public void soigne() {
        this.getStat().setPV(this.pvMax);
        ;
        this.resetPp();
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
        return this.deniersDegatsSubits;
    }

    /**
     * Cette fonction retourne la valeur de la variable `avantDeniersDegatsSubits`
     *
     * @return La valeur de la variable avantDeniersDegatsSubits.
     */
    public double obtenirAvantDeniersDegatsSubits() {
        return this.avantDeniersDegatsSubits;
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
        this.getStat().setPV(this.getStat().getPV() - degats);
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
     * La fonction est appelee lorsqu'un pokemon monte de niveau. Il verifie si le
     * pokemon peut
     * evoluer, et s'il le peut, il le fait evoluer. Puis il calcule les nouvelles
     * stats du pokemon
     */
    public void augmenterNiveau() {
        this.niv++;
        if (this.niv >= espPoke.nivEvolution) {
            evoluer();
        }
        // Les stats de base sont celles de l'espece actuelle du pokemon.
        // Ainsi, si le pokemon a evolue, son espece a change juste avant donc les stats
        // sont calculees sur les nouvelles stat de base.
        this.pvMax = (((2 * (this.espPoke.getBaseStat().getPV() + this.getStatsDV().getPV())
                + this.getStatsEV().getPV() / 4) * this.getNiveau()) / 100) + this.getNiveau() + 10;
        this.getStat().setForce((2 * (this.getEspece().getBaseStat().getForce() + this.getStatsDV().getForce())
                + (this.getStatsEV().getPV() / 4) / 100) + 5);
        this.getStat().setDefense((2 * (this.getEspece().getBaseStat().getDefense() + this.getStatsDV().getDefense())
                + (this.getStatsEV().getDefense() / 4) / 100) + 5);
        this.getStat().setVitesse((2 * (this.getEspece().getBaseStat().getVitesse() + this.getStatsDV().getVitesse())
                + (this.getStatsEV().getVitesse() / 4) / 100) + 5);
        this.getStat().setSpecial((2 * (this.getEspece().getBaseStat().getSpecial() + this.getStatsDV().getSpecial())
                + (this.getStatsEV().getSpecial() / 4) / 100) + 5);
    }

    /**
     * "Si le niveau du pokemon est suffisamment eleve, il evoluera vers une
     * nouvelle espece."
     *
     * La fonction s'appelle "evoluer" qui signifie "evoluer" en français
     */
    public void evoluer() {
        // On modifie uniquement l'espece du pokemon. Le calcul des nouvelles stat se
        // fait dans augmenterNiv
        this.vaMuterEn(this.getEspece().getEvolution(niv));
    }

    /**
     * > Cette fonction renvoie la stat de defense du pokemon si l'attaque n'est pas
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
     * > Cette fonction renvoie la statistique d'attaque du Pokemon, en fonction du
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
     * > Cette fonction retourne vrai si le pokemon a le type passe en parametre
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
    public void resetPp() {
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
        for (ICapacite c : this.getCapacitesApprises()) {
            if (c.equals(actionChoisie)) {
                c.utilise();
            }
        }
    }

}
