package ie.ait.controllers;

import ie.ait.models.classes.EnteredKey;
import ie.ait.models.classes.KeyStrokeFeature;
import ie.ait.models.classes.KeyStrokeFeatureFile;
import ie.ait.models.enums.AlertType;
import ie.ait.models.enums.SelectedUser;
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
    private Map<String, Double> keyPressedCount;
    private Map<String, Double> keyPressedTotalDwellTime;
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
        fetchTextToType();
        initializeMaps();
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
        initializeMaps();
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
                    if (textArea.getText().toUpperCase().equals(textToType)) {
                        textArea.setEditable(false);
                        isTextCompleted = true;
                        List<EnteredKey> enteredKeys = extractEnteredKeys(pressedKeysList, releasedKeysList);
                        try {
                            extractedFeature = extractFeatureFromKeyEnteredKeys(enteredKeys);
                            if(extractedFeature.isValid()){
                                fileUtils.appendTrainData(extractedFeature);
                                Utils.showAlert("Saved Successfully",
                                        "KeyStroke Feature Saved Successfully",
                                        "Keystroke feature for this user has been successfully saved to the " +
                                                "train data file.",AlertType.INFO);
                            }
                            else{
                                Exception invalidExtractedFeatureException = new Exception("Extracted Feature Is Not Valid");
                                throw invalidExtractedFeatureException;
                            }
                        }
                        catch(Exception e){
                            initializeMaps();
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
                sourceTextArea.setText(textToType);
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
        existingUsersComboBox.setValue("--Existing Users--");
    }

    private void fetchTextToType(){
        this.textToType = "THE SHORT HAIRED QUICK BROWN FOX COMES OUT OF IT'S CAGE AS IT JUMPS OVER THE LAZY DOG WHO LIES IN THE GRASS ASLEEP. I HOPE THIS TEST IS ABLE TO COVER ALL THAT NEEDS TO BE COVERED IN KEYSTROKE TESTING.";
    }

    private List<EnteredKey> extractEnteredKeys(List<String> pressedKeysList, List<String> releasedKeysList){
        List<EnteredKey> enteredKeys = new ArrayList<>();
        for(int i=0; i< pressedKeysList.size(); i++){
            try {
                EnteredKey enteredKey = new EnteredKey(pressedKeysList.get(i).split(",")[0].trim());
                Long timePressed = Long.parseLong(pressedKeysList.get(i).split(",")[1].trim());
                Long timeReleased = Long.parseLong(releasedKeysList.get(i).split(",")[1].trim());
                Long dwellTime = Math.abs(timeReleased - timePressed);
                enteredKey.setTimePressed(timePressed);
                enteredKey.setTimeReleased(timeReleased);
                enteredKey.setDwellTime(dwellTime);
                enteredKeys.add(enteredKey);
            }
            catch(Exception exception){
                initializeMaps();
                Utils.showAlert(exception, AlertType.ERROR);
            }
        }
        return enteredKeys;
    }

    private KeyStrokeFeature extractFeatureFromKeyEnteredKeys(List<EnteredKey> enteredKeys){
        KeyStrokeFeature keyStrokeFeature = mapDwellTimesToKeyStrokeFeature(enteredKeys);
        keyStrokeFeature = computeFlightTimes(enteredKeys, keyStrokeFeature);
        keyStrokeFeature = computeTimedAverageValues(enteredKeys, keyStrokeFeature);
        keyStrokeFeature.setFeatureClass(currentTrainedUser);
        return keyStrokeFeature;
    }

    private KeyStrokeFeature mapDwellTimesToKeyStrokeFeature(List<EnteredKey> enteredKeys){
        List<String> alphabetList = Arrays.asList(alphabets);
        for(EnteredKey enteredKey : enteredKeys){
            String currentKey = enteredKey.getKey().trim().toUpperCase();
            if(alphabetList.contains(currentKey) && enteredKey.getDwellTime() > 0){
                keyPressedTotalDwellTime.put(currentKey, keyPressedTotalDwellTime.get(currentKey)+enteredKey.getDwellTime());
                keyPressedCount.put(currentKey, keyPressedCount.get(currentKey)+1);
            }
        }
        KeyStrokeFeature keyStrokeFeature = new KeyStrokeFeature();
        for(String alphabet : alphabetList){
            if(keyPressedTotalDwellTime.get(alphabet) > 0 && keyPressedCount.get(alphabet) > 0){
                double averageDwellTime = (keyPressedTotalDwellTime.get(alphabet)/keyPressedCount.get(alphabet))/1000;
                averageDwellTime = Double.parseDouble(String.format("%.3f",averageDwellTime));
                Statement statement = new Statement(keyStrokeFeature, "set"+alphabet, new Object[] { averageDwellTime });
                try {
                    statement.execute();
                }
                catch(Exception e){

                }
            }
        }
        return keyStrokeFeature;
    }

    private KeyStrokeFeature computeFlightTimes(List<EnteredKey> enteredKeys, KeyStrokeFeature keyStrokeFeature){
        String [] digraphs = {"TH","HE","IN","ER","AN","RE","ND","AT","ON","NT"};
        List<String> digraphList = Arrays.asList(digraphs);
        for(String digraph : digraphList){
            String firstLetter = digraph.substring(0,1);
            String secondLetter = digraph.substring(1);
            double digraphCounter = 0;
            double totalDigraphFlightTimes = 0;
            double averageDigraphFlightTime = 0;
            for(int i = 0; i < enteredKeys.size()-1; i++){
                if(enteredKeys.get(i).getKey().equals(firstLetter) && enteredKeys.get(i+1).getKey().equals(secondLetter)){
                    digraphCounter = digraphCounter+1;
                    totalDigraphFlightTimes = totalDigraphFlightTimes +
                            Math.abs(enteredKeys.get(i+1).getTimePressed() - enteredKeys.get(i).getTimeReleased());
                }
            }
            if(digraphCounter >0 && totalDigraphFlightTimes > 0) {
                averageDigraphFlightTime = (totalDigraphFlightTimes / digraphCounter)/1000;
                averageDigraphFlightTime = Double.parseDouble(String.format("%.3f",averageDigraphFlightTime));
            }
            Statement statement = new Statement(keyStrokeFeature, "set"+digraph, new Object[] { averageDigraphFlightTime });
            try {
                statement.execute();
            }
            catch(Exception e){

            }
        }
        return keyStrokeFeature;
    }

    private KeyStrokeFeature computeTimedAverageValues(List<EnteredKey> enteredKeys, KeyStrokeFeature keyStrokeFeature){
        double averageLettersTypedPerSecond = averageNumberOfLetterInSpecifiedTimeRange(enteredKeys,1);
        double averageLettersTypedPerTwoSeconds = averageNumberOfLetterInSpecifiedTimeRange(enteredKeys,2);
        averageLettersTypedPerSecond = Double.parseDouble(String.format("%.3f",averageLettersTypedPerSecond));
        averageLettersTypedPerTwoSeconds = Double.parseDouble(String.format("%.3f",averageLettersTypedPerTwoSeconds));
        keyStrokeFeature.setAverage1(averageLettersTypedPerSecond);
        keyStrokeFeature.setAverage2(averageLettersTypedPerTwoSeconds);
        return keyStrokeFeature;
    }

    private double averageNumberOfLetterInSpecifiedTimeRange(List<EnteredKey> enteredKeys, int timeRange){
        int timeRangeInMilliSeconds = timeRange * 1000;
        long totalDurationInMilliSeconds = enteredKeys.get(enteredKeys.size()-1).getTimeReleased() - enteredKeys.get(0).getTimePressed();
        int totalDurationInSeconds = (int)Math.ceil((double)(totalDurationInMilliSeconds)/1000);
        int numberOfLettersTyped = 0;
        int totalTimeRange = 0;
        List<String> currentLettersTyped = new ArrayList<>();
        List<Integer> numberOfLettersTypedPerRange = new ArrayList<>();
        for(int i=0; i < enteredKeys.size()-1; i++){
            int tempTotalTimeRange = totalTimeRange + (int)(enteredKeys.get(i+1).getTimePressed()-enteredKeys.get(i).getTimePressed());
            if(tempTotalTimeRange < timeRangeInMilliSeconds){
                if(enteredKeys.get(i).isValidAlphabet()){
                    numberOfLettersTyped = numberOfLettersTyped + 1;
                    currentLettersTyped.add(enteredKeys.get(i).getKey());
                }
                totalTimeRange = tempTotalTimeRange;
            }
            else{
                if(numberOfLettersTyped > 0) {
                    numberOfLettersTypedPerRange.add(numberOfLettersTyped);
                }
                if(enteredKeys.get(i).isValidAlphabet()) {
                    numberOfLettersTyped = 1;
                    currentLettersTyped.add(enteredKeys.get(i).getKey());
                }
                else{
                    numberOfLettersTyped = 0;
                }
                totalTimeRange = tempTotalTimeRange - timeRangeInMilliSeconds;
            }
        }

        double sum = 0;
        for(int value : numberOfLettersTypedPerRange){
            sum = sum + value;
        }
        return sum/numberOfLettersTypedPerRange.size();
    }

    private void initializeMaps(){
        keyPressedCount = new HashMap<>();
        keyPressedTotalDwellTime = new HashMap<>();
        for(String alphabet : alphabets){
            keyPressedCount.put(alphabet,(double)0);
            keyPressedTotalDwellTime.put(alphabet, (double)0);
        }
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
            /*
            trainedUsers.add("John");
            trainedUsers.add("Derrick");
            trainedUsers.add("James");
            trainedUsers.add("Paul");
            trainedUsers.add("Peterside");
            */
        }
        this.trainedUsers = trainedUsers;
        existingUsersComboBox.getItems().clear();
        for (String user : this.trainedUsers) {
            existingUsersComboBox.getItems().add(user);
        }
    }
}
