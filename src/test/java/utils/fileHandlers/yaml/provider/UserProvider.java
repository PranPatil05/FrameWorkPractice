package utils.fileHandlers.yaml.provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.bouncycastle.util.test.Test;
import utils.TestBase;
import utils.fileHandlers.yaml.pojo.UserData;

import java.io.File;
import java.util.List;

public class UserProvider {

    private static List<UserData> userDataList;
    static String yamlFile = "src/test/resources/data/users.yaml";

    public static UserData getUserData(String user){
        TestBase.log("Fetching User Data for '" + user + "' From : " + yamlFile);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try{
            userDataList = mapper.readValue(new File(yamlFile), new TypeReference<List<UserData>>() {
            });
        }
        catch (Exception e){
            new RuntimeException("Issue occured while reading : " + yamlFile);
        }

        for(UserData userData: userDataList){
            if(userData.getUser().equals(user)){
                TestBase.log("User Data is \nUsername:" + userData.getCredentials().getUsername() +
                        "\nPassword:" + userData.getCredentials().getPassword());
                return userData;
            }
        }
        TestBase.log("User Data for Following user not found : " + user);
        return null;
    }
}
