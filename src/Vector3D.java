import java.util.Random;

public class Vector3D {
    public float x;
    public float y;
    public float z;

    public Vector3D add(Vector3D other) {
        return new Vector3D(x + other.x, y + other.y, z + other.z);
    }

    public Vector3D sub(Vector3D other) { return new Vector3D(x - other.x, y - other.y, z - other.z); }

    public Vector3D mul(float t) {
        return new Vector3D(x * t, y * t, z * t);
    }

    public Vector3D mul(Vector3D other) { return new Vector3D(x * other.x, y * other.y, z * other.z); }

    public Vector3D div(float t) { return new Vector3D(x / t, y / t, z / t); }

    public float abs() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float dot(Vector3D other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public Vector3D normalize() {
        return mul(1/abs());
    }

    @Override
    public String toString() {
        return "Vector3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3D randomVec() {
        Random rand = new Random();
        return new Vector3D(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()).normalize();
    }
}
