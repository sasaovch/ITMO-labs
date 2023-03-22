package lab_4.objects;

public abstract class ThisObject {
    private final String object;

    public ThisObject (String object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ThisObject that = (ThisObject) obj;
        return object.equals(that.object);
    }

    @Override
    public String toString() {
        return object;
    }
}
