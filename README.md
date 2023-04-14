## File Splitter

Ideal for log file splitting

## Build

mvn clean build

## Running the app

### To split a file into N number of lines

java -jar FileSplitter-1.0.jar utils.FileSplitter <filename> <numLines> <lineSep(optional)>

#### Example

java -jar FileSplitter-1.0.jar utils.FileSplitter mylogfile-20230301.log 10000

### To split a file into N number of lines with custom line separator

java -jar FileSplitter-1.0.jar utils.FileSplitter <filename> <numLines> <lineSep>

#### Example

java -jar FileSplitter-1.0.jar utils.FileSplitter mylogfile-20230301.log 10000 "\n\n"

java -jar FileSplitter-1.0.jar utils.FileSplitter mylogfile-20230301.log 10000 "\n-------------------------------------------------\n"

