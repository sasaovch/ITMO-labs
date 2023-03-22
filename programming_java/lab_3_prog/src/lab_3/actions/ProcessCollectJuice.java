package lab_3.actions;

import lab_3.objects.Juice;
import lab_3.objects.ThisObject;

public class ProcessCollectJuice implements DescribeCollectAction{
    //I cannot change type of thisObject, because it isn't in interface, in signature it isn't allowed
    //to change something, return type - yes only compatible
    public void describeProcess(ThisObject thisObject) throws ClassCastException{
        try {
            Juice Juice = (Juice) thisObject;
            System.out.println("1. Go to " + Juice.getFlower() + ".");
            System.out.println("2. Make an incision.");
                System.out.println("3. Collect Juice.");
            if (Juice.getFlower().equals("flowers as ficus")) {
                for(int i = 0; i < 3; i++) {
                    System.out.printf("Wait %s hours\n", (3 - i));
                }
                System.out.println("The rubber is ready!");
            }
        }
        catch(ClassCastException e) {
            System.out.printf("ClassCastException: %s is not Juice", e, thisObject);
        }
    }
}
