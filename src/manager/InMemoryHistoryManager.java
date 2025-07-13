package manager;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    Node first;
    Node last;
    Map<Integer, Node> nodes = new HashMap<>();


    @Override
    public void addTaskinHistory(Task task) {
        removeNode(task.getId());
        nodes.put(task.getId(), linkLast(task));
    }

    @Override
    public List<Task> getHistory() {
        List<Task> viewsHistory = new ArrayList<>();
        Node current = first;
        while (current != null) {
            viewsHistory.add(current.taskValeu);
            current = current.next;
        }
        return viewsHistory;
    }

    @Override
    public void remove(Task task) {
        removeNode(task.getId());

    }

    private void removeNode(int taskId) {
        Node nodeForRemove = nodes.get(taskId);
        if (nodeForRemove == null) {
            return;
        }

        // Обновляем ссылки соседних узлов
        if (nodeForRemove.prev != null) {
            nodeForRemove.prev.next = nodeForRemove.next;
        } else {
            first = nodeForRemove.next;
            if (first != null) {
                first.prev = null;  // Обнуляем prev у нового первого элемента
            }
        }

        if (nodeForRemove.next != null) {
            nodeForRemove.next.prev = nodeForRemove.prev;
        } else {
            last = nodeForRemove.prev;
            if (last != null) {
                last.next = null;  // Обнуляем next у нового последнего элемента
            }
        }
        nodeForRemove.prev = null;
        nodeForRemove.next = null;
        nodes.remove(taskId);
    }

    private Node linkLast(Task task) {
        Node newNode = new Node(task, last, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        return last = newNode;
    }

    private static class Node {
        private final Task taskValeu;
        private Node next;
        private Node prev;

        public Node(Task taskValeu, Node prev, Node next) {
            this.taskValeu = taskValeu;
            this.next = next;
            this.prev = prev;
        }
    }
}