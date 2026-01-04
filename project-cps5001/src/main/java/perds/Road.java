/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perds;

public class Road {
    private final Location from;
    private final Location to;
    private double distanceKm;
    private double travelTimeMin;
    private double congestionLevel; // 0.0 - 1.0

    public Road(Location from, Location to,
                double distanceKm, double travelTimeMin, double congestionLevel) {
        this.from = from;
        this.to = to;
        this.distanceKm = distanceKm;
        this.travelTimeMin = travelTimeMin;
        this.congestionLevel = congestionLevel;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public double getTravelTimeMin() {
        return travelTimeMin;
    }

    public void setTravelTimeMin(double travelTimeMin) {
        this.travelTimeMin = travelTimeMin;
    }

    public double getCongestionLevel() {
        return congestionLevel;
    }

    public void setCongestionLevel(double congestionLevel) {
        this.congestionLevel = congestionLevel;
    }

    @Override
    public String toString() {
        return from.getName() + " -> " + to.getName() +
                " [dist=" + distanceKm + "km, time=" + travelTimeMin +
                "min, congestion=" + congestionLevel + "]";
    }
}
