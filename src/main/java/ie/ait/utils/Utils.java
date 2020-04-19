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
        return new String[]{"pandas","numpy","scipy","scikit-learn"};
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

    public static String getRandomTextToTypeString(){
        String text1 = "AIT IS THE LEADER IN THE INSTITUTE OF TECHNOLOGY SECTOR IN APPLIED TEACHING AS WELL AS STUDENT WELFARE AND " +
                "QUEST FOR EXCELLENCE. A CORE STRENGTH OF AIT COMES FROM IDENTIFYING AREAS OF SKILLS SHORTAGE AND WORKING WITH " +
                "BUSINESSES TO IMPROVE LINKS BETWEEN ENTERPRISE AND ACADEMIA. AIT PROVIDES QUALITY EDUCATION TO STUDENTS WHERE THEY " +
                "ARE TAUGHT WITH AN AMAZING SYLLABUS AND EXPOSED TO INDUSTRY EXPERIENCE THROUGH JOINT PARTNERSHIPS WITH EQUIPPED " +
                "INDUSTRY LEADERS. THIS ENSURES THAT AIT PRODUCES EXTREMELY QUALIFIED AND JOB READY GRADUATES.";
        String text2 = "AIT IS HOME TO SIX THOUSAND STUDENTS UNDERTAKING A BROAD RANGE OF SINGLE AND JOINT PROGRAMMES " +
                "IN DOMAINS RANGING FROM BUSINESS HOSPITALITY AND ENGINEERING TO INFORMATICS AND HEALTH. MORE THAN ONE " +
                "TENTH OF THE FULL TIME STUDENT POPULATION COME FROM OVERSEAS WITH SIXTY THREE NATIONALITIES REPRESENTED. " +
                "THIS REFLECTS THE GLOBALIZED NATURE OF THE CAMPUS. AIT INSTILLS THE ZEAL OF KNOWLEDGE IN STUDENTS AND A QUEST FOR " +
                "EXCELLENCE.";
        String text3 = "THE GLOBAL FOCUS OF AIT IS ALSO EVIDENT IN THE JOINT PARTNERSHIPS AND AGREEMENTS IT HAS SIGNED WITH " +
                "UNIVERSITIES AND RESEARCH INSTITUTIONS AROUND THE WORLD AS WELL AS JOINTLY RUN PROGRAMMES WITH COMPANIES " +
                "WITHIN AND OUTSIDE ATHLONE. THIS ENSURES RICH PROGRAMME CONTENT OF THE HIGHEST QUALITY AND HELPS TO EQUALIZE " +
                "THE EXTREME DEMANDS OF THE MODERN JOB MARKET.";
        String text4 = "TALENT AND EXPERIENCE ARE BOTH IMPORTANT QUALITIES OF STRONG LEADERS AS WELL AS THE WILLINGNESS AND ZEAL " +
                "TO LEARN. ONE WAY TO IMPROVE LEADERSHIP SKILLS IS BY LEARNING WHAT TOP BOSSES KNOW. YOU DO NOT NEED AN ADVANCED " +
                "QUALIFICATION TO BE A SUCCESSFUL LEADER. YOU JUST HAVE TO UNDERSTAND A FEW PRINCIPLES FOR MOTIVATING AND " +
                "INSPIRING THE PEOPLE WHO WORK FOR YOU. EXEMPLARY LEADERSHIP MEANS LISTENING MORE THAN SPEAKING AND PAYING " +
                "MORE ATTENTION TO QUESTIONS BEFORE ANSWERING.";
        String text5 = "BOSSES NEED TO SHOW CONCERN FOR STAFF WELFARE WITHIN AND OUTSIDE THE BOUNDS OF WORK IN ORDER TO BUILD " +
                "ORGANIZATIONAL TRUST AND ALSO MAKE EMPLOYEES FEEL HEARD. BOSSES ALSO NEED TO POSSESS A GOOD SENSE OF " +
                "JUDGEMENT ALONGSIDE EXEMPLARY LEADERSHIP. COMPETENCY AS A BOSS IS DISPLAYED BY SHOWING EMPLOYEE CONCERN SUCH AS " +
                "DULY REWARDING THEM WHEN NECESSARY AND ALSO TAKING LEAD IN MAKING IMPORTANT DECISIONS. TREATING EMPLOYEES " +
                "EQUALLY IS ALSO A VERY IMPORTANT ATTRIBUTE A GOOD BOSS NEEDS TO HAVE.";
        String text6 = "WAXING IS THE PROCESS OF HAIR REMOVAL FROM THE ROOT BY USING A COVERING OF A STICKY SUBSTANCE TO ADHERE " +
                "TO BODY HAIR AND THEN REMOVING THIS COVERING AND PULLING OUT THE HAIR FROM THE FOLLICLE. NEW HAIR WILL NOT " +
                "GROW BACK IN THE PREVIOUSLY WAXED AREA FOR FOUR TO SIX WEEKS. ALTHOUGH SOME PEOPLE WILL START TO SEE REGROWTH " +
                "IN JUST A WEEK DUE TO SOME OF THEIR HAIR GROWING AT DIFFERENT RATES. THE QUICKNESS OF HAIR REGROWTH SOMETIMES " +
                "DEPENDS ON THE SIZE OF HAIR THAT GROWS IN THAT REGION.";
        String text7 = "A ZOO IS A FACILITY IN WHICH ANIMALS ARE HOUSED WITHIN ENCLOSURES AND DISPLAYED TO THE PUBLIC. ANIMALS IN " +
                "ZOOS MAY BE ALLOWED TO BREED WHICH IS THE CASE FOR ANIMALS QUITE CLOSE TO EXTINCTION. ZOOLOGY REFERS TO THE STUDY OF " +
                "ANIMALS. THE TERM WAS DERIVED FROM A GREEK WORD. THE WORD ZOO WAS FIRST USED OF THE LONDON ZOOLOGICAL GARDENS " +
                "WHICH WAS OPENED IN THE EIGHTEENTH CENTURY. ZOOS WERE VISITED BY OVER 181 MILLION PEOPLE ANNUALLY IN JUST THE " +
                "UNITED STATES ALONE.";
        String [] values = {text1, text2, text3, text4, text5, text6, text7};
        int randomIndex = (int)(Math.ceil(100*Math.random()) % (values.length));
        return values[randomIndex];
    }

}
