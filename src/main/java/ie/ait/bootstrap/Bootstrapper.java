package ie.ait.bootstrap;

import ie.ait.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Created by Pelumi.Oyefeso on 02-Mar-2020
 */
public class Bootstrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static void init(){
        new FileUtils().createSharedDirectory();
        LOGGER.info("Bootstrapping completed");
    }
    private String getPath(){
        //getClass().getClassLoader();
        return "";
    }
}
