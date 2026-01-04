/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perds;

import java.util.ArrayList;
import java.util.List;

public class DispatchCenter extends Location {
    private final List<ResponseUnit> units = new ArrayList<>();

    public DispatchCenter(String id, String name) {
        super(id, name, LocationType.DISPATCH_CENTER);
    }

    public void addUnit(ResponseUnit unit) {
        units.add(unit);
    }

    public void removeUnit(String unitId) {
        units.removeIf(u -> u.getId().equals(unitId));
    }

    public List<ResponseUnit> getUnits() {
        return units;
    }

    public List<ResponseUnit> getAvailableUnits() {
        List<ResponseUnit> result = new ArrayList<>();
        for (ResponseUnit u : units) {
            if (u.getStatus() == UnitStatus.AVAILABLE) {
                result.add(u);
            }
        }
        return result;
    }
}
