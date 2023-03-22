package src.attact;
import ru.ifmo.se.pokemon.*;

public class Growl extends StatusMove {
    public Growl() {super(Type.NORMAL, 0.0D, 100.0D);}

    protected void applyOppEffects(Pokemon p) {
         p.setMod(Stat.ATTACK, -1);
    }
    protected String describe() {return "использует Growl";}
}
