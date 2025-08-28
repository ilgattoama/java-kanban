package test.manager;

import manager.FileBackedTaskManager;
import manager.Managers;
import task.Task;
import task.Status;
import task.Epic;
import task.Subtask;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    @Test
    void saveAndLoadEmptyFile() throws IOException {
        File tempFile = File.createTempFile("test", ".csv");

        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile);
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertTrue(loadedManager.getTasks().isEmpty());
        assertTrue(loadedManager.getEpics().isEmpty());
        assertTrue(loadedManager.getSubtasks().isEmpty());

        tempFile.delete();
    }

    @Test
    void saveAndLoadTasks() throws IOException {
        File tempFile = File.createTempFile("test", ".csv");

        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile);

        Task task1 = new Task(0, "Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(0, "Task 2", "Description 2", Status.IN_PROGRESS);

        manager.createTask(task1);
        manager.createTask(task2);

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(2, loadedManager.getTasks().size());
        assertEquals("Task 1", loadedManager.getTask(task1.getId()).getName());
        assertEquals("Task 2", loadedManager.getTask(task2.getId()).getName());

        tempFile.delete();
    }

    @Test
    void saveAndLoadEpicWithSubtasks() throws IOException {
        File tempFile = File.createTempFile("test", ".csv");

        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile);

        Epic epic = new Epic(0, "Epic", "Description");
        manager.createEpic(epic);

        Subtask subtask1 = new Subtask(0, "Subtask 1", "Desc", Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask(0, "Subtask 2", "Desc", Status.DONE, epic.getId());

        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(1, loadedManager.getEpics().size());
        assertEquals(2, loadedManager.getSubtasks().size());
        assertEquals(2, loadedManager.getEpic(epic.getId()).getSubtaskIds().size());

        tempFile.delete();
    }
}