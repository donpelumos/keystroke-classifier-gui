package ie.ait.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

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
    }
}
