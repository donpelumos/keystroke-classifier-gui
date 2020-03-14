package ie.ait.controllers;

import ie.ait.models.classes.EnteredKey;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pelumi.Oyefeso on 12-Mar-2020
 */
public class TrainController {
    @FXML
    private TextArea textArea;
    private boolean isTypingStarted = false;
    private long startTime = 0l;
    private List<EnteredKey> enteredKeys = new ArrayList<>();

    /**
     The "initialize" method is automatically called because this class is annotated with the @FXML.
     */
    public void initialize(){
        handleEvents();
    }

    private void setComponentValues(){
    }

    private void handleEvents(){
        textArea.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(!isTypingStarted){
                    isTypingStarted = true;
                    startTime = System.currentTimeMillis();
                    System.out.println("START TIME : " + startTime);
                }
                String key = keyEvent.getCharacter().toUpperCase();
                long timeValue = System.currentTimeMillis();
                EnteredKey enteredKey = new EnteredKey(key, timeValue);
                enteredKeys.add(enteredKey);
                System.out.println("KEY TYPED : " + enteredKey);
            }
        });
    }
}
