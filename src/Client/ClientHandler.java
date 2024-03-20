package Client;

import GameGUI.Game;
import GameGUI.ID;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {
    private static final List<ClientHandler> clients = new ArrayList<>();
    private final Socket clientSocket;
    private ObjectOutputStream objectOutputStream;
    private String username;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Initialize the ObjectOutputStream for sending messages
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            Packet connectPacket = Packet.receiveObject(objectInputStream);
            this.username = connectPacket.getSender();

            synchronized (clients) {
                clients.add(this);
                for (ClientHandler client : clients) {
                    System.out.println(username + " has joined!");
                }
            }

            while (true)
            {
                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                Packet packet = Packet.receiveObject(objectInputStream);
                if (packet != null) {
                    if (packet.getID() == ID.P2Pabble) {
                        int p2X = packet.getHandler().getP2Pabble().getX();
                        int p2Y = packet.getHandler().getP2Pabble().getY();
                        Game.MainHandler.getP2Pabble().setX(p2X);
                        Game.MainHandler.getP2Pabble().setY(p2Y);
                        sendTick(new Packet("S",
                                ID.ServerTick,
                                Game.frames,
                                Game.MainHandler));
                    }
                }

                // Adjust the sleep duration to reduce unnecessary looping
                Thread.sleep(100);
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            synchronized (clients) {
                clients.remove(this);
                System.out.println(username + " has left the chat.");
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void sendTick(Packet newTick) throws IOException {
        if (objectOutputStream != null) {
            // Send the Message object using ObjectOutputStream
           Packet.sendObjectAsync(objectOutputStream, newTick);
        }
    }
   // public void sendPabbleInfo(String )

}
