package gestionPokemon;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokedex;
import interfaces.IPokemon;
import interfaces.IType;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.util.Arrays;
import java.util.Scanner;

/**
 * Classe appelée Pokedex qui gère toutes les espèces et toutes les capacites
 * On peut interroger la classe pokedex en lui demandant des capacites ou des especes
 * Elle permet aussi de generer le ranch d'un dresseur
 */

public class Pokedex implements IPokedex{
    /**
     * Instanciation du nombre de pokemon
     */
    private static int nbPokemon = 152;
    /**
     * Instanciation
     */
    private static int nbCapacite = 111;
    /**
     * Création d'un tableau d'objets Espece.
     */
    public static Espece[] listeEspece = new Espece[nbPokemon];
    /**
     * Creation d'un tableau de 110 objets Capacite.
     */
    public static Capacite[] listeCapacite = new Capacite[nbCapacite];
    
	//////////////////////// methode de IPokedex/////////////////////////////////

    /**
     * @deprecated Il faut préférer utiliser la version static de cette méthode
     */
	@Override
	public IPokemon[] engendreRanch() {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * @deprecated Il faut préférer utiliser la version static de cette méthode
     */
	@Override
	public IEspece getInfo(String nomEspece) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * @deprecated Il faut préférer utiliser la version static de cette méthode
     */
	@Override
	public Double getEfficacite(IType attaque, IType defense) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * @deprecated Il faut préférer utiliser la version static de cette méthode
     */
	@Override
	public ICapacite getCapacite(String nomCapacite) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * @deprecated Il faut préférer utiliser la version static de cette méthode
     */
	@Override
	public ICapacite getCapacite(int numCapacite) {
		// TODO Auto-generated method stub
		return null;
	}
    //////////////////////////////////////////////////////////////////

    /**
     * Il crée une liste de 6 Pokémon aléatoires
     *
     * @return Une liste de 6 Pokémon aléatoires.
     */
    public static IPokemon[] engendreRanchStatic() {
        IPokemon[] listePokeAleatoire = new Pokemon[6];
        for (int i = 0; i < 6; i++) {
            listePokeAleatoire[i] = new Pokemon(listeEspece[(int) (Math.random() * ((nbPokemon) + 1))]);
        }
        return listePokeAleatoire;
    }

    /**
     * Il renvoie les informations sur une espèce, compte tenu de son nom
     *
     * @param nomEspece le nom de l'espèce
     * @return Les informations de l'espèce.
     */
    public static IEspece getInfoStatic(String nomEspece) {
        int i = 0;
        boolean trouve = false;
        IEspece info = null;
        while (i < listeEspece.length && !trouve) {
            if (listeEspece[i].nom.equals(nomEspece)) {
                trouve = true;
                info = listeEspece[i];
            }
            i++;
        }
        return info;
    }

    /**
     * Il renvoie le coefficient de dégâts d'une attaque de type `attaque` sur une
     * défense de type `defense`
     *
     * @param attaque Le type d'attaque
     * @param defense Le type du Pokémon défenseur
     * @return Le coefficient de dégâts de l'attaque sur la défense.
     */
    public static Double getEfficacite(Type attaque, Type defense) {
        return attaque.getCoeffDamageOn(defense);
    }

    /**
     * Il renvoie la capacité avec le nom `nomCapacite` de la liste des capacités
     * `listeCapacite`
     *
     * @param nomCapacite le nom de la capacité à trouver
     * @return La méthode renvoie l'objet ICapacite qui porte le même nom que la
     *         chaîne nomCapacite.
     */
    public static ICapacite getCapaciteStatic(String nomCapacite) {
        int i = 1;
        boolean tester = false;
        while (i < listeCapacite.length && !tester) {
            if (listeCapacite[i].nom.equals(nomCapacite)) {
                tester = true;
            }
            i++;
        }
        if (!tester) {
            return null;
        } else {
            return listeCapacite[i - 1];
        }
    }

    /**
     * Il renvoie la capacité à l'indice donné
     *
     * @param numCapacite Le numéro de la capacité que vous souhaitez obtenir.
     * @return La méthode renvoie la capacité du pokémon.
     */
    public static ICapacite getCapaciteStatic(int numCapacite) {
        return listeCapacite[numCapacite];
    }
    

    
    /**
     * Il renvoie l'espèce avec l'identifiant donné
     *
     * @param id l'identifiant de l'espèce
     * @return L'espèce avec le numéro d'identification.
     */
    public static Espece getEspeceParId(int id) {
        return listeEspece[id];
    }

    /**
     * Il renvoie l'espèce avec le nom donné
     *
     * @param nom le nom de l'espèce
     * @return L'espèce avec le nom donné en paramètre.
     */
    public static Espece getEspeceParNom(String nom) {
        int i = 1;
        boolean tester = false;
        while (i < listeEspece.length-1 && !tester) {
            if (listeEspece[i].nom.equals(nom)) {
                tester = true;
            }
            i++;
        }
        return listeEspece[i-1];
    }
    
    /*
    /**
     * Il renvoie la capacité avec l'identifiant donné
     *
     * @param id L'identifiant du déplacement.
     * @return La capacité avec l'identifiant donné.
     
    public static Capacite capaciteParId(int id) {
        return listeCapacite[id];
    }
    //
    /**
     * Il renvoie le premier objet `Capacite` du tableau `listeCapacite` dont
     * l'attribut `nom` est égal au paramètre `nom`
     *
     * @param nom le nom de la capacité
     * @return La méthode retourne la capacité avec le nom donné en paramètre.
     //
    public static Capacite capaciteParNom(String nom) {
        int i = 1;
        boolean trouve = false;
        ICapacite capacite = null;
        while (i < listeCapacite.length && !trouve) {
            if (listeEspece[i].nom.equals(nomCapacite)) {
                trouve = true;
                capacite = listeCapacite[i];
            }
            i++;
        }
        return capacite;
    }
    */

    /**
     * Il crée la liste des espèces et la liste des capacités
     */
    public static void initialiser() {
        try {
			Pokedex.createListeCapacite();
	        Pokedex.createListeEspece();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * Il lit un fichier CSV et crée un objet Espece à partir des données qu'il
     * trouve dans le fichier
     *
     * @param id l'identifiant du pokémon
     * @return L'objet espèce concerné
     * @throws FileNotFoundException Exception lancée si le fichier csv n'est pas trouvé
     */
    public static Espece createEspece(int id) throws FileNotFoundException {
        Espece espece = new Espece(id);
        File fichierCSV = new File("./csv/listePokemon1G_new.csv");
        try (Scanner scannerCSV = new Scanner(fichierCSV)) {
            scannerCSV.useDelimiter(";");
            scannerCSV.nextLine();
            while (scannerCSV.hasNext()) {
                if (Integer.parseInt(scannerCSV.next()) == id) {
                    espece.nom = scannerCSV.next();
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
                    espece.type1 = Pokedex.setType(scannerCSV.next());
                    espece.type2 = Pokedex.setType(scannerCSV.next());
                    espece.nivDepart = Integer.parseInt(scannerCSV.next());
                    String nivEvolutionTemp = scannerCSV.next();
                    if (nivEvolutionTemp != null) {
                        espece.nivEvolution = Integer.parseInt(nivEvolutionTemp);
                        espece.evolution = scannerCSV.next();
                    } else {

                        espece.nivEvolution = 0;
                        espece.evolution = null;
                    }
                }
                scannerCSV.nextLine();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return espece;
    }

    /**
     * Il crée une liste d'objets Espece, c'est une instanciation
     * 
     * @throws FileNotFoundException Exception lancée si le fichier csv n'est pas trouvé
     */
    public static void createListeEspece() throws FileNotFoundException {
        for (int i = 1; i < nbPokemon; i++) {
            listeEspece[i] = createEspece(i);
        }
    }

    /**
     * Il crée un nouvel objet Capacite et le remplit avec les données du fichier
     * CSV
     *
     * @param id l'identifiant du déménagement
     * @return Un objet Capacite
     * @throws FileNotFoundException Exception lancée si le fichier csv n'est pas trouvé
     */
    public static Capacite createCapacite(int id) throws FileNotFoundException {
        Capacite capacite = new Capacite(id);
        File fichierCSV = new File("./csv/listeCapacites.csv");
        try {
            Scanner scannerCSV = new Scanner(fichierCSV);
            scannerCSV.useDelimiter(";");
            scannerCSV.nextLine();
            while (scannerCSV.hasNext()) {
                try {
                    String ligneTemp = scannerCSV.nextLine();
                    String[] tabLigneTemp = ligneTemp.split(";");
                    if (Integer.parseInt(tabLigneTemp[4]) == id) {
                        capacite.nom = tabLigneTemp[0];
                        capacite.puissance = Integer.parseInt(tabLigneTemp[1]);
                        capacite.precision = Double.parseDouble(tabLigneTemp[2]);
                        capacite.ppBase = Integer.parseInt(tabLigneTemp[3]);
                        capacite.pp = capacite.ppBase;
                        capacite.id = Integer.parseInt(tabLigneTemp[4]);
                        capacite.categorie = Pokedex.setCategorie(tabLigneTemp[5]);
                        capacite.type=Pokedex.setType(tabLigneTemp[6]);
                    }
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
            scannerCSV.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return capacite;
    }

    /**
     * Il crée une liste de toutes les capacités du jeu
     * @throws FileNotFoundException Exception lancée si le fichier csv n'est pas trouvé
     */
    public static void createListeCapacite() throws FileNotFoundException {
        for (int i = 1; i < nbCapacite; i++) {
            listeCapacite[i] = createCapacite(i);
        }
    }

	/**
	 * Retourne l'objet type correspondant au nom du type passé en paramètre
	 * 
	 * @param t nom du type qui doit être ajouté au Pokemon
	 * @return l'objet type qui va être attribuer au Pokemon
	 */
	public static Type setType(String t) {
		int i=0;
		while(i<Type.getListe().length && !Type.getListe()[i].getNom().equals(t)) {
			i++;
		}
		if(i!=15) {
			return Type.getListe()[i];
		}
		return null;
	}
	
	
	/**
	 * Il renvoie un objet CategorieAttaque basé sur le paramètre String
	 *
	 * @param t Le type d'attaque.
	 * @return La méthode renvoie un objet CategorieAttaque.
	 */
	public static CategorieAttaque setCategorie(String t) {
		if(t.equals("Special")) {
			return CategorieAttaque.SPECIALE;
		}
		return CategorieAttaque.PHYSIQUE;
	}
	
    /**
     * Il prend une URL sous forme de chaîne, ouvre une connexion à cette URL, lit
     * la réponse et renvoie un JSONObject
     *
     * @param url L'URL de l'API que vous souhaitez appeler.
     * @return Un objet JSON
     */
    public static JSONObject getJSONfromURL(String url) {
        try {
            URL hp = new URL(url);
            HttpURLConnection hpCon = (HttpURLConnection) hp.openConnection();
            hpCon.connect();

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(hpCon.getInputStream()));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
                // System.out.println(inputStr);
            }
            inputStr = responseStrBuilder.toString();

            streamReader.close();

            return (JSONObject) new JSONParser().parse(inputStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
