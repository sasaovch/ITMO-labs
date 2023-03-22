package lab5.client.commands;

import java.io.IOException;

import lab5.client.data.SpaceMarine;
import lab5.client.exceptions.IncorrectData;
import lab5.client.exceptions.IncorrectDataOfFileException;
import lab5.client.utility.AskMarine;
import lab5.client.utility.IOManager;
import lab5.client.utility.SpaceMarineCollection;

/**
 * Command 'update'. Updates the information about selected marine.
 */
public class UpdateCommand extends Command {
    private final SpaceMarineCollection spaceMarineCollection;
    private final AskMarine asker;
    private final IOManager ioManager;

    public UpdateCommand(SpaceMarineCollection spaceMarineCollection, AskMarine asker, IOManager ioManager) {
        super("update id {element}", "update the value of str collection item whose id is equal to the specified one");
        this.spaceMarineCollection = spaceMarineCollection;
        this.asker = asker;
        this.ioManager = ioManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IOException When something with file went wrong.
     * @throws IncorrectDataOfFileException When data in file isn't correct.
     * @throws IncorrectData When data for element is incorrect.
     * @return Command exit status.
     */
    @Override
    public boolean run(String str) throws IOException, IncorrectDataOfFileException, IncorrectData {
        Long id;
        try {
            id = Long.parseLong(str);
        } catch (NumberFormatException e) {
            ioManager.printerr("Id is not correct.");
            return false;
        }
        if (spaceMarineCollection.getSize() == 0) {
            ioManager.printerr("There are no such element in the collection.");
            return false;
        }
        SpaceMarine changeMarine = spaceMarineCollection.findByID(id);
        if (changeMarine == null) {
            ioManager.printerr("Id is not correct.");
            return false;
        }
        SpaceMarine newMarine = asker.askMarine();
        spaceMarineCollection.updateSpaceMarine(changeMarine, newMarine);
        ioManager.println("Marine has been successfully updated.");
        return true;
    }
}