package tcp;
import java.net.*;
import java.io.*;

class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	public Connection (Socket aClientSocket) {
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream( clientSocket.getInputStream());
			out =new DataOutputStream( clientSocket.getOutputStream());
			this.start();
		} catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
	}
	public void run(){
		try {			                 // an echo server

			String data = in.readUTF();	                  // read a line of data from the stream
			out.writeUTF(data);
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		} catch(IOException e) {System.out.println("readline:"+e.getMessage());
		} finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}
		

	}
}


public class stcp {
    
    public static void main (String args[]) {
		try{
			int serverPort = 7896; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while(true) {
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket);
			}
		} catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
	}
    
}


/*import java.net.*;
import java.io.*;

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();

            // Log a mensagem indicando que uma nova conexão foi estabelecida
            System.out.println("Nova conexão estabelecida com o cliente: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            // Um loop para lidar com várias mensagens do cliente
            while (true) {
                String data = in.readUTF(); // Lê uma linha de dados do fluxo
                out.writeUTF(data); // Envia a mesma linha de volta para o cliente

                // Log da mensagem recebida do cliente
                System.out.println("Mensagem recebida do cliente (" + clientSocket.getInetAddress() + "): " + data);
            }
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                // Log da mensagem indicando que a conexão com o cliente foi fechada
                System.out.println("Conexão com o cliente (" + clientSocket.getInetAddress() + ") fechada.");
            } catch (IOException e) {
                /* Ignora erros ao fechar a conexão */
            }
        }
    }
}

/*public class stcp {
    public static void main(String args[]) {
        try {
            int serverPort = 7896; // a porta do servidor
            ServerSocket listenSocket = new ServerSocket(serverPort);

            // Log a mensagem indicando que o servidor está sendo iniciado
            System.out.println("Servidor TCP iniciado. Aguardando conexões...");

            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }
} */
