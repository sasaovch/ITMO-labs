package lab_4.actions;
import lab_4.kids.*;

public class CarryAction {
    private final String name = "carry";
    private final Korotishi[] who;
    private final Korotishi whom;
    private final String place;

    public CarryAction(Korotishi[] who, Korotishi whom, String place) {
        this.who = who;
        this.whom = whom;
        this.place = place;
    }

    public Korotishi[] getWho(){
        return who;
    }

    public Korotishi getWhom(){
        return whom;
    }

    public void print() {
        System.out.print(who[0]);
        for(int i = 1; i<who.length; i++) System.out.print(", " + who[i]);
        System.out.println(" " + name + " " + whom + " " + place + ".");
    }
}
