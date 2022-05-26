package gestionPokemon;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import gestionCombat.Combat;
import gestionCombat.Dresseur;
import interfaces.ICapacite;
//import java.util.Arrays;

public class TestGestionPokemon {

        public static void main(String[] args) throws IOException, ParseException {
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
    		}
    		*/
            /*
            Dresseur d1 = new Dresseur("arcsti","mdp","antoine");
            d1.showTeam();
            Dresseur d2 = new Dresseur("firlod","1234","clement");
            d2.showTeam();
            d1.equipe[0]=new Pokemon(Pokedex.getEspeceParNom("Magicarpe"));
            d1.pokemon=d1.equipe[0];
            System.out.println(d1.getPokemon());
            d1.getPokemon().espPoke.showCapSet();
            
            int n = d1.getPokemon().getNiveau();
            while(d1.getPokemon().getNiveau()<=n+40) {
            	boolean aChangeNiveau = d1.getPokemon().aChangeNiveau();
				if(aChangeNiveau) {
					d1.enseigne(d1.getPokemon(), d1.getPokemon().getCapacitesApprises());
				}
            	d1.getPokemon().gagneExperienceDe(d2.getEquipe()[0]);
//            	System.out.println("");
            }
            */
            /*
            for(Espece p : Pokedex.listeEspece) {
            	if(p!=null) {
            	System.out.println(new Pokemon(p));
            	}
            	
            }
            */
            /*
            Pokemon magicarpe = new Pokemon(Pokedex.getEspeceParNom("Magicarpe"));
            System.out.println("Les capacites de magicarpe sont :");
            for (Capacite c : magicarpe.getCapacitesApprises()) {
            	System.out.println(c);
            }
            System.out.println("Celles qu'il peut apprendre sont : ");
            magicarpe.espPoke.showCapSet();
            System.out.println();
            Pokemon leviator = new Pokemon(Pokedex.getEspeceParNom("Léviator"));
            System.out.println("Les capacites de leviator sont :");
            for (Capacite c : leviator.getCapacitesApprises()) {
            	System.out.println(c);
            }
            System.out.println("Celles qu'il peut apprendre sont : ");
            leviator.espPoke.showCapSet();
            
            magicarpe.vaMuterEn(Pokedex.getEspeceParNom("Léviator"));

            System.out.println("Les capacites de magicarpe sont maintenant:");
            for (Capacite c : magicarpe.getCapacitesApprises()) {
            	System.out.println(c);
            }*/
            
            
            Dresseur d1 = new Dresseur("arcsti","mdp","antoine");
            //d1.showTeam();
            Dresseur d2 = new Dresseur("firelods","1234","clement");
            //d2.showTeam();
//            Combat combat = new Combat(d1,d2);
//            System.out.println(combat.getVainqueur().getNom());
            
            /*
            System.out.println("Choisir un nb entre 1 et 6");
            System.out.println(d1.getInputInt(1, 6));
            System.out.println("Choisir un nb entre 1 et 4");
            System.out.println(d1.getInputInt(1, 4));*/
            
            
        }

}
