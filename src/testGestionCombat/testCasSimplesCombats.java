package testGestionCombat;

import gestionCombat.Combat;
import gestionCombat.Echange;
import gestionCombat.IARandom;
import gestionCombat.Joueur;
import gestionPokemon.Capacite;
import gestionPokemon.Pokedex;
import gestionPokemon.Pokemon;
import interfaces.IDresseur;

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
       // j1= new Joueur("Thomas","Admin","Thomas");
        GwendIA = new IARandom(false);
        DonatIA = new IARandom (false);
        c1 = new Combat(DonatIA,GwendIA);


    }
    @Test
    public void testEchangePokemon()
    {
        // Test si le pokemon courrant est bien modifié
        assertNotEquals(GwendIA.getPokemon(),pokeTest);
        GwendIA.setActionChoisie(new Echange(pokeTest,GwendIA));
        GwendIA.getActionChoisie().utilise();
        assertEquals(GwendIA.getPokemon(),pokeTest);
    }
    @Test
    public void testKO(){
        assertTrue(GwendIA.pouvoirSeBattre());
        for(int i =0;i<GwendIA.getEquipe().length;i++){
            GwendIA.getEquipe()[i].getStat().setPV(0);
        }
        assertFalse(GwendIA.pouvoirSeBattre());
    }
    @Test
    public void testSoigneRanch(){
        for(int i =0;i<GwendIA.getEquipe().length;i++){
            GwendIA.getEquipe()[i].getStat().setPV(0);
        }
        assertFalse(GwendIA.pouvoirSeBattre());
        GwendIA.soigneRanch();
        assertTrue(GwendIA.pouvoirSeBattre());
    }
    @Test
    public void testUtilise(){
        GwendIA.setPokemon(pokeTest);
        int PPdeBase = GwendIA.getPokemon().getCapacitesApprises()[0].getPP();
        GwendIA.setActionChoisie((Capacite) GwendIA.getPokemon().getCapacitesApprises()[0]);
        GwendIA.getActionChoisie().utilise();
        assertNotEquals(GwendIA.getPokemon().getCapacitesApprises()[0].getPP(), PPdeBase);
    }
    @Test
    public void testnbPokemon(){
        assertEquals(GwendIA.getNbPokemonAlive(),6);
        GwendIA.getPokemon().getStat().setPV(0);
        assertNotEquals(GwendIA.getNbPokemonAlive(),6);

        assertEquals(GwendIA.getNbPokemonAlive(),5);
    }
    @Test
    public void testTermine(){
        //Test si un pokemon est soigné a la fin d'un combat

        DonatIA.getPokemon().getStat().setPV(0);
        assertEquals(DonatIA.getNbPokemonAlive(),5);
        GwendIA.getPokemon().getStat().setPV(0);
        assertEquals(GwendIA.getNbPokemonAlive(),5);

        c1.termine();
        assertEquals(DonatIA.getNbPokemonAlive(),6);
        assertEquals(GwendIA.getNbPokemonAlive(),6);
    }
    @Test
    public void testCommence(){
        c1.commence();
        assertEquals(c1.getNbTours(),1);
    }
    @Test
    public void testWinner(){
        IDresseur resultat = c1.getVainqueur();
        assertNotNull(resultat);
        if(resultat==DonatIA) assertEquals(DonatIA,resultat);
        else assertEquals(GwendIA,resultat);
    }


}
