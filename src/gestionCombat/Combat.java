package gestionCombat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import gestionPokemon.Pokemon;
import interfaces.IAttaque;
import interfaces.ICombat;
import interfaces.IDresseur;
import interfaces.IPokemon;
import interfaces.ITour;

public class Combat implements ICombat {
	static final String msgChoixAction = "Entrez 1 pour attaquer ou 2 pour changer de pokemon : ";
	private int nbTours;
	private Dresseur dresseur1;
	private IPokemon pokemon1;
	private Dresseur dresseur2;
	private IPokemon pokemon2;
	private List<ITour> tours = new ArrayList<ITour>();
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
		System.out.println("");
		this.pokemon2 = this.dresseur2.choisitCombattant();
		System.out.println("\nQue le combat commence !");
	}
	

	public IDresseur getDresseur1() {
		return (IDresseur) this.dresseur1;
	}
	
	public IDresseur getDresseur2() {
		return (IDresseur) this.dresseur2;
	}
	
	public ITour nouveauTour(IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2) {
		this.nbTours++;
		return new Tour(this.nbTours);
	}

	public void termine(){
		dresseur1.soigneRanch();
		dresseur2.soigneRanch();
	}
	
	///////////////////////////////////////////////////////////////
	
	public Dresseur getVainqueur () {
		this.commence();
		while (!this.estTermine()){
			this.startTour();
			tours.add( this.nouveauTour(this.pokemon1, this.dresseur1.getActionChoisie(), this.pokemon2, this.dresseur2.getActionChoisie()));
			System.out.println("");
			this.executerActions();
		}
		this.termine();
		return this.vainqueur;
	}
	
	private boolean estTermine(){
		//System.out.println(dresseur1.pouvoirSeBattre());
		//System.out.println(dresseur2.pouvoirSeBattre());
		if(dresseur1.pouvoirSeBattre() && dresseur2.pouvoirSeBattre()){
			return false;
		}else if(!dresseur1.pouvoirSeBattre()){
			this.vainqueur=dresseur2;
		}else if(!dresseur2.pouvoirSeBattre()){
			this.vainqueur=dresseur1;
		}
		return true;
	}
	
	private void startTour() {
		System.out.println("\n\n======================= Début du tour "+this.nbTours+" =======================\n");
		System.out.println(dresseur1.getNom()+"\t"+this.pokemon1/*.getNom()+" "+((Pokemon)pokemon1).getPVBar()*/);
		System.out.println(dresseur2.getNom()+"\t"+this.pokemon2/*.getNom()+" "+((Pokemon)pokemon2).getPVBar()*/);
		System.out.println("");
		//recuperation du choix d'action du dresseur1
		dresseur1.selectAction(pokemon1, pokemon2);
		System.out.println("");
		//recuperation du choix d'action du dresseur2
		dresseur2.selectAction(pokemon2, pokemon1);
	}
	
	private void executerActions(){
		//d1 echange...
		if (this.dresseur1.getActionChoisie()==null){ //d1 echange
			dresseur1.echangeCombattant();
			pokemon1=dresseur1.getPokemon();
			// ...et d2 echange
			if(this.dresseur2.getActionChoisie()==null){
				dresseur2.echangeCombattant();
				pokemon2=dresseur2.getPokemon();
			// ...et d2 attaque
			}else{
				System.out.println("");
				pokemon1.subitAttaqueDe(pokemon2, dresseur2.getActionChoisie());
				this.testerPokeAMisKOPok(dresseur2, pokemon2, dresseur1, pokemon1);
				this.switchIfKO(pokemon2, false);
			}
		}else{
			//d2 echange puis d1 attaque
			if(this.dresseur2.getActionChoisie()==null){ //d2 echange
				dresseur2.echangeCombattant();
				pokemon2=dresseur2.getPokemon();
				System.out.println("");
				pokemon2.subitAttaqueDe(pokemon1, dresseur1.getActionChoisie());
				this.testerPokeAMisKOPok(dresseur1, pokemon1, dresseur2, pokemon2);
				this.switchIfKO(pokemon1, false);
			//d1 et d2 attaquent
			}else{ 
				//d1 attaque avant
				if(((Pokemon) pokemon1).estPlusRapideQue((Pokemon) pokemon2)){
					pokemon2.subitAttaqueDe(pokemon1, dresseur1.getActionChoisie());
					if(!this.testerPokeAMisKOPok(dresseur1, pokemon1, dresseur2, pokemon2)) {
						this.switchIfKO(pokemon1, false);
						System.out.println("");
						pokemon1.subitAttaqueDe(pokemon2, dresseur2.getActionChoisie());
						this.testerPokeAMisKOPok(dresseur2, pokemon2, dresseur1, pokemon1);
						this.switchIfKO(pokemon2, false);
					}else {
						this.switchIfKO(pokemon1, false);
					}
				//d2 attaque avant
				}else{
					pokemon1.subitAttaqueDe(pokemon2, dresseur2.getActionChoisie());
					if(!this.testerPokeAMisKOPok(dresseur2, pokemon2, dresseur1, pokemon1)) {
						this.switchIfKO(pokemon2, false);
						System.out.println("");
						pokemon2.subitAttaqueDe(pokemon1, dresseur1.getActionChoisie());
						this.testerPokeAMisKOPok(dresseur1, pokemon1, dresseur2, pokemon2);
						this.switchIfKO(pokemon1, false);
					}else {
						this.switchIfKO(pokemon2, false);
					}
				}
			}
		}
	}
	
	public boolean testerPokeAMisKOPok(IDresseur dresseurLanceur, IPokemon lanceur, IDresseur dresseurReceveur, IPokemon receveur) {
		IPokemon oldReceveur = receveur;
		if(receveur.estEvanoui()){
    		System.out.println(receveur.getNom()+" est KO !");
			try {
				lanceur.gagneExperienceDe(oldReceveur);
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			if(lanceur.aChangeNiveau()) {
				dresseurReceveur.enseigne(lanceur, lanceur.getCapacitesApprises());
			}
			this.switchIfKO(receveur, true);
			return true;
		}
		return false;
	}
	
	public void switchIfKO(IPokemon pokKO, boolean alreadyPrinted) {
		if(pokKO.estEvanoui()) {
			if(!alreadyPrinted) {
	    		System.out.println(pokKO.getNom()+" est KO !");
			}
			if(!this.estTermine()){ //si le combat n'est pas terminé, d2 envoie un autre pokemon
				if(pokKO.equals(pokemon1)) {
					pokemon1=dresseur1.choisitCombattantContre(pokemon2);
					((Dresseur) dresseur1).setPokemon(pokemon1);
				}
				if(pokKO.equals(pokemon2)) {
					pokemon2=dresseur2.choisitCombattantContre(pokemon1);
					((Dresseur) dresseur2).setPokemon(pokemon2);
				}
			}
		}
	}
	
}
