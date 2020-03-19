package ie.ait.utils;

import ie.ait.controllers.AlertController;
import ie.ait.controllers.TrainController;
import ie.ait.models.enums.KeyStrokeFileType;
import ie.ait.models.classes.KeyStrokeFeature;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pelumi.Oyefeso on 02-Mar-2020
 */
public class Utils {
    public static List<String[]> keyStrokeFeatureToList(KeyStrokeFeature keyStrokeFeature, KeyStrokeFileType type){
        List<String[]> featureList = new ArrayList<>();
        if(type == KeyStrokeFileType.MOCK) {
            featureList.add(KeyStrokeFeature.getKeyStrokeFeatureHeader().split(","));
            featureList.add(keyStrokeFeature.toString().split(","));
        }
        else if(type == KeyStrokeFileType.HEADER){
            featureList.add(KeyStrokeFeature.getKeyStrokeFeatureHeader().split(","));
        }
        else{
            featureList.add(keyStrokeFeature.toString().split(","));
        }
        return featureList;
    }

    public static void showAlert(String title, String header, String message){
        AlertController alertController = new AlertController();
        alertController.display(title,header,message);
    }

}
