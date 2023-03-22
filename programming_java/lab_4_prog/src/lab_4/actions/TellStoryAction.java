package lab_4.actions;

import lab_4.kids.Korotishi;
import lab_4.objects.*;
import lab_4.objects.Book.StoryOfBook;
//anon class
public class TellStoryAction {
    private Korotishi who;
    private Korotishi whom;
    private StoryOfBook[] stories;

    public TellStoryAction(Korotishi who, Korotishi whom, StoryOfBook[] stories){
        this.who = who;
        this.whom = whom;
        this.stories = stories;
    }

    public void tellStories(){
        CheckInterface check = (ThisObject object) -> {   
            StoryOfBook story = (StoryOfBook) object;
            if (story.getTopic().equals("journey")) return true;
            else{return false;}
    };  
        for(StoryOfBook st: stories){
            System.out.println(who + " tells story about " + st.getTopic() + " to " + whom + ".");
            if (check.check(st)) {
                System.out.println(whom + " likes story about " + st.getTopic());
            }
            else{
                System.out.println(whom + " doesn't like story about " + st.getTopic());
        }
    }
    }
}
