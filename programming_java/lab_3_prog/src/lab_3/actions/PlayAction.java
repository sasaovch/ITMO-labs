package lab_3.actions;

import lab_3.kids.Korotishi;

public class PlayAction {
    protected final String name = "play";
    protected final Korotishi who;
    protected Korotishi whom;
    protected String game;

    public PlayAction(Korotishi who, Korotishi whom, String game) {
        this.who = who;
        this.whom = whom;
        this.game = game;
    }

    public Korotishi getWho(){
        return who;
    }

    public Korotishi getWhom(){
        return whom;
    }

    public String getGame(){
        return game;
    }

    public void print() {
        System.out.println(who + " " + name + " with " + whom + " " + game + ".");
    }
}
