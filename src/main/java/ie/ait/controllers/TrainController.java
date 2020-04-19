package ie.ait.controllers;

import ie.ait.models.classes.EnteredKey;
import ie.ait.models.classes.KeyStrokeFeature;
import ie.ait.models.classes.KeyStrokeFeatureFile;
import ie.ait.models.enums.AlertType;
import ie.ait.models.enums.SelectedUser;
import ie.ait.utils.FeatureExtractionUtils;
import ie.ait.utils.FileUtils;
import ie.ait.utils.Utils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import jdk.nashorn.internal.runtime.ECMAException;

import java.beans.Statement;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static ie.ait.utils.Utils.toSentenceCase;

/**
 * Created by Pelumi.Oyefeso on 12-Mar-2020
 */
public class TrainController {
    @FXML
    private TextArea textArea;
    @FXML
    private TextArea sourceTextArea;
    @FXML
    private RadioButton newUserRadioButton;
    @FXML
    private RadioButton existingUserRadioButton;
    @FXML
    private Label existingUserLabel;
    @FXML
    private TextField newUserTextField;
    @FXML
    private ComboBox<String> existingUsersComboBox;
    @FXML
    private Button continueButton;
    @FXML
    private Label trainedUsernameLabel;
    @FXML
    private HBox newUserHBox;
    @FXML
    private HBox existingUserHBox;
    @FXML
    private Button resetButton;
    private boolean isTextCompleted;
    private String textToType = "";
    private List<String> pressedKeysList;
    private List<String> releasedKeysList;
    private KeyStrokeFeature extractedFeature;
    private String [] alphabets = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S",
            "T","U","V","W","X","Y","Z"};
    private FileUtils fileUtils;
    private boolean newUserRadioButtonClicked = true;
    private boolean existingUserRadioButtonClicked = false;
    private SelectedUser selectedUser = SelectedUser.NEW_USER;
    private Set<String> trainedUsers;
    private KeyStrokeFeatureFile keyStrokeFeatureFile;
    private String currentTrainedUser;

    /**
     The "initialize" method is automatically called because this class is annotated with the @FXML.
     */
    public void initialize(){
        this.fileUtils = new FileUtils();
        this.keyStrokeFeatureFile = fileUtils.readTrainFile();
        initializeValues();
        this.textToType = Utils.getRandomTextToTypeString();
        handleEvents();
    }

    private void initializeValues(){
        isTextCompleted = false;
        newUserRadioButton.setSelected(true);
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
    }

    private void resetComponentValues(){
        //initializeValues();
        isTextCompleted = false;
        textArea.setText("");
        textArea.setEditable(true);
        fetchTextToType();
        pressedKeysList = new ArrayList<>();
        releasedKeysList = new ArrayList<>();
    }

    private void handleEvents(){
        textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(!isTextCompleted){
                    String keyPressed = keyEvent.getText().trim().toUpperCase();
                    pressedKeysList.add(keyPressed+","+ System.currentTimeMillis());
                }
            }
        });
        textArea.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(!isTextCompleted){
                    String keyReleased = keyEvent.getText().trim().toUpperCase();
                    releasedKeysList.add(keyReleased+","+ System.currentTimeMillis());
                    String formattedInputText = textArea.getText().replaceAll("\\s+"," ");
                    formattedInputText = formattedInputText.toUpperCase().trim();
                    if (formattedInputText.equals(textToType.trim())) {
                        textArea.setEditable(false);
                        isTextCompleted = true;
                        List<EnteredKey> enteredKeys = new ArrayList<>();
                        try {
                            enteredKeys = FeatureExtractionUtils.extractEnteredKeys(pressedKeysList, releasedKeysList);
                        }
                        catch(Exception exception){
                            Utils.showAlert(exception, AlertType.ERROR);
                        }
                        try {
                            extractedFeature = new FeatureExtractionUtils().extractFeatureFromKeyEnteredKeys(enteredKeys, currentTrainedUser);
                            extractedFeature.setLeftAndRightKeysSum();
                            //extractedFeature = extractFeatureFromKeyEnteredKeys(enteredKeys);
                            if(extractedFeature.isValid()){
                                fileUtils.appendTrainData(extractedFeature);
                                Utils.showAlert("Saved Successfully",
                                        "KeyStroke Feature Saved Successfully",
                                        "Keystroke feature for this user has been successfully saved to the " +
                                                "train data file.",AlertType.INFO);
                                fetchTextToType();
                            }
                            else{
                                Exception invalidExtractedFeatureException = new Exception("Extracted Feature Is Not Valid");
                                throw invalidExtractedFeatureException;
                            }
                        }
                        catch(Exception e){
                            Utils.showAlert(e, AlertType.ERROR);
                        }
                        resetComponentValues();
                    }
                }
            }
        });

        newUserRadioButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                newUserRadioButtonClicked = true;
                existingUserRadioButtonClicked = false;
                selectedUser = SelectedUser.NEW_USER;
                toggleRadioButtons();
                resetButtonActions();
            }
        });
        existingUserRadioButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                existingUserRadioButtonClicked = true;
                newUserRadioButtonClicked = false;
                existingUserLabel.setVisible(false);
                selectedUser = SelectedUser.EXISTING_USER;
                toggleRadioButtons();
                resetButtonActions();
                existingUsersComboBox.setValue("--Existing Users--");
                getTrainedUsers();
            }
        });

        existingUsersComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String currentSelectedUser = existingUsersComboBox.getValue();
                if(trainedUsers.contains(currentSelectedUser)){
                    currentTrainedUser = currentSelectedUser;
                    continueButton.setDisable(false);
                }
                else{
                    continueButton.setDisable(true);
                }
            }
        });
        newUserTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String currentTextFieldString = newUserTextField.getText().toUpperCase();
                StringBuilder stringBuilder = new StringBuilder();
                for(String stringCharacter : currentTextFieldString.split("")){
                    if(Arrays.asList(alphabets).contains(stringCharacter)){
                        stringBuilder.append(stringCharacter);
                    }
                }
                newUserTextField.setText(stringBuilder.toString());
                newUserTextField.positionCaret(newUserTextField.getText().length());
                List<String> trainedUsersInUpperCase = trainedUsers.stream()
                        .map((content) -> content.toUpperCase()).collect(Collectors.toList());
                if(trainedUsersInUpperCase.contains(newUserTextField.getText())){
                    existingUserLabel.setVisible(true);
                    continueButton.setDisable(true);
                }
                else{
                    existingUserLabel.setVisible(false);
                    continueButton.setDisable(false);
                    currentTrainedUser = toSentenceCase(newUserTextField.getText());
                }
            }
        });

        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textArea.setDisable(false);
                textArea.setEditable(true);
                newUserTextField.setDisable(true);
                existingUsersComboBox.setDisable(true);
                resetButton.setDisable(false);
                continueButton.setDisable(true);
                fetchTextToType();
            }
        });

        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetButtonActions();
            }
        });
    }

    private void resetButtonActions(){
        textArea.setDisable(true);
        textArea.setEditable(false);
        newUserTextField.setDisable(false);
        newUserTextField.setText("");
        existingUsersComboBox.setDisable(false);
        resetButton.setDisable(true);
        continueButton.setDisable(true);
        sourceTextArea.setText("");
        textArea.setText("");
        existingUsersComboBox.setValue("--Existing Users--");
    }

    private void fetchTextToType(){
        //this.textToType = "THE SHORT HAIRED QUICK BROWN FOX COMES OUT OF IT'S CAGE AS IT JUMPS OVER THE LAZY DOG WHO LIES IN THE GRASS ASLEEP. I HOPE THIS TEST IS ABLE TO COVER ALL THAT NEEDS TO BE COVERED IN KEYSTROKE TESTING.";
        this.textToType = Utils.getRandomTextToTypeString();
        sourceTextArea.setText(textToType);
    }

    private void toggleRadioButtons(){
        if(newUserRadioButtonClicked) {
            newUserRadioButton.setDisable(true);
            newUserRadioButton.setSelected(true);
            existingUserRadioButton.setDisable(false);
            existingUserRadioButton.setSelected(false);
            existingUserHBox.setVisible(false);
            newUserHBox.setVisible(true);
            newUserTextField.setPromptText("Enter Username (Letters Only)");
            existingUserLabel.setVisible(false);
            continueButton.setDisable(true);
        }
        else{
            newUserRadioButton.setDisable(false);
            newUserRadioButton.setSelected(false);
            existingUserRadioButton.setDisable(true);
            existingUserRadioButton.setSelected(true);
            existingUserHBox.setVisible(true);
            newUserHBox.setVisible(false);
            continueButton.setDisable(true);
        }
    }

    private void getTrainedUsers(){
        this.keyStrokeFeatureFile = fileUtils.readTrainFile();
        List<KeyStrokeFeature> keyStrokeFeatures = keyStrokeFeatureFile.getKeyStrokeFeatures();
        Set<String> trainedUsers = new HashSet<>();
        for(KeyStrokeFeature keyStrokeFeature : keyStrokeFeatures) {
            trainedUsers.add(keyStrokeFeature.getFeatureClass());
        }
        this.trainedUsers = trainedUsers;
        existingUsersComboBox.getItems().clear();
        for (String user : this.trainedUsers) {
            existingUsersComboBox.getItems().add(user);
        }
    }
}
