package GameGUI;

import java.awt.*;
import java.util.Random;

public class Ball extends GameObject
{
    private final Random BallDevation = new Random();

    private Rectangle PrevP1;
    private Rectangle PrevP2;
    private Rectangle PrevBall;
    public Ball(int x, int y, ID id)
    {
        super(x, y, id);
        velX = 5;
        velY = 5;
    }

    @Override
    public void tick() {
        PrevBall = getBounds();
        PrevP1 = Game.MainHandler.getP1Pabble().getBounds();
        PrevP2 = Game.MainHandler.getP2Pabble().getBounds();
        x += velX;
        y += velY;
        if (y >= 645) {
            y --;
            velY *= -1;

        }else if(y<=0) {
            y++;
            velY *= -1;
        }
        if(x<=0)
        {
            x++;
            velX *= -1;
        }else if(x>=970)
        {
            x--;
            velX *= -1;
        }

    }

    @Override
    public void render(Graphics g) {

        g.drawOval(x, y, 20, 20);
        g.setColor(new Color(64,64,102));
        g.fillOval(x,y,20,20);


    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y, 20,20);
    }
}

