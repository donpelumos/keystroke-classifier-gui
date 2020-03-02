package ie.ait.bootstrap;

import ie.ait.models.KeyStrokeFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pelumi.Oyefeso on 02-Mar-2020
 */
public class Utils {
    public static List<String[]> keyStrokeFeatureToList(KeyStrokeFeature keyStrokeFeature){
        List<String[]> featureList = new ArrayList<>();
        featureList.add(KeyStrokeFeature.getKeyStrokeFeatureHeader().split(","));
        featureList.add(keyStrokeFeature.toString().split(","));
        return featureList;
    }
}
