package master;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        System.out.println("=====Создаем задачу=====");
        Task task_1 = new Task("Оплатить квартплату", "на сайте Дом.клик");
        Task task_2 = new Task("Проверить счетчики", "записать показания счетчиков");
        taskManager.createTask(task_1);
        taskManager.createTask(task_2);
        System.out.println(task_1);
        System.out.println();

        System.out.println("=====Создаем эпик=====");
        Epic epic_1 = new Epic("Отпуск", "с 12 июня 2 недели");
        Epic epic_2 = new Epic("Театральный лагерь", "сезон с 16 июня");
        Epic epic_3 = new Epic("Организовать ДР", "сюрприз сестре");
        taskManager.createEpic(epic_1);
        taskManager.createEpic(epic_2);
        taskManager.createEpic(epic_3);
        System.out.println(epic_1);
        System.out.println();

        System.out.println("======Создаем подзадачу======");
        Subtask subtask_1 = new Subtask("Алтай", "маршрут поездки", 3);
        Subtask subtask_2 = new Subtask("Справка", "офрмить доки по форме", 4);
        Subtask subtask_3 = new Subtask("Список вещей", "накидать список, что с собой", 3);
        Subtask subtask_4 = new Subtask("Кто будет?", "составить список приглашенных", 5);
        Subtask subtask_5 = new Subtask("Ресторан", "выбрать рестик и заказать осн.блюда", 5);
        taskManager.createSubtask(subtask_1);
        taskManager.createSubtask(subtask_2);
        taskManager.createSubtask(subtask_3);
        taskManager.createSubtask(subtask_4);
        taskManager.createSubtask(subtask_5);
        System.out.println(subtask_3);
        System.out.println();

        System.out.println("======Получить таску по id======");
        System.out.println(taskManager.getTaskById(1));
        System.out.println(taskManager.getEpicById(3));
        System.out.println();

        System.out.println("======Список подзадач в листе======");
        System.out.println(taskManager.getArrayListSubtask());
        System.out.println();

        System.out.println("======Список задач эпиков======");
        System.out.println(taskManager.getEpicHashMap());
        System.out.println();

        System.out.println("=====Обновляем таску=====");
        taskManager.updateTaskStatus(2, TaskStatus.IN_PROGRESS);
        System.out.println(taskManager.getTaskById(2));
        taskManager.updateSubtaskStatus(6, TaskStatus.IN_PROGRESS);
        taskManager.updateTaskStatus(8, TaskStatus.DONE);
        System.out.println(taskManager.getEpicById(3)); //проверяем статус эпика
        System.out.println();

        Task taskUpdate = new Task(task_1.getId(), "Оплатить аренду", task_1.getDescriptionTask(), TaskStatus.IN_PROGRESS);
        taskManager.updateTask(taskUpdate); //меняем название таски
        System.out.println(taskManager.getTasksHashMap());
        System.out.println();

        System.out.println("======Удаляем таску=====");
        taskManager.deleteTask(task_1.getId());
        System.out.println(taskManager.getTasksHashMap());
        System.out.println();

        System.out.println("======Удаляем весь список эпиков======");
        taskManager.deleteAllEpic();
        System.out.println(taskManager.getEpicHashMap());
        System.out.println(taskManager.getArrayListSubtask());
    }
}
