### XBRL -> CSV Transformer

This project takes an XBRL file and transforms it into a CSV file. The CSV file is a table of the XBRL data, with the XBRL tags as the column headers.

#### Usage
```bash
java -jar XBRL-CSV.jar <folder-path> <batch-size>
java -jar XBRL-CSV.jar ../test-data 20
```

- "Batch Size" refers to the documents the process should iterate through before writing to the CSV file. This is to prevent the CSV data from getting too large and crashing the program.
- "Folder Path" refers to the folder containing the XBRL files to be transformed. This path is relative to the location of the jar file.