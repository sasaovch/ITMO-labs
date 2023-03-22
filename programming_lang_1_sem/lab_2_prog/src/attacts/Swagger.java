package src.attact;
import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove {
    public Swagger() {super(Type.NORMAL, 0.0D, 85.0D);}

    protected void applyOppEffects(Pokemon p) {
        p.confuse();
        p.setMod(Stat.ATTACK, +2);
    }
    protected String describe() {return "использует Swagger";
    }
}
