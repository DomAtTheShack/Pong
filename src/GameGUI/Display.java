package GameGUI;

import java.awt.*;

public class Display extends GameObject
{
    String Display;

    public Display(int x, int y, ID id, String toDisplay) {
        super(x, y, id);
        Display = toDisplay;
    }

    @Override
    public void tick() {
        Display = String.valueOf(Game.fps);
    }

    @Override
    public void render(Graphics g) {

        g.setColor(new Color(64,64,102));
        g.drawString("FPS: " + Display, x, y);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
