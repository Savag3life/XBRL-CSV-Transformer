package me.savag3.xbrl;

import java.util.List;

/**
 * @author Savag3life
 * @since 1.0.0
 */
public interface Formats {

    /**
     * Converts a list of Strings into a single comma-separated String
     * @param values The list of Strings to convert
     * @return The comma-separated String
     */
    default String arrayToCSV(List<String> values) {
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            sb.append(value).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

}
