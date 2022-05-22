package testGestionPokemon;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import gestionPokemon.Espece;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class testPokemon {
    private Pokemon pokeTest ;
    @BeforeEach
    public void createPokedex() throws FileNotFoundException {
        // Initialisation du pokedex et du pokemon Test.
        Pokedex.createListeCapacite();
        Pokedex.createListeEspece();
        pokeTest = new Pokemon(Pokedex.listeEspece[3]);
        System.out.println(pokeTest);
    }
    @Test
    public void createSimplePokemon() throws FileNotFoundException {
       //On créé le pokémon
        Pokemon florizarre = new Pokemon("Flofloriri",Pokedex.listeEspece[3]);
        // On test si le pokémon possède la bonne espece et le bon niveau.
        assertEquals(florizarre.getEspece(),Pokedex.listeEspece[3]);
    }
    @Test
    public void maxCreatePokemon(){
        // On test si le pokémon possède la bonne espece et le bon niveau.
        Pokemon out = new Pokemon("maxValue",Pokedex.listeEspece[151]);
        assertEquals(out.getEspece(),Pokedex.listeEspece[151]);

    }
    @Test
    public void testStatsPokemon(){
        //On test si les stats sont correctes.
        assertNotNull(pokeTest.getStatsDV());
        assertNotNull(pokeTest.getStatsEV());
    }
    @Test
    public void testCapactiePokemon(){
        //On test si les capacités sont bien instanciées
        assertNotNull(pokeTest.getCapacitesApprises());
    }

    @Test
    public void testRemplacementCapacite() throws Exception {
        //On test s'il on peut changer une capacité
       pokeTest.remplaceCapacite(3,Pokedex.listeCapacite[73]);
    }
    @Test
    public void testSubirDegats(){
        // On test si le pokemon peut subir des dégats
        int pvAvantAttaque = pokeTest.getStat().getPV();
        pokeTest.subirDegats(5);
        assertNotEquals(pvAvantAttaque,pokeTest.getStat().getPV());
        assertEquals(pokeTest.obtenirDeniersDegatsSubits(),5);
    }
    @Test
    public void testGainNiveau(){
        // On test si le pokemon peut gagner des niveaux
        double xpBefore= pokeTest.getExperience();
        Pokemon pokeBattu = new Pokemon(Pokedex.listeEspece[33]);
        pokeTest.gagneExperienceDe(pokeBattu);
        assertNotEquals(pokeTest.getExperience(),xpBefore);
    }
    @Test
    public void testEstEvanoui(){
        // test si le pokémon est évanoui
        assertFalse(pokeTest.estEvanoui());
        pokeTest.subirDegats(200);
        assertTrue(pokeTest.estEvanoui());
    }
    @Test
    public void testSoigne(){
        // On test si le pokemon peut etre soigne

        pokeTest.subirDegats(200);
        assertTrue(pokeTest.estEvanoui());
        assertNotEquals(pokeTest.getStat().getPV(),pokeTest.pvMax);
        pokeTest.soigne();
        assertFalse(pokeTest.estEvanoui());
        assertEquals(pokeTest.getStat().getPV(), pokeTest.pvMax);

    }
    @Test
    public void testResetPP(){
        int nbPPBeforeUse = pokeTest.getCapacitesApprises()[1].getPP();
        pokeTest.getCapacitesApprises()[1].utilise();
        assertNotEquals(nbPPBeforeUse,pokeTest.getCapacitesApprises()[1].getPP());
    }
}
