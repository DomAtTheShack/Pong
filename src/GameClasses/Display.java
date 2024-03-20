package GameClasses;

import GameGUI.Game;
import GameGUI.ID;

import java.awt.*;

public class Display extends GameObject
{
    String Display;

    int score = 0;

    public Display(int x, int y, ID id, String toDisplay) {
        super(x, y, id);
        Display = toDisplay;
    }

    @Override
    public void tick() {
        Display = String.valueOf(Game.fps);
    }
    public void incrementScore()
    {
        score++;
    }

    @Override
    public void render(Graphics g) {

        if (id.equals(ID.Display)) {


            g.setColor(new Color(64, 64, 102));
            g.drawString("FPS: " + Display + "\nBall XY: " + Game.MainHandler.object.get(1).getX() + " " + Game.MainHandler.object.get(1).getY()
                    + "\n P1 XY: " + Game.MainHandler.getP1Pabble().getX() + ", " + Game.MainHandler.getP1Pabble().getY()
                    + "\n P2 XY: " + Game.MainHandler.getP2Pabble().getX() + ", " + Game.MainHandler.getP2Pabble().getY()
                    + "\n Ball velX & velY: " + Game.MainHandler.getBall().velX + ", " + Game.MainHandler.getBall().velY, x, y);
        }else
        {
            g.setColor(new Color(151,217,255));
            Font normalFont = g.getFont();
            g.setFont(g.getFont().deriveFont(g.getFont().getSize() * 9f));
            g.drawString(String.valueOf(score), x, y);
            g.setFont(normalFont);
        }
    }
    public int getScore()
    {
        return score;
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }


}
