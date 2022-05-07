package gestionPokemon;

import java.io.*;

public class Type {
	public static String[] listeTypes = {"Combat","Dragon","Eau","Electrik","Feu","Glace","Insecte","Normal","Plante","Poison","Psy","Roche","Sol","Spectre","Vol"};
	public int id;
	public String nom;
	private double [] tabCoeffEfficacite = new double[15];


	public Type(String nom){
		this.id=obtenirIndexDuType(nom);
		this.nom = nom;
		this.initialiserCoeff("efficacite.csv");
	}

	public Type(int id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	public static int obtenirIndexDuType(String type){
		for (int i = 0; i <Type.listeTypes.length;i++){
			if (listeTypes[i].equals(type));{
				return i;
			}
		}
		return -1;

	}
	public double obtenirCoeffDegatSur(Pokemon cible) {
		return this.tabCoeffEfficacite[cible.espPoke.type1.id]*this.tabCoeffEfficacite[cible.espPoke.type2.id];
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


	///////////////// Methodes de debug /////////////////


	public static Type[] creerTypes(String fileName) {
		Type[] tab = new Type[15];
		for (int i=0;i<15;i++) {
			tab[i]=new Type(i,Type.listeTypes[i]);
			tab[i].initialiserCoeff(fileName);
		}
		return tab;
	}

	public void showTab() {
		StringBuilder s  = new StringBuilder();
		s.append(this.nom+" :");
		for(double d : this.tabCoeffEfficacite) {
			s.append(" "+d+",");
		}
		System.out.println(s);
	}

}