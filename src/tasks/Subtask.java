package tasks;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String nameTask, String descriptionTask, TaskStatus taskStatus, int epicId) {
        super(nameTask, descriptionTask, taskStatus);
        this.epicId = epicId;
    }

    public Subtask(int id, String name, String description, TaskStatus status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask: {" +
                "id: " + getId() + ", название: " + "'" + getNameTask() + "'"
                + ", описание: " + "'" + getDescriptionTask() + "'"
                + ", статус: " + "'" + getTaskStatus() + "'"
                + ", epicId: " + epicId + "}";
    }
}
