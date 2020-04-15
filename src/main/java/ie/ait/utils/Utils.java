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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
        LOGGER.info(className.toString() +" : "+exception);
    }

    public static void logError(Class className, String errorMessage){
        Logger LOGGER = LoggerFactory.getLogger(className.toString());
        LOGGER.info(className.toString() +" : "+errorMessage);
    }

    public static void showAlert(Exception exception, AlertType... alertType){
        StringBuilder sb = new StringBuilder();
        Arrays.stream(exception.getStackTrace()).forEach((s) -> sb.append(s.toString()+"\r\n"));
        Utils.showAlert(exception.getClass().toString().replace("class","").trim(),
                exception.getMessage(), sb.toString(), alertType);
    }

    public static void showAlert(String headerTitle, String headerDescription, String message, AlertType... alertType){
        AlertController alertController = new AlertController();
        String title = "";
        if(alertType[0] == AlertType.INFO){
            title = headerTitle.toUpperCase();
        }
        else{
            title = alertType[0].toString();
        }
        headerTitle += " : ";
        alertController.display(title,headerTitle, headerDescription,message, alertType);
    }


    public static String toSentenceCase(String string){
        if(string.trim().equals("")){
            return "";
        }
        else if(string.trim().length() == 1){
            return string.toUpperCase();
        }
        else {
            return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        }
    }
    public static String getJavaVersion(){
        String javaVersion = "";
        try
        {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("java -version");
            BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                if(line.toLowerCase().contains("java") && line.toLowerCase().contains("version")){
                    javaVersion = line.toLowerCase().trim().split("version")[1];
                    break;
                }
                line = r.readLine();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return javaVersion.replace("\"","").trim();
    }

    public static String getPythonVersion(){
        String pythonVersion = "";
        try
        {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("python --version");
            BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            if(line == null){
                r = new BufferedReader(new InputStreamReader(process.getInputStream()));
                line = r.readLine();
            }
            while (line != null) {
                if(line.toLowerCase().contains("python")){
                    pythonVersion = line.toLowerCase().trim().split("\\s+")[1];
                    break;
                }
                line = r.readLine();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return pythonVersion.trim();
    }

    public static boolean checkPythonDependency(){
        boolean allDependenciesExist = true;
        Map<String, String> installedPythonDependencies = getExistingInstalledPythonDependencies();
        String [] requiredPythonDependencies = definedPythonDependencies();
        for(String dependency : requiredPythonDependencies){
            if(!installedPythonDependencies.containsKey(dependency)){
                allDependenciesExist = false;
                break;
            }
        }
        return allDependenciesExist;
    }

    public static String [] getOutstandingPythonDependencies(){
        List<String> outstandingDependencies = new ArrayList<>();
        Map<String, String> installedPythonDependencies = getExistingInstalledPythonDependencies();
        String [] requiredPythonDependencies = definedPythonDependencies();
        for(String dependency : requiredPythonDependencies){
            if(!installedPythonDependencies.containsKey(dependency)){
                outstandingDependencies.add(dependency);
            }
        }
        String [] outstandingDependenciesArray = new String [outstandingDependencies.size()];
        for(int i=0 ; i<outstandingDependencies.size(); i++){
            outstandingDependenciesArray[i] = outstandingDependencies.get(i);
        }
        return outstandingDependenciesArray;
    }

    private static Map<String, String> getExistingInstalledPythonDependencies(){
        Map<String, String> installedPythonDependencies = new HashMap<>();
        try
        {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("pip list");
            BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            if(line == null){
                r = new BufferedReader(new InputStreamReader(process.getInputStream()));
                line = r.readLine();
            }
            while (line != null) {
                String dependencyName = line.trim().split("\\s+")[0];
                String dependencyVersion = line.trim().split("\\s+")[1];
                installedPythonDependencies.put(dependencyName, dependencyVersion);
                line = r.readLine();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return installedPythonDependencies;
    }

    private static String[] definedPythonDependencies(){
        return new String[]{"pandas","numpy","sklearn"};
    }

    public static String getKNNPythonScriptPath(){
        String scriptPath = Paths.get(System.getProperty("user.dir")).toString()+ "\\python_files\\KeyStrokeKNNClassifier.py";
        return scriptPath;
    }

    public static String getDatasetCSVFilePath(){
        String datasetPath = Paths.get(System.getProperty("user.dir")).toString()+
                "\\keystroke-classifier-shared-folder\\keystroke-classifier-train.csv";
        return datasetPath;
    }

}
