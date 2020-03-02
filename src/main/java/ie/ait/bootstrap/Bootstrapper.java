package ie.ait.bootstrap;

/**
 * Created by Pelumi.Oyefeso on 02-Mar-2020
 */
public class Bootstrapper {
    public static void init(){
        new FileUtils().createSharedDirectory();
    }
    private String getPath(){
        //getClass().getClassLoader();
        return "";
    }
}
