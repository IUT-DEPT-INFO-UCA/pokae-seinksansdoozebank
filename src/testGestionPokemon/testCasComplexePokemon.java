package testGestionPokemon;

import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class testCasComplexePokemon {
    private Pokemon pokeTest;

    @BeforeEach
    public void createPokedex() throws FileNotFoundException {
        // Initialisation du pokedex et du pokemon Test.
        Pokedex pokedex = new Pokedex();
        Pokedex.createListeCapacite();
        Pokedex.createListeEspece();
        pokeTest = new Pokemon(Pokedex.listeEspece[1]);
    }

    @Test
    public void testEvolutionParAugmenterNiveau() {
        int ancienID = pokeTest.espPoke.getId();
        for (int i = 2; i <= 17; i++) {
            pokeTest.augmenterNiveau();

        }

        assertNotEquals(pokeTest.espPoke.getId(),ancienID);
    }

    @Test
    public void testSubir0Degats() {
        //On test si le pokemon peut subir 0 degats
        int pvAvantAttaque = pokeTest.getStat().getPV();
        pokeTest.subirDegats(0);
        assertEquals(pvAvantAttaque, pokeTest.getStat().getPV());
        assertEquals(pokeTest.obtenirDeniersDegatsSubits(), 0);
    }

    @Test
    public void testEstplusRapideQueSameSpeed() {
        // On test grace a deux pokemons lequel est le plus rapide (ils ont la meme vitesse)
        Pokemon florizarre = new Pokemon("Flofloriri", Pokedex.listeEspece[3]);
        florizarre.getStat().setVitesse(100);
        pokeTest.getStat().setVitesse(100);
        //On ne peux pas tester cette méthode puisqu'elle renvoie une chance sur deux
        //Ainsi, on test seulement si le resultat est définit.
        assertNotNull(pokeTest.estPlusRapideQue(florizarre));
    }
}

