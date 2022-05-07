package gestionCombat;
import gestionPokemon.Pokemon;

public class Combat {
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
			this.choisirActions();
			this.executerActions();
		}
		return this.vainqueur;
	}

	private void initialiser(){
		this.nbTours = 1;
		this.dresseur1.choisirPokemon();
		this.dresseur1.mettrePokemonActifA(dresseur1.obtenirPokemonChoisi());
		this.dresseur2.choisirPokemon();
		this.dresseur2.mettrePokemonActifA(dresseur2.obtenirPokemonChoisi());
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

	private void choisirActions(){
		choisirActions(dresseur1);
		choisirActions(dresseur2);
	}

	private void choisirActions(Dresseur d){
		if(input = "echange"){
			d.choisirPokemon();
		}else{
			d.choisirAttaqueDe(d.obtenirPokemonActif());
		}
	}

	private void executerActions(){
		if (this.dresseur1.obtenirActionChoisie()==null){ //d1 echange
			dresseur1.mettrePokemonActifA(dresseur1.obtenirPokemonChoisi());
			if(this.dresseur2.obtenirActionChoisie()==null){ //d2 echange
				dresseur2.mettrePokemonActifA(dresseur2.obtenirPokemonChoisi());
			}else{ //d2 attaque
				dresseur2.attaquer(dresseur1);
				if(dresseur1.obtenirPokemonActif().estMort()){
					if(!this.estTermine()){
						dresseur1.choisirPokemon();
						dresseur1.mettrePokemonActifA(dresseur1.obtenirPokemonChoisi());
					}
				}
			}
		}else{ //d1 attaque
			if(this.dresseur2.obtenirActionChoisie()==null){ //d2 echange
				dresseur2.mettrePokemonActifA(dresseur2.obtenirPokemonChoisi());
				dresseur1.attaquer(dresseur2);
				if(dresseur2.obtenirPokemonActif().estMort()){
					if(!this.estTermine()){
						dresseur2.choisirPokemon();
						dresseur2.mettrePokemonActifA(dresseur2.obtenirPokemonChoisi());
					}
				}
			}else{ //d2 attaque
				if(dresseur1.obtenirPokemonActif().estPlusRapideQue(dresseur2.obtenirPokemonActif())){
					dresseur1.attaquer(dresseur2);
					if(dresseur2.obtenirPokemonActif().estMort()){
						if(!this.estTermine()){
							dresseur2.choisirPokemon();
							dresseur2.mettrePokemonActifA(dresseur2.obtenirPokemonChoisi());
						}
					}else{
						dresseur2.attaquer(dresseur1);
					}
				}else{
					dresseur2.attaquer(dresseur1);
					if(dresseur1.obtenirPokemonActif().estMort()){
						if(!this.estTermine()){
							dresseur1.choisirPokemon();
							dresseur1.mettrePokemonActifA(dresseur1.obtenirPokemonChoisi());
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
