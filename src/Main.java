import manager.TaskManager;
import manager.Managers;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        
        Task task1 = new Task(0, "Купить продукты", "Молоко, хлеб, яйца", Status.NEW);
        taskManager.createTask(task1);

        Task task2 = new Task(0, "Сделать домашнее задание", "Математика, страница 45", Status.NEW);
        taskManager.createTask(task2);

        Epic bigTask = new Epic(0, "Подготовка к празднику", "День рождения в субботу");
        taskManager.createEpic(bigTask);

        Subtask subtask1 = new Subtask(0, "Купить торт", "Шоколадный с кремом", Status.NEW, bigTask.getId());
        taskManager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask(0, "Пригласить гостей", "Список из 10 человек", Status.NEW, bigTask.getId());
        taskManager.createSubtask(subtask2);

        task1.setStatus(Status.DONE);
        taskManager.updateTask(task1);

        subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);

        System.out.println("МОИ ЗАДАЧИ");
        System.out.println("\nПростые задачи:");
        for (Task task : taskManager.getTasks()) {
            System.out.println("- " + task.getName() + " (" + getRussianStatus(task.getStatus()) + ")");
        }

        System.out.println("\nСложные задачи:");
        for (Epic epic : taskManager.getEpics()) {
            System.out.println(epic.getName() + " (" + getRussianStatus(epic.getStatus()) + ")");

            System.out.println("  Что нужно сделать:");
            for (Subtask subtask : taskManager.getSubtasks()) {
                if (subtask.getEpicId() == epic.getId()) {
                    System.out.println("  - " + subtask.getName() + " (" + getRussianStatus(subtask.getStatus()) + ")");
                }
            }
        }
    }

    private static String getRussianStatus(Status status) {
        if (status == Status.NEW) return "Не начато";
        if (status == Status.IN_PROGRESS) return "Выполняется";
        if (status == Status.DONE) return "Готово";
        return "";
    }
}