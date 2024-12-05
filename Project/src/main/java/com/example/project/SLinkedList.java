package com.example.project;


public class SLinkedList <T extends Comparable<T>>  {
    private SNode<T> sDummyHead;

    public SLinkedList() {
        sDummyHead = new SNode<>(null);
    }

    //O(n)
    public void insert(T data) {
        SNode<T> newNode = new SNode<>(data);
        SNode<T> current = sDummyHead.getNext();
        SNode<T> previous = sDummyHead;

        for (; current != null && current.compare(data) < 0; previous = current, current = current.getNext());

        if (previous == sDummyHead && current == null) {    // case 0: empty list
            sDummyHead.setNext(newNode);
        } else if (current == null) {    // case 1: insert at the end
            previous.setNext(newNode);
        } else if (previous == sDummyHead) {    // case 2: insert at the beginning
            newNode.setNext(current);
            sDummyHead.setNext(newNode);
        } else {    // case 3: insert in the middle
            newNode.setNext(current);
            previous.setNext(newNode);
        }
    }

    //O(n)
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative.");
        }
        SNode<T> current = sDummyHead.getNext(); // Start from the first node after dummyHead
        for (int i = 0; i < index; i++) {
            if (current == null) {
                throw new IndexOutOfBoundsException("Index out of bounds.");
            }
            current = current.getNext();
        }
        if (current == null) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        return current.getData();
    }

    //O(n)
    public boolean delete(T data) {
        SNode<T> curr = sDummyHead.getNext(); // Start from the first node after dummyHead
        SNode<T> previous = sDummyHead;

        while (curr != null && !curr.getData().equals(data)) {
            previous = curr;
            curr = curr.getNext();
        }

        if (curr == null) {
            // Data not found in the list
            System.out.println(data + " is not in the list");
            return false;
        }

        // Link the previous node directly to the node after the current node (skip the current node)
        previous.setNext(curr.getNext());
        return true; // Deletion successful
    }

    //O(n)
    public boolean find(T data) {
        SNode<T> current = sDummyHead.getNext(); // Start from the first node after dummyHead
        int index = 0;
        while (current != null) {
            if (current.getData().equals(data)) {
                return true;
            }
            current = current.getNext();
            index++;
        }
        return false; // Not found
    }

    //O(n)
    public void traverse() {
        SNode<T> current = sDummyHead.getNext(); // Start from the first node after dummyHead
        while (current != null) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
        System.out.println("null");
    }

    //O(n)
    public int length() {
        int length = 0;
        SNode<T> curr = sDummyHead.getNext(); // Start from the first node after dummyHead
        while (curr != null) {
            length++;
            curr = curr.getNext();
        }
        return length;
    }

    //O(l^2)
    public void sortByNameL() {
        if (sDummyHead.getNext() == null || sDummyHead.getNext().getNext() == null) {
            return; // No or only one element in the list, no sorting needed
        }

        SNode<T> current = sDummyHead.getNext(); // Start from the first node after dummy head

        // Outer loop to iterate over the list
        while (current != null && current.getNext() != null) {//L
            SNode<T> nextNode = current.getNext();
            T currentData = current.getData();
            T nextData = nextNode.getData();

            // Compare current and next node data by name
            if (currentData.compareTo(nextData) > 0) {
                // Swap current and next node data
                current.setData(nextData);
                nextNode.setData(currentData);

                // Reset current to the beginning to re-sort the list
                current = sDummyHead.getNext();//L
            } else {
                // Move to the next node
                current = current.getNext();
            }
        }
    }

    //O(1)
    public boolean isEmpty() {
        // Check if the next node after sDummyHead is null
        return sDummyHead.getNext() == null;
    }
    public boolean contains(T data) {
        SNode<T> current = sDummyHead.getNext();

        // Traverse the list to check if the data already exists
        while (current != null) {
            if (current.getData().equals(data)) {
                return true; // Data found
            }
            current = current.getNext();
        }

        return false; // Data not found
    }

    //O(n)
    public void clear() {
        sDummyHead.setNext(null); // Remove all references to nodes
    }

    public SNode<T> getsDummyHead() {
        return sDummyHead;
    }

    public void setsDummyHead(SNode<T> sDummyHead) {
        this.sDummyHead = sDummyHead;
    }
}