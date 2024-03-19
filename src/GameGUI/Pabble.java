package GameGUI;

import java.awt.*;

public class Pabble extends MovableObject
{

    Handler handler;
    public Pabble(int x, int y, int width,int length, ID id, Handler handler)
    {
        super(x, y,width,length, id);
        this.handler = handler;

    }

    @Override
    public void tick() throws InterruptedException {
        super.tick();


        //Movement
        if (handler.isUp())
            velY = -9;
        else if (!handler.isDown()) velY = 0;

        if (handler.isDown()) velY = 9;
        else if (!handler.isUp()) velY = 0;
        if (y <= 10)
        {
            velY = 0;
            y++;
        }else if(y >= 575) {
            velY = 0;
            y--;
        }
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
