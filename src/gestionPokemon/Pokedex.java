package gestionPokemon;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokedex;
import interfaces.IPokemon;
import interfaces.IType;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.util.Arrays;
import java.util.Scanner;

/**
 * Classe appelée Pokedex qui gère toutes les espèces et toutes les capacites
 * On peut interroger la classe pokedex en lui demandant des capacites ou des
 * especes
 * Elle permet aussi de generer le ranch d'un dresseur
 */

public abstract class Pokedex implements IPokedex {
    /**
     * Instanciation du nombre de pokemon
     */
    private static int nbPokemon = 152;

    /**
     * Instanciation du nombre de pokemon par ranch
     */
    private static int nbPokemonParRanch = 6;
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
        return null;
    }

    /**
     * @deprecated Il faut préférer utiliser la version static de cette méthode
     */
    @Override
    public IEspece getInfo(String nomEspece) {
        return null;
    }

    /**
     * @deprecated Il faut préférer utiliser la version static de cette méthode
     */
    @Override
    public Double getEfficacite(IType attaque, IType defense) {
        return null;
    }

    /**
     * @deprecated Il faut préférer utiliser la version static de cette méthode
     */
    @Override
    public ICapacite getCapacite(String nomCapacite) {
        return null;
    }

    /**
     * @deprecated Il faut préférer utiliser la version static de cette méthode
     */
    @Override
    public ICapacite getCapacite(int numCapacite) {
        return null;
    }
    //////////////////////////////////////////////////////////////////

    /**
     * Il crée une liste de 6 Pokémon aléatoires;
     *
     * @return Une liste de 6 Pokémon choisi aléatoirement parmis le spokémon dont l
     *         eniveau de base vaut 1 dans le pokédex.
     * @throws IOException    Une exception
     * @throws ParseException Une autre exception
     */
    public static IPokemon[] engendreRanchStatic() throws IOException, ParseException {
    	System.out.println("Chargement en cours...\n");
        IPokemon[] listePokeAleatoire = new Pokemon[nbPokemonParRanch];
        int i = 0;
        while (i < nbPokemonParRanch) {
            int index = (int) (Math.random() * (Pokedex.nbPokemon - 1) + 1);
            if (listeEspece[index].nivDepart <= 1) {
                listePokeAleatoire[i] = new Pokemon(listeEspece[index]);
                i++;
            }
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
        while (i < listeEspece.length - 1 && !tester) {
            tester = listeEspece[i].nom.equals(nom);
            i++;
        }
        if (tester) {
            return listeEspece[i - 1];
        } else {
            System.out.println("Error : Espece not found");
            return null;
        }
    }
    
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
     * @throws FileNotFoundException Exception lancée si le fichier csv n'est pas
     *                               trouvé
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
     * @throws FileNotFoundException Exception lancée si le fichier csv n'est pas
     *                               trouvé
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
     * @throws FileNotFoundException Exception lancée si le fichier csv n'est pas
     *                               trouvé
     */
    public static Capacite createCapacite(int id) throws FileNotFoundException {
        Capacite capacite = new Capacite(id);
        File fichierCSV = new File("./csv/listeCapacites.csv");
        try {
            try (Scanner scannerCSV = new Scanner(fichierCSV)) {
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
                            capacite.type = Pokedex.setType(tabLigneTemp[6]);
                        }
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                }
                scannerCSV.close();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return capacite;
    }

    /**
     * Il crée une liste de toutes les capacités du jeu
     * 
     * @throws FileNotFoundException Exception lancée si le fichier csv n'est pas
     *                               trouvé
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
        int i = 0;
        while (i < Type.getListe().length && !Type.getListe()[i].getNom().equals(t)) {
            i++;
        }
        if (i != 15) {
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
        if (t.equals("Special")) {
            return CategorieAttaque.SPECIALE;
        }
        return CategorieAttaque.PHYSIQUE;
    }

    /**
     * Il prend une URL sous forme de chaîne, ouvre une connexion à cette URL, lit
     * la réponse et renvoie un JSONObject
     *
     * @param nomFichier le nom du fichier JSON à télécharger
     * @param url        L'URL de l'API que vous souhaitez appeler.
     * @return Un objet JSON
     */
    public static JSONObject downloadJSONfromURL(String nomFichier, String url) {
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
            // File newFile = new File("./JSON/"+nomFichier+".json");
            FileWriter myWriter = new FileWriter("./JSON/" + nomFichier + ".json");
            myWriter.write(inputStr);
            myWriter.close();
            streamReader.close();

            return (JSONObject) new JSONParser().parse(inputStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cette fonction renvoie le nombre de Pokemon par ranch
     * 
     * @return Le nombre de Pokémon par ranch.
     */
    public static int getNbPokemonParRanch() {
        return nbPokemonParRanch;
    }

    /**
     * Il prend un tableau de String et renvoie une chaîne qui est une case avec
     * les chaînes du tableau comme lignes
     * 
     * @param data le tableau de données à afficher dans la case
     * @return Un string répresenant une case comportant les élément de data
     */
    public static String createCase(String[] data) {
        int largeur = 20 - 2;
        StringBuilder rep = new StringBuilder("+");
        rep.append("-".repeat(largeur));
        rep.append("+\n\t\t");
        for (String datum : data) {
            int lenData = (largeur - datum.length());
            rep.append("   |");
            rep.append(" ".repeat(Math.max(0, lenData / 2)));
            rep.append(datum);
            rep.append(" ".repeat(Math.max(0, lenData / 2 - ((lenData + 1) % 2) + 1)));
            rep.append("|\n\t\t");
        }
        rep.append("   +");
        rep.append("-".repeat(largeur));
        rep.append("+");
        return rep.toString();
    }

}
