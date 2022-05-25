package gestionCombat;

import java.util.Scanner;

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
}
