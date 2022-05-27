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
		System.out.println(this.getNom()+"\tVotre equipe est composée de : ");
		for(int i=0;i<this.getEquipe().length;i++) {
			System.out.println("\t\t"+(i+1)+" - "+this.getEquipe()[i]);
		}
		System.out.println("\tEntrer le numéro du pokemon à envoyer au combat : ");
		int input=InputViaScanner.getInputInt(1, 6);
		Pokemon choosen = this.getEquipe()[input-1];
		this.setPokemon(choosen);
		return choosen;
	}

	public IPokemon choisitCombattantContre(IPokemon pok) {
		//TODO choix "annuler" pour revenir au choix des actions
		System.out.println(this.getNom()+"\tChoisissez le pokemon à envoyer au combat  : ");
		for(int i=0;i<this.getEquipe().length;i++) {
			if(!this.getEquipe()[i].estEvanoui())
				System.out.println("\t\t"+(i+1)+"- "+this.getEquipe()[i]);
			else {
				System.out.println("\t\t"+"KO "+this.getEquipe()[i]);
			}
		}
		System.out.println("\tChoississez le numéro du pokemon à envoyer au combat : ");
		int input = InputViaScanner.getInputIntPokemon(1, 6, this.getEquipe());
		while(this.getEquipe()[input-1].equals(this.getPokemon())) {
			System.out.println(this.getPokemon().getNom()+" est déjà au combat.");
			input = InputViaScanner.getInputIntPokemon(1, 6, this.getEquipe());
		}
		Pokemon choosen = this.getEquipe()[input-1];
		this.setPokemonChoisi(choosen);
		return choosen;
	}

	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		//TODO choix "annuler" pour revenir au choix des actions
		if(((Pokemon)attaquant).getNombreDeToursAvantAttaque()==0) { //dans le cas ou patience a ete utilisee
			if(((Pokemon)attaquant).getCapacitesUtilisables().length>0) {
				ICapacite[] caps = attaquant.getCapacitesApprises();
				for(int i=0; i<caps.length; i++) {
					System.out.println("\t\t"+(i+1)+"- "+caps[i]/*+" PP : "+caps[i].getPP()+"/"+((Capacite)caps[i]).getPPBase()*/);
				}
				System.out.println("\tChoississez le numéro de l'attaque à utiliser : ");
				int input = InputViaScanner.getInputIntCapacite(1, attaquant.getCapacitesApprises().length,attaquant.getCapacitesApprises());
				this.setActionChoisie((Capacite) ((Pokemon)attaquant).getCapacitesApprises()[input-1]);
			}else {//utilisation de Lutte si aucune capacite n'est dispo
				try {
					this.setActionChoisie( Pokedex.createCapacite(((Capacite)Pokedex.getCapaciteStatic("Lutte")).id));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}else {//utilisation de patience
        	((Pokemon) attaquant).updateNombreDeToursAvantAttaque();//on decremente la duree avant la fin de Patience
        	if(((Pokemon)attaquant).getNombreDeToursAvantAttaque()==0) {//si Patiece est prete
        		((Pokemon)attaquant).setNombreDeToursAvantAttaque(-1); //on met le nb de tour a -1 pour que dans calculDommage() le nb nne soit pas remis a 2
        	}
			
		}
		//System.out.println(attaquant.getNom()+" va utiliser "+this.actionChoisie);
		((Pokemon) attaquant).setAttaqueChoisie(this.getActionChoisie());
		return this.getActionChoisie();
		
	}

	@Override
	public void selectAction(IPokemon p, IPokemon pAdv) {
		System.out.println(this.getNom()+"\t"+p.getNom()+" est sur le terrain. Que voulez vous faire ?");
		//System.out.println("nb tours avant attaque : "+((Pokemon)p).getNombreDeToursAvantAttaque());
		System.out.println("\t"+Combat.msgChoixAction);
		int input = InputViaScanner.getInputInt(1, 2);
		if(input == 1){
			this.choisitAttaque(p,pAdv);
		}else{
			p = this.choisitCombattantContre(pAdv);
			this.setActionChoisie(null);
		}
		
	}

}
