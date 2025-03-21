package ar.edu.itba.ss;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int N = 5000, M = 5, L = 20;
//        double Rc = (double) L / M * 3 / 4;
        double Rc = 3;
        boolean periodic = true;

        List<Particle> particles = generateParticles(N, L);
        writeParticles("static.txt", "dynamic.txt", L, particles);

        CellIndexMethod cim = new CellIndexMethod(N, L, M, Rc, periodic, particles);
        BruteForce bf = new BruteForce(Rc, particles, L, periodic);

        long cimStartTime = System.nanoTime();
        Map<Integer, Set<Integer>> neighbors = cim.findNeighbors();
        long cimEndTime = System.nanoTime();
        System.out.println("Tiempo de ejecución CIM: " + (cimEndTime - cimStartTime) / 1e6 + " ms");

        long bruteStartTime = System.nanoTime();
        Map<Integer, Set<Integer>> neighborsBrutos = bf.findNeighborsBruteForce();
        long bruteEndTime = System.nanoTime();
        System.out.println("Tiempo de ejecución fuerza bruta: " + (bruteEndTime - bruteStartTime) / 1e6 + " ms");

        try {
            writeNeighbors("output.txt", neighbors);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writeNeighbors("outputForce.txt", neighborsBrutos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeParticles(String staticFileName, String dynamicFileName, int L, List<Particle> particles) throws IOException {
        List<String> staticLines = new ArrayList<>();
        List<String> dynamicLines = new ArrayList<>();

        staticLines.add(String.valueOf(particles.size()));
        staticLines.add(String.valueOf(L));

        dynamicLines.add("0");

        for (Particle p : particles) {
            staticLines.add(p.getR() + " " + p.getP());
            dynamicLines.add(p.getX() + " " + p.getY() + " " + p.getVx() + " " + p.getVy());
        }
        Files.write(Paths.get(staticFileName), staticLines);
        Files.write(Paths.get(dynamicFileName), dynamicLines);
    }

    public static void writeNeighbors(String fileName, Map<Integer, Set<Integer>> neighbors) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Map.Entry<Integer, Set<Integer>> n : neighbors.entrySet()) {
            StringBuilder line = new StringBuilder("[").append(n.getKey());
            for (Integer p : n.getValue()) {
                line.append(" ").append(p);
            }
            line.append("]");
            lines.add(line.toString());
        }
        Files.write(Paths.get(fileName), lines);
    }

    public static void writeParticles(String fileName, List<Particle> particles) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Particle p : particles) {
            lines.add(p.getId() + " " + p.getX() + " " + p.getY() + " " + p.getVx() + " " + p.getVy() + " " + p.getR());
        }
        Files.write(Paths.get(fileName), lines);
    }

    private static List<Particle> generateParticles(int N, double L) {
        List<Particle> particles = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < N; i++) {
            double x = rand.nextDouble() * L;
            double y = rand.nextDouble() * L;
            double vx = rand.nextDouble() - 0.5;
            double vy = rand.nextDouble() - 0.5;
            particles.add(new Particle(i, x, y, vx, vy, 0.25, 1.0));
        }

        return particles;
    }

}
