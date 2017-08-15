package streamvideo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class ServerSocketStream {

    // 1028x720
    
    public static void main(String[] args) throws IOException {
        
        ServerSocket serverSocket = new ServerSocket(10999);
        
        while(true){
            Socket socket = serverSocket.accept();
            
            File file = new File("arquivo.mp4");
            FileInputStream fileInput = new FileInputStream(file);

            BufferedInputStream input = new BufferedInputStream(socket.getInputStream());
            input.read();

            BufferedOutputStream output = new BufferedOutputStream(socket.getOutputStream());
            output.write("HTTP/1.1 200 \r\n".getBytes());
            output.write("Content-type: video/mp4\r\n".getBytes());
            output.write(("Accept-ranges: bytes\r\n").getBytes());
            output.write(("Server: euserver\r\n").getBytes());
            output.write(("Connection: keep-alive\r\n").getBytes());
            output.write(("Content-lenght: " + file.length() + "\r\n\r\n").getBytes());
            
            int total = 0;
            int received = 0;
            
            while(true){
                
                byte[] b = new byte[1024];
                
                received = fileInput.read(b);
                
                if(received > 0){
                    output.write(b,0, received);
                    total += received;
                } else {
                    break;
                }
                
                System.out.println("Enviado: " + total);
            }
            
            input.close();
            fileInput.close();
            output.close();
            socket.close();
        }
    }
}
