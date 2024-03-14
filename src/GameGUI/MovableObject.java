package GameGUI;

import java.awt.*;

public class MovableObject extends GameObject
{
    protected int width, length;

    public MovableObject(int x, int y,  int width,int length, ID id) {
        super(x, y, id);
        this.length = length;
        this.width = width;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, length);
    }
}
