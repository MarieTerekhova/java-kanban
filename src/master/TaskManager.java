package master;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {

    private HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtasksHashMap = new HashMap<>();

    public Task getTaskById(int id) {
        return taskHashMap.get(id);
    }

    public Epic getEpicById(int id) {
        return epicHashMap.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasksHashMap.get(id);
    }

    public ArrayList<Task> getTasksHashMap() {
        return new ArrayList<>(taskHashMap.values());
    }

    public ArrayList<Epic> getEpicHashMap() {
        return new ArrayList<>(epicHashMap.values());
    }

    public ArrayList<Subtask> getArrayListSubtask() {
        return new ArrayList<>(subtasksHashMap.values());
    }

    public void createTask(Task task) {
        taskHashMap.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epicHashMap.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        subtasksHashMap.put(subtask.getId(), subtask);
        Epic epic = epicHashMap.get(subtask.getEpicId());
        if (epic != null) {
           epic.addSubtask(subtask.getId());
           updateEpicStatus(epic.getId());
        }
    }

    public void updateTaskStatus(int taskId, TaskStatus taskStatus) {
        Task task = taskHashMap.get(taskId);
        if (task!= null) {
            task.setTaskStatus(taskStatus);
        }
    }

    public void updateSubtaskStatus (int subtaskId, TaskStatus taskStatus) {
        Subtask subtask = subtasksHashMap.get(subtaskId);
        if (subtask != null) {
            subtask.setTaskStatus(taskStatus);
            updateEpicStatus(subtask.getEpicId());
        }
    }

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

    public Task updateTask (Task task) {
        taskHashMap.put(task.getId(), task);
        return task;
    }

    public Task deleteTask(int id) {
        return taskHashMap.remove(id);
    }

    public Epic deleteEpic(int id) {
        return epicHashMap.remove(id);
    }

    public Subtask deleteSubtask(int id) {
        return subtasksHashMap.remove(id);
    }

    public void deleteAllTasks() {
        taskHashMap.clear();
    }

    public void deleteAllEpic() {
        epicHashMap.clear();
        deleteAllSubasks();
    }

    public void deleteAllSubasks() {
        subtasksHashMap.clear();
    }
}
