package lab5.client.commands;

import java.util.List;

import lab5.client.data.SpaceMarine;
import lab5.client.utility.IOManager;
import lab5.client.utility.SpaceMarineCollection;

/**
 * Command 'print_descending'. Prints elements in descending order.
 */
public class PrintDescendingCommand extends Command {
    private final SpaceMarineCollection spaceMarineCollection;
    private final IOManager ioManager;

    public PrintDescendingCommand(SpaceMarineCollection spaceMarineCollection, IOManager ioManager) {
        super("print_descending", "print all the elements of the collection in descending order");
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
        List<SpaceMarine> list = spaceMarineCollection.sortCollection();
        for (int i = list.size() - 1; i >= 0; i--)
            ioManager.println(list.get(i));
        return true;
    }
}