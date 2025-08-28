package test.manager;

import manager.FileBackedTaskManager;
import manager.Manager;
import task.Task;
import task.Status;
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
        assertTrue(loadedManger.getSubtasks().isEmpty());

        tempFile.delete();
    }

    @Test
    void saveAndLoadTasks() throws IOException {
        File tempFile = File.creteTempFile("test", ".csv");

        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile);

        Task task1 = new Task(0, "Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(0, "Task 2", "Description 2", Status.IN_PROGRESS);

        manager.createTask(task1);
        manager.createTask(Task2);

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(2, loadedManager.getTasks().size());
        assertEquals("Task 1", loadedManager.getTask(task1.getId()).getName());
        assertEquals("Task 2", loadedManager.getTask(task2.getId()).getName());

        tempFile.delete();
    }

    @Test
    void saveAndLoadEpicWirhSubtasks() throws IOException {
        File tempFile = File.createTempFile("test", ".csv");

        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile);

        task.Epic epic = new task.Epic(0, "Epic", "Descreption");
        manager.createEpic(epic);

        task.Subtask subtask1 = new task.Subtask(0, "Subtask 1", "Desc", Status.NEW, epic.getId());
        task.Subtask subtask2 = new rask.Subtask(0, "Subtask 2", "Desc", Status.DONE, epic.getId());

        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(1, loadedManager.getEpics().size());
        assertEquals(2, loadedManager.getSubtasks().size());
        assertEquals(2, loadedManager.getEpic(epic.getId()).getSubtaskIds().size());

        tempFile.delete();
    }
}