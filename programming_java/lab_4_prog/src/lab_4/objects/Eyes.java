package lab_4.objects;

import lab_4.kids.Korotishi;

public class Eyes {
    private Korotishi who;
    private StatusOpenClose status;

    public Eyes(Korotishi who, StatusOpenClose status){
        this.who = who;
        this.status = status;
    }

    public Korotishi getKorotishi(){
        return who;
    }

    public StatusOpenClose getStatus(){
        return status;
    }
}
