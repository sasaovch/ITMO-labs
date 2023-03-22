package src.pokemon;
import ru.ifmo.se.pokemon.*;
import src.attact.*;
public class Numel extends Pokemon{
    
    public Numel() {
        super("Numel", 1);
        setType(Type.FIRE, Type.GROUND);
        setStats(60.0D, 60.0D, 40.0D, 65.0D, 45.0D, 35.0D);
        setMove(new Scald(), new Hydro_Pump(), new Facade());
    }
    
    Numel(int lv) {
        super("Numel", lv);
        setType(Type.FIRE, Type.GROUND);
        setStats(60.0D, 60.0D, 40.0D, 65.0D, 45.0D, 35.0D);
        setMove(new Scald(), new Hydro_Pump(), new Facade());
    }

    Numel(String nm, int lv) {
        super(nm, lv);
        setType(Type.FIRE, Type.GROUND);
        setStats(60.0D, 60.0D, 40.0D, 65.0D, 45.0D, 35.0D);
        setMove(new Scald(), new Hydro_Pump(), new Facade());
    }
}