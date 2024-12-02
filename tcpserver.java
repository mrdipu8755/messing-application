package demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class tcpserver {
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(1111)) {
            System.out.println("Waiting for connection...");
            Socket s = ss.accept();
            System.out.println("Client connected!");

            try (Scanner scanner = new Scanner(System.in);
                 DataInputStream din = new DataInputStream(s.getInputStream());
                 DataOutputStream dout = new DataOutputStream(s.getOutputStream())) {

                while (true) {
                    String cmsg = din.readUTF();
                    System.out.println("Client: " + cmsg);
                    if (cmsg.equalsIgnoreCase("exit")) {
                        System.out.println("Client disconnected. Closing connection...");
                        break;
                    }

                    System.out.print("Enter message: ");
                    String smsg = scanner.nextLine();
                    dout.writeUTF(smsg);

                    if (smsg.equalsIgnoreCase("exit")) {
                        System.out.println("Server shutting down...");
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error while communicating with the client: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Server failed to start: " + e.getMessage());
        }
    }
}


