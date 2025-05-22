import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();

        // Тестирование функционала
        Task task1 = new Task("Оплатить квартплату", "на сайте Дом.клик", TaskStatus.NEW);
        Task task2 = new Task("Проверить счетчики", "записать показания счетчиков", TaskStatus.IN_PROGRESS);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Отпуск", "с 12 июня 2 недели");
        Epic epic2 = new Epic("Театральный лагерь", "сезон с 16 июня");
        Epic epic3 = new Epic("Организовать ДР", "сюрприз сестре");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createEpic(epic3);

        Subtask subtask1 = new Subtask("Алтай", "маршрут поездки", TaskStatus.NEW, 3);
        Subtask subtask2 = new Subtask("Справка", "офрмить доки по форме", TaskStatus.NEW, 4);
        Subtask subtask3 = new Subtask("Список вещей", "накидать список, что с собой", TaskStatus.NEW, 3);
        Subtask subtask4 = new Subtask("Кто будет?", "составить список приглашенных", TaskStatus.NEW, 5);
        Subtask subtask5 = new Subtask("Ресторан", "выбрать рестик и заказать осн.блюда", TaskStatus.NEW, 5);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);
        taskManager.createSubtask(subtask4);
        taskManager.createSubtask(subtask5);

        System.out.println("Все задачи:");
        printAllTasks(taskManager);

        // Обновление статусов
        taskManager.updateTask(new Task(task1.getId(), "Ремонт", "Купить коробки, скотч, упаков.пленку", TaskStatus.IN_PROGRESS));
        taskManager.updateSubtask(new Subtask(subtask1.getId(), "Поездка в Алтай", subtask1.getDescriptionTask(), TaskStatus.IN_PROGRESS, epic1.getId()));
        taskManager.updateSubtask(new Subtask(subtask2.getId(), subtask2.getNameTask(), "записаться в поликл.на прием", TaskStatus.DONE, epic2.getId()));

        System.out.println("\nПосле обновления статусов:");
        printAllTasks(taskManager);

        // Вызов истории
        taskManager.getTaskById(task1.getId());
        taskManager.getEpicById(epic3.getId());
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.getSubtaskById(subtask4.getId());
        printHistory((InMemoryTaskManager) taskManager); // история

        // Тестирование удаления
        taskManager.deleteSubtask(subtask1.getId());
        System.out.println("\nПосле удаления подзадачи:");
        printAllTasks(taskManager);

        System.out.println("\nПодзадачи эпика:");
        System.out.println(taskManager.getEpicSubtasks(epic1.getId()));
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Обычные задачи:");
        manager.getAllTasks().forEach(System.out::println);

        System.out.println("\nЭпики:");
        manager.getAllEpic().forEach(System.out::println);

        System.out.println("\nПодзадачи:");
        manager.getAllSubtask().forEach(System.out::println);
    }

    private static void printHistory (InMemoryTaskManager taskManager){
        System.out.println("\nИстория просмотров:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }
}
