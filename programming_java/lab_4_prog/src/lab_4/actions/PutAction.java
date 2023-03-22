package lab_4.actions;

import lab_4.kids.Korotishi;

public class PutAction {
    private Korotishi whom;
    private Korotishi[] who;
    private String place;

    public PutAction(Korotishi[] who, Korotishi whom, String place){
        this.who = who;
        this.whom = whom;
        this.place = place;
    }

    public void print(){
        System.out.print(who[0]);
        for(int i = 1; i<who.length; i++) System.out.print(", " + who[i]);
        System.out.println(" put " + whom + " " + place + ".");
    }
}
