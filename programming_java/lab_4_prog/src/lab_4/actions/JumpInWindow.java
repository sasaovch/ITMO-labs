package lab_4.actions;
import lab_4.exception.*;
import lab_4.kids.Korotishi;
import lab_4.objects.StatusOpenClose;
import lab_4.objects.Window;
//Check exception
public class JumpInWindow {
    private Korotishi who;
    private Window window;

    public JumpInWindow(Korotishi who, Window window){
        this.who = who;
        this.window = window;
    }
    public void jumpInWindow() throws WindowCloseException{
        if(window.getStatus() == StatusOpenClose.Close) throw new WindowCloseException("the window is closed.");
        else{System.out.println(who + " jumps out in window " + window.getPlace() + ".");}
    }
}
