package gestionPokemon;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokemon;
import interfaces.IType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Pokedex {
    public static Espece[] listeEspece;
    public static Capacite[] listeCapacite;
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
			        /*
			        espece.pv=Integer.parseInt(scannerCSV.next());
			        espece.atq=Integer.parseInt(scannerCSV.next());
			        espece.def=Integer.parseInt(scannerCSV.next());
			        espece.spe=Integer.parseInt(scannerCSV.next());
			        espece.vit=Integer.parseInt(scannerCSV.next());
			        espece.setExpDeBase(Integer.parseInt(scannerCSV.next()));
			        espece.setGainPv(Integer.parseInt(scannerCSV.next()));
			        espece.setGainAtq(Integer.parseInt(scannerCSV.next()));
			        espece.setGainDef(Integer.parseInt(scannerCSV.next()));
			        espece.setGainSpe(Integer.parseInt(scannerCSV.next()));
			        espece.setGainVit(Integer.parseInt(scannerCSV.next()));*/
			        

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

    public Capacite createCapacite(int id) throws FileNotFoundException {
        Capacite capacite = new Capacite(id);
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
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return capacite;

    }
    public void createListeCapacite() throws FileNotFoundException {
        for (int i =0;i<110;i++){
            listeCapacite[i]=createCapacite(i);
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

    public static void main(String[] args){
        System.out.println("test");
    }
}
