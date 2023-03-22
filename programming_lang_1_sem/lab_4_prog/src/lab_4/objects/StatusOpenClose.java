package lab_4.objects;

public enum StatusOpenClose {
    Open("open"),
    Close("close");

    private String status;

    StatusOpenClose(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
