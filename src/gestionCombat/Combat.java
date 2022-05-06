package gestionCombat;
import gestionPokemon.Pokemon;

public class Combat {
    private Dresseur dresseur1 = new Dresseur();
    private Dresseur dresseur2 = new Dresseur();
    private Pokemon poke1 = new Pokemon("pikapute");
    private Pokemon poke2 = new Pokemon("carapute");

    public Combat(Dresseur d1, Dresseur d2) {
        this.dresseur1 = d1;
        this.dresseur2 = d2;
    }
    public fight () {
        nbtours=1

        while nbPokemonvivant >0{
            if(nbtour==1) {
                /*On définit les pokés actifs
                 * des 2 dresseurs
                 */
                poke1 = d1.choisirpokemon()
                poke2 = d2.choisirpokemon()
            }
            else {
                d1.choice = choisirAttaqueOrEchange()
                d2.choice = choisirAttaqueOrEchange()
                if (d1.choice== attaque && d2.choice ==echange)
                    d2.echange
                d1.attaque

				else if(d1.choice== echange && d2.choice ==attaque)
                    d1.echange
                d2.attaque

				else if(d1. choice == echange)
                    d1.echange;
                else if (d2.choice == echange) d2.echange
                else {
                    if(poke1.plusrapide(poke2)) {
                        d1.attaque
                        if (poke2.estMort()) {
                            d2.echange
                        }
                        else {
                            d2.attaque

                        }
                        if(poke1.estMort()) {
                            d1.echange
                        }

                    }
                    else {
                        d2.attaque
                        if(poke1.estMort()) {
                            d1.echange
                        }
                        else {
                            d1.attaque
                        }
                        if(poke2.estMort()) {
                            d2.echange
                        }

                    }
                }

            }
            nbtour++;

        }
    }
}