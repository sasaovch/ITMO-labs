package lab_3;
import lab_3.actions.*;
import lab_3.kids.*;
import lab_3.objects.*;

public class Main {
    public static void main(String[] args) {
        Korotishi znayka = Korotishi.createMalish("Znayka");
        Korotishi neznayka = Korotishi.createMalish("Neznayka");
        Korotishi gynka = Korotishi.createMalish("Gynka");
        Korotishi simka = Korotishi.createMalish("Simka");
        Korotishi simka2 = Korotishi.createMalish("Simka");
        Korotishi shpula = Korotishi.createMalish("Shpula");
        ThinkAction znthoughts = new ThinkAction(znayka, 3, 3, "to make rubber ball");
        znthoughts.print();
        GivecmdAction collJuiceCmd = new GivecmdAction(znayka, Korotishi.malishi, "start to collect Juice");
        collJuiceCmd.print();
        Juice juice = new Juice("flowers as ficus");
        CollectAction collJuice = new CollectAction("for big ball", "in big bottle", new Korotishi[]{Korotishi.malishi}, juice);
        DescribeCollectAction descrCollJuice = new ProcessCollectJuice();
        GetThisObjectAction getJuice = new GetJuiceAction();
        getJuice.getObjectAction(collJuice, descrCollJuice);
        GoAction goNez = new GoAction(neznayka, "to collect Juice");
        goNez.print();
        MeetAction meetNez = new MeetAction(neznayka, gynka);
        meetNez.print();
        PlayAction gameGyn = new PlayAction(gynka, Korotishi.malishki, "hight - seek");
        gameGyn.print();
        System.out.println("simka hashcode - " + simka.hashCode());
        System.out.println("simka2 hashcode - " + simka2.hashCode());
        System.out.println("simka == simka2 ? " + simka.equals(simka2));
        System.out.println("simka == shpula ? " + simka2.equals(shpula));
    }
}
