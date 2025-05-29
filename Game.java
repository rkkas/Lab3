public class Game {
    private String name;
    private int price;
    private int quality;
    private String category;

    public Game(String name, int price, int quality, String category) {
        this.name = name;
        this.price = price;
        this.quality = quality;
        this.category = category;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getQuality() { return quality; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return "Nombre: " + name + ", Precio: " + price +
               ", Calidad: " + quality + ", Categoría: " + category;
    }

    public static void main(String[] args) {
        Game juego = new Game("GalaxyQuest", 29990, 85, "Aventura");
        System.out.println(juego);
    }
}
