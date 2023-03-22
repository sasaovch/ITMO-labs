package lab5.client.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import lab5.client.exceptions.IncorrectData;
import lab5.client.exceptions.IncorrectDataOfFileException;
import lab5.client.utility.InputManager;
import lab5.client.utility.IOManager;

/**
 * Command 'execute_script'. Executes scripts from a file.
 */
public class ExecuteScriptCommand extends Command {
    private final IOManager ioManager;
    private final InputManager inputManager;
    private final Stack<BufferedReader> previosReaders = new Stack<>();
    private final Stack<File> currentFiles = new Stack<>();


    public ExecuteScriptCommand(IOManager ioManager, InputManager inputManager) {
        super("execute_script file_name", "read and execute the script from the specified file");
        this.ioManager = ioManager;
        this.inputManager = inputManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IOException When something with file went wrong.
     * @throws IncorrectData When data for element is incorrest.
     * @return Command exit status.
     */
    @Override
    public boolean run(String filename) throws IOException, IncorrectData  {
        File file = new File(filename);
        try (BufferedReader newReader = new BufferedReader(new FileReader(file))) { 
            if (file.exists() && !currentFiles.contains(file)) {
                ioManager.println("Started to execute script: " + file.getName());
                ioManager.println("------------------------------------------");
                currentFiles.push(file);
                previosReaders.push(ioManager.getBufferedReader());
                ioManager.setBufferReader(newReader);
                inputManager.turnOnFile();
                inputManager.run();
                currentFiles.pop();
                ioManager.setBufferReader(previosReaders.pop());
                inputManager.turnOffFile();
            } else if (!file.exists()) {
                ioManager.printerr("File doesn't exist.");
                return false;
            } else if(currentFiles.contains(file)) {
                ioManager.printerr("The file was not executed due to recursion.");
                return false;
            }
        } catch (IncorrectDataOfFileException | NullPointerException e) {
            ioManager.printerr("Incorrect data of file.");
            return false;
        } catch (IOException e) {
            ioManager.printerr("File isn't exist or invalid user rights.");
        }
        ioManager.println("------------------------------------------");
        ioManager.println("Finished to execute script: " + file.getName());
        return true;
    }
}