package lab_3.actions;
import lab_3.objects.*;
// check work of this class
public class GetJuiceAction implements GetThisObjectAction{

    public void getObjectAction(CollectAction collection, DescribeCollectAction decribe) throws ClassCastException{
        try {
            Juice Juice = (Juice) collection.getObject();
            collection.printResult();
            System.out.println("<< -- start collect Juice -- >>");
            decribe.describeProcess(Juice);
            System.out.println("<< -- finish collect Juice -- >>");
        }
        catch(ClassCastException e) {
            System.out.printf("ClassCastException: %s is not Juice", collection.object);
        }
    }
}
