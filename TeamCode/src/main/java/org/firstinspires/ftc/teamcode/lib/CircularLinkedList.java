package org.firstinspires.ftc.teamcode.lib;

public class CircularLinkedList<T> {
    public Node<T> head = null;
    public Node<T> tail = null;

    public void addNode(T value) {
        Node<T> newNode = new Node<T>(value);

        if (head == null) {
            head = newNode;
            head.previousNode = head;
        } else {
            tail.nextNode = newNode;
            newNode.previousNode = tail;
        }

        tail = newNode;
        tail.nextNode = head;
        head.previousNode = tail;
    }

    public Node<T> stepForwards() {
        head = head.nextNode;
        return head;
    }

    public Node<T> stepBackwards() {
        head = head.previousNode;
        return head;
    }

    public Node<T> GetHead(){
        return head;
    }


}
