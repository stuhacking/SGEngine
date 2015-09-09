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
public class Vec2MirrorDemo extends JFrame implements KeyListener, Runnable, MouseMotionListener {

    private Vector2 mousePosition;
    private Vector2 ray;

    private boolean running = false;

    public Vec2MirrorDemo () {
        setTitle("Mirror Demo");
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
            int x = getWidth() / 2;
            int y = getHeight() / 2;

            g2.setColor(Color.ORANGE);
            g2.setStroke(new BasicStroke(2));
            v = mousePosition.sub(new Vector2(x, y)).scale(1000.0f);

            g2.drawLine(x, y, x + (int) v.x, y + (int) v.y);
            g2.drawLine(x, y, x - (int) v.x, y - (int) v.y);

            Vector2 N = v.normalize().rotate(-90.0f);
            Vector2 N_ = N.scale(20.0f);
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(1));
            g2.drawLine(x, y, x + (int) N_.x, y + (int) N_.y);

            if (null != ray) {
                Vector2 incident = ray.sub(new Vector2(x, y));

                g2.setColor(Color.BLUE);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(x, y, x + (int) incident.x, y + (int) incident.y);

                Vector2 reflection = incident.mirror(N);

                g2.setColor(Color.PINK);
                g2.drawLine(x, y, x + (int) reflection.x, y + (int) reflection.y);
            }

            g2.setColor(Color.BLACK);
            g2.drawString("Normal = " + N.toString(), 10, 40);
        }

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
        if (e.getKeyCode() == KeyEvent.VK_S)
            ray = mousePosition;
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            running = false;
    }

    @Override
    public void mouseDragged (MouseEvent e) {}

    @Override
    public void mouseMoved (MouseEvent e) {
        mousePosition = new Vector2(e.getX(), e.getY());
    }

    public static void main (String[] args) {
        Vec2MirrorDemo frame = new Vec2MirrorDemo();
        frame.setVisible(true);
    }

    /** Serial Version UID */
    private static final long serialVersionUID = -4167645891409471975L;

}
