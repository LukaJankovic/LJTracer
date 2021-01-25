import java.awt.*;

public class Object {

    public Vector3D diffuseColor;
    public boolean light;
    public boolean reflective;

    public float interceptRay(Ray r) {
        return -1;
    }

    public Vector3D normalAt(Vector3D p) {
        return null;
    }
}
