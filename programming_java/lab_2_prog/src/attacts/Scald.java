package src.attact;
import ru.ifmo.se.pokemon.*;

public class Scald extends SpecialMove {
    public Scald() {super(Type.WATER, 80.0D, 100.0D);
    }
    protected void applyOppEffects(Pokemon p) {
        int z = 0;
        if (Math.random()<=0.3D) {
            for(Type t: p.getTypes()) {
                if (t == Type.FIRE) {
                    z = -1;
                }
            }
            if (z == 0) Effect.burn(p);
        }
    }
    protected String describe() {return "использует Scald";
    }
}
