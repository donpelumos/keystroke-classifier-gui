package ie.ait.utils;

import ie.ait.models.classes.EnteredKey;
import ie.ait.models.classes.KeyStrokeFeature;
import ie.ait.models.enums.AlertType;

import java.beans.Statement;
import java.util.*;

/**
 * Created by Pelumi.Oyefeso on 13-04-2020
 */
public class FeatureExtractionUtils {

    private  String [] alphabets = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S",
            "T","U","V","W","X","Y","Z"};
    private  Map<String, Double> keyPressedCount;
    private  Map<String, Double> keyPressedTotalDwellTime;

    public FeatureExtractionUtils(){
        initializeMaps();
    }

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

    public  KeyStrokeFeature extractFeatureFromKeyEnteredKeys(List<EnteredKey> enteredKeys, String currentTrainedUser){
        KeyStrokeFeature keyStrokeFeature = mapDwellTimesToKeyStrokeFeature(enteredKeys);
        keyStrokeFeature = computeFlightTimes(enteredKeys, keyStrokeFeature);
        keyStrokeFeature = computeTimedAverageValues(enteredKeys, keyStrokeFeature);
        keyStrokeFeature.setFeatureClass(currentTrainedUser);
        return keyStrokeFeature;
    }

    public  KeyStrokeFeature extractFeatureFromKeyEnteredKeys(List<EnteredKey> enteredKeys){
        KeyStrokeFeature keyStrokeFeature = mapDwellTimesToKeyStrokeFeature(enteredKeys);
        keyStrokeFeature = computeFlightTimes(enteredKeys, keyStrokeFeature);
        keyStrokeFeature = computeTimedAverageValues(enteredKeys, keyStrokeFeature);
        keyStrokeFeature.setFeatureClass(null);
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
}
