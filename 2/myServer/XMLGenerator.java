package myServer;
import java.io.FileWriter;
import java.util.*;

public class XMLGenerator {

    public static void generateXML(Map<String, String> dataMap, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<data>\n");

            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                writer.write("  <" + key + ">" + value + "</" + key + ">\n");
            }

            writer.write("</data>\n");
            writer.close();
            System.out.println("XML file generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
