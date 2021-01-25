public class Box extends Object {
    enum Axis {
        X, Y, Z
    }

    public Vector3D p;
    public Vector3D normal;
    public Vector3D ll;
    public Vector3D lr;
    public Vector3D ul;
    public Vector3D ur;
    public Axis axis;

    public Box(Vector3D p, Vector3D normal, Vector3D ll, Vector3D lr, Vector3D tl, Vector3D tr, Axis axis) {
        this.p = p;
        this.normal = normal;
        this.ll = ll;
        this.lr = lr;
        this.ul = tl;
        this.ur = tr;
        this.axis = axis;
    }

    @Override
    public float interceptRay (Ray r) {

        float denom = r.direction.dot(normal);

        if (denom == 0) {
            return -1;
        }

        float t = (p.sub(r.origin)).dot(normal) / denom;

        if (t > 0) {

            Vector3D pHit = r.extend(t);

            switch (axis) {
                case X:
                    if ((pHit.x > ll.x && pHit.y > ll.y) && (pHit.x > ul.x && pHit.y < ul.y) &&
                            (pHit.x < ur.x && pHit.y < ur.y) && (pHit.x < lr.x && pHit.y > lr.x)) {
                        return t;
                    }
                    break;
                case Z:
                    if ((pHit.x > ll.x && pHit.z > ll.z) && (pHit.x > ul.x && pHit.z < ul.z) &&
                            (pHit.x < ur.x && pHit.z < ur.z) && (pHit.x < lr.x && pHit.z > lr.z)) {
                        return t;
                    }
                    break;
            }
        }
        return -1;
    }

    @Override
    public Vector3D normalAt(Vector3D p) {
        return normal;
    }
}
