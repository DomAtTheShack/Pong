package GameClasses;

import GameGUI.Game;
import GameGUI.ID;

import java.awt.*;
import java.util.Random;

public class PowerUps extends MovableObject {

    Random poop = new Random();
    private final int initialValue;
    private final int maxValue;
    private boolean changeDirection = true;
    private int durationPU;
    private Color PUColor;
    private static Color speedColor = Color.PINK;
    private static Color largeColor = Color.RED;

    public PowerUps(int x, int y, int width, int length, ID id) {
        super(x, y, width, length, id);
        initialValue = y;
        maxValue = initialValue + 30;
        switch (id)
        {
            case SpedPU:
                PUColor = speedColor;
                break;
            case LargePabblePU:
                PUColor = largeColor;
                break;
            default:
                PUColor = Color.BLACK;
        }
    }

    @Override
    public void tick() {
        synchronized (Game.MainHandler.object) {
            if (changeDirection) {
                // Move downwards until reaching maxValue
                if (y < maxValue) {
                    y++;
                } else {
                    // Change direction when reaching maxValue
                    changeDirection = false;
                }
            } else {
                // Move upwards until reaching initialValue
                if (y > initialValue) {
                    y--;
                } else {
                    // Change direction when reaching initialValue
                    changeDirection = true;
                }
            }

            poop.nextInt(32);
            if (getBounds().intersects(Game.MainHandler.getBall().getBounds())) {
                Game.MainHandler.removeObeject(this);
                switch (id) {
                    case LargePabblePU:
                        durationPU = 600;
                        if (Game.MainHandler.getBall().velX > 0) {
                            MovableObject tempPabble = Game.MainHandler.getP1Pabble();
                            tempPabble.setLength(tempPabble.getLength() + 10);
                        } else {
                            MovableObject tempPabble = Game.MainHandler.getP2Pabble();
                            tempPabble.setLength(tempPabble.getLength() + 10);
                        }
                        if (durationPU != 0) {
                            durationPU--;
                        } else {
                            if (Game.MainHandler.getBall().velX > 0) {
                                MovableObject tempPabble = Game.MainHandler.getP1Pabble();
                                tempPabble.setLength(tempPabble.getLength() - 10);
                            } else {
                                MovableObject tempPabble = Game.MainHandler.getP2Pabble();
                                tempPabble.setLength(tempPabble.getLength() - 10);
                            }
                        }
                        break;
                    case SpedPU:
                        Game.MainHandler.getBall().velX *= 3;
                        break;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {

        g.setColor(PUColor);
        g.drawOval(x, y, width, length);
        g.fillOval(x, y, width, length);
    }
}
