package gestionCombat;
import gestionPokemon.Pokemon;

public class Combat {
	private Dresseur dresseur1 = new Dresseur();
	private Dresseur dresseur2 = new Dresseur();
	private Pokemon poke1 = new Pokemon("pikapute");
	private Pokemon poke2 = new Pokemon("carapute");
	
	public Combat(Dresseur d1, Dresseur d2) {
		this.dresseur1 = d1;
		this.dresseur2 = d2;
	}
}
