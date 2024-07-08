import java.util.*;

class PUPMainMap {
    private final Map<String, List<Node>> adjList = new HashMap<>();

    static class Node implements Comparable<Node> {
        String building;
        double weight;

        Node(String building, double weight) {
            this.building = building;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.weight, other.weight);
        }
    }

    void addPath(String source, String destination, double weight) {
        adjList.computeIfAbsent(source, k -> new ArrayList<>()).add(new Node(destination, weight));
        adjList.computeIfAbsent(destination, k -> new ArrayList<>()).add(new Node(source, weight));
    }

    void dijkstra(String startBuilding, String endBuilding) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> settled = new HashSet<>();
    
        for (String building : adjList.keySet()) {
            distances.put(building, Double.POSITIVE_INFINITY);
        }
        distances.put(startBuilding, 0.0);
        pq.add(new Node(startBuilding, 0.0));
    
        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            String currentBuilding = currentNode.building;
    
            if (settled.contains(currentBuilding)) continue;
            settled.add(currentBuilding);
    
            List<Node> adjacentNodes = adjList.get(currentBuilding);
            if (adjacentNodes != null) {
                for (Node node : adjacentNodes) {
                    String adjacentBuilding = node.building;
                    double edgeWeight = node.weight;
    
                    if (!settled.contains(adjacentBuilding)) {
                        double newDist = distances.get(currentBuilding) + edgeWeight;
    
                        if (newDist < distances.get(adjacentBuilding)) {
                            distances.put(adjacentBuilding, newDist);
                            pq.add(new Node(adjacentBuilding, newDist));
                            previous.put(adjacentBuilding, currentBuilding);
                        }
                    }
                }
            }
        }
    
        printSolution(distances, previous, startBuilding, endBuilding);
    }    

    private void printSolution(Map<String, Double> distances, Map<String, String> previous, String startBuilding, String endBuilding) {
        System.out.println("Building\t\tDistance from Source\tPath");
        for (Map.Entry<String, Double> entry : distances.entrySet()) {
            String building = entry.getKey();
            double distance = entry.getValue();
            String path = buildPath(previous, startBuilding, building);
            System.out.printf("%-20s%-20.2f%s\n", building, distance, path);
        }
        System.out.println("\nShortest path from " + startBuilding + " to " + endBuilding + ": " + buildPath(previous, startBuilding, endBuilding));
        System.out.println("Total distance: " + distances.get(endBuilding));
    }

    private String buildPath(Map<String, String> previous, String startBuilding, String endBuilding) {
        Deque<String> path = new LinkedList<>();
        for (String at = endBuilding; at != null; at = previous.get(at)) {
            path.addFirst(at);
        }
        return String.join(" -> ", path);
    }

    public static void main(String[] args) {
        PUPMainMap map = new PUPMainMap();

        map.addPath("Main Gate", "Information Desk", 11);
        map.addPath("Main Gate", "Souvenir Shop", 81);
        map.addPath("Souvenir", "Oval", 110); 
        map.addPath("Main Gate", "Oblesik", 140); 
        map.addPath("Oblesik", "College of Human Kinetics", 67);
        map.addPath("Oblesik", "Pool", 67); 
        map.addPath("Lagoon", "Amphitheater", 59); 
        map.addPath("Amphitheater", "Main Dome", 85); 
        map.addPath("Oval", "Chapel", 28); 
        map.addPath("Chapel", "East Wing", 74); 
        map.addPath("Lagoon", "Oblesik", 46); 
        map.addPath("East Wing", "Main Dome", 68); 
        map.addPath("Main Dome", "West Wing", 73); 
        map.addPath("Main Dome", "South Wing", 49); 
        map.addPath("Pool", "College of Human Kinetics", 20); 
        map.addPath("Pool", "Tahanan ng Alumni", 18); 
        map.addPath("Pool", "Tennis Court", 70); 
        map.addPath("Tennis Court", "Gymnasium", 21); 
        map.addPath("Amphitheater", "School Supplies and Printing Bldg.", 30); 
        map.addPath("Printing Press", "Ninoy Aquino Library and Learning Resource Center", 77); 
        map.addPath("Lab High", "Charlie", 131); 
        map.addPath("Charlie", "School Supplies and Printing Bldg.", 45); 
        map.addPath("Visitor's Lounge", "Engineering Center", 79); 
        map.addPath("Gymnasium", "Engineering Center", 19); 
        map.addPath("Sampaguita Canteen", "Charlie", 20); 
        map.addPath("Ninoy Aquino Library and Learning Resource Center", "Charlie", 58); 
        map.addPath("Pool", "Gymnasium", 70); 
        map.addPath("West Wing", "Sampaguita Canteen", 37); 
        map.addPath("Pumping Station", "South Wing", 25); 
        map.addPath("Sampaguita Canteen", "South Wing", 62); 
        map.addPath("FAMO", "East Wing", 75); 
        map.addPath("Sampaguita Canteen", "West Wing", 13); 
        map.addPath("Pumping Station", "West Wing", 17); 
        map.addPath("Linear Park", "Sampaguita Canteen", 51); 
        map.addPath("Ninoy Aquino Library and Learning Resource Center", "West Wing", 89); 
        map.addPath("Gymnasium", "Visitor's Lounge", 69); 
        map.addPath("FAMO", "South Wing", 40); 
        map.addPath("Pumping Station", "Sampaguita Canteen", 32); 
        map.addPath("Linear Park", "Charlie", 17); 
        map.addPath("Linear Park", "Laboratory High", 25); 
        map.addPath("Printing Press", "Laboratory High", 41); 
        map.addPath("Laboratory High", "Property and Supply Office bldg", 41); 
        map.addPath("Ninoy Aquino Library and Learning Resource Center", "Property and Supply Office bldg", 96); 
        map.addPath("Tennis Court", "Oblesik", 36); 
        map.addPath("Tahanan ng Alumni", "Oblesik", 70); 
        map.addPath("School Supplies & Printing Bldg.", "Laboratory High", 120); 
        map.addPath("Oblesik", "Souvenir Shop", 46); 
        map.addPath("Tennis Court", "Souvenir Shop", 18); 
        map.addPath("Visitor's Lounge", "Souvenir Shop", 66); 
        map.addPath("Information Desk", "Souvenir Shop", 66); 
        map.addPath("Souvenir Shop", "Chapel", 130); 
        map.addPath("Charlie", "West Wing", 52); 
        map.addPath("Main Gate", "Visitor's Lounge", 7); 
        map.addPath("Visitor's Lounge", "Tennis Court", 70); 
        map.addPath("College of Nutrition and Food Science", "East Wing", 45); 
        map.addPath("FAMO", "College of Nutrition and Food Science", 65); 
        map.addPath("Oval", "College of Nutrition and Food Science", 53); 
        map.addPath("College of Human Kinetics", "Nutrition and Food Tech Research Center", 87);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the starting building: ");
            String startBuilding = scanner.nextLine();
            System.out.print("Enter the destination building: ");
            String destinationBuilding = scanner.nextLine();

            map.dijkstra(startBuilding, destinationBuilding);
        }
    }
}
