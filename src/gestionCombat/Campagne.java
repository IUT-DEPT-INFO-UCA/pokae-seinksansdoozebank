package gestionCombat;


/**
 * Objet modélisant la campagne d'un dresseur, il lui permet de lancer un combat pour entrainer ses pokemon ou de consulter ses stats
 */
public class Campagne {
	private static String strMenuCampagne = 
			"\nMenu de la campagne : \n"
			+ "1 - Lancer le combat suivant\n"
			+ "2 - Afficher les statistiques\n"
			+ "3 - Quitter";
	/*private static String[] listeNoms= {"Violette","Lino","Cornélia","Amaro","Lem","Valériane","Astera",
			"Urup","Pierre","Ondine","Major Bob","Erika","Koga","Morgane","Auguste","Blue"};
    */
	/**
	 * Joueur de la campagne
	 */
	public Dresseur joueur;
	/**
	 * Nombre de victoire dans la campagne
	 */
    public int nbVictoires;


    /**
     * Il demande à l'utilisateur un identifiant et renvoie un nouveau joueur avec cet identifiant
     *
     * @return Une nouvelle instance de la classe Joueur.
     */
    public static Joueur seConnecter() {
        System.out.println("Identifiant : ");
        String id=InputViaScanner.getInputString();
        return new Joueur(id);
    }

    /**
     * Il demande à l'utilisateur un identifiant, un mot de passe et un nom, et renvoie un nouveau joueur avec ces valeurs
     *
     * @return Une nouvelle instance de la classe Joueur.
     */
    public static Joueur sInscrire() {
		System.out.println("Identifiant : ");
		String id=InputViaScanner.getInputString();
		System.out.println("Mot de passe : ");
		String mdp=InputViaScanner.getInputString();
		System.out.println("Nom du dresseur : ");
		String nom=InputViaScanner.getInputString();
		return  new Joueur(id, mdp, nom);
    }


    /**
     * La fonction `continuer` est une boucle qui affiche le menu du jeu et permet au joueur de choisir entre trois options
     * :
     *
     * 1. Commencez une nouvelle bataille
     * 2. Afficher les statistiques du joueur
     * 3. Quittez le jeu
     *
     * La fonction `continuer` est appelée dans la fonction principale du jeu:
     *
     * @param player Le joueur
     */
    public static void continuer(Dresseur player) {
    	int input=0;
    	while(true) {
        	System.out.println(Campagne.strMenuCampagne);
        	player.enregistrerRanch();
        	input = InputViaScanner.getInputInt(1, 3);
        	if(input==1) {
        		Campagne.lancerNouveauCombat(player);
        	}else if(input==2){
        		player.afficherStat();
        	}else{
        		System.exit(0);
        	}
    	}
    }

    /**
     * Lancement d'un nouveau combat du joueur player contre un adversaire généré dans cette méthode
     * @param player le joueur qui joue la campagne
     */
    private static void lancerNouveauCombat(Dresseur player) { 
		Dresseur adv = Campagne.genererNouvelAdversaire();
	    Combat combat = new Combat(player,adv);
	    System.out.println("\n"+combat.getVainqueur().getNom() + " a gagne le combat !\n\n");
    }
    
    /**
     * Génère un nouvel adversaire pour un combat de la campagne
     * @return le dresseur généré
     */
    private static Dresseur genererNouvelAdversaire() {
		IARandom adv = new IARandom();
    	return adv;
    }

    @SuppressWarnings("unused")
	private void choisirTypeAdversaire() {
    }

}
