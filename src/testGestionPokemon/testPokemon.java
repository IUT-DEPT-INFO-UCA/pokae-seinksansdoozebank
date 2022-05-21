package testGestionPokemon;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import gestionPokemon.Espece;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class testPokemon {
    /*@BeforeAll
    public void createPokedex() throws FileNotFoundException {
        Pokedex pokedex = new Pokedex();
        Pokedex.createListeCapacite();
        Pokedex.createListeEspece();
    }*/
    @BeforeEach
    public void createPokedex() throws FileNotFoundException {
        Pokedex pokedex = new Pokedex();
        Pokedex.createListeCapacite();
        Pokedex.createListeEspece();
    }
    @Test
    public void createSimplePokemon() throws FileNotFoundException {

        Pokemon florizarre = new Pokemon("Flofloriri",Pokedex.listeEspece[3]);
        System.out.println(florizarre);
    }
//    @Test
//    public void FailCreatePokemon(){
//
//        Pokemon out = new Pokemon("outOfRange",Pokedex.listeEspece[152]);
//        System.out.println(out);
//    }
    @Test
    public void simplePokemon() throws FileNotFoundException {




    }

}
