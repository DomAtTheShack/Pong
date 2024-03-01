package GameGUI;

import java.awt.*;

public class Paddle extends GameObject
{

    private final int startX;
    private final int startY = 10;

    public Paddle(int x, int y, ID id)
    {
        super(x, y, id);
        if(id.equals(ID.P1Pabble))
        {
            startX = 10;
        }else
            startX = 100;

    }

    @Override
    public void tick()
    {

    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(startX, startY, 20, 60);
    }

    @Override
    public Rectangle getBounds()
     {
        return new Rectangle(x, y, 20, 60);
    }
}
