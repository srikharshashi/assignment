package myServer;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("Server is running on port 8081");
            ServerSocket serverSocket = new ServerSocket(8081);
            //set env variables using custom class 👍
            env env=new env();
            Socket socket = serverSocket.accept();
            System.out.println("Server has accepted a new client connection");
            //use a wrapper around JDBC to stay sane 😞
            JDBCHelper helper=new JDBCHelper(env.getenv("DB_URL"),env.getenv("DB_USERNAME"),env.getenv("DB_PASSWORD"));
            System.out.println("Enter roll no");
            int rollno=new Scanner(System.in).nextInt();
            //query data and add it into a hashmap
            Map<String,String> map=new HashMap<>();  //-> {shashi:{rollnum:1,marks:10,marks2:13}}
            ResultSet resultSet = helper.executeQueryWithResult("SELECT * FROM student_info WHERE rollnum="+rollno);
            while (resultSet.next()) {
                map.put("name", resultSet.getString("name"));
                map.put("rollnum", resultSet.getString("rollnum"));
                map.put("sub1_marks", resultSet.getString("sub1_marks"));
                map.put("sub2_marks", resultSet.getString("sub2_marks"));
                map.put("sub3_marks", resultSet.getString("sub3_marks"));
                map.put("sub4_marks", resultSet.getString("sub4_marks"));
            }
            //generate the XML file from given data
            XMLGenerator.generateXML(map,"output.xml");
            // Read the file and get data in terms of bytes
            File file = new File("output.xml");
            byte[] fileBytes = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

            fileInputStream.read(fileBytes, 0, fileBytes.length);
            bufferedOutputStream.write(fileBytes, 0, fileBytes.length);
            bufferedOutputStream.flush();

            fileInputStream.close();
            bufferedOutputStream.close();
            socket.close();
            serverSocket.close();

            System.out.println("File sent successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }catch(SQLException s){
            System.out.println("SQL Exception");
            s.printStackTrace();
        }
    }
}
