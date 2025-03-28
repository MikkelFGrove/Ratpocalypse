import dk.lima.common.services.IPostEntityProcessingService;
import dk.lima.persistence.PersistentScore;

module Persistence {
    requires Common;
    provides IPostEntityProcessingService with PersistentScore;
}