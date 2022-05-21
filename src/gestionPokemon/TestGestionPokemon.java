package gestionPokemon;

import java.io.FileNotFoundException;
//import java.util.Arrays;

public class TestGestionPokemon {

<<<<<<< Updated upstream
	public static void main(String[] args) throws FileNotFoundException {
        Pokedex pokedex = new Pokedex();
        pokedex.createListeCapacite();
        pokedex.createListeEspece();
        Espece espece=Pokedex.listeEspece[2];
        espece.initCapaciteSelonNiveau();
        //System.out.println(Arrays.toString(espece.getCapSet()));
	}
=======
        public static void main(String[] args) throws FileNotFoundException {
                /*Pokedex pokedex = new Pokedex();
                pokedex.createListeCapacite();
                for(int i=1;i<Pokedex.listeCapacite.length;i++) {
                	System.out.println(Pokedex.listeCapacite[i].getNom());
                }/*
                pokedex.createListeEspece();
                for(int i=1;i<Pokedex.listeEspece.length;i++) {
                	System.out.println(Pokedex.listeEspece[i].getNom());
                }*/

                
                  
                 
             	System.out.println(Type.VOL.getCoeffTotal(Type.COMBAT, Type.INSECTE));
        }
>>>>>>> Stashed changes

}
