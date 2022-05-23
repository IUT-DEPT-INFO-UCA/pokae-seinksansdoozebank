package gestionCombat;

import java.util.ArrayList;
import java.util.Scanner;

import interfaces.IAttaque;
import interfaces.ICombat;
import interfaces.IDresseur;
import interfaces.IPokemon;
import interfaces.ITour;

public class Combat implements ICombat {
	private static final String msgChoixAction = "Entrez 1 pour attaquer ou 1 pour changer de pokemon : ";
	private int nbTours;
	private Dresseur dresseur1;
	private IPokemon pokemon1;
	private Dresseur dresseur2;
	private IPokemon pokemon2;
	private ArrayList<ITour> tours;
	private Dresseur vainqueur;
	
	public Combat(Dresseur d1, Dresseur d2) {
		this.nbTours = 0;
		this.dresseur1 = d1;
		this.dresseur2 = d2;
	}
	
	///////////////////// Méthodes de ICombat /////////////////////

	public void commence(){
		this.nbTours = 1;
		this.pokemon1 =this.dresseur1.choisitCombattant();
		this.dresseur1.setPokemon(dresseur1.getEquipe()[0]);
		this.pokemon2 = this.dresseur2.choisitCombattant();
		this.dresseur2.setPokemon(dresseur2.getEquipe()[0]);
	}
	

	public IDresseur getDresseur1() {
		return (IDresseur) this.dresseur1;
	}
	
	public IDresseur getDresseur2() {
		return (IDresseur) this.dresseur2;
	}
	
	public ITour nouveauTour(IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2) {
		this.nbTours++;
		//recuperation du choix d'action du dresseur1
		System.out.println(msgChoixAction);
		try (Scanner sc = new Scanner(System.in)) {
			int input = sc.nextInt();
			if(input == 0){
				this.pokemon1 =this.dresseur1.choisitCombattantContre(pok2);
			}else{
				this.dresseur1.choisitAttaque(pok1,pok2);
			}
		}
		//recuperation du choix d'action du dresseur2
		System.out.println(msgChoixAction);
		try (Scanner sc = new Scanner(System.in)) {
			int input = sc.nextInt();
			if(input == 0){
				this.pokemon2 =this.dresseur2.choisitCombattantContre(pok1);
			}else{
				this.dresseur2.choisitAttaque(pok2,pok1);
			}
		}
		return new Tour(this.nbTours);
	}

	public void termine(){
		dresseur2.soigneRanch();
	}
	
	///////////////////////////////////////////////////////////////
	
	public Dresseur obtenirVainqueur () {
		this.commence();
		while (!this.estTermine()){
			tours.add( this.nouveauTour(this.dresseur1.getPokemon(0), dresseur2, null, dresseur1));
			this.executerActions();
		}
		return this.vainqueur;
	}

	private boolean estTermine(){
		if(dresseur1.pouvoirSeBattre() && dresseur2.pouvoirSeBattre()){
			return false;
		}else if(!dresseur1.pouvoirSeBattre()){
			this.vainqueur=dresseur2;
		}else if(!dresseur2.pouvoirSeBattre()){
			this.vainqueur=dresseur1;
		}
		this.termine();
		return true;
	}

	/*
	private void choisirAction(Dresseur d){
		System.out.println("Entrez <0> pour attaquer ou <1> pour changer de pokemon : ");
		try (Scanner sc = new Scanner(System.in)) {
			int input = sc.nextInt();
			if(input == 0){
				this.pokemonChoisi1 = d.choisitCombattantContre()
			}else{
				d.choisirAttaqueDe(d.getPokemon());
			}
		}
	}*/

	private void executerActions(){
		if (this.dresseur1.getActionChoisie()==null){ //d1 echange
			dresseur1.echangeCombattant();
			if(this.dresseur2.getActionChoisie()==null){ //d2 echange
				dresseur2.echangeCombattant();
			}else{ //d2 attaque
				dresseur2.attaquer(dresseur1);
				if(dresseur1.getPokemon().estEvanoui()){
					if(!this.estTermine()){
						dresseur1.choisitCombattantContre(pokemon2); //TODO faire un truc bien la
						dresseur1.echangeCombattant();
					}
				}
			}
		}else{ //d1 attaque
			if(this.dresseur2.getActionChoisie()==null){ //d2 echange
				dresseur2.echangeCombattant();
				dresseur1.attaquer(dresseur2);
				if(dresseur2.getPokemon().estEvanoui()){
					if(!this.estTermine()){
						dresseur2.choisitCombattantContre(pokemon1); //TODO faire un truc bien la
						dresseur2.echangeCombattant();
					}
				}
			}else{ //d2 attaque
				if(dresseur1.getPokemon().estPlusRapideQue(dresseur2.getPokemon())){
					dresseur1.attaquer(dresseur2);
					if(dresseur2.getPokemon().estEvanoui()){
						if(!this.estTermine()){
							dresseur2.choisitCombattantContre(pokemon1); //TODO faire un truc bien la
							dresseur2.echangeCombattant();
						}
					}else{
						dresseur2.attaquer(dresseur1);
					}
				}else{
					dresseur2.attaquer(dresseur1);
					if(dresseur1.getPokemon().estEvanoui()){
						if(!this.estTermine()){
							dresseur1.choisitCombattantContre(pokemon2); //TODO faire un truc bien la
							dresseur1.echangeCombattant();
						}
					}else{
						dresseur1.attaquer(dresseur2);
					}
				}
			}
		}
	}
}
