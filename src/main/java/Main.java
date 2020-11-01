
import util.Archiver;
import util.Unzip;

/**
 * This is Main class. Depending on the argument passed, it starts zipping or unzipping
 */
public class Main {
    public static void main(String[] args)  {
        boolean isZip = args[0].endsWith(".zip") ? new Unzip().start(args[0]) : new Archiver().start(args);
    }
}