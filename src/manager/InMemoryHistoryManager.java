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
        return new ArrayList<>(viewsHistory);
    }

    @Override
    public void remove(Task task) {
        removeNode(task.getId());
    }

    public void removeNode(int taskId) {
        Node nodeForRemove = nodes.get(taskId);

        if (nodeForRemove == null) {
            return;
        } else {
            if (nodeForRemove == first) {
                first = first.next;
            } else if (nodeForRemove == last) {
                last = last.prev;
            } else {
                nodeForRemove.prev.next = nodeForRemove.next;
                nodeForRemove.next.prev = nodeForRemove.prev;
            }
        }
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
        private Task taskValeu;
        private Node next;
        private Node prev;

        public Node(Task taskValeu, Node prev, Node next) {
            this.taskValeu = taskValeu;
            this.next = next;
            this.prev = prev;
        }
    }
}