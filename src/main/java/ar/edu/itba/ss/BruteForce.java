package ar.edu.itba.ss;

import java.util.*;

class BruteForce {

    private final double Rc;
    private final List<Particle> particles;
    private final double L;
    private final boolean periodic;

    public BruteForce(double Rc, List<Particle> particles, double L, boolean periodic) {
        this.Rc = Rc;
        this.particles = particles;
        this.L = L;
        this.periodic = periodic;
    }

    public Map<Integer, Set<Integer>> findNeighborsBruteForce() {
        Map<Integer, Set<Integer>> neighbors = new HashMap<>();

        for (Particle p : particles) {
            neighbors.put(p.getId(), new HashSet<>());
            for (Particle neighbor : particles) {
                if (p.getId() != neighbor.getId()) {
                    double distance = p.distanceTo(neighbor);
                    if (periodic) {
                        double dx = Math.abs(p.getX() - neighbor.getX());
                        double dy = Math.abs(p.getY() - neighbor.getY());
                        dx = Math.min(dx, L - dx);
                        dy = Math.min(dy, L - dy);
                        distance = Math.sqrt(dx * dx + dy * dy) - neighbor.getR();
                    }
                    if (distance < Rc) {
                        neighbors.get(p.getId()).add(neighbor.getId());
                    }
                }
            }
        }

        return neighbors;
    }
}