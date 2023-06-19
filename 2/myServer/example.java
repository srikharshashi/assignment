//rename this file to env.java and insert your credentials
//rename class to env
package myServer;
import java.util.*;
public class example {
    static Map<String,String> getEnvMap(){
        Map<String,String> map=new HashMap<>();
        map.put("DB_URL","");
        map.put("DB_USERNAME","");
        map.put("DB_PASSWORD","");
        return map;
    }
    static void setEnvironmentVariables(Map<String, String> envVariables) {
        for (Map.Entry<String, String> entry : envVariables.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.setProperty(key, value);
        }
    }
}
