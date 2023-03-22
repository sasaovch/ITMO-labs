package lab_3.objects;

public class Juice extends ThisObject{
    private final String flower;

    public Juice(String flower) {
        super("Juice");
        this.flower = flower;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            Juice that = (Juice) obj;
            return flower == that.flower;
        }
        else {return false;}
    }

    public String getFlower() {
        return flower;
    }
}
