import java.util.*;
import java.io.*;

public class GenerateData {

    public static ArrayList<Game> generate(int n) {
        String[] nombres = {"Dragon", "Empire", "Quest", "Galaxy", "Legends", "Warrior"};
        String[] categorias = {"Acción", "Aventura", "Estrategia", "RPG", "Deportes", "Simulación"};
        ArrayList<Game> lista = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            String name = nombres[rand.nextInt(nombres.length)] + nombres[rand.nextInt(nombres.length)];
            String category = categorias[rand.nextInt(categorias.length)];
            int price = rand.nextInt(70001);
            int quality = rand.nextInt(101);
            lista.add(new Game(name, category, price, quality));
        }

        return lista;
    }

    public static void saveToCSV(ArrayList<Game> lista, String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("name,category,price,quality");
            for (Game g : lista) {
                pw.println(g.getName() + "," + g.getCategory() + "," + g.getPrice() + "," + g.getQuality());
            }
            System.out.println("Archivo guardado: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Método main para prueba simple
    public static void main(String[] args) {
        ArrayList<Game> lista = generate(5);
        for (Game g : lista) {
            System.out.println(g);
        }

        saveToCSV(lista, "ejemplo_dataset.csv");
    }
}
