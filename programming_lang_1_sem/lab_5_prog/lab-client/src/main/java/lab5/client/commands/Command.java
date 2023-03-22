package lab5.client.commands;

import java.io.IOException;

import lab5.client.exceptions.IncorrectData;
import lab5.client.exceptions.IncorrectDataOfFileException;


/**
 * Abstract class for all commands.
 */
public abstract class Command {
    protected final String name;
    private final String description;
    
    protected Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return name of command.
     */
    public String getName() {
        return name;
    }

    /**
     * @return description of command.
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }

    abstract public boolean run(String a) throws IOException, IncorrectDataOfFileException, IncorrectData;
}