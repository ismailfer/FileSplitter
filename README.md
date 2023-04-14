## File Splitter

Ideal for log file splitting

## Build

mvn clean build

## Running the app

java -cp FileSplitter-1.0.jar utils.FileSplitter <filepath> <numLines> <lineSep(optional)>

#### Example: splits a file into N number of lines  

java -cp FileSplitter-1.0.jar utils.FileSplitter mylogfile-20230301.log 10000

### splits a file into N number of lines with custom line separator

java -cp FileSplitter-1.0.jar utils.FileSplitter mylogfile-20230301.log 10000 "\n\n"

java -cp FileSplitter-1.0.jar utils.FileSplitter mylogfile-20230301.log 10000 "\n-------------------------------------------------\n"

