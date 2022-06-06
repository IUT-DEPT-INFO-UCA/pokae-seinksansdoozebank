package gestionCombat;

import gestionPokemon.Pokedex;

/**
 * Classe de lancement du jeu
 */
public class Main {

	/**
	 * Méthode principale de l'application
	 * 
	 * @param args les paramètres du main
	 */
	public static void main(String[] args) {
		Pokedex.initialiser();
		System.out.println("Continuer une campagne : 1 | Démarrer un tournoi 2");
		int input1 = InputViaScanner.getInputInt(1, 2);
		if (input1 == 1) {
			System.out.println("Se connecter : 1 | S'inscrire : 2");
			int input2 = InputViaScanner.getInputInt(1, 2);
			Joueur player;
			if (input2 == 1) {
				player = Campagne.seConnecter();
			} else {
				player = Campagne.sInscrire();
			}
			Campagne.continuer(player);
		} else {
			System.out.println("Fonctionnalité indisponible :)");
			System.exit(0);
		}
	}

}
