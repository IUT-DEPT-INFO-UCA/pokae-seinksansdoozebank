package gestionPokemon;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Pokedex {
    public static Espece[] listeEspece;
    static int nombrePokemons;
    public Espece createEspece(int id) throws FileNotFoundException {
        Espece espece = new Espece(id);
        File fichierCSV = new File("./listePokemon1G_new.csv");
        Scanner scannerCSV = new Scanner(fichierCSV);
        scannerCSV.useDelimiter(";");
        scannerCSV.nextLine();
        while (scannerCSV.hasNext()) {
            if(Integer.parseInt(scannerCSV.next())==id){
                espece.nom=scannerCSV.next();
                System.out.println(espece);
                espece.pv=Integer.parseInt(scannerCSV.next());
                espece.atq=Integer.parseInt(scannerCSV.next());
                espece.def=Integer.parseInt(scannerCSV.next());
                espece.spe=Integer.parseInt(scannerCSV.next());
                espece.vit=Integer.parseInt(scannerCSV.next());
                espece.setExpDeBase(Integer.parseInt(scannerCSV.next()));
                espece.setEvPv(Integer.parseInt(scannerCSV.next()));
                espece.setEvAtq(Integer.parseInt(scannerCSV.next()));
                espece.setEvDef(Integer.parseInt(scannerCSV.next()));
                espece.setEvSpe(Integer.parseInt(scannerCSV.next()));
                espece.setEvVit(Integer.parseInt(scannerCSV.next()));
                espece.type1= new Type(scannerCSV.next());
                espece.type2= new Type(scannerCSV.next());
                espece.nivDepart=Integer.parseInt(scannerCSV.next());
                espece.nivEvolution=Integer.parseInt(scannerCSV.next());
                espece.evolution=createEspece(id+1);
            }
            else{
                scannerCSV.nextLine();
            }

        }
        return espece;
    }

    public void createListeEspece(int id) throws FileNotFoundException {
        for (int i=1; i<152;i++){
            listeEspece[i]=createEspece(i);
        }
    }

    public void createCapacite(int id) throws FileNotFoundException {
        Capacite capacite = new Capacite(id);
        File fichierCSV = new File("./listePokemon1G_new.csv");
        Scanner scannerCSV = new Scanner(fichierCSV);
        scannerCSV.useDelimiter(";");
        scannerCSV.nextLine();
        while (scannerCSV.hasNext()) {
            capacite.nom=scannerCSV.next();

        }
    }
}
