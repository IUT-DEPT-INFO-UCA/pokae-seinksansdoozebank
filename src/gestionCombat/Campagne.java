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
	public Dresseur joueur;
    public int nbVictoires;


    public void seConnecter(Dresseur player) {
    }

    public void sInscrire() {
    	
    }

    /**
     * Permet au joueur de continuer sa campagne tant qu'il le souhaite
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

    public void sauvegarder() {

    }
}
