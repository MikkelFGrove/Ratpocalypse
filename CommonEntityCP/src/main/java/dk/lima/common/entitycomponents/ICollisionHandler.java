package dk.lima.common.entitycomponents;

import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.IEntityComponent;

public interface ICollisionHandler extends IEntityComponent {
    void onCollide(Entity other, World world);
}
