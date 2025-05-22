package master;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtasksHashMap = new HashMap<>();

    private int nextId = 1;

    private int generatorNextId() {
        return nextId++;
    }

    public Task getTaskById(int id) {
        return taskHashMap.get(id);
    }

    public Epic getEpicById(int id) {
        return epicHashMap.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasksHashMap.get(id);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskHashMap.values());
    }

    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epicHashMap.values());
    }

    public ArrayList<Subtask> getAllSubtask() {
        return new ArrayList<>(subtasksHashMap.values());
    }

    public void createTask(Task task) {
        task.setId(generatorNextId());
        taskHashMap.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(generatorNextId());
        epicHashMap.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        Epic epic = epicHashMap.get(subtask.getEpicId());
        if (epic != null) {
            subtask.setId(generatorNextId());
            subtasksHashMap.put(subtask.getId(), subtask);
            epic.addSubtask(subtask.getId());
            updateEpicStatus(epic.getId());
        }
    }

    private void updateEpicStatus(int epicId) {
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

    public boolean updateEpic(Epic epic) {
        boolean isExistenceOfTask;
        Epic epicUpdate = epicHashMap.get(epic.getId());
        if (epicUpdate != null) {
            epicUpdate.setNameTask(epic.getNameTask());
            epicUpdate.setDescriptionTask(epic.getDescriptionTask());
            isExistenceOfTask = true;
        } else {
            isExistenceOfTask= false;
        }
        return isExistenceOfTask;
    }

    public boolean updateSubtask(Subtask subtask) {
        boolean isExistenceOfTask;
        Subtask savedSubtask = subtasksHashMap.get(subtask.getId());
        if (savedSubtask != null) {
            savedSubtask.setNameTask(subtask.getNameTask());
            savedSubtask.setDescriptionTask(subtask.getDescriptionTask());
            savedSubtask.setTaskStatus(subtask.getTaskStatus());
            updateEpicStatus(savedSubtask.getEpicId());
            isExistenceOfTask = true;
        } else {
            isExistenceOfTask = false;
        }
        return isExistenceOfTask;
    }

    public void deleteTask(int id) {
        taskHashMap.remove(id);
    }

    public void deleteEpic(int id) {
        Epic epic = epicHashMap.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtasksList()) {
                subtasksHashMap.remove(subtaskId);
            }
        }
    }

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

    public void deleteAllTasks() {
        taskHashMap.clear();
    }

    public void deleteAllEpic() {
        epicHashMap.clear();
        subtasksHashMap.clear();
    }

    public void deleteAllSubasks() {
        subtasksHashMap.clear();
        for (Epic epic : epicHashMap.values()) {
            epic.clearSubtasks();
            updateEpicStatus(epic.getId());
        }
    }
}
