package me.savag3;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Savag3life
 * @since 1.0.0
 */
public class Record {

    // Index 0 is always the owners file_name
    @Getter private final List<String> values = new ArrayList<>();

    public Record(HashMap<String, String> valueMap) {

        // Check the map for existing keys first to keep order in csv file
        Parser.getInstance().getKeys().forEach(key -> {
            String existing = valueMap.get(key);

            // If null, it means the map provided doesn't contain this key
            // So we "spoof" the value so that the csv file is still in order
            if (existing == null) {
                addNewColumn("");
            } else {
                // Otherwise, we add the value to the list
                addNewColumn(existing);
                // And remove the key from the map so that we don't add it again when we iterate through the map
                // This is to prevent duplicates
                valueMap.remove(key);
            }
        });

        // Update all other rows of new data
        valueMap.forEach((key, value) -> {
            values.add(value);
            // Add the key to the list of keys
            // Has to update each of the existing records to parse CSV properly
            Parser.getInstance().addNewColumn(key, getFileName());
        });

        Parser.getInstance().addRecord(this);
    }


    public void addNewColumn(String value) {
        values.add(value);
    }

    public String getFileName() {
        return values.get(0);
    }
}
