import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    private static boolean isRunning = false;
    private static Thread thread;

    public Game() throws IOException {
        new Window(1000,563,"Pong", this);
        start();
    }
    public static void main(String[] args) throws IOException {
        new Game();
    }

    @Override
    public void run() {
        double TARGET_FPS = 60.0;
        double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            // Only render if we have time
            if (now - lastTime >= TARGET_TIME_BETWEEN_RENDERS) {
                render();
                frames++;
            }
        }
        try {
            stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void tick(){
    }
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        /////////////////////////////////
        //Drawing stuff for the game
        g.setColor(Color.red);
        g.fillRect(0,0,1000,563);

        /////////////////////////////////
        g.dispose();
        bs.show();
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