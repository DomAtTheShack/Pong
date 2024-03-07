package GameGUI;

import java.awt.*;
import java.util.Random;

public class Ball extends GameObject
{
    private final Random BallDevation = new Random();

    private Rectangle PrevP1;
    private Rectangle PrevP2;
    private Rectangle PrevBall;
    int score1;

    int score2;
//    Display score = new Display(485, 640, )
    public Ball(int x, int y, ID id)
    {
        super(x, y, id);
        velX = 5;
        velY = 5;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        handleWallCollisions();

    }

    private void handlePaddleCollision(GameObject paddle, Rectangle paddleR, Rectangle ballR) {
        if (paddleR.intersects(ballR)) {
            if (ballR.y <= paddleR.y + paddleR.height && ballR.y + ballR.height >= paddleR.y + paddleR.height) {
                // This is a top collision
                y--;
                velY *= -1;
            } else if (ballR.y <= paddleR.y && ballR.y + ballR.height >= paddleR.y) {
                // This is a bottom collision
                y++;
                velY *= -1;
            } else if (ballR.x + ballR.width >= paddleR.x && ballR.x <= paddleR.x) {
                // This is a left side collision
                x--;
                velX *= -1;
            } else if (ballR.x <= paddleR.x + paddleR.width && ballR.x + ballR.width >= paddleR.x + paddleR.width) {
                // This is a right side collision
                x++;
                velX *= -1;
            }
            relocateBall(paddle, paddleR, ballR);
        }
    }
    private void handleWallCollisions() {
        if (y >= 645) {
            y --;
            velY *= -1;
        } else if(y<=0) {
            y++;
            velY *= -1;
        }
        if(x <= 0) {
            Game.MainHandler.getP2Score().incrementScore();
        } else if(x >= 970) {
            Game.MainHandler.getP1Score().incrementScore();
        }
    }
    private void relocateBall(GameObject paddle, Rectangle paddleR, Rectangle ballR) {
        if (ballR.y <= paddleR.y + paddleR.height && ballR.y + ballR.height >= paddleR.y + paddleR.height) {
            // Top collision
            y = paddleR.y + paddleR.height;
        } else if (ballR.y <= paddleR.y && ballR.y + ballR.height >= paddleR.y) {
            // Bottom collision
            y = paddleR.y - ballR.height;
        } else if (ballR.x + ballR.width >= paddleR.x && ballR.x <= paddleR.x) {
            // Left side collision
            x = paddleR.x - ballR.width;
        } else if (ballR.x <= paddleR.x + paddleR.width && ballR.x + ballR.width >= paddleR.x + paddleR.width) {
            // Right side collision
            x = paddleR.x + paddleR.width;
        }
    }

    @Override
    public void render(Graphics g) {

        g.drawOval(x, y, 20, 20);
        g.setColor(new Color(64,64,102));
        g.fillOval(x,y,20,20);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y, 20,20);
    }
}

