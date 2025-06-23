package manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void defaultTaskManagerIsNotNull() {
        assertNotNull(Managers.getDefault());
    }

    @Test
    void defaultHistoryManagerIsNotNull() {
        assertNotNull(Managers.getDefaultHistory());
    }
}
