package GameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private static boolean InMenu = true;

    public static int frames = 0;
    public static int updates = 0;

    public Game() {

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

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length >= 1 && args[0].equals("--usemenu")) {
            createMenu();
            do {
            //Nothing
            } while (InMenu);
            Thread.sleep(100);
        }
        new Game();
    }

    private static void createMenu()
    {
        // Create JFrame and set properties
        JFrame frame = new JFrame("Menu");
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Color ButtonColor = new Color(254,249,217);
        Color BackroundColor = new Color(226,121,59);

        // Create buttons for main menu
        JButton startButton = new JButton("Start");
        startButton.setBounds(50, 50, 100, 30); // Adjusted position and size
        startButton.setBackground(ButtonColor); // Set background color
        startButton.setOpaque(true); // Make button opaque

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(50, 150, 100, 30); // Adjusted position and size
        exitButton.setBackground(ButtonColor); // Set background color
        exitButton.setOpaque(true); // Make button opaque

        JButton settingsButton = new JButton("Settings");
        settingsButton.setBounds(50, 100, 100, 30); // Adjusted position and size
        settingsButton.setBackground(ButtonColor); // Set background color
        settingsButton.setOpaque(true); // Make button opaque

        // Create buttons for settings menu
        JButton backButton = new JButton("Back");
        backButton.setBounds(5, 5, 75, 30); // Adjusted position and size
        backButton.setBackground(ButtonColor); // Set background color
        backButton.setOpaque(true); // Make button opaque

        // Create JPanel for main menu
        JPanel mainPanel = new JPanel(null); // Set layout to null for absolute positioning
        mainPanel.setLayout(null); // Set layout to null for absolute positioning
        mainPanel.setBackground(BackroundColor); // Add background color for visualization
        mainPanel.setBounds(0, 0, 600, 300); // Adjusted size to cover the frame
        mainPanel.add(startButton);
        mainPanel.add(exitButton);
        mainPanel.add(settingsButton);

        // Create JPanel for settings menu
        JPanel settingsPanel = new JPanel(null); // Set layout to null for absolute positioning
        settingsPanel.setLayout(null); // Set layout to null for absolute positioning
        settingsPanel.setBackground(BackroundColor); // Add background color for visualization
        settingsPanel.setBounds(0, 0, 600, 300); // Adjusted size to cover the frame
        settingsPanel.add(backButton);

        ImageIcon icon = new ImageIcon(Game.class.getClassLoader().getResource("test.gif"));
        JLabel label = new JLabel(icon);
        label.setBounds(495,158,icon.getIconWidth(),icon.getIconHeight());
        settingsPanel.add(label);

        // Create CardLayout and add panels to it
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        cardPanel.add(mainPanel, "Main");
        cardPanel.add(settingsPanel, "Settings");

        // Add ActionListener to settingsButton to switch to settingsPanel
        settingsButton.addActionListener(e -> cardLayout.show(cardPanel, "Settings"));

        // Add ActionListener to backButton to switch back to mainPanel
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Main"));

        exitButton.addActionListener(e -> System.exit(0));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                InMenu = false;
            }
        });

        // Add cardPanel to the frame
        frame.add(cardPanel);

        // Make the JFrame visible
        frame.setVisible(true);
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