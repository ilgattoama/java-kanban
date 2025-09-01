package manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultTaskManagerShouldReturnNonNull() {
        assertNotNull(Managers.getDefault());
    }

    @Test
    void getDefaultHistoryManagerShouldReturnNonNull() {
        assertNotNull(Managers.getDefaultHistory());
    }
}
