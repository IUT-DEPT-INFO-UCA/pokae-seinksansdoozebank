package gestionCombat;

import java.util.HashMap;

import gestionPokemon.Capacite;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IPokemon;

public class IAMinMax extends Dresseur {
	//private HashMap<Integer,Object[]> PiXi = new HashMap<>();//[0]-> P(Xi)  [1]-> Xi=Etat du jeu i
	
	public IAMinMax(boolean empty) {
		super(empty);
		// TODO Auto-generated constructor stub
	}

	private Object[] ProbaVictoire(EtatDuJeu edj) { 
		Object[] rep = new Object[2];
		if(edj.estTerminal()) {
			System.out.println("ETAT TERMINAL");
			if(this.pouvoirSeBattre()) {
				rep[0]=1.0;
			}else {
				rep[0]=0.0;
			}
			rep[1] = null;
		}else {
			double maxi=0;
			IAttaque[] C1 = this.getCoupsPossibles();
			IAttaque cmax = C1[0];
			for(int i=0; i<C1.length;i++) {
				double mini=1;			
				IAttaque[] C2 = ((Dresseur) edj.getDresseur2()).getCoupsPossibles();
				for(int j=0; j<C2.length;j++) {
					double[] p_i = this.getPi(edj, cmax, C2[j]);
					EtatDuJeu[] x_i = this.getXi(edj, cmax, C2[j]);
					double val=0; // val=somme_i P_i P(X_i)[0]
					for(int k=0;k<4+j;k++) {
						if(p_i[i]!=0) {//OPTI : on ne prend pas en compte les etats qui n'arrive jamais
							val+=p_i[i]*(double)(ProbaVictoire(x_i[i])[0]);
						}
					}
					mini = Math.min(mini,val);
					maxi = Math.max(mini,maxi);
				}
			}
			rep[0] = maxi;
			rep[1] = cmax;
		}
		return rep;
	}
	
	public EtatDuJeu[] getXi(EtatDuJeu edj, IAttaque c1, IAttaque c2) {
		//TODO voir a integrer c1 c2 dans le constructeur d'EtatDuJeu
		EtatDuJeu[] x_i = new EtatDuJeu[1]; //TODO 4
		EtatDuJeu e1 = new EtatDuJeu(edj);
		x_i[0] = this.etatSuivant(e1, c1, c2);
		/*
		EtatDuJeu e2 = new EtatDuJeu(edj);
		EtatDuJeu e3 = new EtatDuJeu(edj);
		EtatDuJeu e4 = new EtatDuJeu(edj);
		x_i[1] = this.etatSuivant(e2, c1, null);
		x_i[2] = this.etatSuivant(e3, null, c2);
		x_i[3] = this.etatSuivant(e4, null, null);
		*/
		return x_i;
	}
	
	public double[] getPi(EtatDuJeu edj, IAttaque c1, IAttaque c2) {
		double[] p_i = new double[4];
		double probaC1 = 0;
		if(c1 instanceof Echange) {
			probaC1 = 1;
		}else {
			probaC1 = ((Capacite)c1).getPrecision();
		}
		double probaC2 = 0;
		if(c1 instanceof Echange) {
			probaC2 = 1;
		}else {
			probaC2 = ((Capacite)c2).getPrecision();
		}
		System.out.println("precision c1 = "+probaC1);
		System.out.println("precision c2 = "+probaC2);
		p_i[0] = probaC1*probaC2;
		p_i[1] = probaC1*(1-probaC2);
		p_i[2] = (1-probaC1)*probaC2;
		p_i[3] =  (1-probaC1)*(1-probaC2);
		return p_i;
	}
	
	public EtatDuJeu etatSuivant(EtatDuJeu edj, IAttaque atq1, IAttaque atq2) {
		EtatDuJeu etatSuivant = new EtatDuJeu(edj.getDresseur1(),edj.getDresseur2(),edj.getPokemon1(),edj.getPokemon2());
		System.out.println("########## generation de l'etat : "+atq1+" / "+atq2+" ##########");
		//TODO ok c le bordel
		System.out.println("pokemon 1 = "+((Dresseur)etatSuivant.getDresseur1()).getPokemon());
		System.out.println("pokemon 2 = "+((Dresseur)etatSuivant.getDresseur2()).getPokemon());
		Tour tmp = new Tour(etatSuivant, atq1, atq2);
		tmp.commence(); //TODO empecher d'avoir des etat avec 2 pokemon KO si ls dresseur peuvent se battre
		return etatSuivant;
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public IPokemon choisitCombattant() {//TODO retirer le random
		System.out.println(this.getNom() + "\tchoisi un pokemon a envoyer au combat...");
		int i = (int) (Math.random() * Pokedex.getNbPokemonParRanch());
		Pokemon choosen = this.getEquipe()[i];
		this.setPokemon(choosen);
		return choosen;
	}

	@Override
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		System.out.println("L'IAMinMax de "+this.getNom()+" choisi le coup a faire");
		EtatDuJeu etatActuel = new EtatDuJeu(this, this.getAdversaire(), attaquant, defenseur);
		return (IAttaque) ProbaVictoire(etatActuel)[1];
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
	


	@Override
	protected IAMinMax copy() {
		System.out.println("Debut de la copie d'une "+this.getClass().getSimpleName());
		IAMinMax copy = new IAMinMax(true);
		if(this.getActionChoisie() instanceof Echange) {
			copy.setActionChoisie(((Echange)this.getActionChoisie()).copy(copy));
		}else if(this.getActionChoisie() instanceof Capacite) {
			copy.setActionChoisie(((Capacite)this.getActionChoisie()).copy());
		}else {
			copy.setActionChoisie(null);
		}
		for(int i=0;i<Pokedex.getNbPokemonParRanch();i++) {
			copy.setPokemon(i,(Pokemon) ((Pokemon) this.getPokemon(i)).copy());
			
		}
		copy.setIdentifiant(this.getIdentifiant());
		copy.setMotDepasse(this.getMotDepasse());
		copy.updateNiveau();
		copy.setNom(this.getNom());
		copy.setPokemon(copy.getEquipe()[copy.getIndexPokemon(this.getPokemon())]);
		if(this.getPokemonChoisi() != null) {
			copy.setPokemonChoisi(copy.getEquipe()[copy.getIndexPokemon(this.getPokemonChoisi())]);
		}else{
			copy.setPokemonChoisi(null);
		}
		return copy;
	}
}
