package tasks;

public class Task {

    protected int id;
    protected String nameTask;
    protected String descriptionTask;
    protected TaskStatus taskStatus;

    public Task(String nameTask, String descriptionTask) {
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.taskStatus = TaskStatus.NEW;
    }

    public Task(String nameTask, String descriptionTask, TaskStatus taskStatus) {
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.taskStatus = taskStatus;
    }

    public Task(int id, String nameTask, String descriptionTask, TaskStatus taskStatus) {
        this.id = id;
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.taskStatus = taskStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Task: { " +
                "id: " + id +
                ", название: " + "'" + nameTask + "'"
                + ", описание: " + "'" + descriptionTask + "'"
                + ", статус: " + "'" + taskStatus + "'" + " }";
    }
}
