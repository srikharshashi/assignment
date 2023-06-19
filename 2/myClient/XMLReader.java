package myClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLReader {

    public static void readXML(String fileName) {
        try {
            File xmlFile = new File(fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            NodeList nodeList = document.getElementsByTagName("name");
            if (nodeList.getLength() > 0) {
                Node nameNode = nodeList.item(0);
                String name = nameNode.getTextContent();
                System.out.println("Name: " + name);
            }

            int totalMarks = 0;
            int count = 0;
            nodeList = document.getElementsByTagName("sub1_marks");
            count += nodeList.getLength();
            totalMarks += getNodeValuesSum(nodeList);

            nodeList = document.getElementsByTagName("sub2_marks");
            count += nodeList.getLength();
            totalMarks += getNodeValuesSum(nodeList);

            nodeList = document.getElementsByTagName("sub3_marks");
            count += nodeList.getLength();
            totalMarks += getNodeValuesSum(nodeList);

            nodeList = document.getElementsByTagName("sub4_marks");
            count += nodeList.getLength();
            totalMarks += getNodeValuesSum(nodeList);

            double averageMarks = (double) totalMarks / count;
            System.out.println("Average Marks: " + averageMarks);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getNodeValuesSum(NodeList nodeList) {
        int sum = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                sum += Integer.parseInt(element.getTextContent());
            }
        }
        return sum;
    }


}
