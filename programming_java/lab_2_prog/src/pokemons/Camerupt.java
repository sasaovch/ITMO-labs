package src.pokemon;
import ru.ifmo.se.pokemon.*;
import src.attact.*;
public class Camerupt extends Numel{
    
    public Camerupt() {
        super("Camerupt", 1);
        setType(Type.FIRE, Type.GROUND);
        setStats(70.0D, 100.0D, 70.0D, 105.0D, 75.0D, 40.0D);
        setMove(new Scald(), new Hydro_Pump(), new Facade(), new Dazzling_Gleam());
    }

    Camerupt(int lv) {
        super("Camerupt", lv);
        setType(Type.FIRE, Type.GROUND);
        setStats(70.0D, 100.0D, 70.0D, 105.0D, 75.0D, 40.0D);
        setMove(new Scald(), new Hydro_Pump(), new Facade(), new Dazzling_Gleam());
    }

    Camerupt(String nm, int lv) {
        super(nm, lv);
        setType(Type.FIRE, Type.GROUND);
        setStats(70.0D, 100.0D, 70.0D, 105.0D, 75.0D, 40.0D);
        setMove(new Scald(), new Hydro_Pump(), new Facade(), new Dazzling_Gleam());
    }
}
