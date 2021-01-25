public class Plane extends Object {
    Vector3D p;
    Vector3D normal;

    public Plane(Vector3D p, Vector3D normal) {
        this.p = p;
        this.normal = normal;
    }

    @Override
    public float interceptRay (Ray r) {

        float denom = r.direction.dot(normal);

        if (denom == 0) {
            return -1;
        }

        float t = (p.sub(r.origin)).dot(normal) / denom;

        return t > 0 ? t : -1;
    }

    @Override
    public Vector3D normalAt(Vector3D p) {
        return normal;
    }
}
