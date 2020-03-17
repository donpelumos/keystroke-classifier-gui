package ie.ait.controllers;

import ie.ait.models.classes.EnteredKey;
import ie.ait.models.classes.KeyStrokeFeature;
import ie.ait.utils.FileUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.beans.Statement;
import java.util.*;

/**
 * Created by Pelumi.Oyefeso on 12-Mar-2020
 */
public class TrainController {
    @FXML
    private TextArea textArea;
    private boolean isTextCompleted = false;
    private String textToType = "";
    private List<String> pressedKeysList = new ArrayList<>();
    private List<String> releasedKeysList = new ArrayList<>();
    private KeyStrokeFeature extractedFeature;
    private Map<String, Double> keyPressedCount;
    private Map<String, Double> keyPressedTotalDwellTime;
    private String [] alphabets = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S",
            "T","U","V", "W","X","Y","Z"};
    private FileUtils fileUtils;

    /**
     The "initialize" method is automatically called because this class is annotated with the @FXML.
     */
    public void initialize(){
        fetchTextToType();
        initializeMaps();
        handleEvents();
        this.fileUtils = new FileUtils();
    }

    private void setComponentValues(){
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
                        extractedFeature = extractFeatureFromKeyEnteredKeys(enteredKeys);
                    }
                }
            }
        });
    }

    private void fetchTextToType(){
        this.textToType = "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG.";
    }

    private List<EnteredKey> extractEnteredKeys(List<String> pressedKeysList, List<String> releasedKeysList){
        List<EnteredKey> enteredKeys = new ArrayList<>();
        for(int i=0; i< pressedKeysList.size(); i++){
            EnteredKey enteredKey = new EnteredKey(pressedKeysList.get(i).split(",")[0].trim());
            Long timePressed = Long.parseLong(pressedKeysList.get(i).split(",")[1].trim());
            Long timeReleased = Long.parseLong(releasedKeysList.get(i).split(",")[1].trim());
            Long dwellTime = Math.abs(timeReleased - timePressed);
            enteredKey.setTimePressed(timePressed);
            enteredKey.setTimeReleased(timeReleased);
            enteredKey.setDwellTime(dwellTime);
            enteredKeys.add(enteredKey);
        }
        return enteredKeys;
    }

    private KeyStrokeFeature extractFeatureFromKeyEnteredKeys(List<EnteredKey> enteredKeys){
        KeyStrokeFeature keyStrokeFeature = new KeyStrokeFeature();
        keyStrokeFeature = mapDwellTimesToKeyStrokeFeature(enteredKeys);
        keyStrokeFeature = computeFlightTimes(enteredKeys, keyStrokeFeature);
        //TODO: FINISH UP
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

    private void initializeMaps(){
        keyPressedCount = new HashMap<>();
        keyPressedTotalDwellTime = new HashMap<>();
        for(String alphabet : alphabets){
            keyPressedCount.put(alphabet,(double)0);
            keyPressedTotalDwellTime.put(alphabet, (double)0);
        }
    }
}
