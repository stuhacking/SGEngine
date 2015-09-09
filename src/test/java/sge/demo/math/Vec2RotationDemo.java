package sge.demo.math;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import sge.math.Vector2;

/**
 * Demo/Verify Vector2 Rotation
 */
public class Vec2RotationDemo extends JFrame implements KeyListener, Runnable {

    private Vector2 vector;
    private float rotSpeed = 5f;

    private boolean running = false;

    public Vec2RotationDemo () {
        setTitle("Rotation Demo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 640);
        addKeyListener(this);

        vector = Vector2.random();

        start();
    }

    @Override
    public void paint (Graphics g) {
        BufferedImage canvas = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) canvas.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(3));
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        Vector2 v = vector.scale(200);
        g2.drawLine(x, y, (int) (x + v.x), (int) (x + v.y));

        g2.setColor(Color.BLACK);
        g2.drawString(vector.toString() + " = " + vector.getLength(), 10, 40);

        g.drawImage(canvas, 0, 0, getWidth(), getHeight(), null);
    }

    // RUNNABLE
    @Override
    public void run () {
        while (running) {
            repaint();

            vector = vector.rotate(rotSpeed);

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
            }
        }

        System.exit(0);
    }

    public void start () {
        if (!running) {
            running = true;
            new Thread(this).start();
        }

    }

    public void stop () {
        if (running) {
            running = false;
        }
    }

    // KEYLISTENER

    @Override
    public void keyTyped (KeyEvent e) {}

    @Override
    public void keyPressed (KeyEvent e) {}

    @Override
    public void keyReleased (KeyEvent e) {
        if (e.getKeyChar() == 'r')
            vector = Vector2.random();
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            rotSpeed -= 1f;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            rotSpeed += 1f;
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            running = false;
    }

    public static void main (String[] args) {
        Vec2RotationDemo frame = new Vec2RotationDemo();
        frame.setVisible(true);
    }

    /** Serial Version UID */
    private static final long serialVersionUID = 6871297657110514582L;
}
