package GameGUI;

import java.awt.*;

public class Display extends GameObject
{
    String Display;

    String score1;
    String score2;

    public Display(int x, int y, ID id, String toDisplay) {
        super(x, y, id);
        Display = toDisplay;
    }

    @Override
    public void tick() {
        Display = String.valueOf(Game.fps);

//        score1 = String.valueOf(Game.scoreP1);
//        score2 = String.valueOf(Game.scoreP2);

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
