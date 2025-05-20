package tasks;

import java.util.Objects;

public class Task {

    private final int id;
    private String nameTask;
    private String descriptionTask;
    private TaskStatus taskStatus;

    private static int nextId = 1;

    public Task (String nameTask, String descriptionTask) {
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.taskStatus = TaskStatus.NEW;
        this.id = nextId++;
    }

    public Task (int id, String nameTask, String descriptionTask, TaskStatus taskStatus) {
        this.id = id;
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.taskStatus  = taskStatus;
    }

    public int getId() {
        return id;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getDescriptionTask() {
        return descriptionTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "\nTask: { " +
                "id: " + id +
                ", название: " + "'" + nameTask + "'"
                + ", описание: " + "'" + descriptionTask + "'"
                + ", статус: " + "'" + taskStatus + "'" + " }";
    }
}
