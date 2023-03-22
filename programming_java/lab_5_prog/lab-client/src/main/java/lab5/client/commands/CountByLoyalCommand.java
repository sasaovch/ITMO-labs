package lab5.client.commands;

import lab5.client.data.SpaceMarine;
import lab5.client.utility.IOManager;
import lab5.client.utility.SpaceMarineCollection;

/**
 * Command 'count_by_loyal'. Counts element with such loyal.
 */
public class CountByLoyalCommand extends Command {
    private final SpaceMarineCollection spaceMarineCollection;
    private final IOManager ioManager;

    public CountByLoyalCommand(SpaceMarineCollection spaceMarineCollection, IOManager ioManager) {
        super("count_by_loyal loyal", "print the number of elements whose value of the loyal field is equal to the specified");
        this.spaceMarineCollection = spaceMarineCollection;
        this.ioManager = ioManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean run(String strLoyal) {
        Boolean loyal;
        if (strLoyal.equals("")) {
            loyal = null;
        } else if (!(strLoyal.equals("true") || strLoyal.equals("false"))) {
            ioManager.printerr("The value of Loyal isn't correct (true, false, null - empty line)");
            return false;
        } else {
            loyal = Boolean.parseBoolean(strLoyal);
        }
        ioManager.println("Number of elements: " + spaceMarineCollection.countBySomeThing(SpaceMarine::getLoyal, loyal));
        return true;
    }
}