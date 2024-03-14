package GameGUI;

import java.awt.*;
import java.util.Random;
import java.util.Timer;

public class PowerUps extends MovableObject{

    Random poop = new Random();
    public final int initialValue;

    public final int maxValue;

    public PowerUps(int x, int y, int width, int length, ID id) {
        super(x, y, width, length, id);
        initialValue = y;
         maxValue = initialValue + 20;
    }

    @Override
    public void tick()
    {


        for (int i = initialValue; i <= maxValue; i++) {
            y++;
            if(y >= maxValue){
                for (int j = maxValue; j >= initialValue; j--) {
                    y--;
                }
            }
        }

        // Decrement from maximum value - 1 to initial value



        poop.nextInt(32);
        if(getBounds().intersects(Game.MainHandler.getBall().getBounds()))
        {
            Game.MainHandler.removeObject(this);
            Game.MainHandler.getBall().velX *= 3;

        }

    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.PINK);
        g.drawOval(x, y, width, length);
        g.fillOval(x,y, width, length);
    }

}
