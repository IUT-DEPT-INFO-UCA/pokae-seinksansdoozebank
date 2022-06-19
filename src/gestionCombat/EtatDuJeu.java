package gestionCombat;

import gestionPokemon.Pokemon;
import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IPokemon;

/**
 * La classe représentant l'état du jeu
 */
public class EtatDuJeu implements Cloneable{
	private IDresseur dresseur1;
	private IDresseur dresseur2;
	private IPokemon pokemon1;
	private IPokemon pokemon2;


	/**
	 * Constructeur de la classe EtatDuJeu
	 * @param dresseur1 Le premier dresseur
	 * @param dresseur2 Le deuxième dresseur
	 * @param pokemon1 Le premier pokemon
	 * @param pokemon2 Le deuxième pokemon
	 */
	public EtatDuJeu(IDresseur dresseur1,IDresseur dresseur2,IPokemon pokemon1,IPokemon pokemon2) {
		this.dresseur1=dresseur1;
		this.dresseur2=dresseur2;
		this.pokemon1=pokemon1;
		this.pokemon2=pokemon2;
		((Dresseur)dresseur1).setPokemon(pokemon1);
		((Dresseur)dresseur2).setPokemon(pokemon2);
	}

	/**
	 * Constructeur de la classe EtatDuJeu prenant en paramètre un EtatDuJeu
	 * @param edj Un EtatDuJeu
	 */
	public EtatDuJeu(EtatDuJeu edj) {
		/*
		String nomAtq1 = atq1!=null?atq1.getClass().getSimpleName():"null";
		String nomAtq2 = atq2!=null?atq2.getClass().getSimpleName():"null";
		System.out.println("########## Creation d'un nouvel etat : "+nomAtq1+" / "+nomAtq2+" ##########");
		*/
		System.out.println("########## Creation d'un nouvel etat ##########");
		this.dresseur1= ((Dresseur)edj.dresseur1).copy();
		this.dresseur2= ((Dresseur)edj.dresseur2).copy();
		this.pokemon1=((Dresseur)this.dresseur1).getPokemon();
		this.pokemon2=((Dresseur)this.dresseur2).getPokemon();
	}

	/**
	 * Il affiche l'état actuel du jeu
	 */
	public void show() {
		System.out.println("\n----------------------------------------------------------");
		System.out.println(this.dresseur1.getNom()+" : ");
		System.out.println("Pokemon actif : " + this.pokemon1);
		ICapacite[] caps = ((Pokemon)this.pokemon1).getCapacitesUtilisables();
		for (int i = 0; i < caps.length; i++) {
			System.out.println("\t\t" + (i + 1) + "- " + caps[i].getNom());
		}
		System.out.println("Equipe : ");
		for (int i = 0; i < ((Dresseur) this.dresseur1).getEquipe().length; i++) {
			if (!((Dresseur) this.dresseur1).getEquipe()[i].estEvanoui() && ((Pokemon)((Dresseur) this.dresseur1).getEquipe()[i]).echangePossible())
				System.out.println("\t\t" + (i + 1) + "- " + ((Dresseur) this.dresseur1).getEquipe()[i]);
			else if (((Dresseur) this.dresseur1).getEquipe()[i].estEvanoui()){
				System.out.println("\t\t" + "KO " + ((Dresseur) this.dresseur1).getEquipe()[i]);
			}else {
				System.out.println("\t\t" + "OF " + ((Dresseur) this.dresseur1).getEquipe()[i]+" (!)impossible à envoyer au combat");
			}
		}
		System.out.println(this.dresseur2.getNom()+" : ");
		System.out.println("Pokemon actif : " + this.pokemon2);
		ICapacite[] caps2 =((Pokemon)this.pokemon2).getCapacitesUtilisables();
		for (int i = 0; i < caps2.length; i++) {
			System.out.println("\t\t" + (i + 1) + "- " + caps2[i].getNom());
		}
		System.out.println("Equipe : ");
		for (int i = 0; i < ((Dresseur) this.dresseur2).getEquipe().length; i++) {
			if (!((Dresseur) this.dresseur2).getEquipe()[i].estEvanoui() && ((Pokemon)((Dresseur) this.dresseur2).getEquipe()[i]).echangePossible())
				System.out.println("\t\t" + (i + 1) + "- " + ((Dresseur) this.dresseur2).getEquipe()[i]);
			else if (((Dresseur) this.dresseur2).getEquipe()[i].estEvanoui()){
				System.out.println("\t\t" + "KO " + ((Dresseur) this.dresseur2).getEquipe()[i]);
			}else {
				System.out.println("\t\t" + "OF " + ((Dresseur) this.dresseur2).getEquipe()[i]+" (!)impossible à envoyer au combat");
			}
		}
		System.out.println("---------------------------------------------------------");
	}
	
	/**
	 * Il retourne le dresseur1
	 *
	 * @return Le dresseur1
	 */
	public IDresseur getDresseur1() {
		return dresseur1;
	}
	
	/**
	 * Il fixe la variable dresseur1 à la valeur du paramètre dresseur1.
	 *
	 * @param dresseur1 Le premier dresseur
	 */
	public void setDresseur1(IDresseur dresseur1) {
		this.dresseur1 = dresseur1;
	}
	
	/**
	 * Il renvoie le dresseur2.
	 *
	 * @return Le dresseur2.
	 */
	public IDresseur getDresseur2() {
		return dresseur2;
	}
	
	/**
	 * Il set la valeur de l'attribut dresseur2
	 *
	 * @param dresseur2 Le deuxième dresseur.
	 */
	public void setDresseur2(IDresseur dresseur2) {
		this.dresseur2 = dresseur2;
	}
	
	/**
	 * Il  renvoie le premier Pokémon de la bataille
	 *
	 * @return Le premier pokémon du tour.
	 */
	public IPokemon getPokemon1() {
		return pokemon1;
	}
	
	/**
	 * Il définit la valeur de la variable pokemon1 à la valeur du paramètre pokemon1
	 *
	 * @param pokemon1 Le premier pokémon du tour.
	 */
	public void setPokemon1(IPokemon pokemon1) {
		this.pokemon1 = pokemon1;
	}
	
	/**
	 * Il renvoie le deuxième Pokémon du tour
	 *
	 * @return le deuxième Pokémon du tour.
	 */
	public IPokemon getPokemon2() {
		return pokemon2;
	}
	
	/**
	 * Définit la valeur de la variable `pokemon2` à la valeur du paramètre `pokemon2`
	 *
	 * @param pokemon2 Le pokemon qui sera utilisé dans le tour
	 */
	public void setPokemon2(IPokemon pokemon2) {
		this.pokemon2 = pokemon2;
	}
	
	/**
	 * Il renvoie vrai si le tour est dans un état terminal, faux sinon
	 *
	 * @return l'état terminal du tour
	 */
	public boolean estTerminal() {
		return !((Dresseur) this.dresseur1).pouvoirSeBattre() || !((Dresseur)this.dresseur2).pouvoirSeBattre();
	}
}
