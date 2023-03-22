package lab_3.actions;
import lab_3.kids.*;

public class MeetAction {
    protected final String name = "meet";
    protected final Korotishi who;
    protected final Korotishi whom;

    public MeetAction(Korotishi who, Korotishi whom) {
        this.who = who;
        this.whom = whom;
    }

    public Korotishi getWho(){
        return who;
    }

    public Korotishi getWhom(){
        return whom;
    }

    public void print() {
        System.out.println(who + " " + name + " " + whom + ".");
    }
}
