import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class GenerateData {

    private static final String[] CATEGORIES = {"Acción", "Aventura", "Puzzle", "Deportes", "RPG", "Estrategia"};

    public static ArrayList<Game> generate(int cantidad) {
        ArrayList<Game> juegos = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < cantidad; i++) {
            String name = "Game" + (i + 1);
            int price = random.nextInt(50000); // 0 - 49999
            int quality = random.nextInt(101); // 0 - 100
            String category = CATEGORIES[random.nextInt(CATEGORIES.length)];
            juegos.add(new Game(name, price, quality, category));
        }

        return juegos;
    }

    public static void saveToCSV(ArrayList<Game> juegos, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("name,price,quality,category");
            for (Game game : juegos) {
                writer.printf("%s,%d,%d,%s%n", game.getName(), game.getPrice(), game.getQuality(), game.getCategory());
            }
            System.out.println("Archivo guardado: " + filename);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + filename);
            e.printStackTrace();
        }
    }

    public static ArrayList<Game> loadFromCSV(String filename) {
        ArrayList<Game> juegos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String linea;
            br.readLine(); // Saltar cabecera

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    String name = partes[0].trim();
                    int price = Integer.parseInt(partes[1].trim());
                    int quality = Integer.parseInt(partes[2].trim());
                    String category = partes[3].trim();
                    juegos.add(new Game(name, price, quality, category));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + filename);
            e.printStackTrace();
        }

        return juegos;
    }
}
