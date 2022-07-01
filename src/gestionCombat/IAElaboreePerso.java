package gestionCombat;


import java.io.FileNotFoundException;

import gestionPokemon.Capacite;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IPokemon;

/**
 * Classe représentant l'IA implémentée par notre équipe pour palier à l'IAMiniMax
 */
public class IAElaboreePerso extends Dresseur {
	//private HashMap<Integer,Object[]> PiXi = new HashMap<>();//[0]-> P(Xi)  [1]-> Xi=Etat du jeu i

	/**
	 * Enumération de stratégie de l'IA
	 */
	public enum Strategy{
		/**
		 * Stratégie aggressive qui priorise la force
		 */
		AGRESSIVE("Force", 1, "Puisance"),
		/**
		 * Stratégie defensive qui priorise les PV
		 */
		DEFENSIVE("PV", 0.5, "Precision");
		
		private String statChoixPokem;
		private double coeffDeSwitch;
		private String statChoixCapa;
		
		/**
		 * Constructeur de Strategy
		 * @param statChoixPokem la stat de choix pour choisir le pokemon a envoyer
		 * @param coeffDeSwitch le coefficient subit a partir de laquelle L'IA echange de pokemon
		 * @param statChoixCapala stat de choix pour choisir l'attaque a envoyer
		 */
		Strategy(String statChoixPokem, double coeffDeSwitch, String statChoixCapa){
			this.statChoixPokem = statChoixPokem;
			this.coeffDeSwitch = coeffDeSwitch;
			this.statChoixCapa = statChoixCapa;
		}
	}
	
	private Strategy strat;
	private String statChoixPokem;
	private double coeffDeSwitch;
	private String statChoixCapa;

	/**
	 * Constructeur de IAElaboreePerso qui initialise la stratégie choisie
	 * @param strat Stratégie choisie
	 * @param empty true si le IARandom doit être vide, false sinon
	 */
	public IAElaboreePerso(Strategy strat,boolean empty) {
		super(empty);
		this.setup(strat);
		this.strat=strat;
	}
	
	/**
	 * Met a jour les parametres de l'IA en fonction de la strategie de celle-ci
	 * @param s La stratégie choisie pour l'IA
	 */
	private void setup(Strategy s) {
		//on mette les variable de la strat dans les variables de l'ia
		this.statChoixPokem = s.statChoixPokem;
		this.coeffDeSwitch = s.coeffDeSwitch;
		this.statChoixCapa = s.statChoixCapa;
	}
	
	@Override
	public IPokemon choisitCombattant() {
		System.out.println(this.getNom() + "\tchoisi un pokemon a envoyer au combat...");
		Pokemon choosen = this.getEquipe()[0];
		for(Pokemon p : this.getEquipe()) {
			if(p.getStat().get(statChoixPokem)>choosen.getStat().get(statChoixPokem)) {
				choosen = p;
			}
		}
		return choosen;
	}

	@Override
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		System.out.println(this.getNom() + "\ta " + attaquant.getNom() + " sur le terrain. Il choisi quoi faire...");
		double coeffMax = this.getCoeffSubitMax(attaquant,defenseur);
		//TODO  rajouter de quoi tester l'efficacite de l'attaquant sur le defenseur si la stratégie est agressive
		if(coeffMax>this.coeffDeSwitch || ((Pokemon) attaquant).getPPTotaux()==0){//stratég defensive
			IPokemon newPokemon = this.choisitCombattantContre(defenseur);
			if(newPokemon!=null) {
				return new Echange(newPokemon,this);
			}
		}
		return this.choisiCapacite(attaquant,defenseur);
	}
	
	
	private double getCoeffSubitMax(IPokemon cible, IPokemon adversaire) {
		double maxi = 0;
		for (ICapacite p : adversaire.getCapacitesApprises()) {
			maxi=Math.max(maxi,((Capacite)p).calculerCM((Pokemon) cible,(Pokemon)adversaire));
		}
		return maxi;
	}

	@Override
	public IPokemon choisitCombattantContre(IPokemon pok) {
		Pokemon choosen = null;
		double effMin = 4;
		if(!this.getPokemon().estEvanoui()) {
			effMin = this.getCoeffSubitMax(this.getPokemon(), pok);
		}
		for(Pokemon p : this.getEquipe()) {
			if(!p.equals(this.getPokemon()) && !p.estEvanoui() && p.echangePossible()) {
				if(this.getAdversaire().getPokemon().getCapacitesApprises().length>0) {
					for(ICapacite c : this.getAdversaire().getPokemon().getCapacitesApprises()) {
						double tmp = ((Capacite)c).getEfficiencyOn(p, false);
						if(tmp <effMin ){
							choosen = p;
							effMin = tmp;
						}else if(tmp ==effMin && choosen != null){
							if(choosen.getStat().get(statChoixPokem)<p.getStat().get(statChoixPokem)) {
//								System.out.println("\tsa "+statChoixPokem+" est plus elevee");
								choosen = p;
								effMin = tmp;
							}
						}
					}
				}else {
					double tmp = ((Capacite) Pokedex.getCapaciteStatic("Lutte")).getEfficiencyOn(p, false);
					if(tmp <effMin ){
						choosen = p;
						effMin = tmp;
					}else if(tmp == effMin && choosen != null){
						if(choosen.getStat().get(statChoixPokem)<p.getStat().get(statChoixPokem)) {
							choosen = p;
							effMin = tmp;
						}
					}
				}
			}

		}
		this.setPokemonChoisi(choosen);
		return choosen;
	}

	/**
	 * Methode permettant à l'IA de choisr la capacité à utiliser
	 * @param attaquant Le Pokémon actuellement au combat du Dresseur courant
	 * @return La capacité à utilier sous forme d'un IAttaque
	 */
	private IAttaque choisiCapacite(IPokemon attaquant,IPokemon cible) {
		if (((Pokemon) attaquant).getNombreDeToursAvantAttaque() == 0) { // dans le cas ou patience a ete utilisee
			if (((Pokemon) attaquant).getCapacitesUtilisables().length > 0) {
				this.setActionChoisie(this.getBestCapacite(attaquant, cible));
			} else {
				// utilisation de Lutte si aucune capacite n'est dispo
				try {
					this.setActionChoisie(Pokedex.createCapacite(((Capacite) Pokedex.getCapaciteStatic("Lutte")).id));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		} else {// utilisation de patience
			((Pokemon) attaquant).updateNombreDeToursAvantAttaque();// on decremente la duree avant la fin de Patience
			if (((Pokemon) attaquant).getNombreDeToursAvantAttaque() == 0) {// si Patiece est prete
				((Pokemon) attaquant).setNombreDeToursAvantAttaque(-1); // on met le nb de tour a -1 pour que dans
																		// calculDommage() le nb nne soit pas remis a 2
			}
		}
		((Pokemon) attaquant).setAttaqueChoisie((Capacite) this.getActionChoisie());
		return this.getActionChoisie();
	}
	
	private Capacite getBestCapacite(IPokemon attaquant, IPokemon adversaire) {
		Capacite maxi=null;
		double effMax = 0;
		//on cherche la capacite qui aura le meilleure coeff multiplicateur
		for (ICapacite c : attaquant.getCapacitesApprises()) {
			double tmpEff = ((Capacite)c).calculerCM((Pokemon) attaquant,(Pokemon)adversaire);
			if(c.getPP()!=0 && maxi!=null) {
				if( tmpEff >=effMax ) {
					if(tmpEff==effMax) {
						if(((Capacite) c).get(this.statChoixCapa) > maxi.get(this.statChoixCapa)) { //test ajouté plus tard que l'IA de base
							maxi = (Capacite) c ;
							effMax = maxi.getEfficiencyOn((Pokemon) adversaire, false);
						}
					}else {
						maxi = (Capacite) c ;
						effMax = maxi.getEfficiencyOn((Pokemon) adversaire, false);
					}
				}
			}else {
					maxi = (Capacite) c ;
					effMax = maxi.getEfficiencyOn((Pokemon) adversaire, false);
			}
		}
		return maxi;
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
			} else {
				int inputChoix = InputViaScanner.getInputInt(1, 2);
				if (inputChoix == 1) {
					int inputCapacite = (int) (Math.random() * caps.length);
					try {
						pok.remplaceCapacite(inputCapacite - 1, capaciteAApprendre);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	
	
	@Override
	protected IAElaboreePerso copy() {
		//System.out.println("Debut de la copie d'une "+this.getClass().getSimpleName());
		IAElaboreePerso copy = new IAElaboreePerso(this.strat, true);
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
