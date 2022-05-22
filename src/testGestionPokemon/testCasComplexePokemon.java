package testGestionPokemon;

import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class testCasComplexePokemon {
    private Pokemon pokeTest ;
    @BeforeEach
    public void createPokedex() throws FileNotFoundException {
        // Initialisation du pokedex et du pokemon Test.
        Pokedex pokedex = new Pokedex();
        Pokedex.createListeCapacite();
        Pokedex.createListeEspece();
        pokeTest = new Pokemon(Pokedex.listeEspece[1]);
    }
    @Test // De la derniere valeur
    public void testMaxCreatePokemon(){

        Pokemon out = new Pokemon("maxValue",Pokedex.listeEspece[151]);
        assertEquals(out.getEspece(),Pokedex.listeEspece[151]);
    }
    @Test
    public void testEvolutionParAugmenterNiveau(){
        for(int i=2; i<=17;i++){
            pokeTest.augmenterNiveau();
            System.out.println(pokeTest.getId());
        }
        System.out.println(pokeTest.getId());
    }
}
