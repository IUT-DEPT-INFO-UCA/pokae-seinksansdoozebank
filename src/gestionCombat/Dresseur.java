package gestionCombat;

import gestionPokemon.*;
import interfaces.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Un objet représenant un dresseur
 *
 */
public abstract class Dresseur implements IDresseur, IStrategy {

	/**
	 * Une liste de chaînes constante stockant des noms pour les IA crées
	 */
	private static final String[] listeNoms = { "Violette", "Lino", "Cornélia", "Amaro", "Lem", "Valériane", "Astera",
			"Urup", "Pierre", "Ondine", "Major Bob", "Erika", "Koga", "Morgane", "Auguste", "Blue" };

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
	private IAttaque actionChoisie;

	/**
	 * Le dresseur adverse si le dresseur ocurrant est en combat
	 */
	private Dresseur adversaire = null;

	/**
	 * le constructeur d'un dresseur pour une IA
	 *
	 */

	public Dresseur(boolean empty) {
		if(!empty) {
			this.nom = listeNoms[(int) (Math.random() * listeNoms.length)];
			try {
				this.equipe = (Pokemon[]) Pokedex.engendreRanchStatic();
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			this.updateNiveau();
			this.pokemon = this.equipe[0];
		}
	}

	/**
	 * le constructeur du Dresseur lorsqu'un joueur se connecte
	 *
	 * @param id identifiant unqiue de l'utilisateur
	 */
	public Dresseur(String id) {
		this.identifiant = id;
		try {
			JSONParser parser = new JSONParser();
			JSONObject datafile = (JSONObject) parser
					.parse(new BufferedReader(new FileReader("./dataSave/DataFile.json")));
			JSONArray dresseurs = (JSONArray) datafile.get("user");
			while (this.testUserDejaAjoute(dresseurs, this.identifiant) == -1) {
				System.out.println("Identifiant invalide");
				System.out.println("Identifiant : ");
				this.identifiant = InputViaScanner.getInputString();
			}
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}
		boolean connected;
		try {
			connected = this.connection(this.identifiant);
			if (connected) {
				this.loadRanch();
				this.updateNiveau();
				this.pokemon = this.equipe[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		boolean inscription;
		try {
			inscription = this.saveData(id, mdp, nom);
			if (inscription) {
				this.equipe = (Pokemon[]) Pokedex.engendreRanchStatic();
				this.updateNiveau();
				this.pokemon = this.equipe[0];

			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Il vérifie si le fichier existe, si c'est le cas, il vérifie si l'utilisateur
	 * est déjà dans le fichier, si ce n'est pas
	 * le cas, il ajoute l'utilisateur au fichier, si c'est le cas, il renvoie false
	 *
	 * @param id  l'identifiant de l'utilisateur
	 * @param mdp le mot de passe de l'utilisateur
	 * @param nom Le nom d'utilisateur
	 * @return Un booléen
	 * @throws IOException    si le fichier n'existe pas
	 * @throws ParseException si le fichier n'est pas un fichier JSON
	 */
	@SuppressWarnings("unchecked")
	public boolean saveData(String id, String mdp, String nom) throws IOException, ParseException {
		if (testPresence()) {
			JSONParser parser = new JSONParser();
			JSONObject datafile = (JSONObject) parser
					.parse(new BufferedReader(new FileReader("./dataSave/DataFile.json")));
			JSONArray dresseurs = (JSONArray) datafile.get("user");
			if (testUserDejaAjoute(dresseurs, id) == -1) {
				assert (dresseurs.size() > 0);
				JSONObject dresseur = new JSONObject();
				dresseur.put("id", id);
				dresseur.put("mdp", mdp);
				dresseur.put("nom", nom);
				dresseurs.add(dresseur);
				FileWriter myWriter = new FileWriter("./dataSave/DataFile.json");
				myWriter.write(datafile.toJSONString());
				myWriter.close();
				System.out.println("Dresseur ajouté");
				return true;
			} else {
				System.out.println("Ce dresseur existe deja");
				return false;
			}
		} else {
			String JSONComplet;
			@SuppressWarnings("unused")
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
			System.out.println("Dresseur et fichier de jeu ajoutés");
			return true;
		}
	}

	/**
	 * Il renvoie l'index de l'utilisateur avec l'identifiant donné dans le JSON
	 * d'utilisateurs donné, ou -1 si
	 * l'utilisateur n'est pas dans le JSON
	 *
	 * @param dresseurs la panoplie d'utilisateurs
	 * @param id        l'identifiant de l'utilisateur
	 * @return L'index de l'utilisateur dans le JSONArray si l'utilisateur est déjà
	 *         dans le JSONArray, -1 sinon.
	 */
	public int testUserDejaAjoute(JSONArray dresseurs, String id) {
		int test = -1;
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

	/**
	 * Il vérifie si le fichier existe et si il est vide ou non
	 *
	 * @return Un booléen
	 */
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

	/**
	 * Il vérifie si l'utilisateur existe, si c'est le cas, il demande le mot de
	 * passe et si le mot de passe est correct, il
	 * connecte l'utilisateur
	 *
	 * @param id l'identifiant de l'utilisateur
	 * @return Un booléen
	 * @throws IOException    Si le fichier n'existe pas
	 * @throws ParseException Si le fichier n'est pas un JSON
	 */
	public boolean connection(String id) throws IOException, ParseException {
		if (testPresence()) {
			JSONParser parser = new JSONParser();
			JSONObject datafile = (JSONObject) parser
					.parse(new BufferedReader(new FileReader("./dataSave/DataFile.json")));
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
						this.nom = dresseur.get("nom").toString();
						while (!connected) {
							System.out.println("Mot de passe : ");
							String mdp = InputViaScanner.getInputString();
							if (dresseur.get("mdp").equals(mdp)) {
								System.out.println("Bienvenue " + dresseur.get("id"));
								// System.out.println("Vous etes connecté");
								connected = true;
							} else {
								System.out.println("Mot de passe invalide");
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

	/**
	 * Il charge le ranch de l'utilisateur à partir du fichier JSON
	 */
	public void loadRanch() {
		try {
			if (testPresence()) {
				JSONParser parser = new JSONParser();
				JSONObject datafile = (JSONObject) parser
						.parse(new BufferedReader(new FileReader("./dataSave/DataFile.json")));
				JSONArray dresseurs = (JSONArray) datafile.get("user");
				int indexDresseur = testUserDejaAjoute(dresseurs, this.identifiant);
				JSONObject dresseur = (JSONObject) dresseurs.get(indexDresseur);
				JSONArray ranchDresseur = (JSONArray) dresseur.get("ranch");
				for (int i = 0; i < ranchDresseur.size(); i++) {
					JSONObject objet = (JSONObject) ranchDresseur.get(i);
					int id = Integer.parseInt(objet.get("id").toString());
					String nom = objet.get("nom").toString();
					int niv = Integer.parseInt(objet.get("niv").toString());
					double xp = Double.parseDouble(objet.get("xp").toString());
					String nomEspPoke = (String) objet.get("espPoke");
					Espece espece = Pokedex.getEspeceParNom(nomEspPoke);
					Capacite[] capacites = new Capacite[4];
					for (int j = 0; j < ((JSONArray) objet.get("capacites")).size(); j++) {
						String nomCapaTemp = ((JSONArray) objet.get("capacites")).get(j).toString();
						Capacite capa = (Capacite) Pokedex.getCapaciteStatic(nomCapaTemp);
						capacites[j] = capa;
					}
					int force = 0;
					int vitesse = 0;
					int defense = 0;
					int pv = 0;
					int special = 0;

					for (int y = 0; y < 4; y++) {
						force = Integer.parseInt(
								((JSONObject) ((JSONArray) objet.get("stats")).get(0)).get("force").toString());
						vitesse = Integer.parseInt(
								((JSONObject) ((JSONArray) objet.get("stats")).get(0)).get("vitesse").toString());
						defense = Integer.parseInt(
								((JSONObject) ((JSONArray) objet.get("stats")).get(0)).get("defense").toString());
						pv = Integer
								.parseInt(((JSONObject) ((JSONArray) objet.get("stats")).get(0)).get("pv").toString());
						special = Integer.parseInt(
								((JSONObject) ((JSONArray) objet.get("stats")).get(0)).get("special").toString());
					}
					Stats statsEV = new Stats(force, vitesse, defense, pv, special);
					for (int w = 0; w < 4; w++) {
						force = Integer.parseInt(
								((JSONObject) ((JSONArray) objet.get("stats")).get(1)).get("force").toString());
						vitesse = Integer.parseInt(
								((JSONObject) ((JSONArray) objet.get("stats")).get(1)).get("vitesse").toString());
						defense = Integer.parseInt(
								((JSONObject) ((JSONArray) objet.get("stats")).get(1)).get("defense").toString());
						pv = Integer
								.parseInt(((JSONObject) ((JSONArray) objet.get("stats")).get(1)).get("pv").toString());
						special = Integer.parseInt(
								((JSONObject) ((JSONArray) objet.get("stats")).get(1)).get("special").toString());
					}
					Stats statsDV = new Stats(force, vitesse, defense, pv, special);

					int pvMax = Integer.parseInt(objet.get("pvMax").toString());

					this.equipe[i] = new Pokemon(id, nom, niv, xp, espece, capacites, pvMax, statsDV, statsEV);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Il enregistre le ranch de l'utilisateur dans le fichier JSON
	 */
	@SuppressWarnings("unchecked")
	public void enregistrerRanch() {
		try {
			if (testPresence()) {
				JSONParser parser = new JSONParser();
				JSONObject datafile = (JSONObject) parser
						.parse(new BufferedReader(new FileReader("./dataSave/DataFile.json")));
				JSONArray dresseurs = (JSONArray) datafile.get("user");
				int indexDresseur = testUserDejaAjoute(dresseurs, this.identifiant);
				JSONObject dresseur = (JSONObject) dresseurs.get(indexDresseur);
				JSONArray ranch = new JSONArray();
				for (int i = 0; i < this.equipe.length; i++) {
					JSONObject pokemon = new JSONObject();
					pokemon.put("id", this.equipe[i].id);
					pokemon.put("nom", this.equipe[i].nom);
					pokemon.put("niv", this.equipe[i].getNiveau());
					pokemon.put("aChangeNiveau", this.equipe[i].aChangeNiveau());
					pokemon.put("xp", this.equipe[i].xp);
					pokemon.put("espPoke", this.equipe[i].espPoke.getNom());
					JSONArray capacites = new JSONArray();
					for (int j = 0; j < this.equipe[i].getCapacitesApprises().length; j++) {
						capacites.add(this.equipe[i].getCapacitesApprises()[j].getNom());
					}
					pokemon.put("capacites", capacites);
					pokemon.put("pvMax", this.equipe[i].pvMax);
					JSONArray stats = new JSONArray();
					JSONObject statDV = new JSONObject();
					statDV.put("pv", this.equipe[i].statsDV.getPV());
					statDV.put("force", this.equipe[i].statsDV.getForce());
					statDV.put("defense", this.equipe[i].statsDV.getDefense());
					statDV.put("vitesse", this.equipe[i].statsDV.getVitesse());
					statDV.put("special", this.equipe[i].statsDV.getSpecial());
					stats.add(statDV);
					JSONObject statEV = new JSONObject();
					statEV.put("pv", this.equipe[i].statsEV.getPV());
					statEV.put("force", this.equipe[i].statsEV.getForce());
					statEV.put("defense", this.equipe[i].statsEV.getDefense());
					statEV.put("vitesse", this.equipe[i].statsEV.getVitesse());
					statEV.put("special", this.equipe[i].statsEV.getSpecial());
					stats.add(statEV);
					pokemon.put("stats", stats);
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
			e.printStackTrace();
		}
	}

	/////////////////////// methode de IDresseur ///////////////////////

	public IPokemon getPokemon(int i) {
		return this.equipe[i];
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
	public String toString() {
		return this.nom;

	}

	@Override
	public void soigneRanch() {
		for (Pokemon p : this.equipe) {
			p.soigne();
		}
	}
	
	@Deprecated
	public IPokemon[] getRanchCopy() {
		IPokemon[] copy = new IPokemon[6];
		for(int i=0;i<this.equipe.length;i++) {
			copy[i]=this.equipe[i];
		}
		return copy;
	}
	/////////////////////// methode de IStrategy ///////////////////////

	public abstract IPokemon choisitCombattant();

	public abstract IPokemon choisitCombattantContre(IPokemon pok);

	public abstract IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur);

	/////////////////////////////////////////////////////////////////////

	/**
	 * Retourne le pokémon actif du dresseur
	 *
	 * @return L'objet pokémon actif du dresseur.
	 */
	public Pokemon getPokemon() {
		return this.pokemon;
	}

	/**
	 * Cette fonction prend un objet IPokemon et définit le champ pokemon sur
	 * l'objet pokemon qui a été
	 * transmis.
	 * 
	 * @param pokemon L'objet Pokemon que vous souhaitez ajouter au Pokedex.
	 */
	public void setPokemon(IPokemon pokemon) {
		this.pokemon = (Pokemon) pokemon;
	}

	public void setPokemon(int i, Pokemon copy) {
		this.equipe[i]=copy;
	}

	/**
	 * Cette fonction renvoie l'attribut pokemonChoisi (le pokemonChoisi est le
	 * pokemon qui sera envoyé au combat lors de l'appel de la méthode
	 * echangeCombattant() )
	 *
	 * @return Le pokemonChoisi par le dresseur.
	 */
	public Pokemon getPokemonChoisi() {
		return pokemonChoisi;
	}

	/**
	 * Défini le pokemon que le dresseur veut envoyer au combat dans l'attribut
	 * pokemonChoisi
	 *
	 * @param pokemonChoisi le pokémon que le dresseur veut envoyer au combat
	 */
	public void setPokemonChoisi(IPokemon pokemonChoisi) {
		this.pokemonChoisi = (Pokemon) pokemonChoisi;
	}

	/**
	 * Cette fonction renvoie le ranch du ranch du Dresseur sous forme de tableau de
	 * Pokemon
	 *
	 * @return Le tableau équipe.
	 */
	public Pokemon[] getEquipe() {
		return this.equipe;
	}

	/**
	 * Cette fonction renvoie l'identifiant du dresseur
	 *
	 * @return L'identifiant du dresseur
	 */
	public String getIdentifiant() {
		return identifiant;
	}

	/**
	 * Cette fonction renvoie le mot de passe du dresseur
	 *
	 * @return La méthode renvoie la valeur de la variable motDepasse.
	 */
	public String getMotDepasse() {
		return motDepasse;
	}

	/**
	 * Cette fonction renvoie le nom du du dresseur
	 *
	 * @return Le nom du du dresseur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Cette fonction renvoie le niveau du dresseur
	 *
	 * @return La valeur de la variable niveau.
	 */
	public int getNiveau() {
		return niveau;
	}

	/**
	 * Cette fonction fixe la valeur de l'attribut identifiant du dresseur à la
	 * valeur du paramètre
	 * identifiant
	 *
	 * @param identifiant L'identifiant unique du dresseur.
	 */
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	/**
	 * Cette fonction définit le mot de passe du dresseur
	 *
	 * @param motDepasse Le mot de passe du dresseur
	 */
	public void setMotDepasse(String motDepasse) {
		this.motDepasse = motDepasse;
	}

	/**
	 * Cette fonction fixe la valeur de la variable nom à la valeur du paramètre nom
	 *
	 * @param nom Le nom du paramètre.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Cette fonction renvoie la valeur de l'attribut adversaire
	 * 
	 * @return La méthode renvoie la valeur de l'attribut' adversaire.
	 */
	public Dresseur getAdversaire() {
		return adversaire;
	}

	/**
	 * Cette fonction fixe la valeur de l'attribut adversaire à la valeur du
	 * paramètre adversaire
	 * 
	 * @param adversaire Le Dresseur adverse
	 */
	public void setAdversaire(Dresseur adversaire) {
		this.adversaire = adversaire;
	}

	/**
	 * Cette fonction calcule le niveau total du dresseur en fonction du niveau des
	 * pokemons de son ranch
	 */
	public void updateNiveau() {
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
	public IAttaque getActionChoisie() {
		return actionChoisie;
	}

	/**
	 * Cette fonction définit l'action choisie par le dresseur
	 *
	 * @param actionChoisie L'action que le dresseur a choisi d'utiliser.
	 */
	public void setActionChoisie(IAttaque actionChoisie) {
		this.actionChoisie = actionChoisie;
	}

	/**
	 * Cette fonction retourne vrai si le joueur peut combattre, faux si tout ces
	 * pokemons sont KO
	 *
	 * @return Une valeur booléenne.
	 */
	public boolean pouvoirSeBattre() {
		//System.out.println("=============================== nb pokemon alive" + this.getNbPokemonAlive());
		return this.getNbPokemonAlive() > 0;
	}

	/**
	 * Cette fonction renvoie le nombre de pokemons vivants du dresseur
	 * 
	 * @return Le nombre de pokemons vivant du dresseur
	 */
	public int getNbPokemonAlive() {
		//TODO emepcher d'avoir des etat avec 0 pkmn alive
		int nb = 0;
		/*
		for (int i = 0; i < this.getEquipe().length; i++) {
			if (!this.getEquipe()[i].estEvanoui() && ((Pokemon)this.getEquipe()[i]).echangePossible())
				System.out.println("\t\t" + (i + 1) + "- " + this.getEquipe()[i]);
			else if (this.getEquipe()[i].estEvanoui()){
				System.out.println("\t\t" + "KO " + this.getEquipe()[i]);
			}else {
				System.out.println("\t\t" + "OF " + this.getEquipe()[i]+" (!)impossible à envoyer au combat");
			}
		}
		*/
		for (Pokemon p : this.getEquipe()) {
			/*
			System.out.println(p.getNom());
			System.out.println("\test evanoui = "+p.estEvanoui());
			System.out.println("\tpeut etre echange = "+p.echangePossible());
			*/
			if (!p.estEvanoui() && p.echangePossible()) {
				nb++;
			}
		}
		return nb;
	}

	int getIndexPokemon(Pokemon p){
		int index = 0;
		boolean trouve = false;
		while (index < this.getEquipe().length && !trouve) {
			if(this.getEquipe()[index].getId() == p.getId()){
				trouve = true;
			}
			index++;
		}
		if(trouve) {
			return index-1;
		}else {
			return -1;
		}
	}

	/**
	 * Il renvoie un tableau de tous les mouvements possibles que le dresseur peut
	 * faire
	 * 
	 * @return Un tableau d'objets IAttaque.
	 */
	//methode codé en prévision de l'implémentation d'une IA élaborée
	public IAttaque[] getCoupsPossibles() {
		int nbCapaUtilisable = Math.max( this.getPokemon().getCapacitesApprises().length,1);
		/*
		ICapacite[] caps = this.getPokemon().getCapacitesApprises();
		for (int i = 0; i < caps.length; i++) {
			System.out.println("\t\t" + (i + 1) + "- " + caps[i]);
		}
		 */
        System.out.println("nbCapaUtilisable vaut : "+nbCapaUtilisable);
        

        
		int nbEchangesPossibles = this.getNbPokemonAlive()-1; //TODO empecher ca d'etre a -1 <=======
		/*
		for (int i = 0; i < this.getEquipe().length; i++) {
			if (!this.getEquipe()[i].estEvanoui() && ((Pokemon)this.getEquipe()[i]).echangePossible())
				System.out.println("\t\t" + (i + 1) + "- " + this.getEquipe()[i]);
			else if (this.getEquipe()[i].estEvanoui()){
				System.out.println("\t\t" + "KO " + this.getEquipe()[i]);
			}else {
				System.out.println("\t\t" + "OF " + this.getEquipe()[i]+" (!)impossible à envoyer au combat");
			}
		}
		*/
		System.out.println("nbEchangesPossibles vaut : "+nbEchangesPossibles);
		
		
		IAttaque[] listeCoup = new IAttaque[nbCapaUtilisable + nbEchangesPossibles];
		
		
		
		int cptTab = 0;
		if(nbEchangesPossibles>0) {
			for (Pokemon p : this.getEquipe()) {
				if (!p.estEvanoui() && p.echangePossible() && !this.getPokemon().equals(p)) {
					listeCoup[cptTab] = new Echange(p, this);
					//System.out.println(listeCoup[cptTab]);
					cptTab++;
				}
			}
		}
		if(this.getPokemon().getCapacitesApprises().length!=0) {
			for (int i = 0; i < nbCapaUtilisable; i++) {
				Capacite tmp = new Capacite(((Capacite) this.getPokemon().getCapacitesApprises()[i])); //TODO la copie est elle necessaire puisque no est censé etre dans une copie de l'etat du jeu
				//listeCoup[cptTab + i] = new Capacite(((Capacite) this.getPokemon().getCapacitesUtilisables()[i]));
				//System.out.println(listeCoup[cptTab+i]);
				listeCoup[cptTab + i] = tmp;
			}
		}else {
			try {
				listeCoup[cptTab] = Pokedex.createCapacite(((Capacite) Pokedex.getCapaciteStatic("Lutte")).id);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return listeCoup;
	}
	
	/**
	 * Il renvoie le mouvement que le pokémon peut apprendre à son niveau actuel
	 *
	 * @return La méthode renvoie le mouvement apprenable du pokemon.
	 */
	public Capacite canTeachAMove() {
		// System.out.println("appel de getLearnableMove().");
		return this.getPokemon().espPoke.getLearnableMove(this.getPokemon().getNiveau());
	}

	/**
	 * Cette fonction affiche l'équipe du joueur, utile lorsqu'on veut qu'il
	 * choisisse un pokemon
	 */
	public void showTeam() {
		for (Pokemon p : this.getEquipe()) {
			System.out.println(p);
			System.out.println(p.getStat());
		}
	}

	/**
	 * Permet d'afficher les statistique d'un dresseur comme son niveau et la
	 * composition de son équipe
	 */
	public void afficherStat() {
		System.out.println(this.getNom() + " est niveau " + this.getNiveau() + " et son equipe est composé de :");
		this.showTeam();
	}

	protected abstract Dresseur copy();
	
}
