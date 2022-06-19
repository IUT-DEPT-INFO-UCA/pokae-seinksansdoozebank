package gestionCombat;


import java.io.FileNotFoundException;

import gestionPokemon.Capacite;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IPokemon;

public class IAElaboreePerso extends Dresseur {
	//private HashMap<Integer,Object[]> PiXi = new HashMap<>();//[0]-> P(Xi)  [1]-> Xi=Etat du jeu i
	public enum Strategy{
		AGRESSIVE("Force", 1, "Puisance"),
		DEFENSIVE("PV", 0.5, "Precision");
		
		protected String statChoixPokem;
		protected double coeffDeSwitch;
		protected String statChoixCapa;
		
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
	
	public IAElaboreePerso(Strategy strat,boolean empty) {
		super(empty);
		this.setup(strat);
		this.strat=strat;
	}
	
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
		//au debut du combat on cherhce le pokemon ayant la meilleure stats de PV
		for(Pokemon p : this.getEquipe()) {
			if(p.getStat().get(statChoixPokem)>choosen.getStat().get(statChoixPokem)) {
				choosen = p;
			}
		}
		return choosen;
	}

	@Override
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		//TODO virer tous les sout
		System.out.println(this.getNom() + "\ta " + attaquant.getNom() + " sur le terrain. Il choisi quoi faire...");
		double coeffMax = this.getMaxCoeffSubit(attaquant,defenseur);
		System.out.println("\tL'efficacite max est de "+coeffMax+ " contre "+this.coeffDeSwitch);
		//rajouter pour tester l'efficite de l'attaquant sur le defenseur si strat est aggro
		if(coeffMax>this.coeffDeSwitch || ((Pokemon) attaquant).getPPTotaux()==0){
			IPokemon newPokemon = this.choisitCombattantContre(defenseur);
			if(newPokemon!=null) {
				return new Echange(newPokemon,this);
			}
		}
		return this.choisiCapacite(attaquant,defenseur);
	}
	
	
	private double getMaxCoeffSubit(IPokemon cible, IPokemon adversaire) {
		double maxi = 0;
		for (ICapacite p : adversaire.getCapacitesApprises()) {
			maxi=Math.max(((Capacite)p).getEfficiencyOn((Pokemon) cible, false),maxi);
		}
		return maxi;
	}

	@Override
	public IPokemon choisitCombattantContre(IPokemon pok) {
		System.out.println("\tRecherche d'un echange a faire");
		Pokemon choosen = null;
		double effMin = 4;
		if(!this.getPokemon().estEvanoui()) {
			effMin = this.getMaxCoeffSubit(this.getPokemon(), pok);
		}else {
			System.out.println("\tle pokemon est ko, on doit trouver un remplacant");
		}
		System.out.println("\tRecherche d'un pokemon dont l'eff min est < à "+effMin);
		//on cherche le pokemon qui resiste le mieux a une des capacite du pokemon adverse actif
		//TODO faire le choix entre une ia qui envoie le pokemon qui resiste le mieux et une qui tappe le plus
		for(Pokemon p : this.getEquipe()) {
			System.out.println("on arrive sur "+p);
			if(!p.equals(this.getPokemon()) && !p.estEvanoui() && ((Pokemon)p).echangePossible()) {
				System.out.println("on teste "+p);
				if(this.getAdversaire().getPokemon().getCapacitesApprises().length>0) {
					for(ICapacite c : this.getAdversaire().getPokemon().getCapacitesApprises()) {
						double tmp = ((Capacite)c).getEfficiencyOn(p, false);
						System.out.println("\tson efficacite serait de "+tmp);
						if(tmp <effMin ){
							System.out.println("\t\tEnvoyer "+p+" est mieux, l'efficacite min est de "+tmp);
							choosen = p;
							effMin = tmp;
						}else if(tmp ==effMin && choosen != null){
							System.out.println("\t\tEnvoyer "+p+" est equivalente, l'efficacite min est de "+tmp);
							System.out.println("\t\tdonc on teste la defense");
							if(choosen.getStat().get(statChoixPokem)<p.getStat().get(statChoixPokem))
								System.out.println("\tsa "+statChoixPokem+" est plus elevee");
								choosen = p;
								effMin = tmp;
						}
					}
				}else {
					double tmp = ((Capacite) Pokedex.getCapaciteStatic("Lutte")).getEfficiencyOn(p, false);
					System.out.println("\tson efficacite serait de "+tmp);
					if(tmp <effMin ){
						System.out.println("\t\tEnvoyer "+p+" est mieux, l'efficacite min est de "+tmp);
						choosen = p;
						effMin = tmp;
					}else if(tmp ==effMin && choosen != null){
						System.out.println("\t\tEnvoyer "+p+" est equivalente, l'efficacite min est de "+tmp);
						System.out.println("\t\tdonc on teste la defense");
						if(choosen.getStat().get(statChoixPokem)<p.getStat().get(statChoixPokem))
							System.out.println("\tsa "+statChoixPokem+" est plus elevee");
							choosen = p;
							effMin = tmp;
					}
				}
			}else {
				if(!p.equals(this.getPokemon())) {
					System.out.println("\t"+p+ " est deja au combat");
				}
				if( !p.estEvanoui()) {
					System.out.println("\t"+p+ " est KO");
				}
				if( ((Pokemon)p).echangePossible()) {
					System.out.println("\t"+p+ " ne peut plus etre echange");
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
				System.out.println("choix de la meilleure capacite");
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
		System.out.println("la meilleure attaque est "+this.getActionChoisie());
		return this.getActionChoisie();
	}
	
	private Capacite getBestCapacite(IPokemon attaquant, IPokemon adversaire) {
		Capacite maxi=null;
		double effMax = 0;
		//on cherche la capacite qui aura le meilleure coeff multiplicateur
		for (ICapacite c : attaquant.getCapacitesApprises()) {
			System.out.println("\t\ton teste "+c);
			double tmpEff = ((Capacite)c).getEfficiencyOn((Pokemon) adversaire, false);
			System.out.println("\t\tdont l'efficacite est "+tmpEff);
			if(c.getPP()!=0 && maxi!=null) {
				if( tmpEff >=effMax ) {
					if(tmpEff==effMax) {
						if(((Capacite) c).get(this.statChoixCapa) > maxi.get(this.statChoixCapa)) { //test ajouté plus tard que l'IA de base
							maxi = (Capacite) c ;
							effMax = maxi.getEfficiencyOn((Pokemon) adversaire, false);
							System.out.println("\t\t"+c.getNom()+" est mieux eff = "+effMax+" et "+statChoixCapa+"= "+((Capacite) c).get(this.statChoixCapa));
						}else {
							System.out.println("\t\t"+c.getNom()+" aussi bien car eff= "+effMax+" mais "+statChoixCapa+"= " +((Capacite) c).get(this.statChoixCapa));
						}
					}else {
						System.out.println("\t\tc'est plus efficace que "+ maxi+", "+effMax);
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
