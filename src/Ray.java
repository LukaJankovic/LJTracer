public class Ray {
    public Vector3D origin;
    public Vector3D direction;

    public Vector3D extend(float t) {
        return origin.add(direction.mul(t));
    }

    public Ray(Vector3D origin, Vector3D direction) {
        this.origin = origin;
        this.direction = direction.normalize();
    }
}
