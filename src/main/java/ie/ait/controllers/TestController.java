package ie.ait.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

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
    /**
     The "initialize" method is automatically called because this class is annotated with the @FXML.
     */
    public void initialize(){
        classificationTechniqueComboBox.getItems().addAll("KNN","SVM","DECISION TREES");
        sourceTextArea.setEditable(false);
        classificationTechniqueComboBox.setVisible(false);
        classificationTechniqueLabel.setVisible(false);
        initializeValues();
        /*
        fetchTextToType();
        initializeMaps();
        handleEvents();
        */
    }

    private void initializeValues(){
        /*
        isTextCompleted = false;
        resetButton.setDisable(true);
        newUserRadioButton.setDisable(true);
        existingUserRadioButton.setSelected(false);
        existingUserRadioButton.setDisable(false);
        textArea.setWrapText(true);
        pressedKeysList = new ArrayList<>();
        releasedKeysList = new ArrayList<>();
        existingUserHBox.setVisible(false);
        newUserHBox.setVisible(true);
        continueButton.setDisable(true);
        existingUserLabel.setVisible(false);
        textArea.setEditable(false);
        textArea.setDisable(true);
        sourceTextArea.setDisable(false);
        sourceTextArea.setEditable(false);
        getTrainedUsers();
        if(existingUsersComboBox.getItems().size() == 0) {
            for (String user : trainedUsers) {
                existingUsersComboBox.getItems().add(user);
            }
        }
        */
    }
}
