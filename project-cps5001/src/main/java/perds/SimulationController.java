/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perds;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationController {
    private final EmergencyNetwork network;
    private final DispatchManager dispatchManager;
    private final List<DispatchCenter> dispatchCenters;
    private final Random random = new Random();

    private final List<Location> cityLocations = new ArrayList<>();

    public SimulationController() {
        this.network = new EmergencyNetwork();
        this.dispatchCenters = new ArrayList<>();
        RoutePlanner routePlanner = new RoutePlanner(network);
        PredictionEngine predictionEngine = new PredictionEngine();
        this.dispatchManager = new DispatchManager(network, dispatchCenters, routePlanner, predictionEngine);

        initialiseNetwork();
        initialiseDispatchCentersAndUnits();
    }

    private void initialiseNetwork() {
        Location cityA = new Location("C1", "Alpha City", LocationType.CITY);
        Location cityB = new Location("C2", "Bravo Town", LocationType.CITY);
        Location cityC = new Location("C3", "Charlie City", LocationType.CITY);
        Location cityD = new Location("C4", "Delta City", LocationType.CITY);

        network.addLocation(cityA);
        network.addLocation(cityB);
        network.addLocation(cityC);
        network.addLocation(cityD);

        cityLocations.add(cityA);
        cityLocations.add(cityB);
        cityLocations.add(cityC);
        cityLocations.add(cityD);

        // Core city graph
        network.addRoad(cityA, cityB, 10, 12, 0.2);
        network.addRoad(cityB, cityC, 15, 18, 0.3);
        network.addRoad(cityC, cityD, 8, 9, 0.1);
        network.addRoad(cityA, cityD, 25, 30, 0.5);
        network.addRoad(cityB, cityD, 12, 14, 0.4);
    }

    private void initialiseDispatchCentersAndUnits() {
        DispatchCenter dc1 = new DispatchCenter("D1", "Alpha Dispatch");
        DispatchCenter dc2 = new DispatchCenter("D2", "Charlie Dispatch");

        network.addLocation(dc1);
        network.addLocation(dc2);

        dispatchCenters.add(dc1);
        dispatchCenters.add(dc2);

        Location cityA = cityLocations.get(0);
        Location cityB = cityLocations.get(1);
        Location cityC = cityLocations.get(2);
        Location cityD = cityLocations.get(3);

        network.addRoad(cityA, dc1, 1.0, 2.0, 0.1);
        network.addRoad(cityB, dc1, 1.5, 3.0, 0.2);

        network.addRoad(cityC, dc2, 1.0, 2.0, 0.1);
        network.addRoad(cityD, dc2, 1.5, 3.0, 0.2);

        ResponseUnit u1 = new ResponseUnit("U1", UnitType.AMBULANCE, dc1);
        ResponseUnit u2 = new ResponseUnit("U2", UnitType.FIRE_TRUCK, dc1);
        ResponseUnit u3 = new ResponseUnit("U3", UnitType.AMBULANCE, dc2);
        ResponseUnit u4 = new ResponseUnit("U4", UnitType.POLICE, dc2);

        dc1.addUnit(u1);
        dc1.addUnit(u2);
        dc2.addUnit(u3);
        dc2.addUnit(u4);
    }

    public void runSimulation() {
        System.out.println("=== Predictive Emergency Response Dispatch System Simulation ===");

        for (int i = 1; i <= 5; i++) {
            Incident incident = generateRandomIncident(i);
            dispatchManager.handleNewIncident(incident);

            if (i % 2 == 0) {
                dispatchManager.suggestPrepositioningForIdleUnits();
            }
        }

        System.out.println("\n=== Simulation complete ===");
    }

    private Incident generateRandomIncident(int index) {
        Location loc = cityLocations.get(random.nextInt(cityLocations.size()));
        IncidentType type = IncidentType.values()[random.nextInt(IncidentType.values().length)];
        int severity = random.nextInt(5) + 1;
        String id = "INC" + index;

        Incident incident = new Incident(id, type, severity, loc);
        System.out.println("\n[SIM] Generated incident: " + incident);
        return incident;
    }
}
