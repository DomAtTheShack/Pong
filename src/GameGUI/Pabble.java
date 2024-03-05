package GameGUI;

import java.awt.*;

public class Pabble extends GameObject
{

    Handler handler;
    public Pabble(int x, int y, ID id, Handler handler)
    {
        super(x, y, id);
        this.handler = handler;

    }

    @Override
    public void tick() {
        x += velX;
        y += velY;


        //Movement
        if (handler.isUp())
            velY = -5;
        else if (!handler.isDown()) velY = 0;

        if (handler.isDown()) velY = 5;
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
        g.fillRect(x, y, 20, 70);

        g.setColor(Color.BLACK); // Border color
        g.drawRect(x, y, 20, 70);
    }

    @Override
    public Rectangle getBounds()
     {
        return new Rectangle(x, y, 20, 60);
    }
}
