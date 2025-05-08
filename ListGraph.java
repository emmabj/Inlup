package se.su.inlupp; //Hej GitTest

import java.util.*;

public class ListGraph implements Graph {
    private Map<String, Set<String>> adjacencyList;
    private Map<String, Map<String, Integer>> edgeWeights;
    private Map<String, Map<String, String>> edgeNames;


    public ListGraph() {
        this.adjacencyList = new HashMap<>();
        this.edgeWeights = new HashMap<>();
        this.edgeNames = new HashMap<>();

    }

    public void add(String node) {
        if (!adjacencyList.containsKey(node)) {
            adjacencyList.put(node, new HashSet<>()); //Lägger till nod om den inte finns i grafen.
            edgeWeights.put(node, new HashMap<>()); //Lägger till plats för viktade kopplingar.
        }
    }

    public void remove(String node) {
        if(!adjacencyList.containsKey(node)) { //Om noden inte finns i grafen adjacencyList
            throw new NoSuchElementException(); //Metoden slutas köra om noden inte finns
        }
        for(String neighbor : adjacencyList.get(node)) { //Loop som går igenom alla grannar till noden
            adjacencyList.get(neighbor).remove(node); //och tar bort noden från grannarnas lista
            edgeWeights.get(neighbor).remove(node); //tar bort vikten på kanten från grannen till noden
        }

        adjacencyList.remove(node); //Tar bort själva noden ur listan
        edgeWeights.remove(node); //Tar bort nodens vikttabell
    }

    public void connect(String node1, String node2, String connectionName, int weight) {
        if(!adjacencyList.containsKey(node1) || !adjacencyList.containsKey(node2)) {
            throw new NoSuchElementException();
        }

        if(weight < 0) {
            throw new IllegalArgumentException();
        }

        if(adjacencyList.get(node1).contains(node2)) {
            throw new IllegalStateException();
        }
            adjacencyList.get(node1).add(node2);
            adjacencyList.get(node2).add(node1); //lägger till kopplingen åt båda hållen för att representera oriktad graf

            edgeWeights.get(node1).put(node2, weight);
            edgeWeights.get(node2).put(node1, weight); //lägger till vikt

            edgeNames.get(node1).put(node2, connectionName);
            edgeNames.get(node2).put(node1, connectionName); //lägger till namn
    }

    public String getEdgesBetween(String node1, String node2) {
        if(!adjacencyList.containsKey(node1) || !adjacencyList.containsKey(node2)) { //om någon av noderna saknas
            throw new NoSuchElementException();
        }

        if(!adjacencyList.get(node1).contains(node2)) {
            return null; //ingen kant finns mellan noderna
        }

        String connectionName = edgeNames.get(node1).get(node2);
        Integer weight = edgeWeights.get(node1).get(node2);

        return connectionName + weight;
    }

    public void disconnect(String node1, String node2, String connectionName) {
        if(!adjacencyList.containsKey(node1) || !adjacencyList.containsKey(node2)) {
            throw new NoSuchElementException(); //någon av noderna eller båda saknas i grafen
        }

        if(!adjacencyList.get(node1).contains(node2)) {
            throw new IllegalStateException(); //ingen kant finns
        }

        adjacencyList.get(node1).remove(node2);
        adjacencyList.get(node2).remove(node1); //tar bort kant

        edgeNames.get(node1).put(node2, connectionName);
        edgeNames.get(node2).put(node1, connectionName); //tar bort namn

        edgeWeights.get(node1).remove(node2);
        edgeWeights.get(node2).remove(node1); //tar bort vikt
    }

    public void setConnectionWeight(String node1, String node2, int newWeight) {
        if(!adjacencyList.containsKey(node1) || !adjacencyList.containsKey(node2)) {
            throw new NoSuchElementException(); //någon av noderna eller båda saknas i grafen
        }

        if(!adjacencyList.get(node1).contains(node2)) {
            throw new IllegalStateException(); //ingen kant finns
        }

        if(newWeight < 0) {
            throw new IllegalArgumentException();
        }

        edgeWeights.get(node1).put(node2, newWeight);
        edgeWeights.get(node2).put(node1, newWeight); //uppdaterar vikten
    }

    public Set<String> getNodes() {
        return new HashSet<>(adjacencyList.keySet());
    }

    public Collection<String> getEdgesFrom(String node) {
        if(!adjacencyList.containsKey(node)) {
            throw new NoSuchElementException();
        }


    }
}
