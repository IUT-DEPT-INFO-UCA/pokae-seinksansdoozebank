package gestionCombat;



public class Campagne {
	private static String[] listeNoms= {"Violette","Lino","Cornélia","Amaro","Lem","Valériane","Astera","Urup","Pierre","Ondine","Major Bob","Erika","Koga","Morgane","Auguste","Blue"};
    public Dresseur joueur;
    public int nbVictoires;

    

    public void seConnecter() {

    }

    public void sInscrire() {

    }

    public static void continuer(Dresseur player) {
    	int input=0;
    	while(true) {
        	System.out.println("\nLancer le combat suivant : 1 | Afficher les stats : 2 | Quitter : 3");
        	input = InputViaScanner.getInputInt(1, 3);
        	if(input==1) { //TODO virer le for
        		Campagne.lancerNouveauCombat(player);
        	}else if(input==2){
        		Campagne.afficherStat(player);
        	}else{
        		System.exit(0);
        	}
    	}
    }

    private static void lancerNouveauCombat(Dresseur player) { 
		Dresseur adv = Campagne.genererNouvelAdversaire();
	    Combat combat = new Combat(player,adv);
	    System.out.println("\n"+combat.getVainqueur().getNom() + " a gagne le combat !\n\n");
    }

    private static Dresseur genererNouvelAdversaire() {
		IARandom adv = new IARandom(listeNoms[(int) (Math.random()*listeNoms.length)]);
    	return adv;
    }

    @SuppressWarnings("unused")
    private String choisirTypeAdversaire() {
        return null;
    }

    public void sauvegarder() {

    }

    public static void afficherStat(Dresseur player) {
    	System.out.println(player.getNom()+" est niveau "+player.getNiveau() + " et son equipe est composé de :");
    	player.showTeam();
    }
}
