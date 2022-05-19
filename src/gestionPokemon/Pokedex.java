package gestionPokemon;
<<<<<<< Updated upstream
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokemon;
//import interfaces.IType;
import org.json.simple.JSONArray;
=======
import interfaces.*;
//import org.json.simple.JSONArray;
>>>>>>> Stashed changes
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
public class Pokedex {
    public static Espece[] listeEspece;
    public static Capacite[] listeCapacite=new Capacite[110];
//    static int nombrePokemons;

    public IPokemon[] engendreRanch(){
        IPokemon[] listePokeAleatoire=new Pokemon[6];
        for (int i=0; i<6; i++){
            listePokeAleatoire[0]=new Pokemon(listeEspece[(int) (Math.random() * ((151) + 1))]);
        }
        return listePokeAleatoire;
    }
    public IEspece getInfo(String nomEspece){
        int i=0;
        boolean trouve=false;
        IEspece info = null;
        while(i<listeEspece.length&& !trouve){
            if (listeEspece[i].nom.equals(nomEspece)){
                trouve=true;
                info=listeEspece[i];
            }
            i++;
        }
        return info;
    }

    public Double getEfficacite(Type attaque, Type defense){
        return attaque.getCoeffDamageOn(defense);
    }
    public void getCapaciteSet(){
        System.out.println(Arrays.toString(listeCapacite));
    }
    public ICapacite getCapacite(String nomCapacite){
        int i=0;
        boolean trouve=false;
        ICapacite capacite = null;
        while(i<listeCapacite.length&& !trouve){
            if (listeEspece[i].nom.equals(nomCapacite)){
                trouve=true;
                capacite=listeCapacite[i];
            }
            i++;
        }
        return capacite;
    }
    public ICapacite getCapacite(int nunCapacite){
        return listeCapacite[nunCapacite];
    }
    //TODO : méthodes déjà faites initiative


    public Espece createEspece(int id) throws FileNotFoundException {
        Espece espece = new Espece(id);
        File fichierCSV = new File("./listePokemon1G_new.csv");
        try (Scanner scannerCSV = new Scanner(fichierCSV)) {
			scannerCSV.useDelimiter(";");
			scannerCSV.nextLine();
			while (scannerCSV.hasNext()) {
			    if(Integer.parseInt(scannerCSV.next())==id){
			        espece.nom=scannerCSV.next();
			        System.out.println(espece);

			        espece.getBaseStat().setPV(Integer.parseInt(scannerCSV.next()));
			        espece.getBaseStat().setForce(Integer.parseInt(scannerCSV.next()));
			        espece.getBaseStat().setDefense(Integer.parseInt(scannerCSV.next()));
			        espece.getBaseStat().setSpecial(Integer.parseInt(scannerCSV.next()));
			        espece.getBaseStat().setVitesse(Integer.parseInt(scannerCSV.next()));
			        espece.setExpDeBase(Integer.parseInt(scannerCSV.next()));
			        espece.getGainsStat().setPV(Integer.parseInt(scannerCSV.next()));
			        espece.getGainsStat().setForce(Integer.parseInt(scannerCSV.next()));
			        espece.getGainsStat().setDefense(Integer.parseInt(scannerCSV.next()));
			        espece.getGainsStat().setSpecial(Integer.parseInt(scannerCSV.next()));
			        espece.getGainsStat().setVitesse(Integer.parseInt(scannerCSV.next()));
			        
			        espece.type1= new Type(scannerCSV.next());
			        espece.type2= new Type(scannerCSV.next());
			        espece.nivDepart=Integer.parseInt(scannerCSV.next());
			        espece.nivEvolution=Integer.parseInt(scannerCSV.next());
			        //ca c'est dramatique pcq mewtwo doit pas evoluer et pourtant la il peut evoluer en mew
			        espece.evolution=createEspece(id+1);
			    }
			    else{
			        scannerCSV.nextLine();
			    }

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return espece;
    }
    public void createListeEspece() throws FileNotFoundException {
        for (int i=1; i<152;i++){
            listeEspece[i]=createEspece(i);
        }
    }
    /* CSV utilisation
    public Capacite createCapacite(int id) throws FileNotFoundException {
        Capacite capacite = new Capacite(id);
<<<<<<< Updated upstream
        File fichierCSV = new File("./listePokemon1G_new.csv");
        try (Scanner scannerCSV = new Scanner(fichierCSV)) {
			scannerCSV.useDelimiter(";");
			scannerCSV.nextLine();
			while (scannerCSV.hasNext()) {
			    try (Scanner scannerTemp = scannerCSV) {
					for (int i = 0; i <4;i++){
					    scannerTemp.next();
					}
					if (Integer.parseInt(scannerTemp.next())==id){
					    capacite.nom=scannerCSV.next();
					    capacite.puissance=Integer.parseInt(scannerCSV.next());
					    capacite.precision=Integer.parseInt(scannerCSV.next());
					    capacite.ppBase=Integer.parseInt(scannerCSV.next());
					    capacite.pp=capacite.ppBase;
					}
					else{
					    scannerCSV.nextLine();
					}
				}
=======
        File fichierCSV = new File("./csv/listeCapacites.csv");
        try {
            try (Scanner scannerCSV = new Scanner(fichierCSV)) {
				scannerCSV.useDelimiter(";");
				scannerCSV.nextLine();
				while (scannerCSV.hasNext()) {
				    try {
				        String ligneTemp = scannerCSV.nextLine();
				        String[] tabLigneTemp = ligneTemp.split(";");

						if (Integer.parseInt(tabLigneTemp[4])==id){
						    capacite.nom=tabLigneTemp[0];
//                        System.out.println(capacite.nom);
						    capacite.puissance=Integer.parseInt(tabLigneTemp[1]);
						    capacite.precision= Double.parseDouble(tabLigneTemp[2]);
						    capacite.ppBase=Integer.parseInt(tabLigneTemp[3]);
						    capacite.pp=capacite.ppBase;
						}
						else{
//					    scannerCSV.nextLine();
						}
					} catch (NumberFormatException e) {
				        throw new RuntimeException(e);
				    }
				}
				scannerCSV.close();
>>>>>>> Stashed changes
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return capacite;

    }
    */
    public static JSONObject getJSONfromURL(String url) {
        try {
            URL hp = new URL(url);
            HttpURLConnection hpCon = (HttpURLConnection) hp.openConnection();
            hpCon.connect();

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(hpCon.getInputStream()));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr = "";
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
//                System.out.println(inputStr);
            }
            inputStr = responseStrBuilder.toString();

            streamReader.close();

            return (JSONObject) new JSONParser().parse(inputStr);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Capacite createCapacite(int i){
        Capacite capacite=new Capacite(i);
        JSONObject jsonCapacite = getJSONfromURL("https://pokeapi.co/api/v2/move/"+i);
        assert jsonCapacite != null;
        if(jsonCapacite.get("accuracy")==null){
            capacite.precision=-1;
        }
        else{
            capacite.precision=Integer.parseInt(jsonCapacite.get("accuracy").toString());
        }

        capacite.nom=jsonCapacite.get("name").toString();
        capacite.pp=Integer.parseInt(jsonCapacite.get("pp").toString());
        capacite.ppBase= capacite.pp;
        System.out.println(" i = "+i);
        if(jsonCapacite.get("power")==null){
            capacite.puissance=-1;
        }
        else{
            capacite.puissance=Integer.parseInt(jsonCapacite.get("power").toString());
        }

        if ((((JSONObject)jsonCapacite.get("damage_class")).get("name").toString()).equals("physical")){
            capacite.categorie=CategorieAttaque.PHYSIQUE;
        }
        else{
            capacite.categorie=CategorieAttaque.SPECIALE;
        }
        JSONObject jsonNomType=getJSONfromURL(((JSONObject)jsonCapacite.get("type")).get("url").toString());
        assert jsonNomType != null;
        capacite.type=new Type((((JSONObject)(((JSONArray)jsonNomType.get("names")).get(3))).get("name").toString()));
        System.out.println(capacite.type.id);
        return capacite;
    }
    public void createListeCapacite() throws FileNotFoundException {
        for (int i =1;i<110;i++){
            listeCapacite[i]=createCapacite(i);
            System.out.println(listeCapacite[i].nom);
        }
    }

    public Espece especeParId(int id) {
        return listeEspece[id];
    }

    public Espece especeParNom(String nom){
        int i=0;
        boolean tester = false;
        while(i<listeEspece.length&& !tester){
            if (listeEspece[i].nom.equals(nom)){
                tester = true;
            }
            i++;
        }
        return listeEspece[i];
    }

    public Capacite capaciteParId(int id){
        return listeCapacite[id];
    }

    public Capacite capaciteParNom(String nom){
        int i=0;
        boolean tester = false;
        while(i<listeCapacite.length&& !tester){
            if (listeCapacite[i].nom.equals(nom)){
                tester=true;
            }
            i++;
        }
        return listeCapacite[i];
    }

    public static void main(String[] args) throws FileNotFoundException {
        Pokedex pokedex = new Pokedex();
        pokedex.createListeCapacite();
        pokedex.getCapaciteSet();
    }
}
