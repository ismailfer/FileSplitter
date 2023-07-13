## File Utilities


## Build

``
mvn clean package
``

## File Splitting

``
java -cp FileSplitter-1.0.jar utils.FileSplitter <filename> <numLines> <lineSep(optional)>
``
#### Example: splits a file into N number of lines  

``
java -cp FileSplitter-1.0.jar utils.FileSplitter mylogfile-20230301.log 10000
``

### splits a file into N number of lines with custom line separator

``
java -cp FileSplitter-1.0.jar utils.FileSplitter mylogfile-20230301.log 10000 "\n\n"
``

``
java -cp FileSplitter-1.0.jar utils.FileSplitter mylogfile-20230301.log 10000 "\n-------------------------------------------------\n"
``

## Directory copying

### copying directories and its content
``
java -cp FileSplitter-1.0.jar utils.DirCopyApp <source dir> <target dir>
``


