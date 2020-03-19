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
    @FXML
    private Button dataSizeButton;
    private FileUtils fileUtils;
    private KeyStrokeFeatureFile keyStrokeFeatureFile;
    private List<KeyStrokeFeature> keyStrokeFeatures;

    /**
       The "initialize" method is automatically called because this class is annotated with the @FXML.
     */
    public void initialize(){
        this.fileUtils = new FileUtils();
        if(!isTrainDataFileExists()){
            fileUtils.createTrainFile();
        }
        else{
            this.keyStrokeFeatureFile = fileUtils.readTrainFile();
        }
        setComponentValues();
        //TODO: CREATE EXCEPTION CLASS FOR HANDLING ERRORS.

        /*
        try {
            fileUtils.appendRandomTrainData();
        }
        catch(Exception e){

        }
        */
    }

    private void setComponentValues(){
        if(this.keyStrokeFeatureFile != null) {
            dataSizeButton.setText("DATA SIZE : " + this.keyStrokeFeatureFile.getDataSize());
            dataCategoriesButton.setText("DATA CLASS SIZE : " + this.keyStrokeFeatureFile.getDataCategoriesSize());
        }
    }

    private boolean isTrainDataFileExists(){
        return fileUtils.isTrainFileExists();
    }
}
