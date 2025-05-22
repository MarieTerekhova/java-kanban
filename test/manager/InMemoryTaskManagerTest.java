package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {

    private TaskManager taskManager;
    private Task testTask;
    private Epic testEpic;
    private Subtask testSubtask;

    @BeforeEach
    void addNewTask() {
        taskManager = Managers.getDefault();
        testTask = new Task("nameTaskTest", "descriptionTaskTest", TaskStatus.NEW);
        testEpic = new Epic("nameEpicTest", "descriptionEpicTest");
        testSubtask = new Subtask("nameSubtaskTest", "descriptionSubtaskTest", TaskStatus.NEW, testEpic.getId());
    }

    @Test
    void testCreateTask() {
        taskManager.createTask(testTask);
        final int taskId = testTask.getId();
        final Task savedTask = taskManager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(testTask, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(testTask, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void testSubtaskEqualityById() {
        taskManager.createEpic(testEpic);
        testSubtask.setEpicId(testEpic.getId());
        taskManager.createSubtask(testSubtask);

        final int subtaskId = testSubtask.getId();
        final Subtask savedSabtask = taskManager.getSubtaskById(subtaskId);

        assertNotNull(savedSabtask, "Подзадача не найдена.");
        assertEquals(testSubtask, savedSabtask, "Подзадачи не совпадают.");

        final List<Subtask> subtasks = taskManager.getAllSubtask();

        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(testSubtask, subtasks.get(0), "Подзадачи не совпадают.");
    }

    @Test
    void epicCannotAddItselfAsSubtask() {
        taskManager.createEpic(testEpic);
        taskManager.createSubtask(testSubtask);
        testSubtask.setId(testEpic.getId());

        List<Subtask> epicSubtasks = taskManager.getEpicSubtasks(testEpic.getId());
        boolean isContainsSelf = false;
        for (Subtask subtask1 : epicSubtasks) {
            if (subtask1.getId() == testEpic.getId()) {
                isContainsSelf = true;
                break;
            }
        }
            assertFalse(isContainsSelf, "Эпик не должен содержать себя в качестве подзадачи");
            // Дополнительная проверка - количество подзадач должно быть 0
            assertEquals(0, epicSubtasks.size(), "У эпика не должно быть подзадач после попытки добавить себя");
    }

    @Test
    void testSubtaskCannotBeItsOwnEpicSilentFail() {
        taskManager.createEpic(testEpic);
        testSubtask.setEpicId(testEpic.getId());
        taskManager.createSubtask(testSubtask);

        testSubtask.setEpicId(testSubtask.getId());

        assertThrows(IllegalArgumentException.class,
                () -> taskManager.updateSubtask(testSubtask),
                "Система должна предотвращать циклические ссылки");
    }

    @Test
    void managersShouldBeInitializedAndReady() {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(taskManager, "Менеджер задач должен быть проинициализирован");
        assertNotNull(historyManager, "Менеджер истории должен быть проинициализирован");
    }

    @Test
    void testTaskImmutabilityWhenAdded() {
        Task originalTask = new Task("Original", "Original");
        taskManager.createTask(originalTask);
        int taskId = originalTask.getId();

        Task retrievedTask = taskManager.getTaskById(taskId);
        assertEquals(originalTask.getNameTask(), retrievedTask.getNameTask());
        assertEquals(originalTask.getDescriptionTask(), retrievedTask.getDescriptionTask());
        assertEquals(originalTask.getTaskStatus(), retrievedTask.getTaskStatus());
    }

    @Test
    void testAddAndFindDifferentTaskTypes() {
        taskManager.createTask(testTask);
        int taskId = testTask.getId();
        taskManager.createEpic(testEpic);
        int epicId = testEpic.getId();
        testSubtask.setEpicId(testEpic.getId());
        taskManager.createSubtask(testSubtask);
        int subtaskId = testSubtask.getId();

        assertNotNull(taskManager.getTaskById(taskId));
        assertNotNull(taskManager.getEpicById(epicId));
        assertNotNull(taskManager.getSubtaskById(subtaskId));
    }

    @Test
    void testNoIdConflictBetweenManualAndGeneratedIds() {
        Task manualIdTask = new Task("Manual", "Manual");
        taskManager.createTask(manualIdTask);
        manualIdTask.setId(999);

        Task autoIdTask = new Task("Auto", "Auto");
        taskManager.createTask(autoIdTask);
        int autoId = autoIdTask.getId();
        assertNotEquals(999, autoId, "ID не должны конфликтовать");
    }

    @Test
    void testDeleteMethods() {
        taskManager.createTask(testTask);
        int taskId = testTask.getId();
        taskManager.createEpic(testEpic);
        int epicId = testEpic.getId();
        taskManager.createSubtask(testSubtask);
        int subtaskId = testSubtask.getId();

        taskManager.deleteTask(taskId);
        assertNull(taskManager.getTaskById(taskId));

        taskManager.deleteSubtask(subtaskId);
        assertNull(taskManager.getSubtaskById(subtaskId));

        taskManager.deleteEpic(epicId);
        assertNull(taskManager.getEpicById(epicId));
    }

    @Test
    void testBulkDeleteMethods() {
        taskManager.createTask(new Task("1", "1"));
        taskManager.createTask(new Task("2", "2"));

        taskManager.createEpic(new Epic("3", "3"));
        taskManager.createEpic(new Epic("4", "4"));

        taskManager.deleteAllTasks();
        assertEquals(0, taskManager.getAllTasks().size());

        taskManager.deleteAllEpic();
        assertEquals(0, taskManager.getAllEpic().size());
    }
}