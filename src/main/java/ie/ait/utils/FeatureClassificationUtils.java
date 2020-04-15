package ie.ait.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Pelumi.Oyefeso on 15-04-2020
 */
public class FeatureClassificationUtils {

    public static String classifyTestData(String datasetFilePath, String filePath, String testString, String testSize, String neighbourSize){
        String predictedClass = "";
        try
        {
            Runtime runtime = Runtime.getRuntime();
            String command = datasetFilePath + " " + filePath+" "+testString+" "+ testSize+" "+neighbourSize;
            Process process = runtime.exec("python.exe "+command);
            BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            if(line == null){
                r = new BufferedReader(new InputStreamReader(process.getInputStream()));
                line = r.readLine();
            }
            while (line != null) {
                if(line.toLowerCase().contains("===>>>")){
                    predictedClass = line.toLowerCase().trim().split("===>>>")[1].trim();
                    break;
                }
                line = r.readLine();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        predictedClass = predictedClass.substring(0,1).toUpperCase()+predictedClass.substring(1);
        return predictedClass;
    }
}
