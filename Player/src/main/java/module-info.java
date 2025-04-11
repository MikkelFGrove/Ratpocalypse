import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.weapon.IWeaponSPI;
import dk.lima.playersystem.PlayerControlSystem;
import dk.lima.playersystem.PlayerPlugin;

module Player {
    requires Common;
    requires CommonPlayer;
    requires CommonWeapon;
    requires CommonEntityCP;

    provides IGamePluginService with PlayerPlugin;
    provides IEntityProcessingService with PlayerControlSystem;

    uses IWeaponSPI;
    uses IEntityComponent;
}