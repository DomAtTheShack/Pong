package GameGUI;

import java.awt.*;
import java.util.Random;

public class Ball extends GameObject
{
    private final Random BallDevation = new Random();

    private final float MIN = 0.3f;
    private final float MAX = 4.7f;

    private float randomSlope = (float) MIN + BallDevation.nextFloat() * (MAX - MIN);
    private Rectangle PrevP1;
    private Rectangle PrevP2;
    private Rectangle PrevBall;



    int score2;
    //    Display score = new Display(485, 640, )
    public Ball(int x, int y, ID id)
    {
        super(x, y, id);
        velX = 5;
        velY = 5;
    }

    @Override
    public void tick()
    {
        x += velX;
        y += velY;
        BallDevation.nextInt(6);

        handleWallCollisions();

        GameObject P1 = Game.MainHandler.getP1Pabble();
        Rectangle P1R = P1.getBounds();
        GameObject P2 = Game.MainHandler.getP2Pabble();
        Rectangle P2R = P2.getBounds();
        Rectangle BALL = getBounds();

        handlePabbleCollision(P1, P1R, BALL);
        handlePabbleCollision(P2, P2R, BALL);
    }

    private void handlePabbleCollision(GameObject paddle, Rectangle paddleR, Rectangle ballR) {
        boolean hit = false;
        if (paddleR.intersects(ballR)) {
            if (ballR.y <= paddleR.y + paddleR.height && ballR.y + ballR.height >= paddleR.y + paddleR.height) {
                // This is a top collision
                y -= 3;
                velY = 2;
                double randomizeAMT = BallDevation.nextInt(125) + 75;
                randomizeAMT *= .01;
                randomizeAMT = velY * randomizeAMT;
                int randomizeINT = (int) randomizeAMT;
                velY += -randomizeINT;


                hit = true;
            } else if (ballR.y <= paddleR.y && ballR.y + ballR.height >= paddleR.y) {
                // This is a bottom collision
                y += 3;
                velY = 2;
                double randomizeAMT = BallDevation.nextInt(75) + 125;
                randomizeAMT *= .01;
                randomizeAMT = velY * randomizeAMT;
                int randomizeINT = (int) randomizeAMT;
                velY *= -randomizeINT;
                hit = true;
            } else if (ballR.x + ballR.width >= paddleR.x && ballR.x <= paddleR.x) {
                // This is a left side collision
                x -= 3;
                velX = 2;
                double randomizeAMT = BallDevation.nextInt(75) + 125;
                randomizeAMT *= .01;
                randomizeAMT = velX * randomizeAMT;
                int randomizeINT = (int) randomizeAMT;
                velX *= -randomizeINT;
                hit = true;
            } else if (ballR.x <= paddleR.x + paddleR.width && ballR.x + ballR.width >= paddleR.x + paddleR.width) {
                // This is a right side collision
                x++;
                velX = 2;
                double randomizeAMT = BallDevation.nextInt(75) + 125;
                randomizeAMT *= .01;
                randomizeAMT = velX * randomizeAMT;
                int randomizeINT = (int) randomizeAMT;
                velX *= -randomizeINT;
                hit = true;
            }
            relocateBall(paddle, paddleR, ballR, hit);
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
            x = 500;
            y = 300;
            velX *= -1;
            velY *= -1;
        } else if(x >= 970) {
            Game.MainHandler.getP1Score().incrementScore();
            x = 500;
            y = 300;
            velX *= -1;
            velY *= -1;
        }
    }
    private void relocateBall(GameObject paddle, Rectangle paddleR, Rectangle ballR, boolean hit) {
        if(hit) {
            if (ballR.y <= paddleR.y + paddleR.height && ballR.y + ballR.height >= paddleR.y + paddleR.height) {
                // Top collision
                y = paddleR.y + paddleR.height;
                velX *= -1;
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

