package lab5.client.commands;

import lab5.client.utility.IOManager;
import lab5.client.utility.SpaceMarineCollection;


/**
 * Command 'remove_by_id'. Removes the element by its ID.
 */
public class RemoveByIdCommand extends Command {
    private final lab5.client.utility.SpaceMarineCollection spaceMarineCollection;
    private final IOManager ioManager;

    public RemoveByIdCommand(SpaceMarineCollection spaceMarineCollection, IOManager ioManager) {
        super("remove_by_id id", "remove element with such id");
        this.spaceMarineCollection = spaceMarineCollection;
        this.ioManager = ioManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean run(String str) {
        Long id;
        try {
            id = Long.parseLong(str);
        } catch (NumberFormatException e) {
            ioManager.printerr("Id is not correct.");
            return false;
        }
            if (spaceMarineCollection.removeIf(spMar -> spMar.getID().equals(id))) {
                ioManager.println("Space Marine has been successfully deleted.");
                return true;
            } else {
                ioManager.printerr("Uknown Id.");
                return false;
            }
    }
}