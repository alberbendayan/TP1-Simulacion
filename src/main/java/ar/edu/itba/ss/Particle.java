package ar.edu.itba.ss;

class Particle implements Comparable<Particle> {
    private final int id;
    private final double x, y, r;
    private final double vx, vy;

    public Particle(int id, double x, double y, double vx, double vy, double r) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.r = r;
    }

    public int getId() { return id; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getR() { return r; }

    public double distanceTo(Particle other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy) - (this.r + other.r);
    }

    @Override
    public int compareTo(Particle o) {
        if (this.distanceTo(o) < this.distanceTo(this)) {
            return -1;
        }  else if (this.distanceTo(o) > this.distanceTo(this)) {
            return 1;
        } else if(this.x == o.x && this.y == o.y && this.r == o.r) {
            return 0;
        }
        return -1;
    }
}
