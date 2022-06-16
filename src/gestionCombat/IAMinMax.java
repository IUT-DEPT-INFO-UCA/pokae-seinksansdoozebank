package gestionCombat;

import gestionPokemon.Capacite;
import gestionPokemon.Pokemon;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IPokemon;

public class IAMinMax extends Dresseur {
	
	private Object[] getProbaVictoire(EtatDuJeu edj) { 
		Object[] rep = new Object[2];
		if(edj.estTerminal()) {
			if(this.pouvoirSeBattre()) {
				rep[0]=1;
			}else {
				rep[0]=0;
			}
			rep[1] = null;
		}else {
			int maxi=0;
			IAttaque[] C1 = this.getCoupsPossibles();
			IAttaque cmax = C1[0];
			for(int i=0; i<C1.length;i++) {
				int mini=1;			
				IAttaque[] C2 = ((Dresseur) edj.getDresseur2()).getCoupsPossibles();
				for(int j=0; j<C2.length;j++) {
					//TODO On note (P_i,X_i) les états possibles ainsi que leurs probabilités à l'issue des coups choisis
					Object[] PiXi = new Object[2]; //[0]-> P(Xi)  [1]-> Xi=Etat du jeu i
					PiXi[0] = 0;//getProbaVictoire(etatDuJeu);
					PiXi[1] = edj;
							
					int val=0; //TODO val=somme_i P_i P(X_i)[0]
					mini = Math.min(mini,val);
					maxi = Math.max(mini,maxi);
				}
			}
			rep[0] = maxi;
			rep[1] = cmax;
		}
		return rep;
	}
	
	private double getGain(IPokemon attaquant, IPokemon defenseur) {
		//TODO faire ca correctement en prenant en compte l'equipe entiere
		if(attaquant.estEvanoui()) {
			return 0;
		}else if(defenseur.estEvanoui()){
			return 1;
		}else {
			return 0.5;
		}
		
	}
	
	@Override
	public IPokemon choisitCombattant() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IPokemon choisitCombattantContre(IPokemon pok) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void enseigne(IPokemon pok, ICapacite[] caps) {
		Capacite capaciteAApprendre = this.canTeachAMove();
		if (capaciteAApprendre != null) {
			if (caps.length < 4) {
				try {
					this.getPokemon().remplaceCapacite(caps.length, capaciteAApprendre);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//System.out.println("\t" + pok.getNom() + " a appris " + capaciteAApprendre.getNom() + " !");
			} else {
				//System.out.println("\t" + pok.getNom() + " veut apprendre " + capaciteAApprendre.getNom() + ".");
				//System.out.println("\tVoulez vous lui faire oublier une des ses capacités (1) ou ne pas l'apprendre (2) ?");
				int inputChoix = InputViaScanner.getInputInt(1, 2);
				if (inputChoix == 1) {
					//((Pokemon) pok).showCapaciteApprise();
					//System.out.println("\tEntrer le numéro de la capacité à oublier (ou 0 pour annuler) :");
					int inputCapacite = (int) (Math.random() * caps.length);
					try {
						pok.remplaceCapacite(inputCapacite - 1, capaciteAApprendre);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					//System.out.println("\t" + pok.getNom() + " n'a pas appris " + capaciteAApprendre.getNom() + ".");
				}
			}
		} else {
			// System.out.println(pok.getNom()+" n'a aucune capacite a apprendre au niveau
			// "+pok.getNiveau());
		}
	}
}
