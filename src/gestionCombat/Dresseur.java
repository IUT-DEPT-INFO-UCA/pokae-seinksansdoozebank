package gestionCombat;

import gestionPokemon.Capacite;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import interfaces.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Scanner;
/**
 * Un objet représenant un dresseur
 *
 */
public abstract class Dresseur implements IDresseur, IEchange, IStrategy {
	/**
	 * L'identifiat unique du dresseur
	 */
	private String identifiant;
	/**
	 * le mot de passe du dresseur
	 */
	private String motDepasse;
	/**
	 * le nom du dresseur
	 */
	private String nom;
	/**
	 * le ranch de 6 pokemons du dresseur
	 */
	private Pokemon[] equipe = new Pokemon[Pokedex.getNbPokemonParRanch()];
	/**
	 * le niveau du dresseur, soit la somme des niveaux des pokemons de son ranch
	 */
	private int niveau;
	/**
	 * le pokemon actif du dresseur
	 */
	private Pokemon pokemon;
	/**
	 * le pokemon choisi par le dresseur, celui qui va etre envoyé au combat
	 */
	private Pokemon pokemonChoisi;
	/**
	 * l'action choisi par le dresseur pour le tour actuel
	 */
	private Capacite actionChoisie;

	/**
	 * le constructeur d'un dresseur pour une IARandom
	 *
	 * @param nom le nom du dresseur
	 */
	public Dresseur(String nom) {
		this.nom = nom;
		try {
			this.equipe = (Pokemon[]) Pokedex.engendreRanchStatic();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		this.updateNiveau();
		this.pokemon = this.equipe[0];

	}

	public void saveData(String id, String mdp, String nom) throws IOException, ParseException {
		if (testPresence()) {
			JSONParser parser = new JSONParser();
			JSONObject datafile = (JSONObject) parser.parse(new BufferedReader(new FileReader("./dataSave/DataFile.json")));

			JSONArray dresseurs = (JSONArray) datafile.get("user");
			if (testUserDejaAjoute(dresseurs, id) == 0) {
				assert (dresseurs.size() > 0);
				JSONObject dresseur = new JSONObject();
				dresseur.put("id", id);
				dresseur.put("mdp", mdp);
				dresseur.put("nom", nom);
				dresseurs.add(dresseur);
				FileWriter myWriter = new FileWriter("./dataSave/DataFile.json");
				myWriter.write(datafile.toJSONString());
				myWriter.close();
			} else {
				System.out.println("Ce dresseur existe deja");
			}
		} else {
			String JSONComplet;
			File newFile = new File("./dataSave/DataFile.json");
			FileWriter myWriter = new FileWriter("./dataSave/DataFile.json");
			JSONObject jsonNewData = new JSONObject();
			JSONObject infoUser = new JSONObject();
			JSONArray newUserData = new JSONArray();
			infoUser.put("id", id);
			infoUser.put("mdp", mdp);
			infoUser.put("nom", nom);
			newUserData.add(infoUser);
			jsonNewData.put("user", newUserData);
			JSONComplet = jsonNewData.toJSONString();
			myWriter.write(JSONComplet);
			myWriter.close();
		}
	}

	public int testUserDejaAjoute(JSONArray dresseurs, String id) {
		int test = 0;
		int i = 0;
		while (i < dresseurs.size()) {
			JSONObject dresseur = (JSONObject) dresseurs.get(i);
			if (dresseur.get("id").equals(id)) {
				test = i;
			}
			i++;
		}
		return test;
	}

	private boolean testPresence() {
		File repertoire = new File("./dataSave/");
		String[] listeFichiers = repertoire.list();
		boolean testPresence = false;
		if (listeFichiers == null) {
			System.out.println("Mauvais répertoire");
		} else {
			int i = 0;
			while (!testPresence && i < listeFichiers.length) {
				if (listeFichiers[i].equals("DataFile.json")) {
					File fichier = new File("./dataSave/DataFile.json");
					if (fichier.length() > 0) {
						testPresence = true;
					}

				}
				i++;
			}
		}
		return testPresence;
	}

	public boolean connection(String id) throws IOException, ParseException {
		if (testPresence()) {
			JSONParser parser = new JSONParser();
			JSONObject datafile = (JSONObject) parser.parse(new BufferedReader(new FileReader("./dataSave/DataFile.json")));
			JSONArray dresseurs = (JSONArray) datafile.get("user");
			boolean test = false;
			boolean connected = false;
			int i = 0;
			if (dresseurs == null) {
				System.out.println("Aucun dresseur n'est enregistré");
			} else {
				while (!test && i < dresseurs.size()) {
					JSONObject dresseur = (JSONObject) dresseurs.get(i);
					if (dresseur.get("id").equals(id)) {
						test = true;
						System.out.println("Bienvenue " + dresseur.get("id"));
						while (!connected) {
							System.out.println("Votre mot de passe : ");
							Scanner sc = new Scanner(System.in);
							String mdp = sc.nextLine();
							if (dresseur.get("mdp").equals(mdp)) {
								System.out.println("Vous etes connecté");
								connected = true;
							} else {
								System.out.println("Mauvais mot de passe");
							}
						}
					}
					i++;
				}
			}
			return connected;
		} else {
			System.out.println("Aucun dresseur n'est enregistré");
			return false;
		}
	}

	public void enregistrerRanch() {
		try {
			if (testPresence()) {
				JSONParser parser = new JSONParser();
				JSONObject datafile = (JSONObject) parser.parse(new BufferedReader(new FileReader("./dataSave/DataFile.json")));
				JSONArray dresseurs = (JSONArray) datafile.get("user");
				int indexDresseur = testUserDejaAjoute(dresseurs, this.identifiant);
				JSONObject dresseur = (JSONObject) dresseurs.get(indexDresseur);
				JSONArray ranch = new JSONArray();
				for (int i = 0; i < this.equipe.length; i++) {
					JSONObject pokemon = new JSONObject();
					pokemon.put("id",this.equipe[i].id);
					pokemon.put("nom",this.equipe[i].nom);
					pokemon.put("niv",this.equipe[i].getNiveau());
					pokemon.put("type1",this.equipe[i].getType1());
					pokemon.put("type2",this.equipe[i].getType2());
					pokemon.put("aChangeNiveau",this.equipe[i].aChangeNiveau());
					pokemon.put("xp",this.equipe[i].xp);
					pokemon.put("espPoke",this.equipe[i].espPoke.getNom());
					JSONArray capacites = new JSONArray();
					for (int j=0;j<this.equipe[i].getCapacitesApprises().length;j++){
						capacites.add(this.equipe[i].getCapacitesApprises()[j].getNom());
					}
					pokemon.put("capacites",capacites);
					pokemon.put("pvMax",this.equipe[i].pvMax);

					ranch.add(pokemon);
				}
				dresseur.put("ranch", ranch);
				dresseurs.remove(indexDresseur);
				dresseurs.add(dresseur);
				JSONObject newDataFile = new JSONObject();
				newDataFile.put("user", dresseurs);
				FileWriter myWriter = new FileWriter("./dataSave/DataFile.json");
				myWriter.write(newDataFile.toJSONString());
				myWriter.close();
			}
		} catch (Exception e) {
			System.out.println("Erreur");
		}
	}


	/**
	 * le constructeur du Dresseur lorsqu'un joueur se connecte
	 *
	 * @param id  identifiant unqiue de l'utilisateur
	 * @param mdp le mot de passe de l'utilisateur
	 */
	public Dresseur(String id, String mdp) {
		// on cree un dresseur en le recuperant dans le stockage
	}

	/**
	 * le constructeur du Dresseur lorsqu'un joueur s'inscrit
	 *
	 * @param id  identifiant unqiue de l'utilisateur
	 * @param mdp le mot de passe de l'utilisateur
	 * @param nom le nom du dresseur que l'utilisateur créé sint dresseur
	 */
	public Dresseur(String id, String mdp, String nom) {

		// on cree un dresseur en l'ajoutant au stockage
		this.identifiant = id;
		this.motDepasse = mdp;
		this.nom = nom;
		boolean connected = false;
		try {
			System.out.println("1 Pour vous connecter, 2 Pour vous inscrire");
			int inputConnection = InputViaScanner.getInputInt(1, 2);
			if (inputConnection == 1) {
				connected = this.connection(id);
				if (connected) {
					this.equipe = (Pokemon[]) Pokedex.engendreRanchStatic();
					this.updateNiveau();
					this.pokemon = this.equipe[0];
				}
			} else if (inputConnection == 2) {
				this.saveData(id, mdp, nom);
				this.equipe = (Pokemon[]) Pokedex.engendreRanchStatic();
				this.updateNiveau();
				this.pokemon = this.equipe[0];
			}

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Definition de l'affichage d'un dresseur par son nom
	 */

	/////////////////////// methode de IDresseur ///////////////////////

	public IPokemon getPokemon(int i) {
		return this.equipe[i];
	}

	@Override
	public void enseigne(IPokemon pok, ICapacite[] caps) {
		Capacite capaciteAApprendre = this.canTeachAMove();
		if (capaciteAApprendre != null) {
			if (caps.length < 4) {
				// System.out.println(pok.getNom()+" peut apprendre
				// "+capaciteAApprendre.getNom()+" et il peut le faire seul.");
				try {
					this.getPokemon().remplaceCapacite(caps.length, capaciteAApprendre);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("\t" + pok.getNom() + " a appris " + capaciteAApprendre.getNom() + " !");
			} else {
				System.out.println("\t" + pok.getNom() + " veut apprendre " + capaciteAApprendre.getNom() + ".");
				System.out.println(
						"\tVoulez vous lui faire oublier une des ses capacités (1) ou ne pas l'apprendre (2) ?");
				int inputChoix = InputViaScanner.getInputInt(1, 2);
				if (inputChoix == 1) {
					((Pokemon) pok).showCapaciteApprise();
					System.out.println("\tEntrer le numéro de la capacité à oublier (ou 0 pour annuler) :");
					int inputCapacite = InputViaScanner.getInputInt(1, this.getPokemon().getCapacitesApprises().length);
					try {
						pok.remplaceCapacite(inputCapacite - 1, capaciteAApprendre);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("\t" + pok.getNom() + " n'a pas appris " + capaciteAApprendre.getNom() + ".");
				}

			}

			this.updateNiveau();
			this.pokemon = this.equipe[0];
		}
	}
		/**
		 * Definition de l'affichage d'un dresseur par son nom
		 */
		public String toString () {
			return this.nom;

		}
		/////////////////////// methode de IDresseur (et IStrategy) ///////////////////////




		@Override
		public void soigneRanch () {
			for (Pokemon p : this.equipe) {
				p.soigne();
			}
		}

		public abstract IPokemon choisitCombattant ();

		public abstract IPokemon choisitCombattantContre (IPokemon pok);

		public abstract IAttaque choisitAttaque (IPokemon attaquant, IPokemon defenseur);

		/////////////////////// methode de IEchange ///////////////////////

		public int calculeDommage (IPokemon lanceur, IPokemon receveur){
			// On return 0 puisque echange ne fait aucun degat
			// de toute facons on appelle jamais cette méthode puisque le calcul des dommage
			// d'un dresseur n'a pas de sens
			return 0;
		}

		public void utilise () {
			this.getPokemon().utilise(this.getActionChoisie());
		}

		public void setPokemon (IPokemon pok){
			System.out.println(this.getNom() + " envoie " + pok.getNom() + " au combat.");
			this.pokemon = (Pokemon) pok;
		}

		public IPokemon echangeCombattant () {
			Pokemon oldPokemonActif = this.pokemon;
			System.out.print(this.getNom() + " rapelle " + oldPokemonActif.getNom() + " et ");
			this.pokemon = this.pokemonChoisi;
			System.out.println("envoie " + this.getPokemon().getNom());
			return oldPokemonActif;
		}
		/////////////////////////////////////////////////////////////////////

		/**
		 * La fonction prend deux objets IPokemon, et le dresseur du premier objet
		 * IPokemon sélectionnera une action à effectuer : une capacité qui touchera le
		 * Pokemon pAdv ou un changement de Pokemon
		 *
		 * @param p    Le pokémon qui utilise le mouvement
		 * @param pAdv Le pokémon de l'adversaire
		 */
		public abstract void selectAction (IPokemon p, IPokemon pAdv);

		/**
		 * Retourne le pokémon actif du dresseur
		 *
		 * @return L'objet pokémon actif du dresseur.
		 */
		public Pokemon getPokemon () {
			return this.pokemon;
		}

		/**
		 * Cette fonction renvoie l'attribut pokemonChoisi (le pokemonChoisi est le
		 * pokemon qui sera envoyé au combat lors de l'appel de la méthode
		 * echangeCombattant() )
		 *
		 * @return Le pokemonChoisi par le dresseur.
		 */
		public Pokemon getPokemonChoisi () {
			return pokemonChoisi;
		}

		/**
		 * Défini le pokemon que le dresseur veut envoyer au combat dans l'attribut
		 * pokemonChoisi
		 *
		 * @param pokemonChoisi le pokémon que le dresseur veut envoyer au combat
		 */
		public void setPokemonChoisi (IPokemon pokemonChoisi){
			this.pokemonChoisi = (Pokemon) pokemonChoisi;
		}

		/**
		 * Cette fonction renvoie le ranch du ranch du Dresseur sous forme de tableau de
		 * Pokemon
		 *
		 * @return Le tableau équipe.
		 */
		public Pokemon[] getEquipe () {
			return this.equipe;
		}

		/**
		 * Cette fonction renvoie l'identifiant du dresseur
		 *
		 * @return L'identifiant du dresseur
		 */
		public String getIdentifiant () {
			return identifiant;
		}

		/**
		 * Cette fonction renvoie le mot de passe du dresseur
		 *
		 * @return La méthode renvoie la valeur de la variable motDepasse.
		 */
		public String getMotDepasse () {
			return motDepasse;
		}

		/**
		 * Cette fonction renvoie le nom du du dresseur
		 *
		 * @return Le nom du du dresseur
		 */
		public String getNom () {
			return nom;
		}

		/**
		 * Cette fonction renvoie le niveau du dresseur
		 *
		 * @return La valeur de la variable niveau.
		 */
		public int getNiveau () {
			return niveau;
		}

		/**
		 * Cette fonction fixe la valeur de l'attribut identifiant du dresseur à la
		 * valeur du paramètre
		 * identifiant
		 *
		 * @param identifiant L'identifiant unique du dresseur.
		 */
		public void setIdentifiant (String identifiant){
			this.identifiant = identifiant;
		}

		/**
		 * Cette fonction définit le mot de passe du dresseur
		 *
		 * @param motDepasse Le mot de passe du dresseur
		 */
		public void setMotDepasse (String motDepasse){
			this.motDepasse = motDepasse;
		}

		/**
		 * Cette fonction fixe la valeur de la variable nom à la valeur du paramètre nom
		 *
		 * @param nom Le nom du paramètre.
		 */
		public void setNom (String nom){
			this.nom = nom;
		}

		/**
		 * Cette fonction calcule le niveau total du dresseur en fonction du niveau des
		 * pokemons de son ranch
		 */
		public void updateNiveau () {
			int s = 0;
			for (Pokemon p : this.getEquipe()) {
				s += p.getNiveau();
			}
			this.niveau = s;
		}

		/**
		 * Cette fonction renvoie l'action choisie par le dresseur
		 *
		 * @return L'action choisie par le dresseur.
		 */
		public Capacite getActionChoisie () {
			return actionChoisie;
		}

		/**
		 * Cette fonction définit l'action choisie par le dresseur
		 *
		 * @param actionChoisie L'action que le dresseur a choisi d'utiliser.
		 */
		public void setActionChoisie (Capacite actionChoisie){
			this.actionChoisie = actionChoisie;
		}

		/**
		 * Cette fonction retourne vrai si le joueur peut combattre, faux si tout ces
		 * pokemons sont KO
		 *
		 * @return Une valeur booléenne.
		 */
		public boolean pouvoirSeBattre () {
			return this.getNbPokemonAlive() > 0;
		}

		public int getNbPokemonAlive () {
			int nb = 0;
			for (Pokemon p : this.getEquipe()) {
				if (!p.estEvanoui()) {
					nb++;
				}
			}
			return nb;
		}


		/**
		 * Il renvoie le mouvement que le pokémon peut apprendre à son niveau actuel
		 *
		 * @return La méthode renvoie le mouvement apprenable du pokemon.
		 */
		public Capacite canTeachAMove () {
			// System.out.println("appel de getLearnableMove().");
			return this.getPokemon().espPoke.getLearnableMove(this.getPokemon().getNiveau());
		}

		/**
		 * Cette fonction affiche l'équipe du joueur, utile lorsqu'on veut qu'il
		 * choisisse un pokemon
		 */
		public void showTeam () {
			for (Pokemon p : this.getEquipe()) {
				System.out.println(p);
			}
		}
		/**
		 * Permet d'afficher les statistique d'un dresseur comme son niveau et la composition de son équipe
		 */
		public void afficherStat () {
			System.out.println(this.getNom() + " est niveau " + this.getNiveau() + " et son equipe est composé de :");
			this.showTeam();
		}
	}

