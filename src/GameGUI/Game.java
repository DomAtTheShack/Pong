package GameGUI;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;


public class Game extends Canvas implements Runnable {

    private static boolean isRunning = false;
    private static Thread thread;
    public final Handler MainHandler;
    public static byte fps;
    private Handler Paddle1;
    private Handler Paddle2;

    public Game() throws IOException {
        new Window(1000,700,"Pong", this);
        start();
        MainHandler = new Handler();
        Paddle1 = new Handler();
        Paddle2 = new Handler();
        this.addKeyListener(new KeyListener(Paddle2));
        this.addKeyListener(new KeyListener(Paddle1));
        Paddle1.addObject(new Pabble(20, 275, ID.P1Pabble, Paddle1));
        Paddle2.addObject(new Pabble(945, 275, ID.P2Pabble, Paddle2));
        MainHandler.addObject(new Display(475, 10, ID.Display, "N/A"));

    }
    public static void main(String[] args) throws IOException
    {
        new Game();
    }

    @Override
    public void run() {
        this.requestFocus();
        final int targetFps = 60;
        final long nanoSecondInterval = 1_000_000_000 / targetFps;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;

        while(isRunning) {
            long now = System.nanoTime();
            if(now - lastTime > nanoSecondInterval) {
                tick();
                updates++;
                render();
                frames++;
                lastTime = now;
            }

            if(System.currentTimeMillis() - timer > 1000) {
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
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(136,202,252));
        g.fillRect(0, 0, getWidth(), getHeight());
        MainHandler.render(g);
        Paddle2.render(g);
        Paddle1.render(g);

        g.dispose();
        bs.show();
    }
    public void tick()
    {
        MainHandler.tick();
        Paddle2.tick();
        Paddle1.tick();
    }
    private void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    private void stop() throws InterruptedException {
        isRunning = false;
        thread.join();
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