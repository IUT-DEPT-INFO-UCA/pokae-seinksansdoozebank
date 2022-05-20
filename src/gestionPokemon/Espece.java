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

	private static HashMap<Capacite,Integer> capaciteSelonNiveau=new HashMap<Capacite,Integer>();


	public Espece(int id) {
		this.setId(id);
	}

	///////////////methode de IEspece/////////////////////////////////


	@Override
	public IStat getBaseStat() {
		return this.statsDeBase;
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public int getNiveauDepart() {
		return this.nivDepart;
	} 

	@Override
	public int getBaseExp() {
		return this.expDeBase;
	}

	@Override
	public IStat getGainsStat() {
		return this.statsGain;
	}
	public void initCapaciteSelonNiveau(){
		JSONObject jsonCapacite = Pokedex.getJSONfromURL("https://pokeapi.co/api/v2/pokemon/"+this.id);
		assert jsonCapacite != null;
		JSONArray listeMoves=(JSONArray) jsonCapacite.get("moves");
//		System.out.println("test");
		System.out.println(this.nom);
		Pokedex pokedex=new Pokedex();
		for(int i=0;i<listeMoves.size();i++){
//			System.out.println(i);

			JSONObject jsonNomsMoves = Pokedex.getJSONfromURL(((JSONObject)((JSONObject) listeMoves.get(i)).get("move")).get("url").toString());
			String nomCapaTemp=((JSONObject)((JSONArray) jsonNomsMoves.get("names")).get(3)).get("name").toString();
			Capacite capaTemp= (Capacite) pokedex.capaciteParNom(nomCapaTemp);

			System.out.println("capaTemp =  "+capaTemp);
			if(capaTemp!=null){
				JSONArray listeVersionGroupDetail=(JSONArray) ((JSONObject) listeMoves.get(i)).get("version_group_details");
				System.out.println(i+"    "+listeVersionGroupDetail.size());
				for(int j=0;j<listeVersionGroupDetail.size();j++){

					if(((JSONObject)((JSONObject) listeVersionGroupDetail.get(j)).get("version_group")).get("name")=="red-blue"){
						capaciteSelonNiveau.put(capaTemp,Integer.parseInt((((JSONObject)listeVersionGroupDetail.get(j)).get("level_learned_at")).toString()));

					}
				}
			}

		}
	}


	@Override
	public ICapacite[] getCapSet() {
		Capacite[] liste = new Capacite[Espece.capaciteSelonNiveau.size()];
		int i =0;
		for (Entry<Capacite, Integer> c : Espece.capaciteSelonNiveau.entrySet()) {
			liste[i] = (Capacite) c.getKey();
			i++;
		}
		return liste;
	}

	public IEspece getEvolution(int niveau) {
		return Pokedex.especeParNom(this.evolution);
	}

	@Override
	public IType[] getTypes() {
		Type[] tab = {this.type1,this.type2};
		return tab;
	}
	//////////////////////////////////////////////////////////////

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setExpDeBase(int expDeBase) {
		this.expDeBase = expDeBase;
	}

	public int obtenirExpDeBase() {
		return this.expDeBase;
	}

	public Capacite capaciteDispo(Pokemon pokemon){
		for (Entry<Capacite, Integer> c : Espece.capaciteSelonNiveau.entrySet()) {
			if (Integer.parseInt(c.getKey().toString())<=pokemon.niv){
				return ((Capacite)c);
			}
		}
		return null;
	}

}