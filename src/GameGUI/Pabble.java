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
        System.out.println(id + " " + handler.isUp());
        System.out.println(handler.isDown());

        System.out.println(handler.isRight());
        System.out.println(handler.isLeft());


        //Movement
        if(handler.isUp())
            velY = -5;
        else if(!handler.isDown()) velY = 0;

        if(handler.isDown()) velY = 5;
        else if (!handler.isUp()) velY = 0;

        if(handler.isRight()) velX = 5;
        else if (!handler.isLeft()) velX = 0;

        if(handler.isLeft()) velX = -5;
        else if (!handler.isRight()) velX = 0;
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
