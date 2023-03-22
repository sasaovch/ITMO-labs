package src.pokemon;
import ru.ifmo.se.pokemon.*;
import src.attact.*;

public class Bruxish extends Pokemon{

    public Bruxish() {
        super("Bruxish", 1);
        setType(Type.WATER, Type.PSYCHIC);
        setStats(68.0D, 105.0D, 70.0D, 70.0D, 70.0D, 92.0D);
        setMove(new Facade(), new Waterfall(), new Scald(), new Swagger());    
    }
    
    Bruxish(int lv) {
        super("Bruxish", lv);
        setType(Type.WATER, Type.PSYCHIC);
        setStats(68.0D, 105.0D, 70.0D, 70.0D, 70.0D, 92.0D);
        setMove(new Facade(), new Waterfall(), new Scald(), new Swagger());
    }

    Bruxish(String nm, int lv) {
        super("Bruxish", 1);
        setType(Type.WATER, Type.PSYCHIC);
        setStats(68.0D, 105.0D, 70.0D, 70.0D, 70.0D, 92.0D);
        setMove(new Facade(), new Waterfall(), new Scald(), new Swagger());
    }
}

