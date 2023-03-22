package lab_4.actions;

import lab_4.kids.Korotishi;
import lab_4.objects.Book;

public class ReadBookAction {
    private final Korotishi who;
    private Book book;
    
    public ReadBookAction(Korotishi who, Book book){
        this.who = who;
        this.book = book;
    }

    public void changeBook(Book book){
        this.book = book;
    }

    public void printReadAction(){
        System.out.println(who + " reads book \"" + book.getNameOfBook() + "\".");
    }
}
