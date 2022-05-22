package testGestionPokemon;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class testCasSimplePokemon {
    private Pokemon pokeTest ;
    @BeforeEach
    public void createPokedex() throws FileNotFoundException {
        // Initialisation du pokedex et du pokemon Test.
        Pokedex.createListeCapacite();
        Pokedex.createListeEspece();
        pokeTest = new Pokemon(Pokedex.listeEspece[3]);

    }
    @Test
    public void createSimplePokemon() throws FileNotFoundException {
       //On créé le pokémon
        Pokemon florizarre = new Pokemon("Flofloriri",Pokedex.listeEspece[3]);
        // On test si le pokémon possède la bonne espece et le bon niveau.
        assertEquals(florizarre.getEspece(),Pokedex.listeEspece[3]);
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
    @Test
    public void testAugmenterEV(){
        //On créé un pokemon qui fait gagner des stats de Spe
        Pokemon florizarre = new Pokemon("Flofloriri",Pokedex.listeEspece[3]);

        //On récup les anciens EV de notre pokeTest
        int ancienEV = pokeTest.getStatsEV().getSpecial();

        //Suite au gain du combat le pokemon doit gagner 3de special

        pokeTest.augmenterEV(florizarre);

        assertNotEquals(ancienEV, pokeTest.getStatsEV().getSpecial());

    }
    @Test
    public void testEstplusRapideQue(){
        // On test grace a deux pokemons lequel est le plus rapide
        Pokemon florizarre = new Pokemon("Flofloriri",Pokedex.listeEspece[3]);
        florizarre.getStat().setVitesse(270);


        System.out.println(pokeTest.getStat());

        System.out.println(florizarre.getStat());


        System.out.println(pokeTest.getStatsDV());

        System.out.println(florizarre.getStatsDV());

        assertFalse(pokeTest.estPlusRapideQue(florizarre));
    }


}
