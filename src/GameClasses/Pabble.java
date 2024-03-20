package GameClasses;

import GameGUI.Handler;
import GameGUI.ID;

import java.awt.*;

public class Pabble extends MovableObject
{

    Handler handler;
    private boolean isBig = false;
    private boolean isSpedUp = false;
    public Pabble(int x, int y, int width, int length, ID id, Handler handler)
    {
        super(x, y,width,length, id);
        this.handler = handler;

    }

    @Override
    public void tick() throws InterruptedException {
        super.tick();


        //Movement
        if (handler.isUp()) {
            velY = -9;
        }else if (!handler.isDown()) {
            velY = 0;
        }


        if (handler.isDown()) {
            velY = 9;
        }
        else if (!handler.isUp()) {
            velY = 0;
        }
        if (y <= 10)
        {
            velY = 0;
            handler.setUp(false);


            if (handler.isDown()) {
                velY = 9;
            }
            else if (!handler.isUp()) {
                velY = 0;
            }

        }else if(y >= 575) {
            velY = 0;
            handler.setDown(false);
            if (handler.isUp()) {
                velY = -9;
            }else if (!handler.isDown()) {
                velY = 0;
            }
        }
    }
    public boolean isBig()
    {
        return isBig;
    }
    public boolean isSpedUp()
    {
        return isSpedUp;
    }
    public void switchBigFlag()
    {
        isBig = !isBig;
    }
    public void switchSpeedFlag()
    {
        isSpedUp = !isSpedUp;
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(new Color(237,204,111));
        g.fillRect(x, y, width, length);

        g.setColor(Color.BLACK); // Border color
        g.drawRect(x, y, width, length);
    }

    @Override
    public Rectangle getBounds()
     {
        return new Rectangle(x, y - 10, width, length + 15);
    }
}
