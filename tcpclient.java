import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class tcpclient {
    public static void main(String[] args) {
        try (Socket c = new Socket("192.168.24.95", 1111)) {
            System.out.println("Connected to server!");

            try (Scanner scanner = new Scanner(System.in);
                 DataInputStream din = new DataInputStream(c.getInputStream());
                 DataOutputStream dout = new DataOutputStream(c.getOutputStream())) {

                while (true) {
                    System.out.print("Enter the message: ");
                    String cmsg = scanner.nextLine();
                    dout.writeUTF(cmsg);

                    if (cmsg.equalsIgnoreCase("exit")) {
                        System.out.println("Disconnected from the server.");
                        break;
                    }

                    String smsg = din.readUTF();
                    System.out.println("Server: " + smsg);

                    if (smsg.equalsIgnoreCase("exit")) {
                        System.out.println("Server has terminated the connection.");
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error while communicating with the server: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Failed to connect to the server: " + e.getMessage());
        }
    }
}

