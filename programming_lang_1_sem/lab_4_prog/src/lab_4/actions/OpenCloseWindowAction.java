package lab_4.actions;

import lab_4.kids.Korotishi;
import lab_4.objects.StatusOpenClose;
import lab_4.objects.Window;

public class OpenCloseWindowAction {
    private Korotishi who;
    private Window window;

    public OpenCloseWindowAction(Korotishi who, Window window){
        this.who = who;
        this.window = window;
    }

    public void openWindow(){
        window.setStatus(StatusOpenClose.Open);
        System.out.println(who + " opens window.");
    }

    public void closeWindow(){
        window.setStatus(StatusOpenClose.Close);
        System.out.println(who + " closes window.");
    }
}
