package gestionPokemon;

import java.io.FileNotFoundException;
//import java.util.Arrays;

public class TestGestionPokemon {

	public static void main(String[] args) throws FileNotFoundException {
        Pokedex pokedex = new Pokedex();
        pokedex.createListeCapacite();
        pokedex.createListeEspece();
        Espece espece=Pokedex.listeEspece[2];
        espece.initCapaciteSelonNiveau();
        //System.out.println(Arrays.toString(espece.getCapSet()));
	}

}
