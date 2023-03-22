package src.attact;
import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove {
    public Facade() {super(Type.NORMAL, 70.0D, 100.0D);}

    protected double calcBaseDamage(Pokemon att, Pokemon def) {
        if (def.getCondition() == Status.BURN | def.getCondition() == Status.POISON | 
        def.getCondition() == Status.PARALYZE) return (0.4D * (double)att.getLevel() + 2.0D) * 
        this.power * 2 / 150.0D;
        else return (0.4D * (double)att.getLevel() + 2.0D) * this.power / 150.0D;
    }
    protected String describe() {return "использует Facade";}
}
