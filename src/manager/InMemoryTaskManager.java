package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    private HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtasksHashMap = new HashMap<>();

    private int nextId = 1;
    private int generatorNextId() {
        return nextId++;
    }

    @Override
    public int getNextId() {
        return nextId;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void addTaskinHistory(Task task) {
        historyManager.addTaskinHistory(task);
    }

    @Override
    public Task getTaskById(int id) {
        Task task = taskHashMap.get(id);
        addTaskinHistory(task);
        return taskHashMap.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        Task task = epicHashMap.get(id);
        addTaskinHistory(task);
        return epicHashMap.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Task task = subtasksHashMap.get(id);
        addTaskinHistory(task);
        return subtasksHashMap.get(id);
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskHashMap.values());
    }

    @Override
    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epicHashMap.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtask() {
        return new ArrayList<>(subtasksHashMap.values());
    }

    @Override
    public void createTask(Task task) {
        task.setId(generatorNextId());
        taskHashMap.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(generatorNextId());
        epicHashMap.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        Epic epic = epicHashMap.get(subtask.getEpicId());
        if (epic != null) {
            subtask.setId(generatorNextId());
            subtasksHashMap.put(subtask.getId(), subtask);
            epic.addSubtask(subtask.getId());
            updateEpicStatus(epic.getId());
        }
    }

    @Override
    public void updateEpicStatus(int epicId) {
        Epic epic = epicHashMap.get(epicId);
        if (epic == null) return;

        if (epic.getSubtasksList().isEmpty()) {
            epic.setTaskStatus(TaskStatus.NEW);
            return;
        }
        boolean allDone = true;
        boolean anyInProgress = false;

        for (int subtaskId : epic.getSubtasksList()) {
            Subtask subtask = subtasksHashMap.get(subtaskId);
            if (subtask == null)
                continue;
            if (subtask.getTaskStatus() != TaskStatus.DONE) {
                allDone = false;
            }
            if (subtask.getTaskStatus() == TaskStatus.IN_PROGRESS) {
                anyInProgress = true;
            }
        }
        if (allDone) {
            epic.setTaskStatus(TaskStatus.DONE);
        } else if (anyInProgress) {
            epic.setTaskStatus(TaskStatus.IN_PROGRESS);
        } else {
            epic.setTaskStatus(TaskStatus.NEW);
        }
    }

    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> result = new ArrayList<>();
        Epic epic = epicHashMap.get(epicId);
        if (epic != null) {
            for (int subtaskId : epic.getSubtasksList()) {
                Subtask subtask = subtasksHashMap.get(subtaskId);
                if (subtask != null) {
                    result.add(subtask);
                }
            }
        }
        return result;
    }

    @Override
    public boolean updateTask(Task task) {
        boolean isExistenceOfTask;
        if (taskHashMap.containsKey(task.getId())) {
            taskHashMap.put(task.getId(), task);
            isExistenceOfTask = true;
        } else {
            isExistenceOfTask = false;
        }
        return isExistenceOfTask;
    }

    @Override
    public boolean updateEpic(Epic epic) {
        boolean isExistenceOfTask;
        Epic epicUpdate = epicHashMap.get(epic.getId());
        if (epicUpdate != null) {
            epicUpdate.setNameTask(epic.getNameTask());
            epicUpdate.setDescriptionTask(epic.getDescriptionTask());
            isExistenceOfTask = true;
        } else {
            isExistenceOfTask = false;
        }
        return isExistenceOfTask;
    }

    @Override
    public boolean updateSubtask(Subtask subtask) {
        boolean isExistenceOfTask = true;
        if (subtask.getId() == subtask.getEpicId()) {
               throw new IllegalArgumentException("Подзадача не может быть своим эпиком");
        } else {
            Subtask savedSubtask = subtasksHashMap.get(subtask.getId());
            if (savedSubtask != null) {
                savedSubtask.setNameTask(subtask.getNameTask());
                savedSubtask.setDescriptionTask(subtask.getDescriptionTask());
                savedSubtask.setTaskStatus(subtask.getTaskStatus());
                updateEpicStatus(savedSubtask.getEpicId());
            }
        }
        return isExistenceOfTask;
    }

    @Override
    public void deleteTask(int id) {
        taskHashMap.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epicHashMap.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtasksList()) {
                subtasksHashMap.remove(subtaskId);
            }
        }
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subtasksHashMap.remove(id);
        if (subtask != null) {
            Epic epic = epicHashMap.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(id);
                updateEpicStatus(epic.getId());
            }
        }
    }

    @Override
    public void deleteAllTasks() {
        taskHashMap.clear();
    }

    @Override
    public void deleteAllEpic() {
        epicHashMap.clear();
        subtasksHashMap.clear();
    }

    @Override
    public void deleteAllSubasks() {
        subtasksHashMap.clear();
        for (Epic epic : epicHashMap.values()) {
            epic.clearSubtasks();
            updateEpicStatus(epic.getId());
        }
    }
}