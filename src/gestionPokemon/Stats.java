package gestionPokemon;

import interfaces.IStat;

/**
 * Un objet représentant un ensemble de statistique utilsié pour stocker les
 * stats d'une espèce ou d'un Pokémon
 */
public class Stats implements IStat {
    /**
     * La stat de Force de l'ensemble.
     */
    public int force;
    /**
     * La stat de Defense de l'ensemble.
     */
    public int defense;
    /**
     * La stat de Vitesse de l'ensemble.
     */
    public int vitesse;
    /**
     * La stat de Special de l'ensemble.
     */
    public int special;
    /**
     * La stat de Point de Vie de l'ensemble.
     */
    public int pv;

    /**
     * Creer un objet Stat avec chacune de ses valeurs initilisée à 0
     */
    public Stats() {
        this.force = 0;
        this.defense = 0;
        this.vitesse = 0;
        this.special = 0;
        this.pv = 0;
    }


    /**
     * Creer un objet Stat en copiant l'argument de type stats passe en parametre
     */
    public Stats(Stats s) {
        this.force = s.getForce();
        this.defense = s.getDefense();
        this.vitesse = s.getVitesse();
        this.special = s.getSpecial();
        this.pv = s.getPV();
    }

    /**
     * Il renvoie une représentation sous forme de chaîne de caractère de l'objet
     *
     * @return Les statistiques du pokémon
     */
    @Override
    public String toString() {
        return "Stats{" +
                "force=" + force +
                ", defense=" + defense +
                ", vitesse=" + vitesse +
                ", special=" + special +
                ", pv=" + pv +
                '}';
    }

    /**
     * Cette fonction renvoie la force du Pokémon
     *
     * @return La variable de force est renvoyée.
     */
    public int getForce() {
        return force;
    }

    /**
     * Cette fonction renvoie la défense du Pokémon
     *
     * @return La variable de défense est renvoyée.
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Cette fonction renvoie la vitesse du Pokémon
     *
     * @return La vitesse du Pokémon.
     */
    public int getVitesse() {
        return vitesse;
    }

    /**
     * Cette fonction renvoie la valeur de la variable spéciale
     *
     * @return La variable spéciale est renvoyée.
     */
    public int getSpecial() {
        return special;
    }

    /**
     * Cette fonction renvoie la valeur de la variable pv
     *
     * @return La valeur de la variable pv.
     */
    public int getPV() {
        return pv;
    }

    /**
     * Cette fonction définit la force du Pokémon
     *
     * @param force La quantité de force du Pokémon.
     */
    public void setForce(int force) {
        this.force = force;
    }

    /**
     * Cette fonction définit la défense du Pokémon
     *
     * @param defense La valeur de la défense du Pokémon.
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }

    /**
     * Cette fonction définit la vitesse du Pokémon
     *
     * @param vitesse la vitesse du Pokémon
     */
    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    /**
     * Cette fonction définit la variable spéciale à la valeur du paramètre spécial
     *
     * @param special La valeur spéciale du Pokémon.
     */
    public void setSpecial(int special) {
        this.special = special;
    }

    /**
     * Cette fonction fixe la valeur de la variable privée pv à la valeur du
     * paramètre pv
     *
     * @param pv Le nombre de points de vie du Pokémon.
     */
    public void setPV(int pv) {
        this.pv = pv;
    }

}
