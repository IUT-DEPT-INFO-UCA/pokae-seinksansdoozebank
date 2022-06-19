package gestionCombat;

import gestionCombat.IAElaboreePerso.Strategy;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import interfaces.IPokemon;

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
        
        
        //Joueur d1 = new Joueur();
		//Joueur d2 = new Joueur();
		/*d1.enregistrerRanch();
		d2.enregistrerRanch();*/
		//Joueur d2 = new Joueur("Koxy","pdm","Thomas");
		//d1.getEquipe()[0] =  new Pokemon (Pokedex.getEspeceParNom("Fantominus"));
        //d1.showTeam();
		//IARandom d1 = new IARandom();
		//IARandom d2 = new IARandom();
		//IAMinMax d2 = new IAMinMax();
        //d2.showTeam();
        //Combat combat = new Combat(d1,d2);
        /*for (int i=0;i<5;i++) {
        	d1.getEquipe()[i].getStat().setPV(0);
        	d2.getEquipe()[i].getStat().setPV(0);
        }*
        d1.showTeam();
        d2.showTeam();*/
		/*
        Joueur d1 = new Joueur();
		IARandom d2 = new IARandom();
        Combat combat = new Combat(d1,d2);
        System.out.println(combat.getVainqueur().getNom() + " a gagne le combat !");*/
        /*
        System.out.println("Choisir un nb entre 1 et 6");
        System.out.println(d1.getInputInt(1, 6));
        System.out.println("Choisir un nb entre 1 et 4");
        System.out.println(d1.getInputInt(1, 4));*/
		
		
		
		Joueur d1 = new Joueur(false);
		IAElaboreePerso d2 = new IAElaboreePerso(Strategy.AGRESSIVE,false);
		d2.showTeam();
		//IARandom d2 = new IARandom(false);
		//TODO gerer le changement de pokemon
        Combat combat = new Combat(d1,d2);
        System.out.println(combat.getVainqueur().getNom() + " a gagne le combat !");
		
		
		/*
		IARandom d1 = new IARandom(false);
		IAMinMax d2 = new IAMinMax(false);
		Pokemon pok1 = (Pokemon) d1.getPokemon(5);
		Pokemon pok2 = (Pokemon) d2.getPokemon(5);
        for (int i=0;i<5;i++) {
        	d1.getEquipe()[i].getStat().setPV(0);
        	d2.getEquipe()[i].getStat().setPV(0);
        }
        pok1.getStat().setPV(1);
        pok2.getStat().setPV(0);
		
		EtatDuJeu edj = new EtatDuJeu(d1, d2, pok1, pok2);
		edj.show();
		*/
        
		/*
		edj.getPokemon1().getStat().setPV(10);
		System.out.println(edj.getPokemon1());
		TestGestionCombat.test(edj.getPokemon1());
		System.out.println(edj.getPokemon1());
		*/
		/*
		EtatDuJeu copy = new EtatDuJeu(edj);
		copy.show();
		*/
        
		/*
		if(!edj.estTerminal()) {
			double[] pi = d2.getPi(edj, pok1.getCapacitesUtilisables()[0], pok2.getCapacitesUtilisables()[0]);
		    for (double p : pi) {
		    	System.out.println(p);
		    }
			EtatDuJeu[] xi = d2.getXi(edj, pok1.getCapacitesUtilisables()[0], pok2.getCapacitesUtilisables()[0]);
			for (EtatDuJeu e : xi) {
		    	e.show();
		    }
		}else {
			System.out.println("l'etat est terminal");
		}

		edj.show();
		*/
	}
}
