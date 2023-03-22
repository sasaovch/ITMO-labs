package lab_4.actions;

import java.util.List;

import lab_4.kids.*;
import lab_4.objects.Eyes;
import lab_4.objects.StatusOpenClose;
import lab_4.exception.*;
//Un-check exception
public class SeeAction {
    private final Korotishi who;
    private final Eyes eyes;

    public SeeAction(Korotishi who, Eyes eyes) throws AlienEyesException{
        if(who.equals(eyes.getKorotishi())){
            this.who = who;
            this.eyes = eyes;
        }
        else{
            throw new AlienEyesException("These eyes is not " + who + "'s eyes."); 
        }
    }

    public void printSeeAction(List<Object> objects) throws EyesCloseException{
        if(eyes.getStatus() == StatusOpenClose.Close){
            for(int i=0; i<objects.size(); i++){
                System.out.println(who + " see " + objects.get(i) + ".");
            }
        }
        else{
            throw new EyesCloseException("Eyes is closed.");
        }
    }
}
