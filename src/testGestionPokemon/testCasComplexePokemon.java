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
        pokeTest = new Pokemon(Pokedex.listeEspece[3]);
    }
    @Test // De la derniere valeur
    public void testMaxCreatePokemon(){
        // On test si le pokémon possède la bonne espece et le bon niveau.
        Pokemon out = new Pokemon("maxValue",Pokedex.listeEspece[151]);
        assertEquals(out.getEspece(),Pokedex.listeEspece[151]);
    }
}
