package gestionPokemon;

import java.util.HashMap;
import java.util.Map.Entry;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IStat;
import interfaces.IType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Espece implements IEspece {
	private int id;
	public String nom;
	public Type type1;
	public Type type2;

	public int nivDepart;
	public int nivEvolution;
	public String evolution;
	private int expDeBase;

	//Stats specifiques :
	public Stats statsDeBase = new Stats();

	//Valeur d'Effort == puissance suite aux combats
	public Stats statsGain = new Stats();

	// Une Hashmap qui contient le niveau auquel un pokémon apprend un certain mouvement.
	private static HashMap<Capacite,Integer> capaciteSelonNiveau= new HashMap<>();


	public Espece(int id) {
		this.setId(id);
	}

	///////////////methode de IEspece/////////////////////////////////


	/**
	 * Il renvoie les statistiques de base du Pokémon
	 *
	 * @return Les statistiques de base du pokémon.
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
	 * Renvoie l'expérience de base du Pokémon.
	 *
	 * @return L'expérience de base du Pokémon.
	 */
	@Override
	public int getBaseExp() {
		return this.expDeBase;
	}

	/**
	 * Renvoie la statistique utilisée pour calculer les gains de cette statistique.
	 *
	 * @return La variable statsGain.
	 */
	@Override
	public IStat getGainsStat() {
		return this.statsGain;
	}

	/**
	 * Il prend l'objet JSON du pokemon, obtient les mouvements, obtient les noms des mouvements, obtient le niveau auquel le
	 * pokemon apprend le mouvement et place le mouvement et le niveau dans un hashmap
	 */
	public void initCapaciteSelonNiveau(){
		JSONObject jsonCapacite = Pokedex.getJSONfromURL("https://pokeapi.co/api/v2/pokemon/"+this.id);
		assert jsonCapacite != null;
		JSONArray listeMoves=(JSONArray) jsonCapacite.get("moves");
		System.out.println(this.nom);
		Pokedex pokedex=new Pokedex();
		for (Object listeMove : listeMoves) {
			JSONObject jsonNomsMoves = Pokedex.getJSONfromURL(((JSONObject) ((JSONObject) listeMove).get("move")).get("url").toString());
			assert jsonNomsMoves != null;
			String nomCapaTemp = ((JSONObject) ((JSONArray) jsonNomsMoves.get("names")).get(3)).get("name").toString();
			Capacite capaTemp = pokedex.capaciteParNom(nomCapaTemp);
			if (capaTemp != null) {
				JSONArray listeVersionGroupDetail = (JSONArray) ((JSONObject) listeMove).get("version_group_details");
				for (Object o : listeVersionGroupDetail) {
					capaciteSelonNiveau.put(capaTemp, Integer.parseInt((((JSONObject) o).get("level_learned_at")).toString()));
				}
			}
		}
	}


	/**
	 * Il renvoie un tableau de toutes les capacités que l'espèce peut apprendre
	 *
	 * @return Une gamme d'ICapacite
	 */
	@Override
	public ICapacite[] getCapSet() {
		Capacite[] liste = new Capacite[Espece.capaciteSelonNiveau.size()];
		int i =0;
		for (Entry<Capacite, Integer> c : Espece.capaciteSelonNiveau.entrySet()) {
			liste[i] = c.getKey();
			i++;
		}
		return liste;
	}

	/**
	 * Il renvoie l'espèce dans laquelle cette espèce évolue
	 *
	 * @param niveau Le niveau du Pokémon.
	 * @return L'évolution du pokémon
	 */
	public IEspece getEvolution(int niveau) {
		return Pokedex.especeParNom(this.evolution);
	}

	/**
	 * Renvoie un tableau des types des deux opérandes.
	 *
	 * @return Un tableau des deux types.
	 */
	@Override
	public IType[] getTypes() {
		return new Type[]{this.type1,this.type2};
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
	 * Cette fonction définit l'id de l'Espece sur la valeur du paramètre id.
	 *
	 * @param id L'identifiant de l'Espece.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * > Cette fonction fixe la valeur de la variable `expDeBase` à la valeur du paramètre `expDeBase`
	 *
	 * @param expDeBase L'expérience de base du Pokémon.
	 */
	public void setExpDeBase(int expDeBase) {
		this.expDeBase = expDeBase;
	}

	/**
	 * Il renvoie la valeur de la variable privée `expDeBase`
	 *
	 * @return La variable expDeBase est renvoyée.
	 */
	public int obtenirExpDeBase() {
		return this.expDeBase;
	}

	/**
	 * Il renvoie la première capacité disponible d'un pokémon
	 *
	 * @param pokemon le pokémon qui utilisera le mouvement
	 * @return La méthode renvoie le premier objet Capacite disponible pour l'objet Pokemon.
	 */
	public Capacite capaciteDispo(Pokemon pokemon){
		for (Entry<Capacite, Integer> c : Espece.capaciteSelonNiveau.entrySet()) {
			if (Integer.parseInt(c.getKey().toString())<=pokemon.niv){
				return ((Capacite)c);
			}
		}
		return null;
	}

}