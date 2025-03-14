package ar.edu.itba.ss;

import java.util.*;

class CellIndexMethod {
    private final int N, M;
    private final double L, Rc;
    private final List<Particle> particles;
    private final Map<Integer, Set<Particle>> grid;
    private final boolean periodic;

    public CellIndexMethod(int N, double L, int M, double Rc, boolean periodic) {
        this.N = N;
        this.L = L;
        this.M = M;
        this.Rc = Rc;
        this.periodic = periodic;
        this.particles = new ArrayList<>();
        this.grid = new HashMap<>();

        initializeGrid();
        generateParticles();
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public Map<Integer, Set<Integer>> findNeighbors() {
        Map<Integer, Set<Integer>> neighbors = new HashMap<>();
        for (Particle p : particles) {
            int cellIndex = getCellIndex(p.getX(), p.getY());
            neighbors.put(p.getId(), new TreeSet<>());
            for (Particle neighbor : getPossibleNeighbors(cellIndex)) {
                if (p.getId() != neighbor.getId() && p.distanceTo(neighbor) < Rc) {
                    neighbors.get(p.getId()).add(neighbor.getId());
                }
            }
        }
        return neighbors;
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

    private Set<Particle> getPossibleNeighbors(int cellIndex) {
        Set<Particle> possibleNeighbors = new TreeSet<>(grid.get(cellIndex));
        int row = cellIndex / M;
        int col = cellIndex % M;
        int[] dx = {0, 1, 1, 0, -1, -1, -1, 0, 1};
        int[] dy = {0, 0, 1, 1, 1, 0, -1, -1, -1};

        for (int i = 0; i < dx.length; i++) {
            int newRow = row + dy[i];
            int newCol = col + dx[i];
            if (periodic) {
                newRow = (newRow + M) % M;
                newCol = (newCol + M) % M;
            }
            if (newRow >= 0 && newRow < M && newCol >= 0 && newCol < M) {
                int neighborIndex = newRow * M + newCol;
                possibleNeighbors.addAll(grid.get(neighborIndex));
            }
        }
        return possibleNeighbors;
    }

    private void initializeGrid() {
        for (int i = 0; i < M * M; i++) {
            grid.put(i, new TreeSet<>());
        }
    }

    private void generateParticles() {
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            double x = rand.nextDouble() * L;
            double y = rand.nextDouble() * L;
            double vx = rand.nextDouble() - 0.5;
            double vy = rand.nextDouble() - 0.5;
            Particle p = new Particle(i, x, y, vx, vy, 5);
            particles.add(p);
            addToGrid(p);
        }
    }

    private int getCellIndex(double x, double y) {
        int row = (int) (y / (L / M));
        int col = (int) (x / (L / M));
        return row * M + col;
    }

    private void addToGrid(Particle p) {
        int index = getCellIndex(p.getX(), p.getY());
        grid.get(index).add(p);
    }

}
