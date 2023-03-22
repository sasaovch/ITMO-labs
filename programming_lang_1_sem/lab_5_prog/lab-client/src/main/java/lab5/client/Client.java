package lab5.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Objects;

import com.google.gson.JsonSyntaxException;

import lab5.client.commands.*;
import lab5.client.exceptions.IncorrectData;
import lab5.client.exceptions.IncorrectDataOfFileException;
import lab5.client.utility.*;

public final class Client {

    private Client() {}
    
    /**
     * Main application class. Creates all instances and runs the program.
     * @author Ovcharenko Aleksandr.
     * @throws IOException When something with file went wrong.
     * @throws IncorrectDataOfFileException Never throw.
     * @throws IncorrectData Never throw.
     */
    public static void main(String[] args) throws JsonSyntaxException, IncorrectData, IncorrectDataOfFileException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out, true);
        IOManager ioManager = new IOManager(reader, writer, "$");
        String filePath = "pars.json" ;//System.getenv("filePath");
        if (Objects.equals(filePath, null)){
            ioManager.printerr("There is no such variable \"filePath\"");
            return;
        }        
        ParsingJSON pars = new ParsingJSON();
        try {
            File file = new File(filePath);
            SpaceMarineCollection collection = pars.deSerialize(ioManager.readfile(file));
            if (Objects.equals(collection, null)) {
                ioManager.printerr("Incorrect data in file for parsing.");
                return;
            }
            CommandManager commands = new CommandManager();
            InputManager inputManager = new InputManager(commands, ioManager);
            AskMarine consoleAsker = new AskMarine(inputManager, ioManager);
            commands.addCommand("help", new HelpCommand(commands, ioManager));
            commands.addCommand("info", new InfoCommand(collection, ioManager));
            commands.addCommand("show", new ShowCommand(collection, ioManager));
            commands.addCommand("add", new AddCommand(collection, consoleAsker, ioManager));
            commands.addCommand("update", new UpdateCommand(collection, consoleAsker, ioManager));
            commands.addCommand("remove_by_id", new RemoveByIdCommand(collection, ioManager));
            commands.addCommand("clear", new ClearCommand(collection, ioManager));
            commands.addCommand("exit", new ExitCommand(inputManager, ioManager));
            commands.addCommand("add_if_min", new AddIfMinCommand(collection, consoleAsker, ioManager));
            commands.addCommand("remove_greater", new RemoveGreaterCommand(collection, consoleAsker, ioManager));
            commands.addCommand("remove_lower", new RemoveLowerCommand(collection, consoleAsker, ioManager));
            commands.addCommand("group_counting_by_name", new GroupCountingByNameCommand(collection, ioManager));
            commands.addCommand("count_by_loyal", new CountByLoyalCommand(collection, ioManager));
            commands.addCommand("print_descending", new PrintDescendingCommand(collection, ioManager));
            commands.addCommand("save", new SaveCommand(collection, pars, file, ioManager));
            commands.addCommand("execute_script", new ExecuteScriptCommand(ioManager, inputManager));
            inputManager.run();
        } catch (IOException e) {
            ioManager.printerr("File isn't exist or invalid user rights.");
        } catch (JsonSyntaxException e) {
            ioManager.printerr("Data in file isn't correct");
        }
    }
}