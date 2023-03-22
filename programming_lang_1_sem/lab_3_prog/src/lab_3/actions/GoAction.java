package lab_3.actions;
import lab_3.kids.Korotishi;

public class GoAction {
    protected final String name = "go";
    protected final Korotishi who;
    protected String reason;
    
    public GoAction(Korotishi who, String reason) {
        this.who = who;
        this.reason = reason;
    }
    
    public Korotishi getWho(){
        return who;
    }

    public String getReason(){
        return reason;
    }

    public void print() {
        System.out.println(who + " " + name + " " + reason + ".");
    }
}
