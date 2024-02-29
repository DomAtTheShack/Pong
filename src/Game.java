import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    private static boolean isRunning = false;
    private static Thread thread;
    private Handler handler;

    public Game() throws IOException {
        new Window(1000,700,"Pong", this);
        start();
        handler = new Handler();

    }
    public static void main(String[] args) throws IOException {
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
                System.out.println("Ticks: " + updates + ", FPS:" + frames);
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
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.dispose();
        bs.show();
    }
    public void tick(){
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