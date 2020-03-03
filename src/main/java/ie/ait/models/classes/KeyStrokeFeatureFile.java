package ie.ait.models.classes;

import java.util.List;

/**
 * Created by Pelumi.Oyefeso on 03-Mar-2020
 */
public class KeyStrokeFeatureFile {
    private List<KeyStrokeFeature> keyStrokeFeatures;
    private String keyStrokeFeatureFileName;

    public List<KeyStrokeFeature> getKeyStrokeFeatures() {
        return keyStrokeFeatures;
    }

    public void setKeyStrokeFeatures(List<KeyStrokeFeature> keyStrokeFeatures) {
        this.keyStrokeFeatures = keyStrokeFeatures;
    }

    public String getKeyStrokeFeatureFileName() {
        return keyStrokeFeatureFileName;
    }

    public void setKeyStrokeFeatureFileName(String keyStrokeFeatureFileName) {
        this.keyStrokeFeatureFileName = keyStrokeFeatureFileName;
    }
}
