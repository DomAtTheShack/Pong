package GameGUI;

import java.awt.*;
import java.util.Random;


public class Ball extends MovableObject
{
    private final Random BallDevation = new Random();
    int randomNum = BallDevation.nextInt(6) + 1;




    private Rectangle PrevP1;
    private Rectangle PrevP2;
    private Rectangle PrevBall;

    private static final int bound = 6;
    private static final int velXCap = 12;
    private static final int velYCap = 8;
    private static final int velYMinCap = 8;
    private  static int waitForBall = 100;
    private boolean waitFor = false;
    private boolean p2Lost;


    public Ball(int x, int y, int width, int length, ID id)
    {
        super(x, y,width, length, id);
        velX = 7;
        velY = 7;
    }

    @Override
    public void tick() throws InterruptedException {
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
                y--;
                velY *= -1;
                hit = true;
            } else if (ballR.y <= paddleR.y && ballR.y + ballR.height >= paddleR.y) {
                // This is a bottom collision
                y++;
                velY *= -1;
                hit = true;
            } else if (ballR.x + ballR.width >= paddleR.x && ballR.x <= paddleR.x) {
                // This is a left side collision
                x--;
                velX *= -1;
                hit = true;
            } else if (ballR.x <= paddleR.x + paddleR.width && ballR.x + ballR.width >= paddleR.x + paddleR.width) {
                // This is a right side collision
                x++;
                velX *= -1;
                hit = true;
            }
            relocateBall(paddle, paddleR, ballR, hit);
        }
    }
    private void handleWallCollisions() throws InterruptedException {
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
            velX = 5;
            velY = 5;


        } else if(x >= 970) {
            Game.MainHandler.getP1Score().incrementScore();
            x = 500;
            y = 300;
            velX = -5;
            velY = -5;

        }
    }
    private void relocateBall(GameObject paddle, Rectangle paddleR, Rectangle ballR, boolean hit) {
        if(hit) {
            if (ballR.y <= paddleR.y + paddleR.height && ballR.y + ballR.height >= paddleR.y + paddleR.height) {
                // Top collision
                y = paddleR.y + paddleR.height;
                velY += BallDevation.nextInt(bound) + 1; // Add deviation to velY
            } else if (ballR.y <= paddleR.y && ballR.y + ballR.height >= paddleR.y) {
                // Bottom collision
                y = paddleR.y - ballR.height;
                velY += -(BallDevation.nextInt(bound) + 1); // Add deviation to velY (negative since it's moving upwards)
            } else if (ballR.x + ballR.width >= paddleR.x && ballR.x <= paddleR.x) {
                // Left side collision
                x = paddleR.x - ballR.width;
                velX += -(BallDevation.nextInt(bound) + 3); // Add deviation to velX (negative since it's moving left)
            } else if (ballR.x <= paddleR.x + paddleR.width && ballR.x + ballR.width >= paddleR.x + paddleR.width) {
                // Right side collision
                x = paddleR.x + paddleR.width;
                velX += BallDevation.nextInt(bound) + 3; // Add deviation to velX
            }
            int tempSpeed = BallDevation.nextInt(2) + 1;
            boolean slow = BallDevation.nextInt(10) > 6;
            int directionFactor;
            if (velX < 0) {
                directionFactor = -1;
            } else {
                directionFactor = 1;
            }
            if (slow) {
                velX -= directionFactor * tempSpeed;
                velY -= directionFactor * tempSpeed;

            } else {
                velX += directionFactor * tempSpeed;
                velY += directionFactor * tempSpeed;
            }
            if(velX > velXCap)
            {
                velX = velXCap;
            }else{
                velX = -velXCap;
            }
            if(velY > velYCap)
            {
                velY = velYCap;
            }else{
                velY = -velYCap;
            }

        }
    }

    @Override
    public void render(Graphics g) {

        g.drawOval(x, y, width, length);
        g.setColor(new Color(64,64,102));
        g.fillOval(x,y,width,length);

    }

}

