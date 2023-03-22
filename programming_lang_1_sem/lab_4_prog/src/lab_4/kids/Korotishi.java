package lab_4.kids;

public class Korotishi {
    private final String name;
    private final Gender gender;
    public static Korotishi malishi = new Korotishi("Malishi", Gender.Malishi);
    public static Korotishi malishki = new Korotishi("Malishki", Gender.Malishki);
    
    private Korotishi(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getGender() {
        return gender.getGender();
    }

    @Override
    public int hashCode() {
        return name.hashCode() + gender.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // сравнение ссылок, та же ли ссылка
        if (obj == null || getClass() != obj.getClass()) return false;
        Korotishi that = (Korotishi) obj; // сужеющее приведение, чтобы взять поля
        return name.equals(that.name) && gender.equals(that.gender);
    }

    public static Korotishi createMalish(String name) {
        if (name.equals("Malishi")) throw new IllegalArgumentException(name + " - wrong name");
        else {return new Korotishi(name, Gender.Malishi);}
    }

    public static Korotishi createMalishka(String name) {
        if (name.equals("Malishki")) throw new IllegalArgumentException(name + " - wrong name");
        else {return new Korotishi(name, Gender.Malishki);}
    }
}
