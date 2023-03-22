package lab_4.objects;
//inner class
public class Book{
    private final String nameOfBook;
    private final String themeOfBook;

    public Book(String name, String theme){
        nameOfBook = name;
        themeOfBook = theme;
    }

    public String getNameOfBook(){
        return nameOfBook;
    }

    public String getThemeOfBook(){
        return themeOfBook;
    }

    public void printBook(){
        System.out.println("Name of Book: " + nameOfBook + "\n" + "Theme of Book: " + themeOfBook);
    }

    public class StoryOfBook extends ThisObject{
        private String topic;

        public StoryOfBook(String topic){
            super("storyOf" + getNameOfBook());
            this.topic = topic;
        }

        public String getTopic(){
            return topic;
        }
    }
}
