package util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

/**
 * This is an unzip class
 */
public class Unzip {

    private final String SLASH = "/";

    public boolean start(String dir)  {
        Unzip(dir);
        return true;
    }

    private void createDir(final String dir) {
        File file = new File(dir);
        if (!file.exists())
            file.mkdirs();
    }

    private void createFolder(final String dirName) {
        if (dirName.endsWith(SLASH))
            createDir(dirName.substring(0, dirName.length() - 1));
    }

    private void checkFolder(final String file_path) {
        if (!file_path.endsWith(SLASH) && file_path.contains(SLASH)) {
            String dir = file_path.substring(0, file_path.lastIndexOf(SLASH));
            createDir(dir);
        }
    }

    /**
     * This methods creates folders and unzip the data
     * @param zipDir
     */
    private void Unzip(final String zipDir) {
        try {
            ZipFile zipFile = new ZipFile(zipDir);
            Enumeration<?> entries = zipFile.getEntries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String entryName = entry.getName();
                if (entryName.endsWith(SLASH)) {
                    createFolder(entryName);
                    continue;
                } else
                    checkFolder(entryName);
                InputStream fis = zipFile.getInputStream(entry);

                FileOutputStream fos = new FileOutputStream(entryName);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer, 0, buffer.length);
                fos.write(buffer, 0, buffer.length);
                fis.close();
                fos.close();
            }
            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
