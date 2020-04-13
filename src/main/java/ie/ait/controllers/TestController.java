package ie.ait.controllers;

import ie.ait.models.classes.KeyStrokeFeature;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        sourceTextArea.setEditable(false);
        classificationTechniqueComboBox.setVisible(false);
        classificationTechniqueLabel.setVisible(false);
        initializeValues();
        fetchTextToType();
        initializeMaps();
        //handleEvents();

    }



    private void initializeValues(){
        isTextCompleted = false;
        resetButton.setDisable(true);
        enteredTextArea.setWrapText(true);
        pressedKeysList = new ArrayList<>();
        releasedKeysList = new ArrayList<>();
        enteredTextArea.setEditable(false);
        enteredTextArea.setDisable(true);
        sourceTextArea.setDisable(false);
        sourceTextArea.setEditable(false);
    }

    private void fetchTextToType(){
        this.textToType = "THE SHORT HAIRED QUICK BROWN FOX COMES OUT OF IT'S CAGE AS IT JUMPS OVER THE LAZY DOG WHO LIES IN THE GRASS ASLEEP. I HOPE THIS TEST IS ABLE TO COVER ALL THAT NEEDS TO BE COVERED IN KEYSTROKE TESTING.";
    }

    private void initializeMaps(){
        keyPressedCount = new HashMap<>();
        keyPressedTotalDwellTime = new HashMap<>();
        for(String alphabet : alphabets){
            keyPressedCount.put(alphabet,(double)0);
            keyPressedTotalDwellTime.put(alphabet, (double)0);
        }
    }
}
