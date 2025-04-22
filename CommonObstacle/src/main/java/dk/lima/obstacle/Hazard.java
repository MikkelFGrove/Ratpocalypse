package dk.lima.obstacle;

import dk.lima.common.data.EEntityTypes;
import dk.lima.common.entity.Entity;

public class Hazard extends Entity {
    @Override
    public EEntityTypes getEntityType() {
        return EEntityTypes.HAZARD;
    }
}
