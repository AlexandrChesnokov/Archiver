package util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import java.io.*;

/**
 * This is an archiver class
 */
public class Archiver {

    public boolean start(String[] args) {
        zip(args);
        return true;
    }

    /**
     * This method creates ZipOutputStream and passes to it System.out. Then starts archiving
     * @param args
     */
    private void zip(String[] args) {

        org.apache.tools.zip.ZipOutputStream zout = new  org.apache.tools.zip.ZipOutputStream(System.out);

        for (String path : args) {
            File fileSource = new File(path);
            try {
                addDirectory(zout, fileSource);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            zout.finish();
            zout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *This method reads data, and uses recursion to create the directory structure.
     * @param zout
     * @param fileSource
     */
    private void addDirectory(ZipOutputStream zout, File fileSource) {
        File[] files = fileSource.listFiles();

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                addDirectory(zout, files[i]);
                continue;
            }

            try (FileInputStream fis = new FileInputStream(files[i])) {
                zout.putNextEntry(new ZipEntry(files[i].getPath()));

                byte[] buffer = new byte[4096];
                int length = -1;
                while ((length = fis.read(buffer)) != -1)
                    zout.write(buffer, 0, length);
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                try {
                    zout.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
