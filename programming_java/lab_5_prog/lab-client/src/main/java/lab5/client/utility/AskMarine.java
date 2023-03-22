package lab5.client.utility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import lab5.client.data.AstartesCategory;
import lab5.client.data.Chapter;
import lab5.client.data.Coordinates;
import lab5.client.data.SpaceMarine;
import lab5.client.exceptions.IncorrectData;
import lab5.client.exceptions.IncorrectDataOfFileException;

/**
 * Asks a user a Marine's value.
 */
public class AskMarine {
    private final InputManager inputManager;
    private final IOManager ioManager;

    public AskMarine(InputManager inputManager, IOManager ioManager) {
        this.inputManager = inputManager;
        this.ioManager = ioManager;
    }

    /**
     * Asks a user the Marine's name.
     * @return Marine's name.
     * @throws IOException If something goes wrong with reading file.
     * @throws IncorrectDataOfFileException When data in file isn't correct.
     */
    public String askName() throws IOException, IncorrectDataOfFileException {
        String name;
        if (!inputManager.getStatusFile()) {
            name = asker(arg -> arg, 
                         arg -> ((String) arg).length() > 0,  
                         "Enter name (String)",
                         "The string must not be empty.", false);
        } else {
            name = ioManager.readLine().trim();
                if (name.equals("")) {
                    throw new IncorrectDataOfFileException();
                }
        }
        return name;
    }

    /**
     * Asks a user the Marine's coordinates.
     * @return Marine's coordinates.
     * @throws Incorrectlab5.client.dataOfFileException If script is running and something goes wrong.
     * @throws IOException If something goes wrong with reading file.
     * @throws IncorrectDataOfFileException When data in file isn't correct.
     * @throws IncorrectData When data for element is incorrect.
     */
    public Coordinates askCoordinates() throws IOException, IncorrectDataOfFileException, IncorrectData {
        double coordinateX = 0;
        Long coordinateY = null;
        if (!inputManager.getStatusFile()) {
            coordinateX = asker(Double::parseDouble, 
                                arg -> true, 
                                "Enter coordinates: X (double)", 
                                "Incorrect input. X must be double.", 
                                false);
            coordinateY = asker(Long::parseLong, 
                                arg -> true, 
                                "Enter coordinates: Y (Long)", 
                                "Incorrect input. Y must be Long and not null.", 
                                false);
        } else {
            try {
                coordinateX = Double.parseDouble(ioManager.readLine());
                coordinateY = Long.parseLong(ioManager.readLine());
            } catch (NumberFormatException e) {
                throw new IncorrectDataOfFileException();
            }
        }
        Coordinates cor = new Coordinates(coordinateX, coordinateY);
        return cor;
    }

    /**
     * Asks a user the Marine's health.
     * @return Marine's health.
     * @throws IOException If something goes wrong with reading file.
     * @throws IncorrectDataOfFileException When data in file isn't correct.
     */
    public Integer askHealth() throws IOException, IncorrectDataOfFileException{
        Integer health;
        if (!inputManager.getStatusFile()) {
            health = asker(Integer::parseInt, 
                           arg -> ((Integer) arg) > 0, 
                           "Enter the level of health (Integer)",
                           "Health must be Integer, not null and greater than zero.", 
                           false);
        } else {
            try {
                health = Integer.parseInt(ioManager.readLine());
            } catch (NumberFormatException e) {
                throw new IncorrectDataOfFileException();
            }
            if (health <= 0) {
               throw new IncorrectDataOfFileException();
            }
        }
        return health;
    }

    /**
     * Asks a user the Marine's heart count.
     * @return Marine's heart count.
     * @throws IOException If something goes wrong with reading file.
     * @throws IncorrectDataOfFileException When data in file isn't correct.
     */
    public Integer askHeartCount() throws IOException, IncorrectDataOfFileException{
        Integer heartCount;
        if (!inputManager.getStatusFile()) {
            heartCount = asker(Integer::parseInt, 
                               arg -> 1 <= ((Integer) arg) && arg <= 3, 
                               "Enter heart count: from 1 to 3 (Integer)",
                               "Heartcount must be form 1 to 3 (Integer)", 
                               false);
        } else {
            try {
                heartCount = Integer.parseInt(ioManager.readLine().trim());
                    if (!((1 <= heartCount) && (heartCount <= 3))) {
                        throw new NumberFormatException();
                    }
            } catch(NumberFormatException e) {
                throw new IncorrectDataOfFileException();
            }
        }
        return heartCount;
    }

    /**
     * Asks a user the Marine's loyal.
     * @return Marine's loyal.
     * @throws IOException If something goes wrong with reading file.
     * @throws IncorrectDataOfFileException When data in file isn't correct.
     */
    public Boolean askLoyal() throws IOException, IncorrectDataOfFileException {
        Boolean loyal;
        if (!inputManager.getStatusFile()) {
                loyal = asker(arg -> {
                    if (!(arg.equals("false") || arg.equals("true") || arg.equals(""))) {
                        throw new NumberFormatException();
                    }
                    return Boolean.parseBoolean(arg);
                                    }, 
                              arg -> true, 
                              "Enter loyal: true, false or null - empty line.",
                              "Incorrect input - loyal is only true, false or null - empty line.",
                              true);
        } else {
            String str = ioManager.readLine().trim().toLowerCase();
            if ((str.equals("true")) || (str.equals("false")) || (str.equals(""))) {
                if (str.equals("")) {
                    loyal = null;
                } else {
                    loyal = Boolean.parseBoolean(str);
                }
            } else {
                throw new IncorrectDataOfFileException();
            }
        }
        return loyal;
    }

    /**
     * Asks a user the Marine's category.
     * @return Marine's category.
     * @throws IOException If something goes wrong with reading file.
     * @throws IncorrectDataOfFileException When data in file isn't correct.
     */
    public AstartesCategory askCategory() throws IOException, IncorrectDataOfFileException {
        AstartesCategory category;
        if (!inputManager.getStatusFile()) {
                category = asker(arg -> AstartesCategory.valueOf(arg.toUpperCase()), 
                                 arg -> true, 
                                 "Enter category: " + AstartesCategory.listOfCategory(), 
                                 "The category is not in the list.", 
                                 false);
        } else {
            try {
                String str = ioManager.readLine().trim().toUpperCase();
                category = AstartesCategory.valueOf(str);
            }
            catch (IllegalArgumentException exception) {
                throw new IncorrectDataOfFileException();
            }
        }
        return category;
    }

    /**
     * Asks a user the Marine's Chapter.
     * @return Marine's Chapter.
     * @throws IOException If something goes wrong with reading file.
     * @throws IncorrectDataOfFileException When data in file isn't correct.
     * @throws IncorrectData When data for element is incorrect.
     */
    public Chapter askChapter() throws IOException, IncorrectDataOfFileException, IncorrectData {
        String name, parentLegion, world;
        long marinesCount;
        if (!inputManager.getStatusFile()) {
            name = asker(arg -> arg, arg -> true, "Enter name of chapter, empty line if chapter is null", 
                         "", true);
            if (!Objects.equals(name, null)) {
                parentLegion = asker(arg -> arg, arg -> true, "Enter parent Legion of chapter", 
                                     "", false);
                marinesCount = asker(Long::parseLong, arg -> 0 < ((Long) arg) && ((Long) arg) <= 1000, 
                                     "Enter marines count of chapter: from 1 to 1000 (Integer)", 
                                     "Marines count must be Integer, not null and from 1 to 1000.", false);
                world = asker(x -> x, arg -> ((String) arg).length() > 0,  "Enter name (String)",
                              "The string must not be empty.", false);
            } else {
                return null;
            }
        } else {
            name = ioManager.readLine();
            if (name.equals("")) {
                return null;
            }
            parentLegion = ioManager.readLine().trim();
            marinesCount = Long.parseLong(ioManager.readLine().trim());
            if ((0 > marinesCount) || (marinesCount > 1000)) {
                throw new IncorrectDataOfFileException();
            }
            world = ioManager.readLine().trim();
            if (world.equals("")) {
                throw new IncorrectDataOfFileException();
            }
        }
        Chapter chapter = new Chapter(name, parentLegion, marinesCount, world);
        return chapter;
    }

    /**
     * Asks a user the Marine.
     * @return Marine.
     * @throws IOException If something goes wrong with reading file.
     * @throws IncorrectDataOfFileException When data in file isn't correct.
     * @throws IncorrectData When data for element is incorrect.
     */
    public  SpaceMarine askMarine() throws IOException, IncorrectDataOfFileException, IncorrectData {
        String name = askName();
        Coordinates coordinates = askCoordinates();
        Integer health = askHealth();
        Integer heartCount = askHeartCount();
        Boolean loyal = askLoyal();
        AstartesCategory category = askCategory();
        Chapter chapter = askChapter();
        return new SpaceMarine(name, coordinates, LocalDateTime.now(), health, heartCount, loyal, category, chapter);
    }

    /**
     * Parsing for input.
     * @param <T> Type for retrurn element.
     * @param function Function for parsing.
     * @param predicate Condition for element.
     * @param askField Message for input.
     * @param wrongValue Message if value isn't correct.
     * @param nullable True if element can be null.
     * @return Element.
     * @throws IOException When something with file went wrong.
     */
    public <T> T asker(Function<String, T> function,
                       Predicate<T> predicate,  
                       String askField, 
                       String wrongValue, 
                       Boolean nullable) throws IOException {
        String stringIn;
        T value;
        while (true) {
            ioManager.println(askField);
            ioManager.prompt();
            try {
                stringIn = ioManager.readLine().trim();
                if ("".equals(stringIn) && nullable) {
                    return null;
                }
                value = function.apply(stringIn);
            } catch (IllegalArgumentException e) {
                ioManager.printerr(wrongValue);
                continue;
            }
            if (predicate.test(value)) {
                return value;
            } else {
                ioManager.printerr(wrongValue);
            }
        }
    }
}