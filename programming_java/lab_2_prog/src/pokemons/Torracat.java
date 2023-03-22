package src.pokemon;
import ru.ifmo.se.pokemon.*;
import src.attact.*;
public class Torracat extends Litten{

    public Torracat() {
        super("Torracat", 1);
        setType(Type.FIRE);
        setStats(65.0D, 85.0D, 50.0D, 80.0D, 50.0D, 90.0D);
        setMove(new Facade(), new Swagger(), new Mud_Slap());
    }
    
    Torracat(int lv) {
        super("Torracat", lv);
        setType(Type.FIRE);
        setStats(65.0D, 85.0D, 50.0D, 80.0D, 50.0D, 90.0D);
        setMove(new Facade(), new Swagger(), new Mud_Slap());
    }

    Torracat(String nm, int lv) {
        super(nm, lv);
        setType(Type.FIRE);
        setStats(65.0D, 85.0D, 50.0D, 80.0D, 50.0D, 90.0D);
        setMove(new Facade(), new Swagger(), new Mud_Slap());
    }
}
