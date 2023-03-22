package lab5.client.commands;

import java.io.IOException;

import lab5.client.exceptions.IncorrectDataOfFileException;
import lab5.client.utility.AskMarine;
import lab5.client.utility.IOManager;
import lab5.client.utility.SpaceMarineCollection;


/**
 * Command 'remove_greater'. Removes elements greater than user entered.
 */
public class RemoveGreaterCommand extends Command {
    private final SpaceMarineCollection spaceMarineCollection;
    private final AskMarine asker;
    private final IOManager ioManager;

    public RemoveGreaterCommand(SpaceMarineCollection spaceMarineCollection, AskMarine asker, IOManager ioManager) {
        super("remove_greater {element}", "remove all items from collection that exceed the specified");
        this.spaceMarineCollection = spaceMarineCollection;
        this.asker = asker;
        this.ioManager = ioManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IOException When something with file went wrong.
     * @throws IncorrectDataOfFileException When data in file isn't correct.
     */
    @Override
    public boolean run(String str) throws IOException, IncorrectDataOfFileException {
        if (!str.isEmpty()) {
            ioManager.printerr("Incorrect input. Right: '" + name + "'.");
            return false;
        }
        Integer specifiedHealth = asker.askHealth();
        Integer specifiedHeart = asker.askHeartCount();
        if (spaceMarineCollection.removeIf(spMar -> 
            {
                if (spMar.getHealth() > specifiedHealth) {
                    return true;
                }
                return (spMar.getHealth() == specifiedHealth) && (spMar.getHeartCount() > specifiedHeart);
            })) {
            ioManager.println("All items have been successfully deleted.");
        } else {
            ioManager.println("No element has been deleted.");
        }
        return true;
    }
}