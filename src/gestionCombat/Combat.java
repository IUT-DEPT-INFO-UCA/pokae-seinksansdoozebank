package gestionCombat;

import java.util.Scanner;

public class Combat {
	@SuppressWarnings("unused")
	private int nbTours;
	private Dresseur dresseur1;
	private Dresseur dresseur2;
	private Dresseur vainqueur;
	
	public Combat(Dresseur d1, Dresseur d2) {
		this.nbTours = 0;
		this.dresseur1 = d1;
		this.dresseur2 = d2;
	}

	public Dresseur obtenirVainqueur () {
		this.initialiser();
		while (!this.estTermine()){
			this.passerAuTourSuivant();
			this.initialiserActions();
			this.executerActions();
		}
		return this.vainqueur;
	}

	private void initialiser(){
		this.nbTours = 1;
		this.dresseur1.choisirPokemon();
		this.dresseur1.setPokemon(dresseur1.getEquipe()[0]);
		this.dresseur2.choisirPokemon();
		this.dresseur2.setPokemon(dresseur2.getEquipe()[0]);
	}

	private boolean estTermine(){
		if(dresseur1.pouvoirSeBattre() && dresseur2.pouvoirSeBattre()){
			return false;
		}else if(!dresseur1.pouvoirSeBattre()){
			this.vainqueur=dresseur2;
		}else if(!dresseur2.pouvoirSeBattre()){
			this.vainqueur=dresseur1;
		}
		this.terminer();
		return true;
	}

	private void initialiserActions(){
		choisirAction(dresseur1);
		choisirAction(dresseur2);
	}

	private void choisirAction(Dresseur d){
		System.out.println("Entrez <0> pour attaquer ou <1> pour changer de pokemon : ");
		try (Scanner sc = new Scanner(System.in)) {
			int input = sc.nextInt();
			if(input == 0){
				d.choisirPokemon();
			}else{
				d.choisirAttaqueDe(d.getPokemon());
			}
		}
	}

	private void executerActions(){
		if (this.dresseur1.getActionChoisie()==null){ //d1 echange
			dresseur1.echangeCombattant();
			if(this.dresseur2.getActionChoisie()==null){ //d2 echange
				dresseur2.echangeCombattant();
			}else{ //d2 attaque
				dresseur2.attaquer(dresseur1);
				if(dresseur1.getPokemon().estEvanoui()){
					if(!this.estTermine()){
						dresseur1.choisirPokemon();
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
						dresseur2.choisirPokemon();
						dresseur2.echangeCombattant();
					}
				}
			}else{ //d2 attaque
				if(dresseur1.getPokemon().estPlusRapideQue(dresseur2.getPokemon())){
					dresseur1.attaquer(dresseur2);
					if(dresseur2.getPokemon().estEvanoui()){
						if(!this.estTermine()){
							dresseur2.choisirPokemon();
							dresseur2.echangeCombattant();
						}
					}else{
						dresseur2.attaquer(dresseur1);
					}
				}else{
					dresseur2.attaquer(dresseur1);
					if(dresseur1.getPokemon().estEvanoui()){
						if(!this.estTermine()){
							dresseur1.choisirPokemon();
							dresseur1.echangeCombattant();
						}
					}else{
						dresseur1.attaquer(dresseur2);
					}
				}
			}
		}
	}
	
	private void passerAuTourSuivant(){
		this.nbTours++;
	}

	private void terminer(){
		dresseur1.soignerEquipe();
		dresseur2.soignerEquipe();
	}

}
