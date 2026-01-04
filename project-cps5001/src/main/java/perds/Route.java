/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {
    private final List<Location> path;
    private final double totalDistanceKm;
    private final double totalTravelTimeMin;

    public Route(List<Location> path, double totalDistanceKm, double totalTravelTimeMin) {
        this.path = new ArrayList<>(path);
        this.totalDistanceKm = totalDistanceKm;
        this.totalTravelTimeMin = totalTravelTimeMin;
    }

    public List<Location> getPath() {
        return Collections.unmodifiableList(path);
    }

    public double getTotalDistanceKm() {
        return totalDistanceKm;
    }

    public double getTotalTravelTimeMin() {
        return totalTravelTimeMin;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Route: ");
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i).getName());
            if (i < path.size() - 1) sb.append(" -> ");
        }
        sb.append(String.format(" [%.2f km, %.2f min]", totalDistanceKm, totalTravelTimeMin));
        return sb.toString();
    }
}
