package com.example.project;

public class DNode<T extends Comparable<T>> {
    T data;
    DNode<T> next, prev;

    public DNode(T data){
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DNode<T> getNext() {
        return next;
    }

    public void setNext(DNode<T> next) {
        this.next = next;
    }

    public DNode<T> getPrev() {
        return prev;
    }

    public void setPrev(DNode<T> prev) {
        this.prev = prev;
    }

    public int compare(T data){
        return this.data.compareTo(data);
    }

    @Override
    public String toString() {
        return data+" ";
    }
}