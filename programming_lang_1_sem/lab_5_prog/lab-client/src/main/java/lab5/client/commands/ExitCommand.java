package lab5.client.commands;


import lab5.client.utility.InputManager;
import lab5.client.utility.IOManager;

/**
 * Command 'exit'.
 */
public class ExitCommand extends Command {
    private final InputManager inputManager;
    private final IOManager ioManager;
    
    public ExitCommand(InputManager inputManager, IOManager ioManager) {
        super("exit", "completes programm");
        this.inputManager = inputManager;
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
        ioManager.println("Good Buy!\n\\(?_?)/");
        inputManager.turnOffInput();
        return true;
    }
}