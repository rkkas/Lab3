import java.util.*;

public class Dataset {
    private ArrayList<Game> data;
    private String sortedByAttribute;

    public Dataset(ArrayList<Game> data) {
        this.data = data;
        this.sortedByAttribute = "";
    }

    public ArrayList<Game> getData() {
        return data;
    }

    public ArrayList<Game> getGamesByPrice(int price) {
        ArrayList<Game> resultado = new ArrayList<>();
        if (sortedByAttribute.equals("price")) {
            int izquierda = 0, derecha = data.size() - 1;
            while (izquierda <= derecha) {
                int medio = (izquierda + derecha) / 2;
                int precioMedio = data.get(medio).getPrice();
                if (precioMedio == price) {
                    int i = medio;
                    while (i >= 0 && data.get(i).getPrice() == price) i--;
                    i++;
                    while (i < data.size() && data.get(i).getPrice() == price) {
                        resultado.add(data.get(i++));
                    }
                    break;
                } else if (precioMedio < price) {
                    izquierda = medio + 1;
                } else {
                    derecha = medio - 1;
                }
            }
        } else {
            for (Game juego : data) {
                if (juego.getPrice() == price) resultado.add(juego);
            }
        }
        return resultado;
    }

    public ArrayList<Game> getGamesByPriceRange(int lower, int upper) {
        ArrayList<Game> resultado = new ArrayList<>();
        if (sortedByAttribute.equals("price")) {
            int izquierda = 0, derecha = data.size() - 1;
            int inicio = -1;
            while (izquierda <= derecha) {
                int medio = (izquierda + derecha) / 2;
                if (data.get(medio).getPrice() >= lower) {
                    inicio = medio;
                    derecha = medio - 1;
                } else {
                    izquierda = medio + 1;
                }
            }
            if (inicio != -1) {
                for (int i = inicio; i < data.size(); i++) {
                    int precio = data.get(i).getPrice();
                    if (precio > upper) break;
                    resultado.add(data.get(i));
                }
            }
        } else {
            for (Game juego : data) {
                int precio = juego.getPrice();
                if (precio >= lower && precio <= upper) {
                    resultado.add(juego);
                }
            }
        }
        return resultado;
    }

    public ArrayList<Game> getGamesByCategory(String category) {
        ArrayList<Game> resultado = new ArrayList<>();
        if (sortedByAttribute.equals("category")) {
            int izquierda = 0, derecha = data.size() - 1;
            while (izquierda <= derecha) {
                int medio = (izquierda + derecha) / 2;
                String categoriaMedio = data.get(medio).getCategory();
                int cmp = categoriaMedio.compareToIgnoreCase(category);
                if (cmp == 0) {
                    int i = medio;
                    while (i >= 0 && data.get(i).getCategory().equalsIgnoreCase(category)) i--;
                    i++;
                    while (i < data.size() && data.get(i).getCategory().equalsIgnoreCase(category)) {
                        resultado.add(data.get(i++));
                    }
                    break;
                } else if (cmp < 0) {
                    izquierda = medio + 1;
                } else {
                    derecha = medio - 1;
                }
            }
        } else {
            for (Game juego : data) {
                if (juego.getCategory().equalsIgnoreCase(category)) {
                    resultado.add(juego);
                }
            }
        }
        return resultado;
    }

    public ArrayList<Game> getGamesByQuality(int quality) {
        ArrayList<Game> resultado = new ArrayList<>();
        if (sortedByAttribute.equals("quality")) {
            int izquierda = 0, derecha = data.size() - 1;
            while (izquierda <= derecha) {
                int medio = (izquierda + derecha) / 2;
                int calidad = data.get(medio).getQuality();
                if (calidad == quality) {
                    int i = medio;
                    while (i >= 0 && data.get(i).getQuality() == quality) i--;
                    i++;
                    while (i < data.size() && data.get(i).getQuality() == quality) {
                        resultado.add(data.get(i++));
                    }
                    break;
                } else if (calidad < quality) {
                    izquierda = medio + 1;
                } else {
                    derecha = medio - 1;
                }
            }
        } else {
            for (Game juego : data) {
                if (juego.getQuality() == quality) {
                    resultado.add(juego);
                }
            }
        }
        return resultado;
    }

    public ArrayList<Game> sortByAlgorithm(String algorithm, String attribute) {
        Comparator<Game> comparador;
        switch (attribute) {
            case "price": comparador = Comparator.comparingInt(Game::getPrice); break;
            case "quality": comparador = Comparator.comparingInt(Game::getQuality); break;
            case "category": comparador = Comparator.comparing(Game::getCategory, String.CASE_INSENSITIVE_ORDER); break;
            default: comparador = Comparator.comparingInt(Game::getPrice);
        }

        switch (algorithm) {
            case "bubbleSort": bubbleSort(comparador); break;
            case "insertionSort": insertionSort(comparador); break;
            case "selectionSort": selectionSort(comparador); break;
            case "mergeSort": mergeSort(comparador); break;
            case "quickSort": quickSort(comparador); break;
            case "countingSort":
                if (attribute.equals("quality")) {
                    countingSortByQuality();
                    sortedByAttribute = attribute;
                    return data;
                }
            default: Collections.sort(data, comparador);
        }

        sortedByAttribute = attribute;
        return data;
    }

    private void bubbleSort(Comparator<Game> comparador) {
        int n = data.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (comparador.compare(data.get(j), data.get(j + 1)) > 0) {
                    Collections.swap(data, j, j + 1);
                }
            }
        }
    }

    private void insertionSort(Comparator<Game> comparador) {
        for (int i = 1; i < data.size(); i++) {
            Game actual = data.get(i);
            int j = i - 1;
            while (j >= 0 && comparador.compare(data.get(j), actual) > 0) {
                data.set(j + 1, data.get(j));
                j--;
            }
            data.set(j + 1, actual);
        }
    }

    private void selectionSort(Comparator<Game> comparador) {
        int n = data.size();
        for (int i = 0; i < n - 1; i++) {
            int minimo = i;
            for (int j = i + 1; j < n; j++) {
                if (comparador.compare(data.get(j), data.get(minimo)) < 0) {
                    minimo = j;
                }
            }
            Collections.swap(data, i, minimo);
        }
    }

    private void mergeSort(Comparator<Game> comparador) {
        data = mergeSortRec(new ArrayList<>(data), comparador);
    }

    private ArrayList<Game> mergeSortRec(ArrayList<Game> lista, Comparator<Game> comp) {
        if (lista.size() <= 1) return lista;
        int medio = lista.size() / 2;
        ArrayList<Game> izq = mergeSortRec(new ArrayList<>(lista.subList(0, medio)), comp);
        ArrayList<Game> der = mergeSortRec(new ArrayList<>(lista.subList(medio, lista.size())), comp);
        return merge(izq, der, comp);
    }

    private ArrayList<Game> merge(ArrayList<Game> izq, ArrayList<Game> der, Comparator<Game> comp) {
        ArrayList<Game> resultado = new ArrayList<>();
        int i = 0, j = 0;
        while (i < izq.size() && j < der.size()) {
            if (comp.compare(izq.get(i), der.get(j)) <= 0) {
                resultado.add(izq.get(i++));
            } else {
                resultado.add(der.get(j++));
            }
        }
        while (i < izq.size()) resultado.add(izq.get(i++));
        while (j < der.size()) resultado.add(der.get(j++));
        return resultado;
    }

    private void quickSort(Comparator<Game> comp) {
        quickSortRec(0, data.size() - 1, comp, new Random());
    }

    private void quickSortRec(int low, int high, Comparator<Game> comp, Random rand) {
        if (low < high) {
            int pivotIndex = low + rand.nextInt(high - low + 1);
            Collections.swap(data, pivotIndex, high);
            int pi = partition(low, high, comp);
            quickSortRec(low, pi - 1, comp, rand);
            quickSortRec(pi + 1, high, comp, rand);
        }
    }

    private int partition(int low, int high, Comparator<Game> comp) {
        Game pivot = data.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comp.compare(data.get(j), pivot) <= 0) {
                i++;
                Collections.swap(data, i, j);
            }
        }
        Collections.swap(data, i + 1, high);
        return i + 1;
    }

    public void countingSortByQuality() {
        int max = 100;
        ArrayList<ArrayList<Game>> buckets = new ArrayList<>(max + 1);
        for (int i = 0; i <= max; i++) buckets.add(new ArrayList<>());
        for (Game g : data) {
            buckets.get(g.getQuality()).add(g);
        }
        data.clear();
        for (ArrayList<Game> bucket : buckets) {
            data.addAll(bucket);
        }
    }
}
