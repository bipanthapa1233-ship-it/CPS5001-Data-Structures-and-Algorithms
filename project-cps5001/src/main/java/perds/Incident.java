/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perds;

import java.time.LocalDateTime;

public class Incident {
    private final String id;
    private final IncidentType type;
    private final int severity; // 1 (low) to 5 (critical)
    private final Location location;
    private IncidentStatus status;
    private final LocalDateTime reportedAt;
    private LocalDateTime resolvedAt;

    public Incident(String id, IncidentType type, int severity, Location location) {
        this.id = id;
        this.type = type;
        this.severity = severity;
        this.location = location;
        this.status = IncidentStatus.NEW;
        this.reportedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public IncidentType getType() {
        return type;
    }

    public int getSeverity() {
        return severity;
    }

    public Location getLocation() {
        return location;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void markDispatched() {
        this.status = IncidentStatus.DISPATCHED;
    }

    public void markResolved() {
        this.status = IncidentStatus.RESOLVED;
        this.resolvedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", severity=" + severity +
                ", location=" + location.getName() +
                ", status=" + status +
                '}';
    }
}
