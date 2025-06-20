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
        Task task2 = new Task(0, "Сделать уроки", "Математика, русский", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Epic epic1 = new Epic(0, "Ремонт в комнате", "Полный ремонт");
        taskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask(0, "Купить обои", "Цвет: бежевый", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask(0, "Нанять рабочих", "Отделочники", Status.NEW, epic1.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        task1.setStatus(Status.DONE);
        subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task1);
        taskManager.updateSubtask(subtask1);

        System.out.println("СПИСОК ЗАДАЧ:");
        System.out.println("\nПростые задачи:");
        taskManager.getTasks().forEach(task ->
                System.out.println(task.getName() + " - " + getStatus(task.getStatus())));

        System.out.println("\nСложные задачи:");
        taskManager.getEpics().forEach(epic -> {
            System.out.println(epic.getName() + " - " + getStatus(epic.getStatus()));
            taskManager.getSubtasks().stream()
                    .filter(sub -> sub.getEpicId() == epic.getId())
                    .forEach(sub -> System.out.println("  " + sub.getName() + " - " + getStatus(sub.getStatus())));
        });
    }

    private static String getStatus(Status status) {
        switch(status) {
            case NEW: return "Не начато";
            case IN_PROGRESS: return "В работе";
            case DONE: return "Выполнено";
            default: return "";
        }
    }
}