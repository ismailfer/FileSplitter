package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Splits a file into multiple files
 *
 * And optionally add spaces betweenlines; or live dividers
 *
 * Useful for log files
 *
 * @author ismail
 * @since 20230414
 */
public class FileSplitter
{


    public static void splitFile(String pFilePath, int pNumLinesPerFile, String pLineSep) throws IOException
    {
        System.out.println("Splitting file.... please wait...");

        String dir = FileUtil.getDirectoryFromPath(pFilePath);
        String filename = FileUtil.getFilenameFromPath(pFilePath);
        String filenameWithNoExt = FileUtil.getFilenameFromPathWithNoExtension(filename);
        String filenameExt = FileUtil.getExtensionFromFilePath(filename);

        FileInputStream fin = new FileInputStream(pFilePath);
        BufferedInputStream bis = new BufferedInputStream(fin);
        BufferedReader dis = new BufferedReader(new InputStreamReader(bis));

        String record = null;


        int lineNum = 0;

        FileOutputStream fout = null;
        BufferedWriter bout = null;


        int fileOutCount = 0;

        while ((record = dis.readLine()) != null) {

            // open new file for writing
            if (lineNum % pNumLinesPerFile == 0)
            {
                fileOutCount++;

                // close previous file (if open)
                if (fout != null)
                {
                    bout.flush();
                    bout.close();
                    fout.close();
                }

                // open new one
                String outputPath = dir + "/" + filenameWithNoExt + "-" + StringUtil.zeros(fileOutCount, 3) + "." + filenameExt;
                fout = new FileOutputStream(outputPath, false);
                bout = new BufferedWriter(new OutputStreamWriter(fout));

            }

            lineNum++;


            // write to file
            bout.write(record);

            bout.write(pLineSep);

        }


        // close previous file (if open)
        if (fout != null)
        {
            bout.flush();
            bout.close();
            fout.close();
        }


        // close streams
        dis.close();
        bis.close();
        fin.close();


        System.out.println("Splitting file completed; " + fileOutCount + " files");

    }




    public static void printUsage() {
        System.out.println("Usage: \n     FileSplitter <filename> <numLines> <lineSep>");
        System.out.println("   Example:   FileSplitter mylogfile-20230301.log 100000");
        System.out.println("   Example:   FileSplitter mylogfile-20230301.log 100000 \"\\n\\n\"");
    }


    public static void main(String[] args) {
        try {
            if ((args.length != 2 || args.length != 3) == false) {
                printUsage();
                throw new IllegalAccessException("Invalid input arguments");
            }

            String filename = args[0];
            int numLines = Integer.parseInt(args[1]);

            String lineSep = "\n";

            if (args.length == 3) {
                lineSep = args[2];

                // remove escapped chars
                lineSep = lineSep.replaceAll("\\\\n", "\n");
            }

            splitFile(filename, numLines, lineSep);


        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
