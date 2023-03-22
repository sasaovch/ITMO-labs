package lab5.client.commands;

import lab5.client.data.SpaceMarine;
import lab5.client.utility.IOManager;
import lab5.client.utility.SpaceMarineCollection;

/**
 * Command 'show'. Shows information about all elements of the collection.
 */
public class ShowCommand extends Command {
    private final SpaceMarineCollection spaceMarineCollection;
    private final IOManager ioManager;

    public ShowCommand(SpaceMarineCollection spaceMarineCollection, IOManager ioManager) {
        super("show", "print all elements of collection");
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
            ioManager.printerr("Incorrect input. Right: '" + name + "'.");
            return false;
        }
        if (spaceMarineCollection.getSize() == 0) {
            ioManager.println("The collection is empty.");
            return true;
        }
        for (SpaceMarine spaceMarine : spaceMarineCollection.getCollection()) {
            ioManager.println(spaceMarine);
        }
        return true;
    }
}