package lab_3.actions;
import lab_3.kids.Korotishi;

public class GivecmdAction {
    protected final String name = "give";
    protected final Korotishi who;
    protected final Korotishi whom;
    protected final String what_do;

    public GivecmdAction(Korotishi who, Korotishi whom, String what_do) {
        this.who = who;
        this.whom = whom;
        this.what_do = what_do;
    }
    
    public Korotishi getWho(){
        return who;
    }

    public Korotishi getWhom(){
        return whom;
    }

    public String getWhatdo(){
        return what_do;
    }

    public void print() {
        System.out.println(who + " " + name + " " + whom + " command " + what_do + ".");
    }
}
