import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class MainWindow {
    public static void main(String[] args) {

        Vector<Object> objects = new Vector<Object>();
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            Sphere s = new Sphere(new Vector3D((rand.nextFloat() * 4) - 2F,(rand.nextFloat() * 4) - 2F, (rand.nextFloat() * 4)), rand.nextFloat());
            s.diffuseColor = new Vector3D(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            //s.light = rand.nextBoolean();
            s.reflective = rand.nextBoolean();
            objects.add(s);
        }

        Plane p = new Plane(new Vector3D(0, -3, 0), new Vector3D(0, 1, 0));
        p.diffuseColor = new Vector3D(1, 1, 1);
        objects.add(p);

        Plane p2 = new Plane(new Vector3D(0, 0, 5), new Vector3D(0, 0, -1));
        p2.diffuseColor = new Vector3D(0, 0, 1);
        //p2.reflective = true;
        objects.add(p2);

        Plane p3 = new Plane(new Vector3D(0, 5, 0), new Vector3D(0, -1, 0));
        p3.diffuseColor = new Vector3D(1, 1, 1);
        //p3.light = true;
        objects.add(p3);

        Plane p4 = new Plane(new Vector3D(3, 0, 0), new Vector3D(-1, 0, 0));
        p4.reflective = true;
        objects.add(p4);

        Box b = new Box(new Vector3D(0, 3, 0), new Vector3D(0, -1, 0), new Vector3D(-2, 4, 2), new Vector3D(2, 4, 2), new Vector3D(-2, 4, 3), new Vector3D(2, 4, 3), Box.Axis.Z);
        b.diffuseColor = new Vector3D(1, 1, 1);
        b.light = true;
        objects.add(b);

        ViewFinder vf = new ViewFinder(new Vector3D(0.F, 0.F, 0.F), new Vector3D(0, 0, 1), objects) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension (1200, 1200);
            }
        };

        vf.addKeyListener(vf);
        vf.setFocusable(true);

        JFrame frame = new JFrame("Ray Tracer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(vf);
        frame.pack();
        frame.setVisible(true);
    }
}
