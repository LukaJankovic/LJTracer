public class Sphere extends Object {
    public Vector3D center;
    public float radius;

    public Sphere(Vector3D center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public float interceptRay(Ray r) {
        Vector3D oc = r.origin.sub(center);
        float a = r.direction.dot(r.direction);
        float b = 2 * r.direction.dot(oc);
        float c = oc.dot(oc) - radius * radius;
        float disc = b*b - 4*a*c;

        if (disc < 0) {
            return -1;
        } else {
            float t0 = (float) ((-b - Math.sqrt(disc)) / (2*a));
            float t1 = (float) ((-b + Math.sqrt(disc)) / (2*a));
            float t = Math.min(t0, t1);

            return t > 0 ? t : -1;
        }
    }

    @Override
    public Vector3D normalAt(Vector3D p) {
        return p.sub(center).normalize();
    }
}
