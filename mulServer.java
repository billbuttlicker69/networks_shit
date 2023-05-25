import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<Socket> clients;

    public Server() {
        clients = new ArrayList<>();
    }

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                System.out.println("New client connected: " + clientSocket);

                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            while (true) {
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);

                if (bytesRead == -1) {
                    break; // End of stream, client disconnected
                }

                String request = new String(buffer, 0, bytesRead);
                System.out.println("Received from client: " + request);

                // Send the received message to all connected clients
                sendToAllClients(request);

                // Send a response back to the client
                String response = "Server response to: " + request;
                outputStream.write(response.getBytes());
                outputStream.flush();
            }

            clientSocket.close();
            clients.remove(clientSocket);
            System.out.println("Client disconnected: " + clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToAllClients(String message) {
        for (Socket client : clients) {
            try {
                OutputStream outputStream = client.getOutputStream();
                outputStream.write(message.getBytes());
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(7708); // Port number
    }
}
