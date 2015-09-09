package sge.demo.math;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import sge.math.FMath;

/**
 * Demo/Verify different Interpolation types.
 */
public class InterpolationDemo extends JFrame implements KeyListener {

    private enum InterpolationType {LINEAR, COSINE}

    private InterpolationType iType = InterpolationType.LINEAR;

    public InterpolationDemo () {
        setTitle("Interpolation Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        addKeyListener(this);
    }

    @Override
    public void paint (Graphics g) {
        for (int i = 0, max = getWidth(); i < max; i++) {
            float ratio = FMath.toRatio(i, 0, getWidth());
            int interp = 0;
            switch (iType) {
                case LINEAR:
                    interp = (int) FMath.lerp(0, 255, ratio);
                    break;
                case COSINE:
                    interp = (int) FMath.cosInterpolate(0, 255, ratio);
                    break;
                default:
                    break;
            }

            g.setColor(new Color(interp, interp, interp));
            g.drawLine(i, 0, i, getHeight());
        }
        g.setColor(Color.BLUE);
        g.drawString(iType.name(), 10, 40);
    }

    @Override
    public void keyTyped (KeyEvent e) {
        if (e.getKeyChar() == 'l')
            iType = InterpolationType.LINEAR;
        else if (e.getKeyChar() == 'c')
            iType = InterpolationType.COSINE;
        else if (e.getKeyChar() == KeyEvent.VK_ESCAPE)
            System.exit(0);

        repaint();
    }

    @Override
    public void keyPressed (KeyEvent e) {}

    @Override
    public void keyReleased (KeyEvent e) {}

    public static void main (String[] args) {
        InterpolationDemo frame = new InterpolationDemo();
        frame.setVisible(true);
    }

    /** Serial Version UID */
    private static final long serialVersionUID = -3820586961926233344L;
}
