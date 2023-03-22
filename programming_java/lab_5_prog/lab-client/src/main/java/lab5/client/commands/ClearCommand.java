package lab5.client.commands;

import lab5.client.utility.IOManager;
import lab5.client.utility.SpaceMarineCollection;

/**
 * Command 'clear'. Clears the collection.
 */
public class ClearCommand extends Command {
    private final SpaceMarineCollection spaceMarineCollection;
    private final IOManager ioManager;

    public ClearCommand(SpaceMarineCollection spaceMarineCollection, IOManager ioManager) {
        super("clear", "clear the collection");
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
        spaceMarineCollection.clearCollection();
        ioManager.println("The collection is cleared.");
        return true;
    }
}