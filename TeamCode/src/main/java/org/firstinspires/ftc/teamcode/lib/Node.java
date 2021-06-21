package org.firstinspires.ftc.teamcode.lib;

public class Node<T> {
    public T value;
    public Node<T> nextNode;
    public Node<T> previousNode;

    public Node(T value){
        this.value = value;
    }
}
