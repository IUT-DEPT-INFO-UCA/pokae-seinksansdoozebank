package gestionCombat;

import java.util.Scanner;

import interfaces.ICapacite;
import interfaces.IPokemon;

public abstract class InputViaScanner {

	public static int getInputInt(int inf, int sup) {
		int rep=0;
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		boolean intValid = false;
        while(!intValid) {
            String input = reader.next();
        	rep = Integer.parseInt(input);
            if (rep >= inf && rep <= sup) {
                intValid = true;
            }
        }
        return rep;
	}
	
	public static int getInputIntPokemon(int inf, int sup, IPokemon[] tab) { //tab de pokemon pour ne pas pouvoir pick des pokemon KO
		int rep=0;
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		boolean intValid = false;
        while(!intValid) {
            String input = reader.next();
        	rep = Integer.parseInt(input);
            if (rep >= inf && rep <= sup && !tab[rep-1].estEvanoui()) {
                intValid = true;
            }
        }
        return rep;
	}
	
	public static int getInputIntCapacite(int inf, int sup, ICapacite[] tab) { //tab de capacite pour ne pas pouvoir pick des capacite a 0pp
		int rep=0;
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		boolean intValid = false;
        while(!intValid) {
            String input = reader.next();
        	rep = Integer.parseInt(input);
            if (rep >= inf && rep <= sup && tab[rep-1].getPP()!=0) {
                intValid = true;
            }
        }
        return rep;
	}
	
}
