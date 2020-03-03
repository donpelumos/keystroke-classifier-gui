package ie.ait.controllers;

import ie.ait.utils.FileUtils;
import ie.ait.models.classes.KeyStrokeFeature;
import ie.ait.models.classes.KeyStrokeFeatureFile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;

/**
 * Created by Pelumi.Oyefeso on 02-Mar-2020
 */
public class HomeController {
    @FXML
    private Button dataCategoriesButton;
    private FileUtils fileUtils;
    private KeyStrokeFeatureFile keyStrokeFeatureFile;
    private List<KeyStrokeFeature> keyStrokeFeatures;

    public void initialize(){
        this.fileUtils = new FileUtils();
        if(!isTrainDataFileExists()){
            fileUtils.createTrainFile();
        }
        else{
            this.keyStrokeFeatureFile = fileUtils.readTrainFile();
        }
        //TODO: CREATE EXCEPTION CLASS FOR HANDLING ERRORS.
    }

    private boolean isTrainDataFileExists(){
        return fileUtils.isTrainFileExists();
    }
}
