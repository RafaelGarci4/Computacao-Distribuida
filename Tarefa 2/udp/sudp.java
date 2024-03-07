package udp;
import java.net.*;
import java.io.*;
import java.util.*;

public class sudp {
    public static void main(String args[]) {
        DatagramSocket socket = null;
        Map<InetAddress, Integer> clients = new HashMap<>();

        try {
            socket = new DatagramSocket(6789);
            final DatagramSocket finalSocket = socket; // Variável final para referência ao socket

            // Thread para receber mensagens dos clientes e encaminhar para todos os clientes
            Thread receiverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] buffer = new byte[1000];
                        while (true) {
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                            finalSocket.receive(packet);
                            String message = new String(packet.getData(), 0, packet.getLength());

                            // Adiciona o endereço do remetente à lista de clientes
                            clients.put(packet.getAddress(), packet.getPort());

                            // Encaminha a mensagem para todos os clientes
                            for (Map.Entry<InetAddress, Integer> entry : clients.entrySet()) {
                                if (!entry.getKey().equals(packet.getAddress())) {
                                    DatagramPacket replyPacket = new DatagramPacket(packet.getData(), packet.getLength(), entry.getKey(), entry.getValue());
                                    finalSocket.send(replyPacket);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiverThread.start();

            System.out.println("Servidor UDP iniciado. Aguardando conexões...");
            while (true) {
                // Espera por novas mensagens dos clientes
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
