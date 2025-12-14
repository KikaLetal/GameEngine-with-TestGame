package Engine.Rendering;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import Engine.Components.BoxCollider;
import Engine.Rendering.RenderCommand;

public class Renderer extends JPanel {
    private static final Renderer instance = new Renderer();

    private List<RenderCommand> renderQueue = new ArrayList<>();

    private final Object lock = new Object();

    private int width;
    private int height;

    private Renderer() {}

    public static Renderer get() {
        return instance;
    }

    public void init(int width, int height){
        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(width, height));
    }

    public void render() {
        synchronized (lock) {
            renderQueue.sort(Comparator.comparingInt(RenderCommand::getLayer));
        }
        repaint();
    }

    private void drawAllCommands(Graphics graphics) {
        List<RenderCommand> commandsToRender;

        synchronized (lock) {
            commandsToRender  = new ArrayList<>(renderQueue);
        }

        for (RenderCommand cmd : commandsToRender){
            if(cmd.getTexture() != null){
                graphics.drawImage(cmd.getImage(),
                        (int)cmd.getTransform().getPosition().x,
                        (int)cmd.getTransform().getPosition().y,
                        null
                );
            }
            else{
                graphics.setColor(Color.MAGENTA);
                graphics.fillRect((int)cmd.getTransform().getPosition().x,
                                  (int)cmd.getTransform().getPosition().y,
                                  (int)cmd.getSprite().width, (int)cmd.getSprite().height);
            }

            if(cmd.isDebug()){
                graphics.setColor(Color.RED);
                BoxCollider bc = cmd.getBoxCollider();

                if(bc != null) {
                    graphics.drawRect((int) bc.getPosition().x,
                            (int) bc.getPosition().y,
                            (int) bc.getSize().x, (int) bc.getSize().y);
                }
            }
        }
    }

    private void clearScreen(Graphics graphics) {
        graphics.setColor(new Color(30, 25, 20));
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    public void enqueueWorld(RenderCommand cmd) {
        synchronized (lock) {
            renderQueue.add(cmd);
        }
    }

    public void clearRenderQueue() {
        synchronized (lock) {
            renderQueue.clear();
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        clearScreen(g);
        drawAllCommands(g);

        clearRenderQueue();
    }

    public int getBufferWidth() { return width; }
    public int getBufferHeight() { return height; }
}
