package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import GameGUI.GameObject;
import GameGUI.ID;

/**
 * @author Dominic Hann
 * @version 1.0
 * @since 2/12/2021
 * This is the packet class that will be used to send information between the client and the server
 * This class implements Serializable so that it can be sent over the network
 * This class is used to send information between the client and the server
 */
public class Packet implements Serializable
{

    /**
     * This is the message that will be sent to the server
     */
    private String Sender;
    /**
     * This is the type of packet that will be sent to the server
     */
    private final ID id;
    /**
     * This is the list of users that will be sent to the server
     */
    private List<GameObject> ObjectsOnScreen;
    /**
     * This is the username of the user that sent the packet
     */
    private boolean gameState;
    /**
     * This is the room that the user is currently in
     */
    private int currentFrame;


    public Packet(String sender, ID ID, int currentFrame, List<GameObject> objectsOnScreen)
    {
        this.Sender = sender;
        this.id = ID;
        this.ObjectsOnScreen = objectsOnScreen;
        this.currentFrame = currentFrame;
    }
    public Packet(String sender, ID ID)
    {
        Sender = sender;
        this.id = ID;
    }


    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public ID getID() {
        return id;
    }

    public List<GameObject> getObjectsOnScreen() {
        return ObjectsOnScreen;
    }

    public void setObjectsOnScreen(List<GameObject> objectsOnScreen) {
        ObjectsOnScreen = objectsOnScreen;
    }

    public boolean isGameState() {
        return gameState;
    }

    public void setGameState(boolean gameState) {
        this.gameState = gameState;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * This is the method that will be used to send a packet to the server
     * @param objectOutputStream This is the output stream that will be used to send the packet to the server
     * @param packet This is the packet that will be sent to the server
     */
    public static void sendObjectAsync(ObjectOutputStream objectOutputStream, Packet packet)
    {
        Thread senderThread = new Thread(() -> {
            try {
                objectOutputStream.writeObject(packet);
                objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        senderThread.start();
    }

    /**
     * This is the method that will be used to receive a packet from the server
     * @param objectInputStream This is the input stream that will be used to receive the packet from the server
     * @return This is the packet that was received from the server
     * @throws IOException This is the exception that will be thrown if there is an error receiving the packet
     * @throws ClassNotFoundException This is the exception that will be thrown if the class is not found
     */
    public static Packet receiveObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException
    {
        return (Packet) objectInputStream.readObject();
    }

}

