package gestionCombat;

import java.util.Scanner;

import interfaces.ICapacite;
import interfaces.IPokemon;

/**
 * Cette classe abstraite permet de récupérer les entrées des joueurs en gérant
 * tous les Scanner dans la même classe
 * 
 */
public abstract class InputViaScanner {
	
    /**
     * Elle prend une entrée entière de l'utilisateur et la renvoie dés qu'elle se
     * situe entre les deux entiers donnés
     * 
     * @param inf la valeur minimale que l'utilisateur peut entrer
     * @param sup la valeur maximale que l'utilisateur peut entrer
     * @return La méthode l'entier entré.
     */
    @SuppressWarnings("resource")
	public static int getInputInt(int inf, int sup) {
        int rep = 0;
        Scanner reader = new Scanner(System.in);
        boolean intValid = false;
        while (!intValid) {
            //rep = Integer.parseInt(reader.next());
            rep = reader.nextInt();
            if (rep >= inf && rep <= sup) {
                intValid = true;
            }
        }
        return rep;
    }


    /**
     * Récupère une entrée de l'utilisateur sous forme de chaine de caractère
     * @return La chaine entrée par l'utilisateur
     */
    @SuppressWarnings("resource")
    public static String getInputString(){
        Scanner reader = new Scanner(System.in);
        //String input = reader.next();
        String input = reader.nextLine();
        return input;
    }
    
    /**
     * Elle prend une entrée entière de l'utilisateur et vérifie si elle correspond
     * bien à un Pokemon non KO
     * 
     * @param inf la valeur minimale que l'utilisateur peut entrer
     * @param sup le nombre le plus élevé que l'utilisateur peut entrer
     * @param tab le ranch de Pokémon du joueur qui entre la valeur
     * @return La méthode renvoie un entier.
     */
    @SuppressWarnings("resource")
    public static int getInputIntPokemon(int inf, int sup, IPokemon[] tab) { // tab de pokemon pour ne pas pouvoir pick
                                                                             // des pokemon KO
        int rep = 0;
        Scanner reader = new Scanner(System.in);
        boolean intValid = false;
        while (!intValid) {
            /*String input = reader.next();
            rep = Integer.parseInt(input);*/
            rep = reader.nextInt();
            if ((rep == 0 && inf==0) || (rep >= inf && rep <= sup && !tab[rep - 1].estEvanoui())) {
                intValid = true;
            }
        }
        return rep;
    }

    /**
     * Il prend un tableau d'objets ICapacite et renvoie un entier qui est l'index
     * de l'objet ICapacite dans le tableau que l'utilisateur a sélectionné
     * 
     * @param inf la valeur minimale que l'utilisateur peut entrer
     * @param sup le nombre maximum que l'utilisateur peut entrer
     * @param tab le tableau d'ICapacite que le Pokemon connait
     * @return La méthode retourne un int.
     */
    @SuppressWarnings("resource")
    public static int getInputIntCapacite(int inf, int sup, ICapacite[] tab) { // tab de capacite pour ne pas pouvoir pick des capacite a 0pp
    	int rep = 0;
        Scanner reader = new Scanner(System.in);
        boolean intValid = false;
        while (!intValid) {
            /*String input = reader.next();
            rep = Integer.parseInt(input);*/
            rep = reader.nextInt();
            if (rep==0 || ( rep >= inf && rep <= sup && tab[rep - 1].getPP() != 0)) {
                intValid = true;
            }
        }
        return rep;
    }

}
