/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perds;
import java.util.*;

public class PredictionEngine {
    private final Map<String, Integer> incidentCounts = new HashMap<>();

    public void updateHistory(Incident incident) {
        String locId = incident.getLocation().getId();
        incidentCounts.put(locId, incidentCounts.getOrDefault(locId, 0) + 1);
    }

    public List<Location> getHotspots(EmergencyNetwork network, int topN) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(incidentCounts.entrySet());
        entries.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        List<Location> result = new ArrayList<>();
        for (int i = 0; i < Math.min(topN, entries.size()); i++) {
            String locId = entries.get(i).getKey();
            Location loc = network.getLocationById(locId);
            if (loc != null) {
                result.add(loc);
            }
        }
        return result;
    }

    public Map<ResponseUnit, Location> suggestPrepositioning(List<ResponseUnit> idleUnits,
                                                             EmergencyNetwork network) {
        Map<ResponseUnit, Location> suggestions = new HashMap<>();
        if (idleUnits.isEmpty() || incidentCounts.isEmpty()) return suggestions;

        List<Location> hotspots = getHotspots(network, idleUnits.size());
        for (int i = 0; i < idleUnits.size(); i++) {
            Location target = hotspots.get(i % hotspots.size());
            suggestions.put(idleUnits.get(i), target);
        }
        return suggestions;
    }
}
