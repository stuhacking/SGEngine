package sge.demo.math;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import sge.math.Vector2;

/**
 * Demo/Verify Vector2 Rotation
 */
public class Vec2FollowDemo extends JFrame implements KeyListener, Runnable, MouseMotionListener {

    private Vector2 mousePosition;
    private boolean limit = false;

    private boolean running = false;

    public Vec2FollowDemo () {
        setTitle("Follow Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 640);
        addKeyListener(this);
        addMouseMotionListener(this);

        start();
    }

    @Override
    public void paint (Graphics g) {
        Vector2 v = new Vector2();
        BufferedImage canvas = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) canvas.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (null != mousePosition) {
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(3));
            int x = getWidth() / 2;
            int y = getHeight() / 2;
            v = mousePosition.sub(new Vector2(x, y));
            if (limit) {
                v = v.setLength(100);
            }
            g2.drawLine(x, y, x + (int) v.x, y + (int) v.y);
        }

        g2.setColor(Color.BLACK);
        g2.drawString(v.toString() + " = " + v.getLength(), 10, 40);
        g2.drawString("(Press 'l' to limit magnitude 100)", 10, 60);

        g.drawImage(canvas,
                    0, 0, getWidth(), getHeight(),
                    null);
    }

    // RUNNABLE
    @Override
    public void run () {
        while (running) {
            repaint();

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
        if (e.getKeyChar() == 'l')
            limit = !limit;
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            running = false;
    }

    @Override
    public void mouseDragged (MouseEvent e) {}

    @Override
    public void mouseMoved (MouseEvent e) {
        mousePosition = new Vector2(e.getX(), e.getY());
    }

    public static void main (String[] args) {
        Vec2FollowDemo frame = new Vec2FollowDemo();
        frame.setVisible(true);
    }

    /** Serial Version UID */
    private static final long serialVersionUID = -4167645891409471975L;

}
