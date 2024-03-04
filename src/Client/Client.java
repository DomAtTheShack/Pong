package Client;


import GameGUI.Game;
import GameGUI.GameObject;
import GameGUI.ID;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/**
 * This is the client class that will handle the connection to the server
 */
public class Client
{


    /**
     * This is the boolean that will determine if the client is connected to the server
     */
    private static boolean connected;
    /**
     * This is the list of clients that are currently connected to the server
     */
    private static List<String> currentClients;
    /**
     * This is the socket that will be used to connect to the server
     */
    private static Socket socket;
    /**
     * This is the packet that will be sent to the server
     */
    public static Packet packet;

    /**
     * This is the output stream that will be used to send packets to the server
     */
    public static ObjectOutputStream out;
    /**
     * This is the username that will be used to identify the client
     */
    private static String username;

    /**
     * This is the room that the client is currently in on the server
     */
    private static int room;

    /**
     * This is the method that will be used to connect to the server
     * @param server This is the server that the client will connect to
     * @param user This is the username that the client will use to identify itself
     * @throws InterruptedException This is the exception that will be thrown if the thread is interrupted
     */

    public static void connect(String[] server, String user) throws InterruptedException
    {
        try
        {
            socket = new Socket(server[0], Integer.parseInt(server[1]));
            out = new ObjectOutputStream(socket.getOutputStream());

            // Send the user information
            Packet userPacket = new Packet(user, ID.NoUser);
            username = user;
            Packet.sendObjectAsync(out, userPacket);
            connected = true;
            currentClients = new ArrayList<>();
            Thread.sleep(1000);

            // Start the thread that will handle the incoming messages
            new Thread(() ->
            {
                try
                {
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                    while (connected)
                    {
                        // Receive Message object
                        Packet receivedPacket = Packet.receiveObject(objectInputStream);
                        if (receivedPacket != null)
                        {
                            if(receivedPacket.getSender().equals("server"))
                            {
                                List<GameObject> LocalObjects = receivedPacket.getObjectsOnScreen();
                                for(GameObject X : LocalObjects)
                                {
                                    System.out.println(X.getBounds());
                                }
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e)
                {
                    if (!e.toString().contains("Socket closed"))
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e)
        {
            connected = false;
            e.printStackTrace();
            if (e.getMessage().contains("Connection Reset") || e.getMessage().contains("Connection refused: connect") || e.getMessage().contains("UnknownHostException:")) {
                System.out.println("Server Not Available");
            }
        }
    }

    /**
     * This is the method that will be used to send a message to the server
     * @return This is the packet that will be sent to the server
     */
    public static boolean isConnected()
    {
        return connected;
    }


    /**
     * This is the method that will be used to send a message to the server
     * @throws IOException This is the exception that will be thrown if there is an error with the output stream
     * @throws InterruptedException This is the exception that will be thrown if the thread is interrupted
     */
    public static void disconnect() throws IOException, InterruptedException
    {
        if (connected)
        {
            connected = false;
            Thread.sleep(100);
            socket.close();
            // Inform the user about the disconnection status
            System.out.println("Disconnected from the server!");
        } else
        {
            System.out.println("Not Connected!");
        }
    }

    /**
     * This is the method that will be used to send a message to the server
     * @return This is the username that the client is using to identify itself
     */
    public static String getUsername()
    {
        return username;
    }

}
