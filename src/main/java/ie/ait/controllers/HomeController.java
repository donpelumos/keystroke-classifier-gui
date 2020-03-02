package ie.ait.controllers;

import ie.ait.bootstrap.FileUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by Pelumi.Oyefeso on 02-Mar-2020
 */
public class HomeController {
    @FXML
    private Button dataCategoriesButton;
    private FileUtils fileUtils;

    public void initialize(){
        this.fileUtils = new FileUtils();
        if(!isTrainDataFileExists()){
            fileUtils.createTrainFile();
        }
        else{
            //TODO: READ TRAIN FILE AND GET CURRENT SIZE INFO
        }
    }

    private boolean isTrainDataFileExists(){
        return fileUtils.isTrainFileExists();
    }
}
