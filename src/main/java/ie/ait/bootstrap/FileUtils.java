package ie.ait.bootstrap;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pelumi.Oyefeso on 02-Mar-2020
 */
public class FileUtils {
    public void createSharedDirectory(){
        Path path = Paths.get(System.getProperty("user.dir")+ File.separator+"keystroke-classifier-shared-folder");
        boolean pathExists = Files.exists(path, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
        if(pathExists){
            try{
                writeEmpty(path);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            try {
                path = Files.createDirectory(path);
            } catch(FileAlreadyExistsException e){
                // the directory already exists.
                e.printStackTrace();
            } catch (IOException e) {
                //something else went wrong
                e.printStackTrace();
            }
        }
    }

    public void writeEmpty(Path path) throws Exception {
        List<String[]> sample = new ArrayList<>();
        sample.add(new String[]{"Name","Country"});
        sample.add(new String[]{"John","Canadian"});
        csvWriter(sample, Paths.get(path.toString()+File.separator+"sample.csv"));
    }

    private void csvWriter(List<String[]> stringArray, Path path) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
        for (String[] array : stringArray) {
            writer.writeNext(array);
        }
        writer.close();
    }

}
