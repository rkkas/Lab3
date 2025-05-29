public class Game {
    private String name;
    private String category;
    private int price;
    private int quality;

    public Game(String name, String category, int price, int quality) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quality = quality;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getPrice() { return price; }
    public int getQuality() { return quality; }

    @Override
    public String toString() {
        return "Nombre: " + name + ", Categoría: " + category + 
               ", Precio: " + price + ", Calidad: " + quality;
    }

    // 🔹 Método main para prueba simple
    public static void main(String[] args) {
        Game juego = new Game("GalaxyQuest", "Aventura", 29990, 85);
        System.out.println(juego);
    }
}
