package udp;
import java.net.*;
import java.io.*;

public class cudp {
    public static void main(String args[]) {
        if (args.length != 2) {
            System.out.println("Usage: java cudp <username> <server-hostname>");
            return;
        }

        String username = args[0];
        String serverHostname = args[1];
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            final DatagramSocket finalSocket = socket; // Variável final para referência ao socket

            // Thread para receber mensagens do servidor
            Thread receiverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] buffer = new byte[1000];
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        while (true) {
                            finalSocket.receive(packet);
                            String message = new String(packet.getData(), 0, packet.getLength());
                            System.out.println(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiverThread.start();

            // Loop para enviar mensagens para o servidor
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String input = reader.readLine();
                String message = username + ": " + input;
                byte[] buffer = message.getBytes();
                InetAddress serverAddress = InetAddress.getByName(serverHostname);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, 6789);
                socket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
