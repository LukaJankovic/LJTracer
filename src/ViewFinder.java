import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.stream.IntStream;

public class ViewFinder extends JPanel implements KeyListener {
    private BufferedImage canvas;
    private Vector3D origin;
    private Vector3D target;
    private float xAngle;
    private float zAngle;
    private Vector<Object> objects;
    private int maxDepth = 1;
    private int samples = 1;
    private float brightness = 1;

    public ViewFinder(Vector3D origin, Vector3D target, Vector<Object> objects) {
        this.origin = origin;
        this.target = target;
        this.objects = objects;
    }

    private Vector3D traceRay(Ray r, int depth) {
        float dist = -1;
        Object closest = null;
        for (Object o : objects) {
            float d = o.interceptRay(r);
            if (dist == -1 || (d > -1 && d < dist)) {
                dist = d;
                closest = o;
            }
        }

        if (dist > -1) {
            if (closest.light) {
                return closest.diffuseColor;
            }
            if (depth != maxDepth) {
                Vector3D norig = r.extend(dist);
                if (closest.reflective) {
                    Ray nray = new Ray(norig, closest.normalAt(norig));
                    return traceRay(nray, depth + 1);
                } else {
                    Vector3D target = norig.add(closest.normalAt(norig)).add(Vector3D.randomVec());
                    Ray nray = new Ray(norig, target.sub(norig));
                    return traceRay(nray, depth + 1).mul(closest.diffuseColor).mul(brightness);
                }
            }
        }

        return new Vector3D(0, 0, 0);
    }

    public void refreshCanvas() {
        Graphics2D g = canvas.createGraphics();
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                float x = ((float)i / getWidth()) - 0.5F;
                float y = 1 - ((float)j / getHeight()) - 0.5F;

                Vector3D color = new Vector3D(0.F, 0.F, 0.F);

                for (int k = 0; k < samples; k++) {
                    Ray r = new Ray(origin, target.add(new Vector3D(x, y, 0)));
                    Vector3D sample = traceRay(r, 0);
                    color = color.add(sample);
                }

                color = color.div(samples);

                Color c = new Color(clamp((float)Math.sqrt(color.x),0, 1), clamp((float)Math.sqrt(color.y), 0, 1), clamp((float)Math.sqrt(color.z), 0, 1));
                //Color c = new Color(clamp(color.x,0, 1), clamp(color.y, 0, 1), clamp(color.z, 0, 1));
                canvas.setRGB(i, j, c.getRGB());
            }
        }
    }

    public float clamp(float t, float l, float h) {
        return Math.max(l, Math.min(h, t));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        canvas = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        refreshCanvas();
        g.drawImage(this.canvas, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> origin.z += 0.1F;
            case KeyEvent.VK_DOWN -> origin.z -= 0.1F;
            case KeyEvent.VK_RIGHT -> origin.x += 0.1F;
            case KeyEvent.VK_LEFT -> origin.x -= 0.1F;
            case KeyEvent.VK_W -> zAngle += 0.1F;
            case KeyEvent.VK_S -> zAngle -= 0.1F;
            case KeyEvent.VK_D -> xAngle += 0.1F;
            case KeyEvent.VK_A -> xAngle -= 0.1F;
            case KeyEvent.VK_R -> origin.y += 0.1F;
            case KeyEvent.VK_F -> origin.y -= 0.1F;
            case KeyEvent.VK_M -> maxDepth += 1;
            case KeyEvent.VK_N -> maxDepth -= 1;
            case KeyEvent.VK_X -> samples += 1;
            case KeyEvent.VK_Z -> samples -= 1;
            case KeyEvent.VK_B -> brightness += 0.1F;
            case KeyEvent.VK_V -> brightness -= 0.1F;
            case KeyEvent.VK_ESCAPE -> System.exit(0);
        }

        target.x = (float) Math.sin(xAngle);
        target.y = (float) Math.sin(zAngle);

        maxDepth = Math.max(maxDepth, 0);
        samples = Math.max(samples, 1);

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
