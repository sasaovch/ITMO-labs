package lab_3.actions;
import lab_3.kids.Korotishi;
import lab_3.objects.*;

public class CollectAction {
    protected final String name = "collect";
    protected final ThisObject object;
    protected final String reason;
    protected String container;
    protected Korotishi[] people;

    public CollectAction(String reason, String container, Korotishi[] people, ThisObject object) {
        this.reason = reason;
        this.container = container;
        this.people = people;
        this.object = object;
    }

    public ThisObject getObject(){
        return object;
    }

    public String getReason(){
        return reason;
    }

    public String getContainer(){
        return container;
    }

    public void printPeople(){
        for (Korotishi man: people) System.out.print(man + ", ");
        System.out.println("\n");
    }

    public void printResult() {
        System.out.print(people[0]);
        for (int i = 1; i < people.length; i++) System.out.print(", " + people[i]);
        System.out.println(" " + name + " " + object + " " + container + " " + reason + ".");
    }
}
