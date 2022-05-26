package testGestionCombat;

import gestionCombat.Dresseur;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class testCasSimplesCombats {
    Dresseur d1;
    Dresseur d2;
    @Test
    public void init() throws IOException, ParseException {
        d1 = new Dresseur("Thomas","Admin","Thomas");
        d2 = new Dresseur("Clement","Admin","Clement");
    }

}
