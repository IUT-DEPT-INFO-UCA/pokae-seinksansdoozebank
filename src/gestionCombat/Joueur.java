package gestionCombat;

import java.io.FileNotFoundException;

import gestionPokemon.Capacite;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IPokemon;

public class Joueur extends Dresseur {
	public Joueur(String id, String mdp) {
		super(id, mdp);
	}

	public Joueur(String id, String mdp, String nom) {
		super(id, mdp, nom);
	}
	


	public IPokemon choisitCombattant() {
		System.out.println(this.getNom()+"\n\tChoisissez le pokemon à envoyer au combat : ");
		for(int i=0;i<this.getEquipe().length;i++) {
			System.out.println("\t\t"+(i+1)+" - "+this.getEquipe()[i]);
		}
		System.out.println("\tEntrer le numéro du pokemon choisi : ");
		int input=InputViaScanner.getInputInt(1, 6);
		Pokemon choosen = this.getEquipe()[input-1];
		this.setPokemon(choosen);
		return choosen;
	}

	public IPokemon choisitCombattantContre(IPokemon pok) {
		//TODO choix "annuler" pour revenir au choix des actions
		System.out.println(this.getNom()+"\n\tChoisissez le pokemon à envoyer au combat : ");
		for(int i=0;i<this.getEquipe().length;i++) {
			if(!this.getEquipe()[i].estEvanoui())
				System.out.println("\t\t"+(i+1)+"- "+this.getEquipe()[i]);
			else {
				System.out.println("\t\t"+"KO "+this.getEquipe()[i]);
			}
		}
		System.out.println("\tChoississez le numéro du pokemon à envoyer au combat : ");
		int input = InputViaScanner.getInputIntPokemon(1, 6, this.getEquipe());
		Pokemon choosen = this.getEquipe()[input-1];
		this.setPokemonChoisi(choosen);
		return choosen;
	}

	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		//TODO choix "annuler" pour revenir au choix des actions
		if(attaquant.getCapacitesApprises().length>0) { //TODO ajouter le test des PP a 0 pour utiliser lutte
			ICapacite[] caps = attaquant.getCapacitesApprises();
			for(int i=0; i<caps.length; i++) {
				System.out.println("\t\t"+(i+1)+"- "+caps[i]+" PP : "+caps[i].getPP()+"/"+((Capacite)caps[i]).getPPBase());
			}
			System.out.println("\t\tChoississez le numéro de l'attaque à utiliser : ");
			int input = InputViaScanner.getInputIntCapacite(1, attaquant.getCapacitesApprises().length,attaquant.getCapacitesApprises());
			this.setActionChoisie((Capacite) ((Pokemon)attaquant).getCapacitesApprises()[input-1]);
		}else {
			//utilisation de Lutte si aucune capacite n'est dispo
			try {
				this.setActionChoisie( Pokedex.createCapacite(((Capacite)Pokedex.getCapaciteStatic("Lutte")).id));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(attaquant.getNom()+" va utiliser "+this.actionChoisie);
		((Pokemon) attaquant).setAttaqueChoisie(this.getActionChoisie());
		return this.getActionChoisie();
		
	}

	@Override
	public void selectAction(IPokemon p, IPokemon pAdv) {
		System.out.println(this.getNom());
		System.out.println("\t"+p.getNom()+" est sur le terrain. Que voulez vous faire ?");
		System.out.println("\t"+Combat.msgChoixAction);
		int input = InputViaScanner.getInputInt(1, 2);
		if(input == 2){
			p = this.choisitCombattantContre(pAdv);
			this.setActionChoisie(null);
		}else{
			this.choisitAttaque(p,pAdv);
		}
		
	}

}
