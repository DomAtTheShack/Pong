import java.awt.*;

public class Paddle extends GameObject
{

    private int width, height;

    public Paddle(int x, int y, ID id)
    {
        super(x, y, id);

    }

    @Override
    public void tick()
    {

    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }

    @Override
    public Rectangle getBounds()
     {
        return new Rectangle(x, y, 10, 20);
    }
}
