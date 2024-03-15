package GameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;


public class Game extends Canvas implements Runnable {

    private static boolean isRunning = false;
    private static Thread thread;
    public static Handler MainHandler = new Handler();
    public static byte fps;
    private Handler Paddle1;
    private Handler Paddle2;
    private Random location = new Random();

    private Random time = new Random();

    private long randoNum = 0;


    public static int frames = 0;
    public static int updates = 0;

    public Game() throws IOException {

        new Window(1000, 700, "Pong", this);
        start();
        Paddle1 = new Handler();
        Paddle2 = new Handler();
        MainHandler.addHandler(Paddle1);
        MainHandler.addHandler(Paddle2);
        this.addKeyListener(new KeyListener(Paddle2));
        this.addKeyListener(new KeyListener(Paddle1));
        Paddle1.addObject(new Pabble(30, 275, 20, 90, ID.P1Pabble, Paddle1));
        Paddle2.addObject(new Pabble(935, 275, 20, 90, ID.P2Pabble, Paddle2));
        MainHandler.addObject(new Display(475, 10, ID.Display, "N/A"));
        MainHandler.addObject(new Display(395, 130, ID.P1Score, "0"));
        MainHandler.addObject(new Display(555, 130, ID.P2Score, "0"));
        MainHandler.addObject(new Ball(100, 100, 20, 20, ID.Ball));
    }

    public static void main(String[] args) throws IOException {
        boolean InMenu = true;
        do {
            JFrame frame = new JFrame("Menu");
            frame.setSize(600, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }while (InMenu);
        new Game();
    }

    @Override
    public void run() {
        this.requestFocus();
        final int targetFps = 60;
        final long nanoSecondInterval = 1_000_000_000 / targetFps;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();


        while (isRunning) {
            long now = System.nanoTime();
            if (now - lastTime > nanoSecondInterval) {
                try {
                    tick();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                updates++;
                render();
                frames++;
                lastTime = now;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                fps = (byte) frames;
                frames = 0;
                updates = 0;
            }
        }

        try {
            stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(136, 202, 252));
        g.fillRect(0, 0, getWidth(), getHeight());
        MainHandler.render(g);
        Paddle2.render(g);
        Paddle1.render(g);

        g.dispose();
        bs.show();
    }

    public void tick() throws InterruptedException {
        if(Game.MainHandler.getP1Score().score == 10)
        {
            GameWin(true);
        }
        if(Game.MainHandler.getP2Score().score == 10)
        {
            GameWin(false);
        }
        randoNum++;
        if (randoNum >= PowerUp()) {
            MainHandler.addObject(new PowerUps(location.nextInt(350) + 320,
                    location.nextInt(400) + 150,
                    20, 20,
                    ID.SpedPU));
            randoNum = 0;
            PowerUp();
        }
        MainHandler.tick();
        Paddle2.tick();
        Paddle1.tick();
    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private static void stop() throws InterruptedException
    {
        isRunning = false;
        thread.join();
    }


    private int PowerUp()
    {

        return time.nextInt(250) + 300;

    }

    private static void GameWin(boolean P1Win) throws InterruptedException {
        JFrame frame = new JFrame("Winner");
        frame.setSize(300, 200); // Set width and height as needed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(P1Win)
        {
            JOptionPane.showMessageDialog(frame,"Player 1 wins the game with a lead of " + (MainHandler.getP1Score().score - MainHandler.getP2Score().score)  + " points!");
        }else {
            JOptionPane.showMessageDialog(frame,"Player 2 wins the game with a lead of " + (MainHandler.getP2Score().score - MainHandler.getP1Score().score)  + " points!");
        }
        System.exit(0);
        stop();
    }
}


/*
This function is typically found in games or graphics-intensive programs written in Java, and it's used to render (or draw) graphics onto the screen using something called "double-buffering" or in this case "triple buffering", hence the '3' in createBufferStrategy(3). Buffering is used to prevent screen tearing, an issue where multiple frames are shown in a single screen draw.
Here's what each line of your function does:
BufferStrategy bs = this.getBufferStrategy(); - The getBufferStrategy method retrieves the BufferStrategy that has been created for this component. It's used for buffering graphics that will be drawn onto the screen.
if(bs == null) {...} - This block checks if a BufferStrategy has been created, and if not, it creates one using this.createBufferStrategy(3);. The '3' argument stands for "triple buffering", which is a specific kind of buffering strategy.
Graphics g = bs.getDrawGraphics(); - This retrieves a Graphics object that can be used for drawing to the screen. Any drawing done using this Graphics object is done to one of the buffers in the BufferStrategy.
g.setColor(new Color(136,202,252)); - This sets the current draw color to a light blue color (you configure the RBG values to get different colors).
g.fillRect(0, 0, getWidth(), getHeight()); - This fills the entire screen with the current draw color, effectively 'clearing' the screen before new graphics are drawn.
handler.render(g); - This line assumes there's a handler object in your program that manages what gets displayed on screen. It's responsible for rendering (or drawing) individual components to the screen.
g.dispose(); - This disposes the graphics context and releases any system resources that it is using.
bs.show(); - This makes the next available buffer visible. It's also part of triple-buffering.
So in essence, this function prepares the screen, 'clears' the display, delegates the rendering of components to the handler object, and then disposes of the Graphics context and makes the buffer visible.
 */