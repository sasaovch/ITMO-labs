package lab5.client.commands;


import java.util.Map;

import lab5.client.utility.IOManager;

/**
 * Command 'help'. Print info about all programs.
 */
public class HelpCommand extends Command {
    private final CommandManager commandManager;
    private final IOManager ioManager;

    public HelpCommand(CommandManager commandManager, IOManager ioManager) {
        super("help", "print info about all commands");
        this.commandManager = commandManager;
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
        Map<String, Command> commandsMap = commandManager.getMap();
        for (Map.Entry<String, Command> entry : commandsMap.entrySet()) {
            ioManager.printcolon(entry.getValue().getName(), entry.getValue().getDescription());
        }
        return true;
    }
}