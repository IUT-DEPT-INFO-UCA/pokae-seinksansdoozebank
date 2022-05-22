package gestionPokemon;

public class Main {

	public static void main(String[] args) {
		Pokedex.initialiser();
		System.out.println("Voici le premier pokemon et ses stats :");
		Pokemon carapuce = new Pokemon (Pokedex.getEspeceParNom("Carapuce"));
		System.out.println(carapuce);
		System.out.println("Voici le deuxieme pokemon et ses stats :");
		Pokemon sulfura = new Pokemon(Pokedex.getEspeceParNom("Sulfura"));
		System.out.println(sulfura);
		Capacite capaCarapuce = new Capacite((Capacite)carapuce.getCapacitesApprises()[1]);
	}

}
