package gestionPokemon;

import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IStat;
import interfaces.IType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Cette classe est utilisée pour créer une espèce de Pokémon
 * Elle est utilisee pour generer les capacites disponible selon les niveaux
 */
public class Espece implements IEspece {
	/**
	 * Id de l'espece
	 */
	private int id;
	/**
	 * Nom de l'espece
	 */
	public String nom;
	/**
	 * Premier type de l'espece
	 */
	public Type type1;
	/**
	 * Deuxieme type de l'espece
	 */
	public Type type2;

	/**
	 * Niveau de depart de l'espece
	 */
	public int nivDepart;
	/**
	 * Niveau de la prochaine evolution de l'espece
	 */
	public int nivEvolution;
	/**
	 * Prochaine evolution de l'espece
	 */
	public String evolution;
	/**
	 * Expérience de base de l'espece
	 */
	private int expDeBase;

	/**
	 * les statistiques de base de l'espece
	 */
	public Stats statsDeBase = new Stats();

	/**
	 * Les statistiques de Gain de l'espece
	 */
	public Stats statsGain = new Stats();

	/**
	 * Une Hashmap qui contient le niveau auquel un pokemon apprend un certain
	 * mouvement.
	 */

	public HashMap<Capacite, Integer> capaciteSelonNiveau = new HashMap<>();

	/**
	 * Constructeur de espece qui l'instancie avec son id
	 * 
	 * @param id l'identifiant du Pokemon
	 */
	public Espece(int id) {
		this.setId(id);
	}

	@Override
	public String toString() {
		return "Espece{" +
				"id=" + id +
				", nom='" + nom + '\'' +
				", type1=" + type1 +
				", type2=" + type2 +
				", nivDepart=" + nivDepart +
				", nivEvolution=" + nivEvolution +
				", evolution='" + evolution + '\'' +
				", expDeBase=" + expDeBase +
				", statsDeBase=" + statsDeBase +
				", statsGain=" + statsGain +
				'}';
	}
	//////////////////////// methode de IEspece/////////////////////////////////

	/**
	 * Il renvoie les statistiques de base du Pokemon
	 *
	 * @return Les statistiques de base du pokemon.
	 */
	@Override
	public IStat getBaseStat() {
		return this.statsDeBase;
	}

	/**
	 * Cette fonction renvoie le nom de la personne
	 *
	 * @return Le nom de la personne.
	 */
	@Override
	public String getNom() {
		return this.nom;
	}

	/**
	 * Cette fonction renvoie le niveau auquel le joueur commence la partie
	 *
	 * @return Le niveau du joueur.
	 */
	@Override
	public int getNiveauDepart() {
		return this.nivDepart;
	}

	/**
	 * Renvoie l'experience de base du Pokemon.
	 *
	 * @return L'experience de base du Pokemon.
	 */
	@Override
	public int getBaseExp() {
		return this.expDeBase;
	}

	/**
	 * Renvoie la statistique utilisee pour calculer les gains de cette statistique.
	 *
	 * @return La variable statsGain.
	 */
	@Override
	public IStat getGainsStat() {
		return this.statsGain;
	}

	/**
	 * Il obtient les moves du pokemon de l'API, puis il obtient les noms des moves
	 * de l'API, puis il obtient le
	 * niveau des moves de l'API
	 */
	public void initCapaciteSelonNiveau() {
		try {
			boolean testPresencePoke = testPresence("Esp" + this.id + ".json");
			if (testPresencePoke) {
				JSONParser parser = new JSONParser();
				JSONObject jsonPoke = (JSONObject) parser
						.parse(new BufferedReader(new FileReader("./JSON/Esp" + this.id + ".json")));
				InitCapPokemon(jsonPoke);
			} else {
				JSONObject pokemon = Pokedex.downloadJSONfromURL(("Esp" + this.id),
						("https://pokeapi.co/api/v2/pokemon/" + this.id));
				assert pokemon != null;
				InitCapPokemon(pokemon);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Initialise les capacites d'une espece de Pokemon en lisant un JSON
	 * 
	 * @param jsonPokemon Le JSONObject du Pokémon
	 * @throws IOException    une exception
	 * @throws ParseException une autre exception
	 */
	private void InitCapPokemon(JSONObject jsonPokemon) throws IOException, ParseException {
		assert jsonPokemon != null;
		JSONArray listeMoves = (JSONArray) jsonPokemon.get("moves");
		for (Object listeMove : listeMoves) {
			boolean testPresenceCapacite = testPresence(
					((JSONObject) ((JSONObject) listeMove).get("move")).get("name").toString() + ".json");
			JSONObject jsonNomsMoves;
			if (testPresenceCapacite) {
				JSONParser parser = new JSONParser();
				jsonNomsMoves = (JSONObject) parser.parse(new FileReader("./JSON/"
						+ (((JSONObject) ((JSONObject) listeMove).get("move")).get("name").toString()) + ".json"));
			} else {
				jsonNomsMoves = Pokedex.downloadJSONfromURL(
						((JSONObject) ((JSONObject) listeMove).get("move")).get("name").toString(),
						((JSONObject) ((JSONObject) listeMove).get("move")).get("url").toString());
			}
			assert jsonNomsMoves != null;
			String nomCapaTemp = ((JSONObject) ((JSONArray) jsonNomsMoves.get("names")).get(3)).get("name").toString();
			Capacite capaTemp = (Capacite) Pokedex.getCapaciteStatic(nomCapaTemp);
			if (capaTemp != null) {
				JSONArray listeVersionGroupDetail = (JSONArray) ((JSONObject) listeMove).get("version_group_details");
				for (Object o : listeVersionGroupDetail) {
					if ((Objects.equals((String) (((JSONObject) ((JSONObject) o).get("version_group")).get("name")),
							"red-blue"))
							&& (Objects.equals(
									(String) (((JSONObject) ((JSONObject) o).get("move_learn_method")).get("name")),
									"level-up"))) {
						capaTemp.nivNecessaire = Integer
								.parseInt((((JSONObject) o).get("level_learned_at")).toString());
						capaciteSelonNiveau.put(capaTemp,
								Integer.parseInt((((JSONObject) o).get("level_learned_at")).toString()));
					}
				}
			}
		}
	}

	/**
	 * Il vérifie si un fichier existe dans un répertoire
	 * 
	 * @param file le nom du fichier à tester
	 * @return Une valeur booléenne.
	 */
	private boolean testPresence(String file) {
		File repertoire = new File("./JSON/");
		String[] listeFichiers = repertoire.list();
		boolean testPresence = false;
		if (listeFichiers == null) {
			System.out.println("Mauvais répertoire");
		} else {
			int i = 0;
			while (!testPresence && i < listeFichiers.length) {
				if (listeFichiers[i].equals(file)) {
					testPresence = true;
				}
				i++;
			}
		}
		return testPresence;
	}

	/**
	 * Il renvoie un tableau de toutes les capacites que l'espece peut apprendre
	 *
	 * @return Une gamme d'ICapacite
	 */
	@Override
	public ICapacite[] getCapSet() {
		Capacite[] liste = new Capacite[this.capaciteSelonNiveau.size()];
		int i = 0;
		for (Entry<Capacite, Integer> c : this.capaciteSelonNiveau.entrySet()) {
			liste[i] = c.getKey();
			i++;
		}
		return liste;
	}

	/**
	 * Il renvoie l'espece dans laquelle cette espece evolue
	 *
	 * @param niveau Le niveau du Pokemon.
	 * @return L'evolution du pokemon
	 */
	public IEspece getEvolution(int niveau) {
		if (niveau == this.nivEvolution) {
			return Pokedex.getEspeceParNom(this.evolution);
		} else {
			return null;
		}
	}

	/**
	 * Renvoie un tableau des types des deux operandes.
	 *
	 * @return Un tableau des deux types.
	 */
	@Override
	public IType[] getTypes() {
		return new Type[] { this.type1, this.type2 };
	}
	//////////////////////////////////////////////////////////////

	/**
	 * Cette fonction renvoie l'identifiant de l'Espece.
	 *
	 * @return L'identifiant de l'Espece.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Cette fonction definit l'id de l'Espece sur la valeur du parametre id.
	 *
	 * @param id L'identifiant de l'Espece.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Cette fonction fixe la valeur de l'attribut `expDeBase` a la valeur du
	 * parametre `expDeBase`
	 *
	 * @param expDeBase L'experience de base du Pokemon.
	 */
	public void setExpDeBase(int expDeBase) {
		this.expDeBase = expDeBase;
	}

	/**
	 * Il renvoie la valeur de l'attibut privee `expDeBase`
	 *
	 * @return La variable expDeBase est renvoyee.
	 */
	public int getExpDeBase() {
		return this.expDeBase;
	}

	/**
	 * Il renvoie la premiere capacite disponible d'un pokemon
	 *
	 * @param pokemon le pokemon qui utilisera le mouvement
	 * @return La methode renvoie le premier objet Capacite disponible pour l'objet
	 *         Pokemon.
	 */
	public Capacite[] capaciteDispo(Pokemon pokemon) {
		Capacite[] tabCapaciteDispo = new Capacite[50];

		int i = 0;
		for (Entry<Capacite, Integer> c : this.capaciteSelonNiveau.entrySet()) {
			if (Integer.parseInt(c.getValue().toString()) <= pokemon.getNiveau()) {
				tabCapaciteDispo[i] = c.getKey();
				i++;
			}
		}
		return tabCapaciteDispo;
	}

	/**
	 * Il renvoie un mouvement que le pokémon peut apprendre au niveau donné
	 * 
	 * @param niv le niveau du pokémon
	 * @return La méthode renvoie un objet Capacite.
	 */
	public Capacite getLearnableMove(int niv) {
		for (Entry<Capacite, Integer> c : this.capaciteSelonNiveau.entrySet()) {
			if (c.getValue() == niv) {
				// System.out.println("return de "+c.getKey()+ " parfait pour le niveau "+niv);
				return c.getKey();
			} else {
				// System.out.println(this.getNom()+" ne peut pas aprendre "+c.getKey()+" au
				// niveau "+niv);
			}
		}
		return null;
	}

	/**
	 * Il imprime le nom de la capacité et le niveau de la capacité pour chaque
	 * element de la liste que le pokemon peut apprendre
	 */
	public void showCapSet() {
		for (Entry<Capacite, Integer> c : this.capaciteSelonNiveau.entrySet()) {
			System.out.println("\t" + c.getKey().getNom() + " : niv " + c.getValue());
		}
	}

}