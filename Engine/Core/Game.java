package Engine.Core;

import javax.swing.*;

import Engine.esc.EntityManager;
import Engine.Input.InputHandler;
import Engine.Rendering.Renderer;
import Engine.esc.Scene;

import Engine.Systems.PhysicsSystem;

import java.util.ArrayList;
import java.util.List;

public class Game{
    private static final int WIDTH =1280;
    private static final int HEIGHT =720;
    private static String TITLE;
    private boolean DebugMode;

    private boolean running = false;
    private JFrame frame;
    private Renderer renderer;
    private InputHandler input;

    private PhysicsSystem physicsSystem;

    private List<Scene> Scenes;
    private int CurScene = 0;

    public Game(String TITLE, boolean mode){
        this.TITLE = TITLE;
        DebugMode = mode;

        initializeWindow();
        initializeGame();
    }

    public Game(String TITLE){
        this.TITLE = TITLE;
        DebugMode = false;

        initializeWindow();
        initializeGame();
    }

    private void initializeWindow(){
        input = InputHandler.get();

        frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        renderer = Renderer.get();
        renderer.init(WIDTH, HEIGHT);

        renderer.addKeyListener(input);
        renderer.addMouseListener(input);
        renderer.addMouseMotionListener(input);

        frame.add(renderer);
        frame.pack();
        frame.setVisible(true);

        renderer.requestFocus();
    }

    private void initializeGame(){
        Scenes = new ArrayList<>();
    }

    public void Start(){
        if(running) return;
        running = true;

        runGameLoop();
    }

    private void runGameLoop(){
        long lastTime = System.nanoTime();
        double nsPerUpdate = 1_000_000_000.0 / 60.0;
        double delta = 0;

        while(running){
            if(Scenes.isEmpty())
                    throw new IllegalStateException("Нет ни одной игровой сцены");

            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerUpdate;
            lastTime = now;

            while(delta >= 1){
                if(DebugMode) Scenes.get(CurScene).debugUpdate(1.0f / 60.0f);
                else Scenes.get(CurScene).update(1.0f / 60.0f);

                delta--;
            }

            try {
                Thread.sleep(2);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void AddScene(Scene scene){
        Scenes.add(scene);
    }
}
