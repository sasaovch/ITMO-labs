package lab5.client.commands;

import java.util.HashMap;
import java.util.Map;

import lab5.client.data.SpaceMarine;
import lab5.client.utility.IOManager;
import lab5.client.utility.SpaceMarineCollection;


/**
 * Command 'group_counting_by_name'. Counts by group elements.
 */
public class GroupCountingByNameCommand extends Command {
    private final SpaceMarineCollection spaceMarineCollection;
    private final IOManager ioManager;

    public GroupCountingByNameCommand(SpaceMarineCollection spaceMarineCollection, IOManager ioManager) {
        super("group_counting_by_name", "groups the elements of the collection by the value of the name field");
        this.spaceMarineCollection = spaceMarineCollection;
        this.ioManager = ioManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean run(String str) {
            if (!str.isEmpty()) {
                ioManager.printerr("Incorrect input.");
                return false;
            }
            if (spaceMarineCollection.getSize() == 0) {
                ioManager.println("The collection is empty.");
                return true;
            }
            HashMap<String, Integer> outHashMap = spaceMarineCollection.groupCountingByField(SpaceMarine::getName);
            for (Map.Entry<String, Integer> entry : outHashMap.entrySet()) {
                ioManager.println("Name: " + entry.getKey() + ". Number of elements: " + entry.getValue());
            }
            return true;
    }
}