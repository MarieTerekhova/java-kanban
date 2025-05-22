package tasks;

import java.util.Objects;

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

    public void setEpicId(int id) {
        epicId = id;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Subtask subtask = (Subtask) object;
        return getEpicId() == subtask.getEpicId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEpicId());
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
