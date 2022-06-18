package gestionPokemon;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		Pokedex.initialiser();
		System.out.println("Voici le premier pokemon et ses stats :");
		Pokemon carapuce = new Pokemon (Pokedex.getEspeceParNom("Carapuce"));
		System.out.println(carapuce);
		System.out.println("\nVoici le deuxieme pokemon et ses stats :");
		Pokemon sulfura = new Pokemon(Pokedex.getEspeceParNom("Sulfura"));
		System.out.println(sulfura);
		
		System.out.println("\n\nChoix des attaques : ");
		carapuce.setAttaqueChoisie((Capacite)carapuce.getCapacitesApprises()[0]);
		System.out.println(carapuce.getNom()+" a choisi d'utiliser la capacite "+carapuce.getAttaqueChoisie().getNom());
		sulfura.setAttaqueChoisie((Capacite)sulfura.getCapacitesApprises()[0]);
		System.out.println(carapuce.getNom()+" a choisi d'utiliser la capacite "+sulfura.getAttaqueChoisie().getNom());
		System.out.println("\n\nOn lance un comat fictif !");
		
		int nbTours =10;
		while((!carapuce.estEvanoui() && !sulfura.estEvanoui()) && nbTours>0) {
			System.out.println("\nLe plus rapide attaque en premier");
			if(carapuce.estPlusRapideQue(sulfura)){
				sulfura.subitAttaqueDe(carapuce, carapuce.getAttaqueChoisie());
				if(sulfura.estEvanoui()){
						System.out.println("\n\n"+sulfura.getNom()+" a perdu");
				}else{
					carapuce.subitAttaqueDe(sulfura, sulfura.getAttaqueChoisie());
				}
			}else{
				carapuce.subitAttaqueDe(sulfura, sulfura.getAttaqueChoisie());
				if(carapuce.estEvanoui()){
					System.out.println("\n"+carapuce.getNom()+" a perdu");
				}else{
	
					sulfura.subitAttaqueDe(carapuce, carapuce.getAttaqueChoisie());
				}
			}
			nbTours--;
		}
		System.out.println("FIN du combat");
	}

}
