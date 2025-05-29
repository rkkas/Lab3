import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // 🔹 Paso 1: Generar y guardar datasets
        ArrayList<Game> data100 = GenerateData.generate(100);
        ArrayList<Game> data10000 = GenerateData.generate(10_000);
        ArrayList<Game> data1000000 = GenerateData.generate(1_000_000);

        GenerateData.saveToCSV(data100, "dataset_10e2.csv");
        GenerateData.saveToCSV(data10000, "dataset_10e4.csv");
        GenerateData.saveToCSV(data1000000, "dataset_10e6.csv");

        // 🔹 Paso 2: Benchmark de ordenamiento
        String[] algoritmos = {"bubbleSort", "insertionSort", "selectionSort", "mergeSort", "quickSort", "collectionsSort"};
        String[] atributos = {"price", "quality", "category"};
        int[] tamanos = {100, 10_000, 1_000_000};

        for (String atributo : atributos) {
            for (String algoritmo : algoritmos) {
                for (int tamano : tamanos) {

                    // ⛔ Omitir algoritmos lentos con dataset grande o errores conocidos
                    if (((algoritmo.equals("bubbleSort") || algoritmo.equals("insertionSort") || algoritmo.equals("selectionSort"))
                            && tamano == 1_000_000) ||
                        (algoritmo.equals("quickSort") && atributo.equals("category") && tamano == 1_000_000)) {

                        System.out.println("[Ordenamiento] " + algoritmo + " (" + atributo + ", " + tamano + "): más de 300s (omitido)");
                        continue;
                    }

                    ArrayList<Game> original = GenerateData.generate(tamano);
                    long total = 0;

                    for (int i = 0; i < 3; i++) {
                        ArrayList<Game> copia = new ArrayList<>(original);
                        Dataset ds = new Dataset(copia);
                        long inicio = System.nanoTime();
                        ds.sortByAlgorithm(algoritmo, atributo);
                        long fin = System.nanoTime();
                        long duracion = (fin - inicio) / 1_000_000;
                        total += duracion;
                        System.out.println("[Ordenamiento] " + algoritmo + " (" + atributo + ", " + tamano + ") ejecución " + (i + 1) + ": " + duracion + " ms");
                    }

                    System.out.println("→ Promedio: " + (total / 3) + " ms\n");
                }
            }
        }

        // 🔹 Paso 3: Benchmark de Búsqueda (solo con dataset de 10^6)
        System.out.println("===== BENCHMARK DE BÚSQUEDA (n = 1.000.000) =====");

        ArrayList<Game> original = GenerateData.generate(1_000_000);
        Dataset dataset = new Dataset(new ArrayList<>(original));

        // Obtener un juego real del dataset para buscarlo
        Game referencia = original.get(original.size() / 2);
        int targetPrice = referencia.getPrice();
        int targetQuality = referencia.getQuality();
        String targetCategory = referencia.getCategory();

        // ---------- getGamesByPrice ----------
        // Lineal
        long start = System.nanoTime();
        dataset.getGamesByPrice(targetPrice); // sin ordenar
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("getGamesByPrice (Lineal): " + duration + " ms");

        // Binaria
        dataset.sortByAlgorithm("collectionsSort", "price");
        start = System.nanoTime();
        dataset.getGamesByPrice(targetPrice); // con orden
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("getGamesByPrice (Binaria): " + duration + " ms");

        // ---------- getGamesByCategory ----------
        // Lineal
        start = System.nanoTime();
        dataset.getGamesByCategory(targetCategory); // sin orden
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("getGamesByCategory (Lineal): " + duration + " ms");

        // Binaria
        dataset.sortByAlgorithm("collectionsSort", "category");
        start = System.nanoTime();
        dataset.getGamesByCategory(targetCategory); // con orden
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("getGamesByCategory (Binaria): " + duration + " ms");

        // ---------- getGamesByQuality ----------
        // Lineal
        start = System.nanoTime();
        dataset.getGamesByQuality(targetQuality); // sin orden
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("getGamesByQuality (Lineal): " + duration + " ms");

        // Binaria
        dataset.sortByAlgorithm("collectionsSort", "quality");
        start = System.nanoTime();
        dataset.getGamesByQuality(targetQuality); // con orden
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("getGamesByQuality (Binaria): " + duration + " ms");
    }
}
