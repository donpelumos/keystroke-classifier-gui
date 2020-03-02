package ie.ait.bootstrap;

import ie.ait.KeyStrokeFileType;
import ie.ait.models.KeyStrokeFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pelumi.Oyefeso on 02-Mar-2020
 */
public class Utils {
    public static List<String[]> keyStrokeFeatureToList(KeyStrokeFeature keyStrokeFeature, KeyStrokeFileType type){
        List<String[]> featureList = new ArrayList<>();
        if(type == KeyStrokeFileType.MOCK) {
            featureList.add(KeyStrokeFeature.getKeyStrokeFeatureHeader().split(","));
            featureList.add(keyStrokeFeature.toString().split(","));
        }
        else if(type == KeyStrokeFileType.HEADER){
            featureList.add(KeyStrokeFeature.getKeyStrokeFeatureHeader().split(","));
        }
        else{
            featureList.add(keyStrokeFeature.toString().split(","));
        }
        return featureList;
    }
}
