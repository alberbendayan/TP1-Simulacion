import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        int N = 100;
        double L = 20;
        int M = 10;
        double Rc = 1.0;
        boolean periodic = true;

        CellIndexMethod cim = new CellIndexMethod(N, L, M, Rc, periodic);
        long startTime = System.nanoTime();
        Map<Integer, Set<Integer>> neighbors = cim.findNeighbors();
        long endTime = System.nanoTime();

        System.out.println("Tiempo de ejecución: " + (endTime - startTime) / 1e6 + " ms");
        for (Map.Entry<Integer, Set<Integer>> entry : neighbors.entrySet()) {
            System.out.println("Partícula " + entry.getKey() + " vecinas: " + entry.getValue());
        }
    }
}