package ar.edu.itba.ss;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        int N = 100;
        double L = 600;
        int M = 10;
        double Rc = L / M * 2;
        boolean periodic = true;

        CellIndexMethod cim = new CellIndexMethod(N, L, M, Rc, periodic);
        List<Particle> particles = cim.getParticles();
        try {
            writeParticles("input.txt", particles);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long startTime = System.nanoTime();
        Map<Integer, Set<Integer>> neighbors = cim.findNeighbors();
        long endTime = System.nanoTime();

        System.out.println("Tiempo de ejecución: " + (endTime - startTime) / 1e6 + " ms");

        try {
            writeNeighbors("output.txt", neighbors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeParticles(String fileName, List<Particle> particles) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Particle p : particles) {
            lines.add(p.getId() + " " + p.getX() + " " + p.getY() + " " + p.getR());
        }
        Files.write(Paths.get(fileName), lines);
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

}
