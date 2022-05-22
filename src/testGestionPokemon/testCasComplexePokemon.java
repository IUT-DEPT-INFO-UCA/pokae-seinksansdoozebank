package testGestionPokemon;

import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import gestionPokemon.Stats;
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
        }
        // On test si l'id du pokemon est bien passe a l'id de son evolution
        assertEquals(pokeTest.espPoke.getId(),2);
    }
    @Test
    public void testSubir0Degats(){
        //On test si le pokemon peut subir 0 degats
        int pvAvantAttaque = pokeTest.getStat().getPV();
        pokeTest.subirDegats(0);
        assertEquals(pvAvantAttaque,pokeTest.getStat().getPV());
        assertEquals(pokeTest.obtenirDeniersDegatsSubits(),0);
    }


}
