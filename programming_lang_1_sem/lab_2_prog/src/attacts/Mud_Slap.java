package src.attact;
import ru.ifmo.se.pokemon.*;

public class Mud_Slap extends SpecialMove {
    public Mud_Slap() {super(Type.GROUND, 20.0D, 100.0D);}
    
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.ACCURACY, -1);
    }
    protected String describe() {return "использует Mud_Slap";
    }
}
