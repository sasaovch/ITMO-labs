package lab5.client.utility;

import java.io.IOException;
import java.util.Objects;

import lab5.client.commands.CommandManager;
import lab5.client.exceptions.IncorrectData;
import lab5.client.exceptions.IncorrectDataOfFileException;



/**
 * Operates command input.
 */
public class InputManager {
    private final CommandManager commands;
    private final IOManager ioManager;
    private Boolean statusInput;
    private Boolean statusFile;
    

    public InputManager(CommandManager commands, IOManager ioManager) {
        this.commands = commands;
        this.ioManager = ioManager;
        statusInput = true;
        statusFile = false;
    }

    /**
     * @return statusInput of input: true - run, false - stop.
     */
    public Boolean getStatusFile() {
        return statusFile;
    }

    /**
     * Mode for catching commands from user input.
     * @throws IOException When something with file went wrong.
     * @throws IncorrectDataOfFileException When data in file isn't correct.
     * @throws IncorrectData 
     */
    public void run() throws IOException, IncorrectDataOfFileException, IncorrectData {
        String name, line;
        String[] command = new String[3];
        boolean resultExecute;
        while (statusInput) {
            if (!statusFile) {
                ioManager.prompt();
            }
            line = ioManager.readLine();
            if (Objects.equals(line, null)) {
                break;
            }
            command = (line.trim() + " " + " ").split(" ", 3);
            name = command[0];
            if (name.equals("")) {
                continue;
            }
            if (!commands.getMap().containsKey(name)) {
                if (!statusFile) {
                    ioManager.printerr("Unknown commands. Print help for getting info about commands");
                } else {
                    ioManager.printerr("Unknow command in file.");
                    break;
                }
                continue;
            }
            if (!command[2].trim().equals("")) {
                if (!statusFile) {
                    ioManager.printerr("Incorrect input. Right: '" + commands.getMap().get(name).getName() + "'.");
                } else {
                    ioManager.printerr("Incorrect data in file.");
                    break;
                }
                continue;
            }
            String value = command[1];
            resultExecute = commands.getMap().get(name).run(value);
            if(!(!statusFile || resultExecute)) {
                break;
            }
        }
    }

    /**
     * Turn on input.
     */
    public void turnOnInput(){
        statusInput = true;
    }

    /**
     * Turn off input.
     */
    public void turnOffInput(){
        statusInput = false;
    }

    /**
     * Turn on file-mode.
     */
    public void turnOnFile(){
        statusFile = true;
    }

    /**
     * Turn off file-mode.
     */
    public void turnOffFile(){
        statusFile = false;
    }
}