package chat.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
   static AtomicBoolean isRun = new AtomicBoolean(true);
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 23456);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Client started!");
            Scanner scanner = new Scanner(System.in);

            Thread fromConsoleToServer = new Thread(() -> {
                while (true) {
                    try {
                        String msg = scanner.nextLine();
                        output.writeUTF(msg);
                        if (msg.equals("\\exit")) {
                            System.out.println("bye");
                            isRun.set(false);
                            break;
                        }
                        System.out.println(input.readUTF());
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            });



            fromConsoleToServer.start();
            //fromClientToServer.start();

            fromConsoleToServer.join();
            //fromClientToServer.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}