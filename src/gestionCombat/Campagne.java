package gestionCombat;


/**
 * Objet modélisant la campagne d'un dresseur, il lui permet de lancer un combat pour entrainer ses pokemon ou de consulter ses stats
 */
public class Campagne {
	/**
	 * Une chaîne constante stockant le message à écrire dans les logs quand un utilsateur commence un combat
	 */
	private static String strLogStartCombat = "a commencé un combat.";
	/**
	 * Une chaîne constante stockant le message à écrire dans les logs quand un utilsateur finit un combat
	 */
	private static String strLogEndCombat = "a fini son combat.";
	/**
	 * Une chaîne constante stockant le message à écrire dans les logs quand un utilsateur consulte ses stats
	 */
	private static String strLogStats = "a consulté ses statistiques.";
	/**
	 * Une chaîne constante stockant le message à écrire dans les logs quand un utilsateur quitte l'application
	 */
	private static String strLogExit = "a quitté l'application.";

	/**
	 * Une chaîne constante stockant le message annonçant au l'utilisateurle menu de la campagne
	 */
	private static String strMenuCampagne =
			"""

					Menu de la campagne :\s
					1 - Lancer le combat suivant
					2 - Afficher les statistiques
					3 - Quitter""";
	
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
     * La fonction `continuer` est appelée dans la fonction principale du jeu
     *
     * @param player Le joueur
     */
    public static void continuer(Dresseur player) {
    	int input;
    	while(true) {
        	System.out.println(Campagne.strMenuCampagne);
        	player.enregistrerRanch();
        	input = InputViaScanner.getInputInt(1, 3);
        	if(input==1) {
        		Combat.addLog(strLogStartCombat);
        		Campagne.lancerNouveauCombat(player);
        		Combat.addLog(strLogEndCombat);
        	}else if(input==2){
        		player.afficherStat();
        		Combat.addLog(strLogStats);
        	}else{
        		Combat.addLog(strLogExit);
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
		return new IARandom();
    }
}
