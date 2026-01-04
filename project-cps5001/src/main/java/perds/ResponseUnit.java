/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perds;

public class ResponseUnit {
    private final String id;
    private final UnitType type;
    private UnitStatus status;
    private Location currentLocation;
    private Incident assignedIncident;

    public ResponseUnit(String id, UnitType type, Location currentLocation) {
        this.id = id;
        this.type = type;
        this.currentLocation = currentLocation;
        this.status = UnitStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public UnitType getType() {
        return type;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public Incident getAssignedIncident() {
        return assignedIncident;
    }

    public void assignTo(Incident incident) {
        this.assignedIncident = incident;
        this.status = UnitStatus.EN_ROUTE;
    }

    public void updateLocation(Location location) {
        this.currentLocation = location;
    }

    public void markAvailable() {
        this.status = UnitStatus.AVAILABLE;
        this.assignedIncident = null;
    }

    public void markOffline() {
        this.status = UnitStatus.OFFLINE;
    }

    public void markBusy() {
        this.status = UnitStatus.BUSY;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", at=" + (currentLocation != null ? currentLocation.getName() : "unknown") +
                '}';
    }
}
