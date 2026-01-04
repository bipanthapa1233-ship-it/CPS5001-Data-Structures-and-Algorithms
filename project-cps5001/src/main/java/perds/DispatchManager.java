/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perds;
import java.util.ArrayList;
import java.util.List;

public class DispatchManager {
    private final EmergencyNetwork network;
    private final List<DispatchCenter> dispatchCenters;
    private final RoutePlanner routePlanner;
    private final PredictionEngine predictionEngine;
    private final List<Incident> activeIncidents = new ArrayList<>();

    public DispatchManager(EmergencyNetwork network,
                           List<DispatchCenter> dispatchCenters,
                           RoutePlanner routePlanner,
                           PredictionEngine predictionEngine) {
        this.network = network;
        this.dispatchCenters = dispatchCenters;
        this.routePlanner = routePlanner;
        this.predictionEngine = predictionEngine;
    }

    public void handleNewIncident(Incident incident) {
        System.out.println("\n[NEW INCIDENT] " + incident);
        activeIncidents.add(incident);
        predictionEngine.updateHistory(incident);

        ResponseUnit bestUnit = null;
        Route bestRoute = null;
        double bestScore = Double.POSITIVE_INFINITY;

        for (DispatchCenter dc : dispatchCenters) {
            for (ResponseUnit unit : dc.getUnits()) {
                if (unit.getStatus() != UnitStatus.AVAILABLE) continue;

                Route route = routePlanner.findShortestPath(
                        unit.getCurrentLocation(),
                        incident.getLocation(),
                        OptimizationMetric.COMBINED
                );

                if (route == null) continue;

                double eta = routePlanner.computeETA(route);
                double severityWeight = 1.0 / Math.max(1, incident.getSeverity());
                double score = eta * severityWeight;

                if (score < bestScore) {
                    bestScore = score;
                    bestUnit = unit;
                    bestRoute = route;
                }
            }
        }

        if (bestUnit == null) {
            System.out.println("[DISPATCH] No available unit for incident " + incident.getId());
            return;
        }

        bestUnit.assignTo(incident);
        incident.markDispatched();

        System.out.println("[DISPATCH] Assigned unit " + bestUnit.getId() +
                " (" + bestUnit.getType() + ") to incident " + incident.getId());
        System.out.println("[ROUTE] " + bestRoute);
        System.out.printf("[ETA] ~%.2f minutes%n", bestRoute.getTotalTravelTimeMin());
    }

    public void updateUnitStatus(ResponseUnit unit, UnitStatus status) {
        unit.markBusy();
        if (status == UnitStatus.AVAILABLE) {
            unit.markAvailable();
        } else if (status == UnitStatus.OFFLINE) {
            unit.markOffline();
        }
    }

    public void markIncidentResolved(String incidentId) {
        for (Incident inc : activeIncidents) {
            if (inc.getId().equals(incidentId)) {
                inc.markResolved();
                System.out.println("[RESOLVED] " + inc);
                break;
            }
        }
    }

    public List<Incident> getActiveIncidents() {
        return activeIncidents;
    }

    public void suggestPrepositioningForIdleUnits() {
        List<ResponseUnit> idleUnits = new ArrayList<>();
        for (DispatchCenter dc : dispatchCenters) {
            for (ResponseUnit u : dc.getUnits()) {
                if (u.getStatus() == UnitStatus.AVAILABLE) {
                    idleUnits.add(u);
                }
            }
        }

        var suggestions = predictionEngine.suggestPrepositioning(idleUnits, network);
        if (suggestions.isEmpty()) {
            System.out.println("[PREDICTION] No pre-positioning suggestions yet.");
            return;
        }

        System.out.println("\n[PREDICTION] Suggested pre-positioning:");
        suggestions.forEach((unit, loc) -> {
            System.out.println("- Move unit " + unit.getId() + " to " + loc.getName());
        });
    }
}

