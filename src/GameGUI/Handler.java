package GameGUI;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    private boolean up = false, down = false, right = false, left = false;

    LinkedList<GameObject> object = new LinkedList<GameObject>();

    LinkedList<Handler> subHandlers = new LinkedList<>();

    public void tick(){
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

    public GameObject getP1Pabble() {
        GameObject tempObject = null;
        for (int i = 0; i < subHandlers.size(); i++) {
            for (GameObject temp : subHandlers.get(i).object) {
                if (temp.id.equals(ID.P1Pabble)) {
                    tempObject = temp;
                    break;
                }
            }
        }
        return tempObject;
    }
    public GameObject getP2Pabble()
    {
        GameObject tempObject = null;
        for (int i = 0; i < subHandlers.size(); i++) {
            for (GameObject temp : subHandlers.get(i).object) {
                if (temp.id.equals(ID.P2Pabble)) {
                    tempObject = temp;
                    break;
                }
            }
        }
        return tempObject;
    }
    public Display getDisplay()
    {
        for(GameObject tempDisplay: object)
        {
            if(tempDisplay.id.equals(ID.Display))
                return (Display) tempDisplay;
        }
        return null;
    }
    public Display getP2Score()
    {
        for(GameObject tempDisplay: object)
        {
            if(tempDisplay.id.equals(ID.P2Score))
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
