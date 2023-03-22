package lab_4.objects;

public class Window extends ThisObject {
    private StatusOpenClose status;
    private String place;

    public Window(String place){
        super("window");
        this.place = place;
        if(Math.random() <= 0.5) status = StatusOpenClose.Open;
        else{status = StatusOpenClose.Close;}
    }

    public StatusOpenClose getStatus(){
        return status;
    }

    public void setStatus(StatusOpenClose status){
        this.status = status;
    }

    public String getPlace(){
        return place;
    }
}
