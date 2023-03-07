package utils.fileHandlers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.TestBase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    static FileReader reader;
    static Properties properties;
    static String value;

    public static String getProperty(String key){
        String filePath = "src/test/resources/configurations/environment.properties";
        TestBase.log("Reading property : " + key);
        try{
            if(reader == null){
                reader = new FileReader(filePath);
                properties = new Properties();
                properties.load(reader);
            }
        }
        catch(FileNotFoundException e){
            new RuntimeException("File Not Found : " + filePath);
        }
        catch (IOException e){
            new RuntimeException("Unable to load properties from reader " + e.getMessage());
        }
        value = properties.getProperty(key);
        TestBase.log("Value is : " + value);
        return  value;
    }

    public static String getProperty(String file, String key){
        TestBase.log("Reading property : '" + key + "' From File : " + file);
        try{
            reader = new FileReader(file);
            properties = new Properties();
            properties.load(reader);
        }
        catch(FileNotFoundException e){
            new RuntimeException("File Not Found : " + file);
        }
        catch (IOException e){
            new RuntimeException("Unable to load properties from reader " + e.getMessage());
        }
        value = properties.getProperty(key);
        TestBase.log("Value is : " + value);
        return  value;
    }
}
