package gestionPokemon;

import java.io.FileNotFoundException;
import java.util.Arrays;
//import java.util.Arrays;

public class TestGestionPokemon {

        public static void main(String[] args) throws FileNotFoundException {
                Pokedex.createListeCapacite();
                Pokedex.createListeEspece();
                ///////////////////////  Pokedex /////////////////////////////
                //Ranch
                //System.out.println(Arrays.toString(Pokedex.engendreRanch()));
                //Espece
                /*
                System.out.println("Espece n°3  = "+Pokedex.especeParId(3));
                System.out.println("Espece qui a pour nom Pikachu :"+Pokedex.especeParNom("Pikachu"));
                //Capacite
                System.out.println("Capacite n°18 = "+Pokedex.capaciteParId(18));
                System.out.println("Capacite qui a pour nom Coupe :"+Pokedex.capaciteParNom("Coupe"));

                ///////////////////////  Espece /////////////////////////////
                Espece espece = Pokedex.especeParId(4);
                espece.initCapaciteSelonNiveau();
                System.out.println(Arrays.toString(espece.getCapSet()));*/

                ///////////////////////  Pokemon /////////////////////////////
                Pokemon pokemon=new Pokemon(Pokedex.listeEspece[1]);
                System.out.println(pokemon);
                for(int i=0; i<16;i++){
                        pokemon.augmenterNiveau();
                }
                System.out.println(pokemon);
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
