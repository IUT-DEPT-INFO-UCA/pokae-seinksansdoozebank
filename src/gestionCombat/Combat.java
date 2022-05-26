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
	private static final String msgChoixAction = "Entrez 1 pour attaquer ou 2 pour changer de pokemon : ";
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
			this.executerActions();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
		System.out.println("\n\n=============== Début du tour "+this.nbTours+" ===============\n");
		//recuperation du choix d'action du dresseur1
		System.out.println(this.dresseur1.getNom());
		System.out.println("\t"+this.pokemon1.getNom()+" est sur le terrain. Que voulez vous faire ?");
		System.out.println("\t"+msgChoixAction);
		int input = InputViaScanner.getInputInt(1, 2);
		if(input == 2){
			this.pokemon1 = this.dresseur1.choisitCombattantContre(this.pokemon2);
			this.dresseur1.setActionChoisie(null);
		}else{
			this.dresseur1.choisitAttaque(this.pokemon1,this.pokemon2);
		}
		//recuperation du choix d'action du dresseur2
		System.out.println("");
		System.out.println(this.dresseur2.getNom());
		System.out.println("\t"+this.pokemon2.getNom()+" est sur le terrain. Que voulez vous faire ?");
		System.out.println("\t"+msgChoixAction);
		input = InputViaScanner.getInputInt(1, 2);
		if(input == 2){
			this.pokemon2 = this.dresseur2.choisitCombattantContre(this.pokemon1);
			this.dresseur2.setActionChoisie(null);
		}else{
			this.dresseur2.choisitAttaque(this.pokemon2,this.pokemon1);
		}
		System.out.println("");
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
		//d1 echange
		if (this.dresseur1.getActionChoisie()==null){ //d1 echange
				dresseur1.echangeCombattant();
				pokemon1=dresseur1.getPokemon();
			// et d2 echange
			if(this.dresseur2.getActionChoisie()==null){
				dresseur2.echangeCombattant();
				pokemon2=dresseur2.getPokemon();
			// et d2 attaque
			}else{
				pokemon1.subitAttaqueDe(pokemon2, dresseur2.getActionChoisie());
				if(dresseur1.getPokemon().estEvanoui()){
					if(!this.estTermine()){
						if(this.pokemon2.aChangeNiveau()) {
							this.dresseur2.enseigne(this.pokemon2, this.pokemon2.getCapacitesApprises());
						}
						pokemon1=dresseur1.choisitCombattantContre(pokemon2); 
						dresseur1.setPokemon(pokemon1);
					}
				}
			}
		}else{
			//d2 echange puis d1 attaque
			if(this.dresseur2.getActionChoisie()==null){ //d2 echange
				dresseur2.echangeCombattant();
				pokemon2=dresseur2.getPokemon();
				pokemon2.subitAttaqueDe(pokemon1, dresseur1.getActionChoisie());
				if(dresseur2.getPokemon().estEvanoui()){
					if(!this.estTermine()){
						if(this.pokemon1.aChangeNiveau()) {
							this.dresseur1.enseigne(this.pokemon1, this.pokemon1.getCapacitesApprises());
						}
						pokemon2=dresseur2.choisitCombattantContre(pokemon1);
						dresseur2.setPokemon(pokemon2);
					}
				}
			//d1 et d2 attaquent
			}else{ 
				//d1 attaque avant
				if(((Pokemon) pokemon1).estPlusRapideQue((Pokemon) pokemon2)){
					pokemon2.subitAttaqueDe(pokemon1, dresseur1.getActionChoisie());
					//dresseur1.attaquer(dresseur2);
					if(pokemon2.estEvanoui()){
						try {
							pokemon1.gagneExperienceDe(pokemon2);
						} catch (IOException | ParseException e) {
							e.printStackTrace();
						}
						if(!this.estTermine()){
							if(pokemon1.aChangeNiveau()) {
								dresseur1.enseigne(this.pokemon1, this.pokemon1.getCapacitesApprises());
							}
							pokemon2=dresseur2.choisitCombattantContre(pokemon1);
							dresseur2.setPokemon(pokemon2);
						}
					}else{
						pokemon1.subitAttaqueDe(pokemon2, dresseur2.getActionChoisie());
						if(pokemon1.estEvanoui()){
							try {
								pokemon2.gagneExperienceDe(pokemon1);
							} catch (IOException | ParseException e) {
								e.printStackTrace();
							}
							if(!this.estTermine()){
								if(this.pokemon2.aChangeNiveau()) {
									dresseur2.enseigne(this.pokemon2, this.pokemon2.getCapacitesApprises());
								}
								pokemon1=dresseur1.choisitCombattantContre(pokemon2);
								dresseur1.setPokemon(pokemon1);
							}
						}
					}

				//d2 attaque avant
				}else{
					pokemon1.subitAttaqueDe(pokemon2, dresseur2.getActionChoisie());
					if(pokemon1.estEvanoui()){
						try {
							pokemon2.gagneExperienceDe(pokemon1);
						} catch (IOException | ParseException e) {
							e.printStackTrace();
						}
						if(!this.estTermine()){
							if(this.pokemon2.aChangeNiveau()) {
								dresseur2.enseigne(this.pokemon2, this.pokemon2.getCapacitesApprises());
							}
							pokemon1=dresseur1.choisitCombattantContre(pokemon2);
							dresseur1.setPokemon(pokemon1);
						}
					}else{
						pokemon2.subitAttaqueDe(pokemon1, dresseur1.getActionChoisie());
						if(pokemon2.estEvanoui()){
								try {
									pokemon1.gagneExperienceDe(pokemon2);
								} catch (IOException | ParseException e) {
									e.printStackTrace();
								}
							if(!this.estTermine()){
								if(pokemon1.aChangeNiveau()) {
									dresseur1.enseigne(this.pokemon1, this.pokemon1.getCapacitesApprises());
								}
								pokemon2=dresseur2.choisitCombattantContre(pokemon1);
								dresseur2.setPokemon(pokemon2);
							}
						}
					}
				}
			}
		}
	}
}
