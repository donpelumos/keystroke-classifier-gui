package ie.ait.bootstrap;

import com.opencsv.CSVWriter;
import ie.ait.KeyStrokeFileType;
import ie.ait.models.KeyStrokeFeature;

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
    private Path rootPath;
    private String sharedFolderName = "keystroke-classifier-shared-folder";
    private String trainFileName = "keystroke-classifier-train.csv";
    private String sampleFileName = "sample.csv";
    public FileUtils(){
        this.rootPath = Paths.get(System.getProperty("user.dir"));
    }
    public void createSharedDirectory(){
        Path path = Paths.get(rootPath.toString() + File.separator + sharedFolderName);
        boolean pathExists = Files.exists(path, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
        if(pathExists){
            try{
                writeMock(path);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            try {
                path = Files.createDirectory(path);
                writeMock(path);
            }
            catch(FileAlreadyExistsException e){
                // the directory already exists.
                e.printStackTrace();
            }
            catch (IOException e) {
                //something else went wrong
                e.printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean isTrainFileExists(){
        Path trainingFilePath = Paths.get(rootPath.toString() + File.separator + sharedFolderName + File.separator +
                trainFileName);
        boolean trainingFileExists = Files.exists(trainingFilePath, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
        return trainingFileExists;
    }

    public void createTrainFile(){
        Path trainingFilePath = Paths.get(rootPath.toString() + File.separator + sharedFolderName);
        try{
            writeHeader(trainingFilePath);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void writeMock(Path path) throws Exception {
        KeyStrokeFeature keyStrokeFeature = new KeyStrokeFeature();
        keyStrokeFeature.mockInitialization();
        List<String[]> sample = Utils.keyStrokeFeatureToList(keyStrokeFeature, KeyStrokeFileType.MOCK);
        csvWriter(sample, Paths.get(path.toString()+File.separator + sampleFileName));
    }

    private void writeHeader(Path path) throws Exception {
        KeyStrokeFeature keyStrokeFeature = new KeyStrokeFeature();
        keyStrokeFeature.mockInitialization();
        List<String[]> sample = Utils.keyStrokeFeatureToList(keyStrokeFeature, KeyStrokeFileType.HEADER);
        csvWriter(sample, Paths.get(path.toString()+File.separator + trainFileName));
    }

    private void csvWriter(List<String[]> fileArray, Path path) throws Exception {
        System.out.println("Writing to path : "+path.toString());
        CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
        for (String[] array : fileArray) {
            writer.writeNext(array,false);
        }
        writer.close();
    }

}
