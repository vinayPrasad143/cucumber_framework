package co.graphene.config;

import co.graphene.util.Debugger;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {

    static Properties configProps;
    //test comment
    public static void loadAppConfig() {
        //String configFileName = "%s-takeda.properties";
        String configFileName = "graphene_accesspoint.properties";
      //  String current_environment = System.getProperty("TestEnvironment");
//        if(current_environment == null){
//            current_environment = "test";
//        }
//        Debugger.println("TestEnvironment: " + current_environment);
   //     configFileName = String.format(configFileName, current_environment);
        configProps          = loadConfigProperties(configFileName);
    }
    public static String getPropertyValue(String propsKey){
        if(configProps == null){
            loadAppConfig();
        }
        return configProps.getProperty(propsKey);
    }
    public static Properties loadConfigProperties(String filename) {
        String configLocation = System.getProperty("user.dir") + File.separator + "config";
        Properties pro = new Properties();
        try {
            pro.load(new FileInputStream(configLocation + File.separator + filename));
        } catch (IOException exp) {
            Assert.fail("File: " + filename + " Not present in location :" + configLocation);
        }
        return pro;
    }

}//end
