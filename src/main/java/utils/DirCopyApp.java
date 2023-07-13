package utils;

/**
 * Copy a directory
 *
 * @author ismail
 * @since 20230713
 */
public class DirCopyApp
{

    public static void printUsage()
    {
        System.out.println("Usage: \n     DirCopyApp <source dir> <target dir>");
        System.out.println("   Example:   DirCopyApp mydir1 mydir2");
    }

    public static void main(String[] args)
    {
        try
        {
            if ((args.length == 2) == false)
            {
                printUsage();
                throw new IllegalAccessException("Invalid input arguments");
            }

            String srcDir = args[0];
            String targetDir = args[1];

            long tick = System.currentTimeMillis();

            System.out.println("Started copying [" + srcDir + "] to [" + targetDir + "] ....");
            System.out.println("This may take a while.. please wait...");

            int filesCount = FileUtil.copyDir(srcDir, targetDir, false);

            tick = System.currentTimeMillis() - tick;

            System.out.println("Finished copying [" + srcDir + "] to [" + targetDir + "]");
            System.out.println(filesCount + " files copied in " + (tick / 60000) + " minutes");


        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
