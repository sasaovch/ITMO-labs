package lab5.client.utility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lab5.client.data.SpaceMarine;
import lab5.client.exceptions.IncorrectData;

/**
 * Class operates the collection itself.
 */
public class SpaceMarineCollection {
    private final HashSet<SpaceMarine> spaceMarineSet;
    private final LocalDateTime initializationTime;
    private final TreeSet<Long> usedID;

    public SpaceMarineCollection(){
        spaceMarineSet = new HashSet<>();
        initializationTime = LocalDateTime.now();
        usedID = new TreeSet<>();
    }

    public SpaceMarineCollection(HashSet<SpaceMarine> spaceMarineSet){
        this.spaceMarineSet = spaceMarineSet;
        initializationTime = LocalDateTime.now();
        usedID = new TreeSet<>();
    }

    /**
     * Adds a new marine to collection.
     * @param element A Marine to add.
     * @throws IncorrectData Never throw.
     */
    public boolean addElement(SpaceMarine element) {
        try {
            if (Objects.equals(element.getID(), null)) {
                if (usedID.isEmpty()) {
                    element.setID(1L);
                    usedID.add(1L);
                } else {
                    element.setID(usedID.last() + 1);
                    usedID.add(usedID.last() + 1);
                }
            } else if (usedID.contains(element.getID())) {
                element.setID(usedID.last() + 1);
                usedID.add(usedID.last() + 1);
            } else {
                usedID.add(element.getID());
            }
        } catch (IncorrectData e) {
            e.printStackTrace(); // never throw
        }
        return spaceMarineSet.add(element);
    }

    /**
     * Adds a new marine to collection if it lower than all elements in set.
     * @param element A Marine to add.
     */
    public boolean addIfMin(SpaceMarine addSpaceMarine) {
        if (spaceMarineSet.size() == 0) {
            addElement(addSpaceMarine);
            return true;
        } else {
            SpaceMarine minSpaceMarine = spaceMarineSet.stream().min((o1, o2) -> o1.compareTo(o2)).orElse(new SpaceMarine());
            if (addSpaceMarine.compareTo(minSpaceMarine) < 0) {
                addElement(addSpaceMarine);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Removes a Marine to collection.
     * @param element A Marine to remove.
     */
    public boolean removeElement(SpaceMarine element){
        usedID.remove(element.getID());
        return spaceMarineSet.remove(element);
    }

    /**
     * Deletes all elements that match the condition 
     * @param condition Condition to remove.
     */
    public boolean removeIf(Predicate<SpaceMarine> condition) {
        Set<SpaceMarine> removeSet = spaceMarineSet.stream().filter(condition).collect(Collectors.toSet());
        if (removeSet.isEmpty()) {
            return false;
        }
        spaceMarineSet.removeAll(removeSet);
        usedID.clear();
        usedID.addAll(removeSet.stream().map(SpaceMarine::getID).collect(Collectors.toSet()));
        return true;
    }

    /**
     * @return Initialization time.
     */
    public LocalDateTime getTime(){
        return initializationTime;
    }

    /**
     * @return Size of Collection.
     */
    public int getSize(){
        return spaceMarineSet.size();
    }

    /**
     * @return Marine Collection.
     */
    public HashSet<SpaceMarine> getCollection(){
        return spaceMarineSet;
    }

    /**
     * @return last id which is used in set.
     */
    public Long getLastId(){
        return usedID.last();
    }

    /**
     * Clears the collection.
     */
    public void clearCollection(){
        usedID.clear();
        spaceMarineSet.clear();
    }

    /**
     * @return sorted list of Marine Collection.
     */
    public List<SpaceMarine> sortCollection(){
        List<SpaceMarine> list = new ArrayList<SpaceMarine>(getCollection());
        Collections.sort(list);
        return list;
    }

    /**
     * Count element which has the same value of field.
     * @param <R> Type of field.
     * @param getter Method for getting value of field.
     * @param value Value of field.
     * @return Number of elements in set with the same value.
     */
    public <R> int countBySomeThing(Function<SpaceMarine, R> getter, R value) {
        int counter = 0;
        for (SpaceMarine thatSpaceMarine : spaceMarineSet) {
            if (Objects.equals(getter.apply(thatSpaceMarine), value)) {
                counter += 1;
            }
        }
        return counter;
    }

    /**
     * @param <R> Type of field.
     * @param getter Method for getting value of field.
     * @return map: key - value of field, value - number of elements in set with the same value of key.
     */
    public <R> HashMap<String, Integer> groupCountingByField(Function<SpaceMarine, R> getter) {
        HashMap<String, Integer> outputMap = new HashMap<>();
        for (SpaceMarine spaceMarine : spaceMarineSet) {
            outputMap.compute(getter.apply(spaceMarine).toString(), (key, val) -> (Objects.equals(val, null) ? 1: val + 1));
        }
        return outputMap;
    }


    /**
     * Find element in set by ID.
     * @param id Id of element.
     * @return Element if it exits, null if element doesn't exit.
     */
    public SpaceMarine findByID(Long id) {
        for (SpaceMarine spaceMarine : spaceMarineSet) {
            if (id.equals(spaceMarine.getID())) {
                return spaceMarine;
            }
        }
        return null;
    }

    /**
     * Update element.
     * @param changeMarine Element is changed.
     * @param newMarine New element
     */
    public boolean updateSpaceMarine(SpaceMarine changeMarine, SpaceMarine newMarine) {
        try {
            newMarine.setID(changeMarine.getID());
        } catch (IncorrectData e) {
            e.printStackTrace();
        }
        return removeElement(changeMarine) && addElement(newMarine); 
    }
}