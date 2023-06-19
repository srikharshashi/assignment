package myClient;
import java.io.*;
import java.net.Socket;

public class client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 8081;
        try {
            // Connect to the server
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server: " + socket.getInetAddress());
            byte[] fileBytes = new byte[1024];
            InputStream is = socket.getInputStream();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("received.xml"));
            int bytesRead;
            while ((bytesRead = is.read(fileBytes)) != -1) {
                bos.write(fileBytes, 0, bytesRead);
            }
            bos.close();
            socket.close();
            XMLReader.readXML("received.xml");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
