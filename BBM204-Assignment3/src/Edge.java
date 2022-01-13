public class Edge {
    int source;//source airport
    int destination;//destination airport
    String weight;//id

    public Edge(int source, int destination, String weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String getWeight() {
        return weight;
    }
}
