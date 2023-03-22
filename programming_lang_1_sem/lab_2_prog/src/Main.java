package src;
import ru.ifmo.se.pokemon.*;
import src.pokemon.*;

class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Bruxish p1 = new Bruxish();
        Numel p2 = new Numel();
        Camerupt p3 = new Camerupt();
        Litten p4 = new Litten();
        Torracat p5 = new Torracat();
        Incineroar p6 = new Incineroar();
        b.addAlly(p1);
        b.addFoe(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addAlly(p5);
        b.addFoe(p6);
        b.go();
        }
}
