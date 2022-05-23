package gestionPokemon;

import java.io.FileNotFoundException;
import java.util.Arrays;

import gestionCombat.Dresseur;
//import java.util.Arrays;

public class TestGestionPokemon {

        public static void main(String[] args) throws FileNotFoundException {

            Pokedex.initialiser();

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
/*
            ///////////////////////  Pokemon /////////////////////////////
            /*Pokemon pokemon=new Pokemon("Bulbi",Pokedex.listeEspece[1]);
            System.out.println(pokemon);
            for(int i=0; i<16;i++){
                    pokemon.augmenterNiveau();
            }

            System.out.println(pokemon); // Print du pokemon
            System.out.println(Arrays.toString(pokemon.espPoke.getCapSet())); //Renvoie les capacités de l'espèce du pokemon
            pokemon.remplaceCapacite(0,Pokedex.listeCapacite[31]);
            System.out.println(pokemon); // Print du pokemon


            System.out.println(pokemon); // Print du pokemon
            System.out.println(Arrays.toString(pokemon.espPoke.getCapSet()));*/

            // Initialisation du pokedex et du pokemon Test.
            /*Pokemon pokeTest = new Pokemon(Pokedex.listeEspece[3]);
            System.out.println(pokeTest);
            System.out.println(pokeTest.getCapacitesApprises()[1]);*/
<<<<<<< Updated upstream
            Pokemon pokeTest = new Pokemon(Pokedex.listeEspece[3]);
            System.out.println(pokeTest);
            System.out.println(Arrays.toString(pokeTest.getEspece().getCapSet()));
            /*Pokemon out=new Pokemon("maxValue",Pokedex.listeEspece[1]);
            System.out.println( out.espPoke.getNom());
            System.out.println(Pokedex.listeEspece[1].getNom());*/
=======
            /*Pokemon pokeTest = new Pokemon(Pokedex.listeEspece[3]);
            System.out.println(pokeTest);
            System.out.println(Arrays.toString(pokeTest.getEspece().getCapSet()));*/
            /*Pokemon out=new Pokemon("maxValue",Pokedex.listeEspece[1]);
            System.out.println( out.espPoke.getNom());
            System.out.println(Pokedex.listeEspece[1].getNom());*/
            
            /*System.out.println("Creation de carapuce...");
    		Pokemon carapuce = new Pokemon (Pokedex.getEspeceParNom("Carapuce"));
    		//System.out.println(carapuce);
    		System.out.println("Carapuce est cree !");
    		while(carapuce.getNiveau()<10) {
    			carapuce.augmenterNiveau();
    		}*/
            
            Dresseur d1 = new Dresseur("arcsti","mdp","antoine");
            d1.showTeam();
            Dresseur d2 = new Dresseur("firlod","1234","clement");
            d2.showTeam();
            System.out.println(d1.getPokemon());
            d1.getPokemon().espPoke.showCapSet();
            int n = d1.getPokemon().getNiveau();
            while(d1.getPokemon().getNiveau()<30) {
            	boolean aChangeNiveau = d1.getPokemon().aChangeNiveau();
				//System.out.println("aChangeNiveau = "+aChangeNiveau);
				if(aChangeNiveau) {
					//System.out.println("changement de niveau");
					d1.enseigne(d1.getPokemon(), d1.getPokemon().getCapacitesApprises());
				}
            	d1.getPokemon().gagneExperienceDe(d2.getEquipe()[0]);
            	System.out.println("");
            }
>>>>>>> Stashed changes
        }

}
