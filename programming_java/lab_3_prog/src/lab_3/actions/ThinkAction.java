package lab_3.actions;
import lab_3.kids.Korotishi;

public class ThinkAction {
    protected final String name = "think";
    protected final Korotishi who;
    protected final int num_day;
    protected final int num_night;
    protected final String result;

    public ThinkAction(Korotishi who, int day, int night, String result) {
        this.who = who;
        num_day = day;
        num_night = night;
        this.result = result;
    }

    public Korotishi getWho(){
        return who;
    }

    public void printDays(){
        System.out.println("num_days = " + num_day + ", num_night = " + num_night);
    }

    public String getResult(){
        return result;
    }

    public void print() {
        System.out.println(who + " " + name + ". He has been thinking for " + num_day + 
        " days and " + num_night + " night. Result - " + result + ".");
    }
}
