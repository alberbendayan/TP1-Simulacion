package ar.edu.itba.ss;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int N = 0;
        double L = 0;
        int M = 6;
        boolean periodic = true;
        List<Particle> inputParticles = new ArrayList<>();
        int id=0;

        try {
            File staticFile = new File("Static100.txt");
            File dynamicFile = new File("Dynamic100.txt");
            Scanner scannerStatic = new Scanner(staticFile);
            Scanner scannerDynamic = new Scanner(dynamicFile);

            if(scannerStatic.hasNextLine() && scannerDynamic.hasNextLine()) {
                N = Integer.parseInt(scannerStatic.nextLine().trim());
            }

            if(scannerStatic.hasNextLine() && scannerDynamic.hasNextLine()) {
                L = Integer.parseInt(scannerStatic.nextLine().trim());
                scannerDynamic.nextLine();
            }

            while (scannerStatic.hasNextLine() && scannerDynamic.hasNextLine()) {
                String dataStatic = scannerStatic.nextLine().trim();
                String[] valuesStatic = dataStatic.split("\\s+");

                String dataDynamic = scannerDynamic.nextLine().trim();
                String[] valuesDynamic = dataDynamic.split("\\s+");
                inputParticles.add(
                        new Particle(id++,
                                Double.parseDouble(valuesDynamic[0]),
                                Double.parseDouble(valuesDynamic[1]),
                                0.0,
                                0.0,
                                Double.parseDouble(valuesStatic[0]),
                                Double.parseDouble(valuesStatic[1])));
            }
            scannerStatic.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        double Rc = 10;
        CellIndexMethod cim = new CellIndexMethod(N, L, M, Rc, periodic, inputParticles);
        BruteForce bf = new BruteForce(Rc, inputParticles);

        long startTime = System.nanoTime();
        Map<Integer, Set<Integer>> neighbors = cim.findNeighbors();
        long endTime = System.nanoTime();
        System.out.println("Tiempo de ejecución CIM: " + (endTime - startTime) / 1e6 + " ms");

        Map<Integer, Set<Integer>> neighborsBrutos = bf.findNeighborsBruteForce();
        long endBrutosTime = System.nanoTime();
        System.out.println("Tiempo de ejecución fuerza bruta: " + (endBrutosTime - endTime) / 1e6 + " ms");

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


}
