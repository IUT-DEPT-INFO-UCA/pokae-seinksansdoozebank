package testGestionPokemon;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import gestionPokemon.Espece;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.*;
import java.io.FileNotFoundException;
import org.junit.rules.Timeout;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class testPokemon {
    private Pokemon pokeTest ;
    @BeforeEach
    public void createPokedex() throws FileNotFoundException {
        Pokedex pokedex = new Pokedex();
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
        assertEquals(florizarre.getNiveau(),0);
    }
    @Test
    public void maxCreatePokemon(){
        // On test si le pokémon possède la bonne espece et le bon niveau.
        Pokemon out = new Pokemon("maxValue",Pokedex.listeEspece[151]);
        assertEquals(out.getEspece(),Pokedex.listeEspece[151]);
        assertEquals(out.getNiveau(),0);
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
    //On test les cas limite du remplacement d'une capacite
    @Test
    public void testRemplacementCapacite() throws Exception {
       pokeTest.remplaceCapacite(3,Pokedex.listeCapacite[73]);
    }
    @Test
    public void testSubirDegats(){
        pokeTest.subirDegats(5);
        // Le pokemon a 80 Pv de base
        System.out.println(pokeTest.getStat().getPV());
        //assertEquals(pokeTest.getStat().getPV(), 75);
    }

}
