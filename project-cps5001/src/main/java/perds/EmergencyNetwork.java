/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perds;
import java.util.*;

public class EmergencyNetwork {
    private final Map<String, Location> locations = new HashMap<>();
    // adjacency list keyed by location id
    private final Map<String, List<Road>> adjacency = new HashMap<>();

    public void addLocation(Location location) {
        locations.put(location.getId(), location);
        adjacency.putIfAbsent(location.getId(), new ArrayList<>());
    }

    public void removeLocation(String id) {
        locations.remove(id);
        adjacency.remove(id);
        for (List<Road> roads : adjacency.values()) {
            roads.removeIf(r -> r.getTo().getId().equals(id));
        }
    }

    public void addRoad(Location from, Location to,
                        double distanceKm, double travelTimeMin, double congestionLevel) {
        Road forward = new Road(from, to, distanceKm, travelTimeMin, congestionLevel);
        Road backward = new Road(to, from, distanceKm, travelTimeMin, congestionLevel);
        adjacency.get(from.getId()).add(forward);
        adjacency.get(to.getId()).add(backward);
    }

    public void updateRoad(String fromId, String toId,
                           double distanceKm, double travelTimeMin, double congestionLevel) {
        List<Road> fromList = adjacency.get(fromId);
        if (fromList != null) {
            for (Road road : fromList) {
                if (road.getTo().getId().equals(toId)) {
                    road.setDistanceKm(distanceKm);
                    road.setTravelTimeMin(travelTimeMin);
                    road.setCongestionLevel(congestionLevel);
                }
            }
        }

        List<Road> toList = adjacency.get(toId);
        if (toList != null) {
            for (Road road : toList) {
                if (road.getTo().getId().equals(fromId)) {
                    road.setDistanceKm(distanceKm);
                    road.setTravelTimeMin(travelTimeMin);
                    road.setCongestionLevel(congestionLevel);
                }
            }
        }
    }

    public List<Road> getNeighbours(Location location) {
        return adjacency.getOrDefault(location.getId(), Collections.emptyList());
    }

    public Location getLocationById(String id) {
        return locations.get(id);
    }

    public Collection<Location> getAllLocations() {
        return locations.values();
    }
}
