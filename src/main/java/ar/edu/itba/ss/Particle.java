package ar.edu.itba.ss;

class Particle implements Comparable<Particle> {
    private final int id;
    private final double x, y, r;
    private final double vx, vy;
    private double p;

    public Particle(int id, double x, double y, double vx, double vy, double r, double p) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.r = r;
        this.p = p;
    }

    public int getId() { return id; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getR() { return r; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

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
