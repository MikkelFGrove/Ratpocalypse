package dk.lima.common.entitycomponents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class HealthCPTest {
    private HealthCP healthCP;

    @BeforeEach
    public void setup() {
        healthCP = new HealthCP();
    }

    @Test
    public void test0HPResultsInDeath() {
        healthCP.setHealth(10);
        assertFalse(healthCP.isDead());

        healthCP.setHealth(0);
        assertTrue(healthCP.isDead());
    }

    @Test
    public void testSubtractHealthLowersHealth() {
        double hp = 10;
        double damage = 3;

        healthCP.setHealth(hp);
        assertEquals(hp, healthCP.getHealth());

        healthCP.subtractHealth(3);
        assertEquals(hp - damage, healthCP.getHealth());
    }
}