### XBRL -> CSV Transformer

This project takes an XBRL file and transforms it into a CSV file. The CSV file is a table of the XBRL data, with the XBRL 
tags as the column headers. This project was originally created as a commission for `Dean#6844` on Discord. 
Since then, the company went into liquidation, so I'm releasing the code for free with permission from Dean.

#### Usage
```bash
java -jar XBRL-CSV.jar <folder-path>
java -jar XBRL-CSV.jar ../test-data
```
Both `.html` & `.xml` input file formats are supported. Output will be written to `output/results.csv` in a comma-separated-value format.