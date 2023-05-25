import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 7708); // Server address and port number

            Thread receiveThread = new Thread(() -> receiveMessages(socket));
            receiveThread.start();

            Thread sendThread = new Thread(() -> sendMessages(socket));
            sendThread.start();

            receiveThread.join();
            sendThread.join();

            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void receiveMessages(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();

            while (true) {
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);

                if (bytesRead == -1) {
                    break; // End of stream, server disconnected
                }

                String message = new String(buffer, 0, bytesRead);
                System.out.println("Received from server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessages(Socket socket) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                outputStream.write(message.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
