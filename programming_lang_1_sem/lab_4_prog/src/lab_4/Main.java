package lab_4;

import java.util.ArrayList;
import java.util.List;

import lab_4.actions.*;
import lab_4.exception.AlienEyesException;
import lab_4.exception.EyesCloseException;
import lab_4.exception.WindowCloseException;
import lab_4.kids.Korotishi;
import lab_4.objects.*;
import lab_4.objects.Book.StoryOfBook;


public class Main{
    public static void main(String[] args) throws WindowCloseException, EyesCloseException{
        //creat heros
        Korotishi znayka = Korotishi.createMalish("Znayka");
        Korotishi neznayka = Korotishi.createMalish("Neznayka");
        Korotishi avoska = Korotishi.createMalish("Avoska");
        Korotishi vintik = Korotishi.createMalish("Vintik");
        Korotishi pilylkin = Korotishi.createMalish("Pilylkin");
        Korotishi[] korotArray = new Korotishi[]{znayka, avoska, vintik, pilylkin};
        Window window1 = new Window("in home");
        //creat Actions
        CarryAction carryNeznayka = new CarryAction(korotArray, neznayka, "home");
        PutAction putNeznayka = new PutAction(korotArray, neznayka, "on the bed");
        List<Object> objectsOfSeeAction = new ArrayList<Object>();
        objectsOfSeeAction.add("on the left");
        objectsOfSeeAction.add("on the right");
        JumpInWindow neznaykaJumpsInWindow = new JumpInWindow(neznayka, window1);
        Book bookZnayka = new Book("first", "journey");
        StoryOfBook storyFirst = bookZnayka.new StoryOfBook("journey");
        StoryOfBook storySecond = bookZnayka.new StoryOfBook("love");
        ThinkAction znaykaThinkFirst = new ThinkAction(znayka, 3, 3, "make a big ball");
        StoryOfBook[] arrayOfStory = new StoryOfBook[]{storyFirst, storySecond};
        ReadBookAction znaykaRead = new ReadBookAction(znayka, bookZnayka);
        TellStoryAction znaykaTellStory = new TellStoryAction(znayka, Korotishi.malishi, arrayOfStory);
        //print story
        carryNeznayka.print();
        putNeznayka.print();
        Eyes eyesOfNeznayka = new Eyes(neznayka, StatusOpenClose.Close);
        try{
            SeeAction neznaykaSee = new SeeAction(neznayka, eyesOfNeznayka);
            neznaykaSee.printSeeAction(objectsOfSeeAction);
        }
        catch(AlienEyesException e){
            System.out.println("      AlienEyesException: " + e.getMessage());
            System.out.println("Please, check the correspondence of Korotish and Eyes");
        }
        catch(EyesCloseException e){
            System.out.println("      EyesCloseExceprion: " + e.getMessage());
        }
        try{
            neznaykaJumpsInWindow.jumpInWindow();
        }
        catch(WindowCloseException e){
            System.out.println("     The window is closed.");
            OpenCloseWindowAction openWindow = new OpenCloseWindowAction(neznayka, window1);
            openWindow.openWindow();
            neznaykaJumpsInWindow.jumpInWindow();
        }
        znaykaRead.printReadAction();
        znaykaTellStory.tellStories();
        znaykaThinkFirst.print();
    }
}
