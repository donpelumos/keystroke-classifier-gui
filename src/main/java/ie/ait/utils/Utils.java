package ie.ait.utils;

import ie.ait.controllers.AlertController;
import ie.ait.controllers.TrainController;
import ie.ait.models.enums.AlertType;
import ie.ait.models.enums.KeyStrokeFileType;
import ie.ait.models.classes.KeyStrokeFeature;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public static void logException(Class className, Exception exception){
        Logger LOGGER = LoggerFactory.getLogger(className.toString());
        LOGGER.info(className.toString(),exception);
    }

    public static void showAlert(Exception exception, AlertType... alertType){
        StringBuilder sb = new StringBuilder();
        Arrays.stream(exception.getStackTrace()).forEach((s) -> sb.append(s.toString()+"\r\n"));
        Utils.showAlert(exception.getClass().toString().replace("class","").trim(),
                exception.getClass().toString().replace("class","").trim(),
                exception.getMessage(), sb.toString(), alertType);
    }

    public static void showAlert(String title, String headerTitle, String headerDescription, String message, AlertType... alertType){
        AlertController alertController = new AlertController();
        headerTitle += " : ";
        alertController.display(title,headerTitle, headerDescription,message, alertType);
    }

}
