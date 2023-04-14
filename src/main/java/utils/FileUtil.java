package utils;

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
}
