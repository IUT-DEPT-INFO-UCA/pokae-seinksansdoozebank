package testGestionCombat;

import gestionCombat.Combat;
import gestionCombat.Dresseur;
import gestionCombat.IARandom;
import gestionCombat.Joueur;
import gestionPokemon.Capacite;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class testCasSimplesCombats {
    Joueur j1;
    Pokemon pokeTest;
    Combat c1;
    IARandom GwendIA;
    IARandom DonatIA;
    @BeforeEach
    public void instanciationObjets(){
        // Initialisation du pokedex et du pokemon Test.
        Pokedex.initialiser();
        pokeTest = new Pokemon(Pokedex.listeEspece[3]);
        j1= new Joueur("Thomas","Admin","Thomas");
        GwendIA = new IARandom("Gwendoline");
        DonatIA = new IARandom ("Donati");
        c1 = new Combat(DonatIA,GwendIA);

    }
    @Test
    public void testEchangePokemon()
    {
        // Test si le pokemon courrant est bien modifié
        assertNotEquals(j1.getPokemon(),pokeTest);
        j1.setPokemonChoisi(pokeTest);
        j1.echangeCombattant();
        assertEquals(j1.getPokemon(),pokeTest);
    }
    @Test
    public void testKO(){
        assertTrue(j1.pouvoirSeBattre());
        for(int i =0;i<j1.getEquipe().length;i++){
            j1.getEquipe()[i].getStat().setPV(0);
        }
        assertFalse(j1.pouvoirSeBattre());
    }
    @Test
    public void testSoigneRanch(){
        for(int i =0;i<j1.getEquipe().length;i++){
            j1.getEquipe()[i].getStat().setPV(0);
        }
        assertFalse(j1.pouvoirSeBattre());
        j1.soigneRanch();
        assertTrue(j1.pouvoirSeBattre());
    }
    @Test
    public void testUtilise(){
        j1.setPokemon(pokeTest);
        int PPdeBase = j1.getPokemon().getCapacitesApprises()[0].getPP();
        j1.setActionChoisie((Capacite) j1.getPokemon().getCapacitesApprises()[0]);
        j1.utilise();
        assertNotEquals(j1.getPokemon().getCapacitesApprises()[0].getPP(), PPdeBase);
    }
    @Test
    public void testnbPokemon(){
        assertEquals(j1.getNbPokemonAlive(),6);
        j1.getPokemon().getStat().setPV(0);
        assertNotEquals(j1.getNbPokemonAlive(),6);

        assertEquals(j1.getNbPokemonAlive(),5);
    }
    @Test
    public void testTermine(){
        //Test si un pokemon est soigné a la fin d'un combat

        j1.getPokemon().getStat().setPV(0);
        assertEquals(j1.getNbPokemonAlive(),5);
        GwendIA.getPokemon().getStat().setPV(0);
        assertEquals(GwendIA.getNbPokemonAlive(),5);

        c1.termine();
        assertEquals(j1.getNbPokemonAlive(),6);
        assertEquals(GwendIA.getNbPokemonAlive(),6);
    }
    @Test
    public void testCommence(){
        c1.commence();
        assertEquals(c1.getNbTours(),1);
    }
    @Test
    public void testWinner(){
        Dresseur resultat = c1.getVainqueur();
        assertNotNull(resultat);
        if(resultat==DonatIA) assertEquals(DonatIA,resultat);
        else assertEquals(GwendIA,resultat);
    }


}
