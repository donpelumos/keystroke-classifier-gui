package ie.ait.controllers;

import ie.ait.models.classes.EnteredKey;
import ie.ait.models.classes.KeyStrokeFeature;
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
    private boolean isTypingStarted = false;
    private boolean isTextCompleted = false;
    private long startTime = 0l;
    private List<EnteredKey> enteredKeys = new ArrayList<>();
    private String textToType = "";
    private KeyStrokeFeature extractedFeature;
    private Map<String, Double> keyPressedCount;
    private Map<String, Double> keyPressedTotalDwellTime;
    private String [] alphabets = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S",
            "T","U","V", "W","X","Y","Z"};

    /**
     The "initialize" method is automatically called because this class is annotated with the @FXML.
     */
    public void initialize(){
        fetchTextToType();
        initializeMaps();
        handleEvents();
    }

    private void setComponentValues(){
    }

    private void handleEvents(){
        textArea.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(!isTextCompleted) {
                    if (!isTypingStarted) {
                        isTypingStarted = true;
                        startTime = System.currentTimeMillis();
                        System.out.println("START TIME : " + startTime);
                    }
                    String key = keyEvent.getCharacter().toUpperCase();
                    long timeValue = System.currentTimeMillis();
                    EnteredKey enteredKey = new EnteredKey(key, timeValue);
                    enteredKeys.add(enteredKey);
                    System.out.println("KEY TYPED : " + enteredKey);
                    if (textArea.getText().toUpperCase().equals(textToType)) {
                        textArea.setEditable(false);
                        isTextCompleted = true;
                        extractFeatureFromKeyEnteredKeys(enteredKeys);
                    }
                }
            }
        });
    }

    private void fetchTextToType(){
        this.textToType = "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG.";
    }

    private KeyStrokeFeature extractFeatureFromKeyEnteredKeys(List<EnteredKey> enteredKeys){
        KeyStrokeFeature keyStrokeFeature = new KeyStrokeFeature();
        enteredKeys = computeDwellTimes(enteredKeys);
        keyStrokeFeature = mapDwellTimesToKeyStrokeFeature(enteredKeys);
        //TODO: FINISH UP
        return null;
    }

    private List<EnteredKey> computeDwellTimes(List<EnteredKey> enteredKeys){
        for(int i=1; i < enteredKeys.size(); i++){
            enteredKeys.get(i).setDwellTime(enteredKeys.get(i).getTimeValue()-enteredKeys.get(i-1).getTimeValue());
        }
        return enteredKeys;
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
                Statement statement = new Statement(keyStrokeFeature, "set"+alphabet, new Object[] { averageDwellTime });
                try {
                    statement.execute();
                }
                catch(Exception e){

                }
            }
        }
        return null;
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
