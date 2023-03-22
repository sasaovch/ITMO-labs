package src.pokemon;
import ru.ifmo.se.pokemon.*;
import src.attact.*;

public class Incineroar extends Torracat{
    
    public Incineroar() {
        super("Incineroar", 1);
        setType(Type.FIRE, Type.DARK);
        setStats(95.0D, 115.0D, 90.0D, 80.0D, 90.0D, 90.0D);
        setMove(new Facade(), new Swagger(), new Mud_Slap(), new Growl());
    }
    
    Incineroar(int lv) {
        super("Incineroar", lv);
        setType(Type.FIRE, Type.DARK);
        setStats(95.0D, 115.0D, 90.0D, 80.0D, 90.0D, 90.0D);
        setMove(new Facade(), new Swagger(), new Mud_Slap(), new Growl());
    }

    Incineroar(String nm, int lv) {
        super(nm, lv);
        setType(Type.FIRE, Type.DARK);
        setStats(95.0D, 115.0D, 90.0D, 80.0D, 90.0D, 90.0D);
        setMove(new Facade(), new Swagger(), new Mud_Slap(), new Growl());
    }
}
