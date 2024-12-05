package com.example.project;

public class DLinkedList<T extends Comparable<T>> {
    DNode<T>  dDummyHead;

    public DLinkedList() {
        dDummyHead = new DNode<>(null);
    }

    public DNode<T> getdDummyHead() {
        return dDummyHead;
    }

    public void setdDummyHead(DNode<T> dDummyHead) {
        this.dDummyHead = dDummyHead;
    }

    //O(d)
    public void insert(T data) {
        DNode<T> newNode = new DNode<>(data);
        DNode<T> current = dDummyHead.next; // Start from the first node after dummyHead
        DNode<T> previous = dDummyHead;

        // Traverse the list to find the appropriate position to insert the new node
        while (current != null && current.data.compareTo(data) < 0) {
            previous = current;
            current = current.next;
        }

        // Case 1: Empty list or insert at the beginning
        if (previous == dDummyHead || current == null) {
            // Insert at the beginning or if list is empty
            newNode.next = current;
            newNode.prev = previous;
            if (current != null) {
                current.prev = newNode;
            }
            previous.next = newNode;
        } else {
            // Case 2: Insert between or at the end
            newNode.next = current;
            newNode.prev = previous;
            previous.next = newNode;
            current.prev = newNode;
        }
    }

    //O(d)
    public void traverse() {
        DNode<T> current = dDummyHead.next; // Start from the first node after dummy head
        System.out.print("List Contents: ");
        while (current != null) {
            System.out.println(current.data + " ");
            current = current.next;
        }
        System.out.println(); // Move to the next line after printing all elements
    }

    // Helper method to get the last node in the list
    //O(d)
    private DNode<T> getLastNode() {
        DNode<T> current = dDummyHead.getNext();
        while (current != null && current.getNext() != null) {
            current = current.getNext();
        }
        return current;
    }

    //O(d)
    public DNode<T> find(T data) {
        DNode<T> curr = dDummyHead.next; // Start from the first node after dummyHead
        while (curr != null && curr.data.compareTo(data) <= 0) {
            if (curr.data.compareTo(data) == 0)
                return curr;
            curr = curr.next;
        }
        return null;
    }

    //O(d)
    public DNode<T> delete(T data) {
        DNode<T> curr = dDummyHead.next; // Start from the first node after dummyHead
        while (curr != null && curr.data.compareTo(data) <= 0) {
            if (curr.data.compareTo(data) == 0)
                break;
            curr = curr.next;
        }

        if (curr != null && curr.data.compareTo(data) == 0) { // found
            if (curr.prev == dDummyHead && curr.next == null) // case 0: delete one item
                dDummyHead.next = null;
            else if (curr.prev == dDummyHead) { // case 1: delete 1st item
                curr.next.prev = null;
                dDummyHead.next = curr.next;
            } else if (curr.next == null) { // case 3: delete last item
                curr.prev.next = null;
            } else { // case 2: delete between
                curr.prev.next = curr.next;
                curr.next.prev = curr.prev;
            }
            return curr;
        }
        return null;
    }

    //O(d)
    public T get (int index){
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative.");
        }
        DNode<T> curr = dDummyHead.next; // Start from the first node after dummyHead
        while (curr != null && index > 0) {
            curr = curr.next;
            index--;
        }
        if (curr!= null) {
            return curr.data;
        } else {
            return null;
        }
    }

    //O(d)
    public int length() {
        int length = 0;
        DNode<T> curr = dDummyHead.next; // Start from the first node after dummyHead
        while (curr != null) {
            length++;
            curr = curr.next;
        }
        return length;
    }

    //O(d)
    public boolean updateDistrictName(String oldName, String newName) {
        DNode<T> current = dDummyHead.next;

        while (current != null) {
            if (current.data instanceof District) {
                District district = (District) current.data;
                if (district.getDistrictName().equalsIgnoreCase(oldName)) {
                    district.setDistrictName(newName);
                    return true;
                }
            }
            current = current.next;
        }
        return false;
    }

    //O(d^2)
    public void sortByName() {
        boolean swapped;
        DNode<T> current;
        DNode<T> last = null;

        if (dDummyHead.next == null) {
            return; // Empty list
        }

        do {
            swapped = false;
            current = dDummyHead.next;

            while (current.next != last) {
                // Compare adjacent nodes by name and swap if necessary
                if (current.data.compareTo(current.next.data) > 0) {
                    T temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
            last = current; // Mark the last node as sorted
        } while (swapped);
    }

    //O(d)
    public void clear () {
        dDummyHead.setNext(null); // Remove all references to nodes
    }

}
