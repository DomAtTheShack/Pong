package GameGUI;

import java.awt.*;
import java.util.Random;
import java.util.Timer;

public class PowerUps extends MovableObject{

    Random poop = new Random();
    public final int initialValue;
    public  final int BALL_CAP = 4;

    public final int maxValue;
    public boolean changeDirection = true;

    public PowerUps(int x, int y, int width, int length, ID id) {
        super(x, y, width, length, id);
        initialValue = y;
         maxValue = initialValue + 30;
    }

    @Override
    public void tick()
    {


        if (changeDirection) {
            // Move downwards until reaching maxValue
            if (y < maxValue) {
                y++;
            } else {
                // Change direction when reaching maxValue
                changeDirection = false;
            }
        } else {
            // Move upwards until reaching initialValue
            if (y > initialValue) {
                y--;
            } else {
                // Change direction when reaching initialValue
                changeDirection = true;
            }
        }

        // Decrement from maximum value - 1 to initial value



        poop.nextInt(32);
        if(getBounds().intersects(Game.MainHandler.getBall().getBounds()))
        {
            Game.MainHandler.removeObject(this);
            Game.MainHandler.getBall().velX *= 2.5;

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
