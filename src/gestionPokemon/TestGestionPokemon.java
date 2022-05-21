package gestionPokemon;

import java.io.FileNotFoundException;
import java.util.Arrays;
//import java.util.Arrays;

public class TestGestionPokemon {

        public static void main(String[] args) throws FileNotFoundException {
                Pokedex pokedex = new Pokedex();
                Pokedex.createListeCapacite();
                Pokedex.createListeEspece();
//                System.out.println(new Pokemon(Pokedex.listeEspece[3]));
                System.out.println(Arrays.toString(Pokedex.engendreRanch()));
        }

        /*
         * public static Type[] creerTypes(String fileName) {
         * Type[] tab = new Type[15];
         * for (int i = 0; i < 15; i++) {
         * tab[i] = new Type(Type.listeTypes[i]);
         * tab[i].initCoeff(fileName);
         * }
         * return tab;
         * }
         * 
         * public void afficherTab() {
         * StringBuilder s = new StringBuilder();
         * s.append(this.nom).append(" :");
         * for (double d : this.tabCoeffEfficacite) {
         * s.append(" ").append(d).append(",");
         * }
         * System.out.println(s);
         * }
         */
}
