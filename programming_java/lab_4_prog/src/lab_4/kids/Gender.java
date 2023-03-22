package lab_4.kids;

public enum Gender {
    Malishi("Malish"),
    Malishki("Malishki");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
