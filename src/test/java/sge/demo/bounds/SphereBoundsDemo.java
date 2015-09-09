package sge.demo.bounds;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import sge.bounds.Sphere;
import sge.math.Vector3;

/**
 * Demo/Verify Vector2 Rotation
 */
public class SphereBoundsDemo extends JFrame implements KeyListener, Runnable, MouseListener {

    private Sphere sphere;
    private Sphere[] collisionSpheres = new Sphere[4];

    private boolean running = false;

    public SphereBoundsDemo () {
        setTitle("Sphere Boundaries Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 640);
        addKeyListener(this);
        addMouseListener(this);

        sphere = new Sphere(new Vector3(getWidth() / 2, getHeight() / 2, 0f), 10.0f);
        collisionSpheres[0] = new Sphere(new Vector3(getWidth() / 4, getHeight() / 4, 0f), 20.0f);
        collisionSpheres[1] = new Sphere(new Vector3(getWidth() * 3 / 4, getHeight() / 4, 0f), 20.0f);
        collisionSpheres[2] = new Sphere(new Vector3(getWidth() / 4, getHeight() * 3 / 4, 0f), 20.0f);
        collisionSpheres[3] = new Sphere(new Vector3(getWidth() * 3 / 4, getHeight() * 3 / 4, 0f), 20.0f);
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

        int rad = (int) sphere.getRadius();
        Vector3 origin = sphere.getOrigin();
        int x = (int) (origin.x - rad);
        int y = (int) (origin.y - rad);

        g2.drawOval(x, y, rad * 2, rad * 2);

        // Draw the four corner spheres, and color them according to how they overlap
        // our control sphere.
        for (Sphere s : collisionSpheres) {
            g2.setColor(Color.GREEN);
            g2.setStroke(new BasicStroke(3));

            rad = (int) s.getRadius();
            origin = s.getOrigin();
            x = (int) (origin.x - rad);
            y = (int) (origin.y - rad);

            if (sphere.intersects(s))
                g2.setColor(Color.MAGENTA);

            if (sphere.contains(s))
                g2.setColor(Color.RED);

            g2.drawOval(x, y, rad * 2, rad * 2);
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
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            running = false;
    }

    public static void main (String[] args) {
        SphereBoundsDemo frame = new SphereBoundsDemo();
        frame.setVisible(true);
    }

    /** Serial Version UID */
    private static final long serialVersionUID = -4167645891409471975L;

    @Override
    public void mouseClicked (MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            sphere.add(new Vector3(e.getX(), e.getY(), 0f));
        if (e.getButton() == MouseEvent.BUTTON3)
            sphere.setOrigin(new Vector3(e.getX(), e.getY(), 0f));
    }

    @Override
    public void mousePressed (MouseEvent e) {}

    @Override
    public void mouseReleased (MouseEvent e) {}

    @Override
    public void mouseEntered (MouseEvent e) {}

    @Override
    public void mouseExited (MouseEvent e) {}

}
