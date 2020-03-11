package ie.ait.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import ie.ait.models.enums.KeyStrokeFileType;
import ie.ait.models.classes.KeyStrokeFeature;
import ie.ait.models.classes.KeyStrokeFeatureFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pelumi.Oyefeso on 02-Mar-2020
 */
public class FileUtils {
    private Path rootPath, trainingFilePath;
    private String sharedFolderName = "keystroke-classifier-shared-folder";
    private String trainFileName = "keystroke-classifier-train.csv";
    private String sampleFileName = "sample.csv";
    private boolean isTrainFileExists =  false;
    public FileUtils(){
        this.rootPath = Paths.get(System.getProperty("user.dir"));
        this.isTrainFileExists = isTrainFileExists();
    }
    public void createSharedDirectory(){
        Path path = Paths.get(rootPath.toString() + File.separator + sharedFolderName);
        boolean pathExists = Files.exists(path, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
        if(pathExists){
            try{
                writeMock(path);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            try {
                path = Files.createDirectory(path);
                writeMock(path);
            }
            catch(FileAlreadyExistsException e){
                // the directory already exists.
                e.printStackTrace();
            }
            catch (IOException e) {
                //something else went wrong
                e.printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean isTrainFileExists(){
        Path trainingFile = Paths.get(rootPath.toString() + File.separator + sharedFolderName + File.separator +
                trainFileName);
        this.trainingFilePath = Paths.get(rootPath.toString() + File.separator + sharedFolderName);
        boolean trainingFileExists = Files.exists(trainingFile, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
        return trainingFileExists;
    }

    public void createTrainFile(){
        Path trainingFilePath = Paths.get(rootPath.toString() + File.separator + sharedFolderName);
        try{
            writeHeader(trainingFilePath);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void writeMock(Path path) throws Exception {
        KeyStrokeFeature keyStrokeFeature = new KeyStrokeFeature();
        keyStrokeFeature.mockInitialization();
        List<String[]> sample = Utils.keyStrokeFeatureToList(keyStrokeFeature, KeyStrokeFileType.MOCK);
        writeContent(sample, Paths.get(path.toString()+File.separator + sampleFileName));
    }

    public KeyStrokeFeatureFile readTrainFile() {
        Path trainingFilePath = Paths.get(rootPath.toString() + File.separator + sharedFolderName);
        KeyStrokeFeatureFile keyStrokeFeatureFile = null;
        try {
            keyStrokeFeatureFile = readFile(trainingFilePath);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return keyStrokeFeatureFile;
    }

    private void writeHeader(Path path) throws Exception {
        KeyStrokeFeature keyStrokeFeature = new KeyStrokeFeature();
        keyStrokeFeature.mockInitialization();
        List<String[]> sample = Utils.keyStrokeFeatureToList(keyStrokeFeature, KeyStrokeFileType.HEADER);
        writeContent(sample, Paths.get(path.toString()+File.separator + trainFileName));
    }

    public void appendRandomTrainData() throws Exception{
        KeyStrokeFeature keyStrokeFeature = new KeyStrokeFeature();
        keyStrokeFeature.randomInitialization();
        List<String[]> random = Utils.keyStrokeFeatureToList(keyStrokeFeature, KeyStrokeFileType.BODY);
        List<KeyStrokeFeature> existingTrainingDataSet = readFileAsList(this.trainingFilePath);
        List<String[]> existingTrainingDataSetStrings = new ArrayList<>();
        existingTrainingDataSetStrings.add(KeyStrokeFeature.getKeyStrokeFeatureHeader().split(","));
        for(KeyStrokeFeature holder : existingTrainingDataSet){
            existingTrainingDataSetStrings.add(holder.toString().split(","));
        }
        existingTrainingDataSetStrings.addAll(random);
        writeContent(existingTrainingDataSetStrings, Paths.get(this.trainingFilePath.toString()+ File.separator + trainFileName));

    }

    private KeyStrokeFeatureFile readFile(Path trainingFilePath) throws Exception{
        String pathString = trainingFilePath.toString() + File.separator + trainFileName;
        //Prepare File for reading by removing trailing alterations that may exist in case file is edited.
        //prepareFileForReading(Paths.get(pathString));
        KeyStrokeFeatureFile keyStrokeFeatureFile = mapFileToKeyStrokeFeature(pathString);
        return keyStrokeFeatureFile;
    }

    private List<KeyStrokeFeature> readFileAsList(Path path) throws Exception{
        String pathString = path.toString() + File.separator + trainFileName;
        KeyStrokeFeatureFile keyStrokeFeatureFile = mapFileToKeyStrokeFeature(pathString);
        return keyStrokeFeatureFile.getKeyStrokeFeatures();
    }

    private void writeContent(List<String[]> fileArray, Path trainingFIle) throws Exception {
        System.out.println("Writing to path : "+trainingFIle.toString());
        //CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
        Writer writer = Files.newBufferedWriter(trainingFIle);
        ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();
        for (String[] array : fileArray) {
            csvWriter.writeNext(array);
        }
        writer.close();
    }

    private KeyStrokeFeatureFile mapFileToKeyStrokeFeature(String pathString) throws Exception{
        KeyStrokeFeatureFile keyStrokeFeatureFile = new KeyStrokeFeatureFile();
        keyStrokeFeatureFile.setKeyStrokeFeatureFileName(pathString);
        Reader reader = Files.newBufferedReader(Paths.get(pathString));
        reader = prepareReaderForReading((BufferedReader) reader);

        HeaderColumnNameMappingStrategy<KeyStrokeFeature> beanStrategy = new HeaderColumnNameMappingStrategy<>();
        beanStrategy.setType(KeyStrokeFeature.class);
        CsvToBean<KeyStrokeFeature> csvToBean = new CsvToBean<KeyStrokeFeature>();
        List<KeyStrokeFeature> keyStrokeFeatureList = csvToBean.parse(beanStrategy, reader);

        keyStrokeFeatureFile.setKeyStrokeFeatures(keyStrokeFeatureList);
        return keyStrokeFeatureFile;
    }

    /**
     * Modify the reader such that, all non formatted rows are ignored in the event
     * that the training file has been altered.
     * @param reader BufferedReader to be modified.
     * @return BufferedReader This is the modified reader.
     * @exception Exception On input error.
     */
    private BufferedReader prepareReaderForReading(BufferedReader reader) throws Exception {
        String correctedString = "";
        StringBuilder content = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            if(line.contains(",")){
                if (line.trim().split(",").length == 39) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
        }
        correctedString = content.toString().trim();
        InputStream inputStream = new ByteArrayInputStream(correctedString.getBytes(Charset.forName("UTF-8")));
        return new BufferedReader(new InputStreamReader(inputStream));
    }

}
