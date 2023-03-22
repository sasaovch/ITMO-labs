package lab5.client.commands;

import java.time.format.DateTimeFormatter;

import lab5.client.data.SpaceMarine;
import lab5.client.utility.IOManager;
import lab5.client.utility.SpaceMarineCollection;


/**
 * Command 'info'. Prints information about the collection.
 */
public class InfoCommand extends Command {
    private final SpaceMarineCollection spaceMarineCollection;
    private final IOManager ioManager;

    public InfoCommand(SpaceMarineCollection spaceMarineCollection, IOManager ioManager) {
        super("info", "print info about collection: type, initialization date, number of elements");
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
        ioManager.println("Initialization time: " +
                spaceMarineCollection.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
        "\nNumber of Marines: " + spaceMarineCollection.getSize() +
        "\nType" + SpaceMarine.class);
        return true;
    }
}