package gestionPokemon;

import java.util.HashMap;
import java.util.Map.Entry;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IStat;
import interfaces.IType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


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
	 * Une Hashmap qui contient le niveau auquel un pokemon apprend un certain mouvement.
 	 */

	private static HashMap<Capacite,Integer> capaciteSelonNiveau= new HashMap<>();

	/**
	 * Constructeur de espece qui l'instancie avec son id
	 * 
	 * @param id l'identifiant du Pokemon
	 */
	public Espece(int id) {
		this.setId(id);
	}

	/////////////// methode de IEspece/////////////////////////////////

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
	 * > Cette fonction renvoie le nom de la personne
	 *
	 * @return Le nom de la personne.
	 */
	@Override
	public String getNom() {
		return this.nom;
	}

	/**
	 * > Cette fonction renvoie le niveau auquel le joueur commence la partie
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
	 * Il prend l'objet JSON du pokemon, obtient les mouvements, obtient les noms
	 * des mouvements, obtient le niveau auquel le
	 * pokemon apprend le mouvement et place le mouvement et le niveau dans un
	 * hashmap
	 */
	public void initCapaciteSelonNiveau() {
		JSONObject jsonCapacite = Pokedex.getJSONfromURL("https://pokeapi.co/api/v2/pokemon/" + this.id);
		assert jsonCapacite != null;
		JSONArray listeMoves = (JSONArray) jsonCapacite.get("moves");
		System.out.println(this.nom);
		Pokedex pokedex = new Pokedex();
		for (Object listeMove : listeMoves) {
			JSONObject jsonNomsMoves = Pokedex
					.getJSONfromURL(((JSONObject) ((JSONObject) listeMove).get("move")).get("url").toString());
			assert jsonNomsMoves != null;
			String nomCapaTemp = ((JSONObject) ((JSONArray) jsonNomsMoves.get("names")).get(3)).get("name").toString();
			Capacite capaTemp = pokedex.capaciteParNom(nomCapaTemp);
			if (capaTemp != null) {
				JSONArray listeVersionGroupDetail = (JSONArray) ((JSONObject) listeMove).get("version_group_details");
				for (Object o : listeVersionGroupDetail) {
					capaciteSelonNiveau.put(capaTemp,
							Integer.parseInt((((JSONObject) o).get("level_learned_at")).toString()));
				}
			}
		}
	}

	/**
	 * Il renvoie un tableau de toutes les capacites que l'espece peut apprendre
	 *
	 * @return Une gamme d'ICapacite
	 */
	@Override
	public ICapacite[] getCapSet() {
		Capacite[] liste = new Capacite[Espece.capaciteSelonNiveau.size()];
		int i = 0;
		for (Entry<Capacite, Integer> c : Espece.capaciteSelonNiveau.entrySet()) {
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
		return Pokedex.especeParNom(this.evolution);
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
	 * Retourne l'objet type correspondant au nom du type passé en paramètre
	 * 
	 * @param t nom du type qui doit être ajouté au Pokemon
	 * @return l'objet type qui va être attribuer au Pokemon
	 */
	public Type setType(String t) {
		int i=0;
		while(i<Type.getListe().length && Type.getListe()[i].getNom().equals(t)) {
			i++;
		}
		if( Type.getListe()[i].getNom().equals(t)) {
			return Type.getListe()[i];
		}
		return null;
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
	public Capacite capaciteDispo(Pokemon pokemon) {
		for (Entry<Capacite, Integer> c : Espece.capaciteSelonNiveau.entrySet()) {
			if (Integer.parseInt(c.getKey().toString()) <= pokemon.niv) {
				return ((Capacite) c);
			}
		}
		return null;
	}

}