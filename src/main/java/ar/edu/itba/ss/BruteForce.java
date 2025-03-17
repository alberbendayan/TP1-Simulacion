package ar.edu.itba.ss;

import java.util.*;

class BruteForce {
    private final double  Rc;
    private final List<Particle> particles;

    public BruteForce(double Rc, List<Particle> particles) {
        this.Rc = Rc;
        this.particles = particles;
    }

    public Map<Integer, Set<Integer>> findNeighborsBruteForce() {
        Map<Integer, Set<Integer>> neighbors = new HashMap<>();
        for (Particle p : particles) {
            neighbors.put(p.getId(), new TreeSet<>());
            for (Particle neighbor : particles) {
                if (p.getId() != neighbor.getId() && p.distanceTo(neighbor) < Rc) {
                    neighbors.get(p.getId()).add(neighbor.getId());
                }
            }
        }
        return neighbors;
    }
}
