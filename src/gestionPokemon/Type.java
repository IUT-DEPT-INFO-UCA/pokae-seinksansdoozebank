package gestionPokemon;

import java.io.*;

public class Type {
	static String[] types = {"Combat","Dragon","Eau","Electrik","Feu","Glace","Insecte","Normal","Plante","Poison","Psy","Roche","Sol","Spectre","Vol"};
	int id;
    String nom;
    double [] tabCoeffEfficacite = new double[15];


	public Type(int id, String nom){
		this.id = id;
	    this.nom = nom;
	}
	
	public void showTab() {
		StringBuilder s  = new StringBuilder();
		s.append(this.nom+" :");
		for(double d : this.tabCoeffEfficacite) {
			s.append(" "+d+",");
		}
		System.out.println(s);
	}
	
	public void initialiserCoeff(String fileName){
	    try{
	        FileReader fichier = new FileReader(fileName);
	        BufferedReader reader = new BufferedReader(fichier);
	        reader.readLine();
	        int i=0;
	        while(reader.ready()){
	            String line = reader.readLine();
	            //System.out.println(line);
	            String[] tab = line.split(";");
	            this.tabCoeffEfficacite[i]=Double.parseDouble(tab[this.id+1]);
	            i++;
	        }
	        reader.close();
	        fichier.close();
	    }catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static Type[] creerTypes(String fileName) {
		Type[] tab = new Type[15];
		for (int i=0;i<15;i++) {
			tab[i]=new Type(i,Type.types[i]);
			tab[i].initialiserCoeff(fileName);
		}
		return tab;
	}
	
	public double obtenirCoeffDegatSur(Pokemon cible) {
		return this.tabCoeffEfficacite[cible.espPoke.type1.id]*this.tabCoeffEfficacite[cible.espPoke.type2.id];
	}
}
