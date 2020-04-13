package ie.ait.utils;

import ie.ait.models.classes.EnteredKey;
import ie.ait.models.enums.AlertType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pelumi.Oyefeso on 13-04-2020
 */
public class FeatureExtractionUtils {

    public static List<EnteredKey> extractEnteredKeys(List<String> pressedKeysList, List<String> releasedKeysList) throws Exception{
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
}
