package lab_4.actions;

import lab_4.kids.*;
import lab_4.objects.*;
//nested class
public class GoOnTrip{
    private Korotishi[] who;
    private String place;

    public GoOnTrip(Korotishi[] who, String place){
        this.who = who;
        this.place = place;
    }

    public void goOnTrip(){
        System.out.print(who[0]);
        for(int i = 1; i<who.length; i++) System.out.print(", " + who[i]);
        System.out.println(" go on trip " + place + ".");
    }
    
    public static class SuggestionTransportForTrip extends ThisObject{
        private String name;
        
        SuggestionTransportForTrip(String name){
            super("suggestionTransportForTrip");
            this.name = name;
        }
        
        public void suggestsTransport(Korotishi who){
            System.out.println(who + "suggests using " + name + "to travel.");
        }

        public String getName(){
            return name;
        }
    }
}
