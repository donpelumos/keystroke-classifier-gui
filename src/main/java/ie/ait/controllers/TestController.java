package ie.ait.controllers;

import ie.ait.models.classes.EnteredKey;
import ie.ait.models.classes.KeyStrokeFeature;
import ie.ait.models.enums.AlertType;
import ie.ait.utils.FeatureClassificationUtils;
import ie.ait.utils.FeatureExtractionUtils;
import ie.ait.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Pelumi.Oyefeso on 24-Mar-2020
 */
public class TestController {
    @FXML
    private TextArea sourceTextArea;
    @FXML
    private TextArea enteredTextArea;
    @FXML
    private ComboBox<String> classificationTechniqueComboBox;
    @FXML
    private HBox classificationTechniqueHBox;
    @FXML
    private Button doneButton;
    @FXML
    private Button resetButton;
    @FXML
    private Label resultLabel;
    @FXML
    private Label classificationTechniqueLabel;
    private boolean isTextCompleted;
    private String textToType = "";
    private List<String> pressedKeysList;
    private List<String> releasedKeysList;
    private KeyStrokeFeature extractedFeature;
    private Map<String, Double> keyPressedCount;
    private Map<String, Double> keyPressedTotalDwellTime;
    private String [] alphabets = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S",
            "T","U","V","W","X","Y","Z"};

    /**
     * The "initialize" method is automatically called because this class is annotated with the @FXML.
     */
    public void initialize(){
        classificationTechniqueComboBox.getItems().addAll("KNN","SVM","DECISION TREES");

        initializeValues();
        fetchTextToType();
        initializeMaps();
        handleEvents();

    }

    private void handleEvents(){
        enteredTextArea.setOnKeyPressed(keyEvent -> {
            if(!isTextCompleted){
                String keyPressed = keyEvent.getText().trim().toUpperCase();
                pressedKeysList.add(keyPressed+","+ System.currentTimeMillis());
            }
        });

        enteredTextArea.setOnKeyReleased(keyEvent -> {
            if(!isTextCompleted){
                String keyReleased = keyEvent.getText().trim().toUpperCase();
                releasedKeysList.add(keyReleased+","+ System.currentTimeMillis());
                String formattedInputText = enteredTextArea.getText().replaceAll("\\s+"," ");
                formattedInputText = formattedInputText.toUpperCase().trim();
                doneButton.setDisable(true);
                if (formattedInputText.equals(textToType)) {
                    isTextCompleted = true;
                    doneButton.setDisable(false);
                }
            }
            else{
                String formattedInputText = enteredTextArea.getText().replaceAll("\\s+"," ");
                formattedInputText = formattedInputText.toUpperCase().trim();
                if(!formattedInputText.equals(textToType)){
                    isTextCompleted = false;
                    doneButton.setDisable(true);
                }
            }
            resultLabel.setText("RESULT : ");
        });

        doneButton.setOnAction(event -> {
            List<EnteredKey> enteredKeys = new ArrayList<>();
            try {
                enteredKeys = FeatureExtractionUtils.extractEnteredKeys(pressedKeysList, releasedKeysList);
            }
            catch(Exception exception){
                Utils.showAlert(exception, AlertType.ERROR);
            }
            try {
                extractedFeature = new FeatureExtractionUtils().extractFeatureFromKeyEnteredKeys(enteredKeys);
                resultLabel.setText("RESULT : ");
                if(extractedFeature.isValid()){
                    String keyStrokeStringForTest = extractedFeature.geKeyStrokeFeatureAsTestString();
                    String trainingCSVFilePathString = getTrainCSVFilePath();
                    String pythonScriptPath = getPythonScriptPath();
                    String predictedUser = "";
                    try{
                        predictedUser = FeatureClassificationUtils.classifyTestData(pythonScriptPath,trainingCSVFilePathString,
                                keyStrokeStringForTest,"0.3","3");
                        resultLabel.setText("RESULT : "+predictedUser);
                    }
                    catch(Exception e){
                        String errorDescription = "Classification Error";
                        String errorBody = "There was an error in classification. Kindly check to ensure that the training dataset " +
                                "file is properly named as well as its parent folder. The naming format is as follows : " +
                                "'\\keystroke-classifier-shared-folder\\keystroke-classifier-train.csv'";
                        Utils.showAlert("Error",errorDescription, errorBody, AlertType.ERROR);
                        Utils.logError(getClass(), errorDescription+ " => "+errorBody);
                        resultLabel.setText("RESULT : ");
                    }
                }
                else{
                    Exception invalidExtractedFeatureException = new Exception("Extracted Feature Is Not Valid");
                    throw invalidExtractedFeatureException;
                }
            }
            catch(Exception e){
                Utils.showAlert(e, AlertType.ERROR);
            }
            resetTextValues();
        });

        resetButton.setOnAction(actionEvent -> {
            resetTextValues();
            resultLabel.setText("RESULT : ");
            fetchTextToType();
        });
    }

    public void resetTextValues(){
        enteredTextArea.setText("");
        pressedKeysList = new ArrayList<>();
        releasedKeysList = new ArrayList<>();
    }


    private void initializeValues(){
        isTextCompleted = false;
        enteredTextArea.setWrapText(true);
        sourceTextArea.setWrapText(true);
        pressedKeysList = new ArrayList<>();
        releasedKeysList = new ArrayList<>();
        sourceTextArea.setDisable(false);
        sourceTextArea.setEditable(false);
        classificationTechniqueComboBox.setVisible(false);
        classificationTechniqueLabel.setVisible(false);
        resetButton.setDisable(false);
        doneButton.setDisable(true);
        enteredTextArea.setEditable(true);
        enteredTextArea.setDisable(false);
        resultLabel.setText("RESULT : ");
    }

    private void fetchTextToType(){
        //this.textToType = "THE SHORT HAIRED QUICK BROWN FOX COMES OUT OF IT'S CAGE AS IT JUMPS OVER THE LAZY DOG WHO LIES IN THE GRASS ASLEEP. I HOPE THIS TEST IS ABLE TO COVER ALL THAT NEEDS TO BE COVERED IN KEYSTROKE TESTING.";
        this.textToType = Utils.getRandomTextToTypeString();
        this.sourceTextArea.setText(this.textToType);
    }

    private void initializeMaps(){
        keyPressedCount = new HashMap<>();
        keyPressedTotalDwellTime = new HashMap<>();
        for(String alphabet : alphabets){
            keyPressedCount.put(alphabet,(double)0);
            keyPressedTotalDwellTime.put(alphabet, (double)0);
        }
    }

    private String getPythonScriptPath(){
        String scriptPath = Utils.getKNNPythonScriptPath();
        if(!Files.exists(Paths.get(scriptPath))){
            String errorDescription = "Application Dependency Error";
            String errorBody = "The python script required to work with this application is not found. If the jar is being run, " +
                    "ensure that the file path 'python_files\\KeyStrokeKNNClassifier.py' exists in the same directory of the jar.\\n" +
                    "If the application is being run from an IDE, ensure that the file path 'python_files\\KeyStrokeKNNClassifier.py' " +
                    "exists in the project root directory";
            Utils.showAlert("Error",errorDescription, errorBody, AlertType.ERROR);
            Utils.logError(getClass(), errorDescription+ " => "+errorBody);
            System.exit(0);
        }
        return scriptPath;
    }

    private String getTrainCSVFilePath(){
        String filePath = Utils.getDatasetCSVFilePath();
        if(!Files.exists(Paths.get(filePath))){
            String errorDescription = "Application Dependency Error";
            String errorBody = "The training data CSV file is not found. Start training new users to re-generate the file.";
            Utils.showAlert("Error",errorDescription, errorBody, AlertType.ERROR);
            Utils.logError(getClass(), errorDescription+ " => "+errorBody);
            System.exit(0);
        }
        return filePath;
    }
}
