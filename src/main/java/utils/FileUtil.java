package utils;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class FileUtil {

    public static String getDirectoryFromPath(String pFilePath)
    {
        // convert all seperators into '/'
        String dir = StringUtil.replaceAll(pFilePath, "\\", "/");

        String file = StringUtil.getAtRight(pFilePath, '/', 1);
        if (StringUtil.size(file, '.') > 0)
        {
            dir = StringUtil.deleteAtRight(dir, '/', 1);
        }
        return dir;
    }

    public static String getFilenameFromPathWithNoExtension(String pFilePath)
    {
        // convert all seperators into '/'
        String path = getFilenameFromPath(pFilePath);

        if (path.indexOf('.') != -1)
        {
            String file = StringUtil.deleteAtRight(path, '.', 1);

            return file;
        }
        else
        {
            return path;
        }
    }

    public static String getExtensionFromFilePath(String pFilePath)
    {
        if (pFilePath.indexOf('.') == -1)
        {
            return "";
        }

        String filename = getFilenameFromPath(pFilePath);
        String extension = StringUtil.getAtRight(filename, '.', 1);
        if (StringUtil.size(extension, '.') > 0)
        {
            return extension;
        }
        return "";
    }

    public static String getFilenameFromPath(String pFilePath)
    {
        // convert all seperators into '/'
        String dir = StringUtil.replaceAll(pFilePath, "\\", "/");

        String file = StringUtil.getAtRight(dir, '/', 1);

        if (StringUtil.size(file, '.') > 0)
        {
            return file;
        }

        return null;
    }




    /**
     * move a file to somewhere else
     *
     * @param pSourceFilePath
     *            source
     * @param pDestinationFilePath
     *            destination
     * @param pOverride
     *            override
     * @throws Exception
     *             exception
     */
    public static void move(String pSourceFilePath, String pDestinationFilePath, boolean pOverride) throws Exception
    {
        // first copy the files
        copy(pSourceFilePath, pDestinationFilePath, pOverride);

        boolean deleted = deleteFile(pSourceFilePath);

        if (deleted == false)
            System.err.println("move() Unable to delete file after moving: " + pSourceFilePath);
    }

    public static void rename(String pSourceFilename, String pDestinationFilename) throws Exception
    {
        File file = new File(pSourceFilename);
        boolean success = file.renameTo(new File(pDestinationFilename));

        if (success == false)
            throw new IOException("Failed to rename file [" + pSourceFilename + "] to [" + pDestinationFilename + "]");
    }

    /**
     * Delete a file
     *
     * @param pFolderPath
     *            folder path
     * @throws Exception
     *             exeption of any
     */
    public static boolean deleteFile(String pFilePath) throws IOException
    {
        File file = new File(pFilePath);
        return file.delete();
    }

    /**
     * Delete a file
     *
     * @param pFolderPath
     *            folder path
     * @throws Exception
     *             exeption of any
     */
    public static boolean deleteFile(File file) throws IOException
    {
        return file.delete();
    }

    /**
     * @param pFilepath
     * @throws IOException
     */
    public static void deleteDir(String pFilepath) throws IOException
    {
        deleteDir(new File(pFilepath));
    }

    public static void deleteDir(File d) throws IOException
    {
        deleteDir(d, false);
    }

    /**
     * Delete a directory
     *
     * @param d the directory to delete
     * @param pRecursive delete the directory and content; otherwise delete the directory if it is empty only
     */
    public static void deleteDir(File d, boolean pRecursive) throws IOException
    {
        if (d.isDirectory() == false)
            throw new IOException("Not a directory: " + d.getAbsolutePath());

        String[] list = d.list();

        if (list == null)
        {
            list = new String[0];
        }

        for (int i = 0; i < list.length; i++)
        {
            String s = list[i];
            File f = new File(d, s);
            if (f.isDirectory())
            {
                deleteDir(f, pRecursive);
            }
            else
            {
                if (deleteFile(f) == false)
                {
                    throw new IOException("Unable to delete file " + f.getAbsolutePath());
                }
            }
        }

        if (d.delete() == false)
        {
            throw new IOException("Unable to delete directory " + d.getAbsolutePath());
        }
    }

    /**
     * Copy a directory and its content to a new location
     *
     * @param d the directory to delete
     */
    public static int copyDir(String pSourceFolder, String pDestinationFolder) throws IOException
    {
        return copyDir(new File(pSourceFolder), pDestinationFolder, true);
    }

    /**
     * Copy a directory and its content to a new location
     *
     * @param d the directory to delete
     */
    public static int copyDir(String pSourceFolder, String pDestinationFolder, boolean pQuite) throws IOException
    {
        return copyDir(new File(pSourceFolder), pDestinationFolder, pQuite);
    }

    /**
     * Copy a directory and its content to a new location
     *
     * @param d the directory to delete
     */
    public static int copyDir(File d, String pDestinationFolder, boolean pQuite) throws IOException
    {
        if (pQuite == false)
            System.out.println("copyDir [" + d.getAbsolutePath() + "]");

        int fileCount = 0;

        if (d.isDirectory() == false)
        {
            throw new IOException("Not a directory: " + d.getAbsolutePath());
        }

        // Create destination if necessary
        if (FileUtil.isDirectoryExists(pDestinationFolder) == false)
        {
            FileUtil.mkdirs(pDestinationFolder);
        }

        String[] list = d.list();
        if (list == null)
        {
            list = new String[0];
        }

        for (int i = 0; i < list.length; i++)
        {
            String s = list[i];
            File f = new File(d, s);

            String destination = pDestinationFolder + "/" + f.getName();

            if (f.isDirectory())
            {
                fileCount += copyDir(f, destination, pQuite);
            }
            // if this is a file
            else
            {
                copy(f.getAbsolutePath(), destination);

                fileCount++;
            }
        }

        return fileCount;
    }

    /**
     * copy a file to somewhere else
     *
     * @param pSourceFilePath
     *            source file path
     * @param pDestinationFilePath
     *            destination file path
     * @param pOverride
     *            boolean
     * @throws Exception
     *             exception of any
     */
    public static void copy(String pSourceFilePath, String pDestinationFilePath, boolean pOverride) throws Exception
    {
        // check if file exists in destination, then delete it first
        File pDestinationDir = new File(pDestinationFilePath);
        if (pOverride && pDestinationDir.exists())
            pDestinationDir.delete();

        // now, copy the file
        FileInputStream fis = new FileInputStream(pSourceFilePath);
        FileOutputStream fos = new FileOutputStream(pDestinationFilePath);

        byte[] buf = new byte[10000];
        int size = 0;
        while ((size = fis.read(buf)) > 0)
            fos.write(buf, 0, size);

        fos.close();
        fis.close();
    }

    public static void copy(String pSourceFilePath, String pDestinationFilePath) throws FileNotFoundException, IOException
    {
        copy(new File(pSourceFilePath), new File(pDestinationFilePath));
    }

    public static void copy(File pSource, File pDestination) throws IOException
    {
        // log.debugln("About to copy <" + pSource.getAbsolutePath() + "> to <"
        // + pDestination.getAbsolutePath() + ">");

        FileInputStream fis = new FileInputStream(pSource);
        FileOutputStream fos = new FileOutputStream(pDestination);
        byte[] buf = new byte[1024];
        int i = 0;

        while ((i = fis.read(buf)) != -1)
        {
            fos.write(buf, 0, i);
        }
        fis.close();
        fos.close();

    }



    /**
     * Checks if directory exists
     *
     * @param pDirectoryPath
     *            dir path
     * @return boolean
     */
    public static boolean isDirectoryExists(String pDirectoryPath)
    {
        File file = new File(pDirectoryPath);
        return file.isDirectory() && file.exists();
    }

    /**
     * Create a directory
     *
     * @param pDirectoryPath
     *            path
     * @return boolean
     */
    public static boolean mkdir(String pDirectoryPath)
    {
        File file = new File(pDirectoryPath);

        if (file.exists())
        {
            return false;
        }
        else
        {
            return file.mkdir();
        }
    }

    /**
     * Create a directory and all parent directories (if not exists)
     *
     * @param s
     * @return
     */
    public static boolean mkdirs(String s) throws IOException
    {
        File file = new File(s);

        if (file.exists())
        {
            return false;
        }
        else
        {
            return file.mkdirs();
        }
    }


}
