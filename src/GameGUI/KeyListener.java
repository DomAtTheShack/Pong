package GameGUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {

    Handler handler;

    public KeyListener(Handler handler){
        this.handler = handler;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        for(int i = 0;i < handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.P1Pabble){
                if(key == KeyEvent.VK_W)
                    handler.setUp(true);
                if(key == KeyEvent.VK_S) handler.setDown(true);

            }
            else if(tempObject.getId() == ID.P2Pabble){
                if(key == KeyEvent.VK_UP) handler.setUp(true);
                if(key == KeyEvent.VK_DOWN) handler.setDown(true);

            }
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        for(int i = 0;i < handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.P1Pabble){
                    if(key == KeyEvent.VK_W) handler.setUp(false);
                    if(key == KeyEvent.VK_S) handler.setDown(false);
                }
            else if(tempObject.getId() == ID.P2Pabble){
                if(key == KeyEvent.VK_UP) handler.setUp(false);
                if(key == KeyEvent.VK_DOWN) handler.setDown(false);

            }
            }
        }
    }
