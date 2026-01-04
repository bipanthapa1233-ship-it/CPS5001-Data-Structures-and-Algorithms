/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perds;
import java.util.*;

public class RoutePlanner {
    private final EmergencyNetwork network;

    public RoutePlanner(EmergencyNetwork network) {
        this.network = network;
    }

    public Route findShortestPath(Location from, Location to, OptimizationMetric metric) {
        if (from == null || to == null) {
            return null;
        }

        Map<String, Double> dist = new HashMap<>();
        Map<String, Double> time = new HashMap<>();
        Map<String, String> prev = new HashMap<>();

        for (Location loc : network.getAllLocations()) {
            dist.put(loc.getId(), Double.POSITIVE_INFINITY);
            time.put(loc.getId(), Double.POSITIVE_INFINITY);
        }

        dist.put(from.getId(), 0.0);
        time.put(from.getId(), 0.0);

        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));
        pq.add(from.getId());

        while (!pq.isEmpty()) {
            String currentId = pq.poll();
            Location current = network.getLocationById(currentId);
            if (currentId.equals(to.getId())) {
                break;
            }

            for (Road road : network.getNeighbours(current)) {
                String neighbourId = road.getTo().getId();
                double weight = getWeight(road, metric);

                double alt = dist.get(currentId) + weight;
                if (alt < dist.get(neighbourId)) {
                    dist.put(neighbourId, alt);
                    // track pure distance/time separately
                    double newDistance = time.get(currentId) + road.getDistanceKm();
                    double newTime = time.get(currentId) + road.getTravelTimeMin();
                    time.put(neighbourId, newTime);
                    prev.put(neighbourId, currentId);
                    pq.remove(neighbourId);
                    pq.add(neighbourId);
                }
            }
        }

        if (!prev.containsKey(to.getId()) && !from.getId().equals(to.getId())) {
            return null;
        }

        List<Location> path = new ArrayList<>();
        String curr = to.getId();
        path.add(network.getLocationById(curr));

        while (prev.containsKey(curr)) {
            curr = prev.get(curr);
            path.add(network.getLocationById(curr));
        }

        Collections.reverse(path);

        double totalDistance = 0.0;
        double totalTime = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Location a = path.get(i);
            Location b = path.get(i + 1);
            for (Road road : network.getNeighbours(a)) {
                if (road.getTo().getId().equals(b.getId())) {
                    totalDistance += road.getDistanceKm();
                    totalTime += road.getTravelTimeMin();
                    break;
                }
            }
        }

        return new Route(path, totalDistance, totalTime);
    }

    private double getWeight(Road road, OptimizationMetric metric) {
        return switch (metric) {
            case DISTANCE -> road.getDistanceKm();
            case TRAVEL_TIME -> road.getTravelTimeMin();
            case COMBINED -> road.getTravelTimeMin() * (1.0 + road.getCongestionLevel());
        };
    }

    public double computeETA(Route route) {
        if (route == null) return Double.POSITIVE_INFINITY;
        return route.getTotalTravelTimeMin();
    }
}
