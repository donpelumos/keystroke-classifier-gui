package ie.ait.bootstrap;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * Created by Pelumi.Oyefeso on 02-Mar-2020
 */
public class FileUtils {
    public void createSharedDirectory(){
        Path path = Paths.get(System.getProperty("user.dir")+ File.separator+"keystroke-classifier-shared-folder");
        boolean pathExists = Files.exists(path, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
        if(pathExists){
            //dd
            System.out.println("");
        }
        else{
            try {
                path = Files.createDirectory(path);
            } catch(FileAlreadyExistsException e){
                // the directory already exists.
            } catch (IOException e) {
                //something else went wrong
                e.printStackTrace();
            }
        }
    }

}
