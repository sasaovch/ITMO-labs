package src.pokemon;
import ru.ifmo.se.pokemon.*;
import src.attact.*;
public class Litten extends Pokemon{

    public Litten() {
        super("Litten", 1);
        setType(Type.FIRE);
        setStats(45.0D, 65.0D, 40.0D, 60.0D, 40.0D, 70.0D);
        setMove(new Facade(), new Swagger());
    }
    
    Litten(int lv) {
        super("Litten", lv);
        setType(Type.FIRE);
        setStats(45.0D, 65.0D, 40.0D, 60.0D, 40.0D, 70.0D);
        setMove(new Facade(), new Swagger());
    }

    Litten(String nm, int lv) {
        super(nm, lv);
        setType(Type.FIRE);
        setStats(45.0D, 65.0D, 40.0D, 60.0D, 40.0D, 70.0D);
        setMove(new Facade(), new Swagger());
    }
}
