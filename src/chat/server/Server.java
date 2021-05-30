package chat.server;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    static AtomicInteger clientsCount = new AtomicInteger(0);
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(23456)) {
            System.out.println("Server started!");
            while (true) {
                try (Socket socket = server.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                    clientsCount.set(clientsCount.get() + 1);
                    int connectionNumber = clientsCount.get();
                    System.out.printf("Client number %d is connected/n", connectionNumber);
                    Scanner scanner = new Scanner(System.in);

                    Thread fromConsoleToClient = new Thread(() -> {
                        while (true) {
                            try {
                                output.writeUTF(scanner.nextLine());
                            } catch (IOException e) {
                                e.printStackTrace();
                                break;
                            }
                        }
                    });

                    Thread fromClientToConsole = new Thread(() -> {
                        while (true) {
                            try {
                                System.out.println(input.readUTF());
                            } catch (IOException e) {
                                break;
                            }
                        }
                    });

                    fromConsoleToClient.start();
                    fromClientToConsole.start();

                    fromConsoleToClient.join();
                    fromClientToConsole.join();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}