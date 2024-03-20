package GameClasses;

import GameGUI.ID;

import java.awt.*;

public abstract class MovableObject extends GameObject
{
    protected int width, length;

    public MovableObject(int x, int y,  int width,int length, ID id) {
        super(x, y, id);
        this.length = length;
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public void tick() throws InterruptedException {
        x += (int) velX;
        y += (int) velY;
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, length);
    }
}
