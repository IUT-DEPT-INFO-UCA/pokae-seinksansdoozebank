package gestionCombat;


import gestionPokemon.Capacite;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IPokemon;

/**
 * Permet de gérer un objet IAMinMax (un dresseur controlé par l'IA MinMax)
 */
public class IAMinMax extends Dresseur {
	//private HashMap<Integer,Object[]> PiXi = new HashMap<>();//[0]-> P(Xi)  [1]-> Xi=Etat du jeu i
	
	/**
	 * @param empty true si le IARandom doit être vide, false sinon
	 */
	public IAMinMax(boolean empty) {
		super(empty);
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
					EtatDuJeu[] x_i = this.getXi(edj, cmax, C2[j],p_i);
					double val=0; // val=somme_i P_i P(X_i)[0]
					for(int k=0;k<4+j;k++) {
						if(p_i[i]>0.1 && x_i[i]!=null) {//OPTI : on ne prend pas en compte les etats qui n'arrive jamais
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
	
	/**
	 * Il renvoie un tableau de 4 états suivants possibles, étant donné l'état actuel, les deux attaques et les probabilités
	 * de chacun des 4 états suivants possibles
	 *
	 * @param edj l'état actuel du jeu
	 * @param c1 l'attaque du premier joueur
	 * @param c2 l'attaque de l'adversaire
	 * @param p_i la probabilité de chacun des quatre états du jeu possibles
	 * @return La méthode renvoie un tableau d'EtatDuJeu.
	 */
	public EtatDuJeu[] getXi(EtatDuJeu edj, IAttaque c1, IAttaque c2, double[] p_i) {
		EtatDuJeu[] x_i = new EtatDuJeu[4];
		
		System.out.println(p_i[0]);
		if(p_i[0]>0.1) {
			EtatDuJeu e1 = new EtatDuJeu(edj);
			x_i[0] = this.etatSuivant(e1, c1, c2);
		}else {
			x_i[0] = null;
		}
		System.out.println(p_i[1]);
		if(p_i[1]>0.1) {
			EtatDuJeu e2 = new EtatDuJeu(edj);
			x_i[1] = this.etatSuivant(e2, c1, null);
		}else {
			x_i[1] = null;
		}
		System.out.println(p_i[2]);
		if(p_i[2]>0.1) {
			EtatDuJeu e3 = new EtatDuJeu(edj);
			x_i[2] = this.etatSuivant(e3, null, c2);
		}else {
			x_i[2] = null;
		}
		System.out.println(p_i[3]);
		if(p_i[3]>0.1) {
			EtatDuJeu e4 = new EtatDuJeu(edj);
			x_i[3] = this.etatSuivant(e4, null, null);
		}else {
			x_i[3] = null;
		}
		return x_i;
	}
	
	/**
	 * Il renvoie un tableau de 4 probabilités, chacune correspondant à une issue possible du tour
	 *
	 * @param edj l'état actuel du jeu
	 * @param c1 La première attaque
	 * @param c2 L'attaque de l'adversaire
	 * @return La probabilité de chacun des 4 résultats possibles du tour.
	 */
	public double[] getPi(EtatDuJeu edj, IAttaque c1, IAttaque c2) {
		double[] p_i = new double[4];
		double probaC1 = 0;
		//System.out.println(c1.getClass().getSimpleName());
		if(c1 instanceof Echange) {
			probaC1 = 1;
		}else {
			probaC1 = ((Capacite)c1).getPrecision();
		}
		double probaC2 = 0;
		//System.out.println(c2.getClass().getSimpleName());
		if(c2 instanceof Echange) {
			probaC2 = 1;
		}else{
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
	
	/**
	 * Il génère l'état suivant
	 *
	 * @param edj l'état actuel du jeu
	 * @param atq1 l'attaque du premier joueur
	 * @param atq2 L'attaque du deuxième joueur
	 * @return Le prochain état du jeu.
	 */
	public EtatDuJeu etatSuivant(EtatDuJeu edj, IAttaque atq1, IAttaque atq2) {
		EtatDuJeu etatSuivant = new EtatDuJeu(edj.getDresseur1(),edj.getDresseur2(),edj.getPokemon1(),edj.getPokemon2());
		System.out.println("pokemon 1 = "+((Dresseur)etatSuivant.getDresseur1()).getPokemon());
		System.out.println("pokemon 2 = "+((Dresseur)etatSuivant.getDresseur2()).getPokemon());
		Tour tmp = new Tour(etatSuivant, atq1, atq2);
		tmp.commence();
		return etatSuivant;
	}
	
	
	
	@Override
	public IPokemon choisitCombattant() {
		System.out.println(this.getNom() + "\tchoisi un pokemon a envoyer au combat...");
		Pokemon choosen = this.getEquipe()[0];
		for(Pokemon p : this.getEquipe()) {
			if(p.getStat().getDefense()>choosen.getStat().getDefense()) {
				choosen = p;
			}
		}
		return choosen;
	}

	@Override
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		//Code utilisé pour laisser l'algo MinMax choisir le coup
		System.out.println("L'IAMinMax de "+this.getNom()+" choisi le coup a faire");
		EtatDuJeu etatActuel = new EtatDuJeu(this, this.getAdversaire(), attaquant, defenseur);
		return (IAttaque) ProbaVictoire(etatActuel)[1];
		
	}
	

	@Override
	public IPokemon choisitCombattantContre(IPokemon pok) {
		return null;
	}
	


	/**
	 * Methode permettant à l'IA de choisr la capacité à utiliser
	 * @param attaquant Le Pokémon actuellement au combat du Dresseur courant
	 * @return La capacité à utilier sous forme d'un IAttaque
	 */
	private IAttaque choisiCapacite(IPokemon attaquant,IPokemon cible) {
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
	protected IAMinMax copy() {
		//System.out.println("Debut de la copie d'une "+this.getClass().getSimpleName());
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
