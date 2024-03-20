package GameGUI;

import GameClasses.Display;
import GameClasses.GameObject;
import GameClasses.MovableObject;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Handler {

    private boolean up = false, down = false, right = false, left = false;

    LinkedList<GameObject> object = new LinkedList<GameObject>();

    LinkedList<Handler> subHandlers = new LinkedList<>();

    public void tick() throws InterruptedException {
        for(int i = 0;i<object.size();i++){
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }
    }
    public void render(Graphics g){
        for(int i = 0;i<object.size();i++){
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }
    public void addObject(GameObject tempObject){
        object.add(tempObject);
    }
    public void removeObject(GameObject tempObject){
        object.remove(tempObject);
    }

    public void addHandler(Handler handler) {subHandlers.add(handler);}
    public void removeHandler(Handler handler) {subHandlers.remove(handler);}

    public MovableObject getP1Pabble() {
        MovableObject tempObject = null;
        for (Handler subHandler : subHandlers) {
            for (GameObject temp : subHandler.object) {
                if (temp.getId().equals(ID.P1Pabble)) {
                    tempObject = (MovableObject) temp;
                    break;
                }
            }
        }
        return tempObject;
    }
    public MovableObject getP2Pabble() {
        MovableObject tempObject = null;
        for (Handler subHandler : subHandlers) {
            for (GameObject temp : subHandler.object) {
                if (temp.getId().equals(ID.P2Pabble)) {
                    tempObject = (MovableObject) temp;
                    break;
                }
            }
        }
        return tempObject;
    }
    public void setP1Pabble(GameObject newPabble)
    {
        for (Handler subHandler : subHandlers) {
            for (int h = 0; h < subHandler.object.size(); h++) {
                if (subHandler.object.get(h).id.equals(ID.P1Pabble)) {
                    subHandler.object.set(h, newPabble);
                    break;
                }
            }
        }
    }
    public void setP2Pabble(GameObject newPabble)
    {
        for (Handler subHandler : subHandlers) {
            for (int h = 0; h < subHandler.object.size(); h++) {
                if (subHandler.object.get(h).id.equals(ID.P2Pabble)) {
                    subHandler.object.set(h, newPabble);
                    break;
                }
            }
        }
    }
    public GameObject getBall() {
        for (GameObject temp : object) {
           if(temp.getId().equals(ID.Ball))
           {
               return temp;
           }
        }
        return null;
    }

    public Display getP1Score()
    {
        for(GameObject tempDisplay: object)
        {
            if(tempDisplay.getId().equals(ID.P1Score))
                return (Display) tempDisplay;
        }
        return null;
    }
    public Display getP2Score()
    {
        for(GameObject tempDisplay: object)
        {
            if(tempDisplay.getId().equals(ID.P2Score))
                return (Display) tempDisplay;
        }
        return null;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
}
