package gestionCombat;

import gestionPokemon.Pokedex;

public class TestGestionCombat {

	public static void main(String[] args) {
		Pokedex.initialiser();
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
        
        
        Joueur d1 = new Joueur("arcsti","mdp","Antoine");
		Joueur d2 = new Joueur("firelods","123","Clement");
		d1.enregistrerRanch();
		d2.enregistrerRanch();
		//Joueur d2 = new Joueur("Koxy","pdm","Thomas");
		//d1.getEquipe()[0] =  new Pokemon (Pokedex.getEspeceParNom("Fantominus"));
        //d1.showTeam();
		//IARandom d1 = new IARandom("Clement");
		/*
		IARandom d2 = new IARandom("Thomas");
        //d2.showTeam();
        Combat combat = new Combat(d1,d2);
        System.out.println(combat.getVainqueur().getNom() + " a gagne le combat !");*/
        /*
        System.out.println("Choisir un nb entre 1 et 6");
        System.out.println(d1.getInputInt(1, 6));
        System.out.println("Choisir un nb entre 1 et 4");
        System.out.println(d1.getInputInt(1, 4));*/
        

	}

}
