package by.it.group151002.kuvshinov.lesson10;

import java.util.*;

public class TaskB<E>  implements NavigableSet<E> {

    //Создайте аналог дерева TreeSet БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
    //Не нужно на массивах это делать или маскируя в поля TreeSet, TreeMap и т.д.
    //Можно реализовать класс Node с двумя полями такого же типа (потомки дерева),
    //в нем также может быть поле элемента E. Далее на этой основе ожидается бинарное дерево.

    //Обязательные к реализации методы и конструкторы
    private class Node{
        E value;
        Node left;
        Node right;
        Node parent;

        Node(E value){this.value = value;}
    }
    private class SetIterator<T> implements Iterator<T> {
        private Node next, node;
        private Node findLeft(Node node) {
            if (node == null)
                return null;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        SetIterator(){
            node = null;
            next = null;
        }
        @Override
        public boolean hasNext() {
            if (node == null) {
                next = findLeft(root);
            } else if (node.right != null) {
                next = findLeft(node.right);
            } else {
                next = node;
                while (next.parent != null && next == next.parent.right) {
                    next = next.parent;
                }
                next = next.parent;
                if (next == null)
                    return false;
            }
            return true;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            if (next == null)
                return null;
            node = next;
            return (T) next.value;
        }
    }

    private Node root;
    private int size = 0;

    public TaskB() {
    }

    private int compareTo(E obj1, E obj2) {
        return Integer.compare((Integer)obj1, (Integer)obj2);
    }

    @Override
    public boolean add(E e) {
        Node currNode = root;
        Node parent = currNode;
        int result = 0;
        while (currNode != null){
            parent = currNode;
            result = compareTo(currNode.value, e);
            if (result > 0){
                currNode = currNode.left;
            }
            else if (result < 0){
                currNode = currNode.right;
            }
            else{
                return false;
            }
        }
        Node newNode = new Node(e);
        if (parent == null)
            root = newNode;
        else if (result > 0) {
            parent.left = newNode;
            parent.left.parent = parent;
        }
        else {
            parent.right = newNode;
            parent.right.parent = parent;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node curr = root;
        int res = 0;
        Node parent = null;
        while (curr != null && ((res = compareTo(curr.value, (E)o)) != 0)) {
            parent = curr;
            if (res > 0)
                curr = curr.left;
            else
                curr = curr.right;
        }
        if (curr == null)
            return false;

        if (curr.left != null && curr.right != null){
            Node minimum = curr.right;
            while (minimum.left != null) {
                minimum = minimum.left;
            }
            remove(minimum.value);
            size++;
            curr.value = minimum.value;
        } else if (curr.left == null && curr.right == null) {
            if (curr != root) {
                if (parent.right == curr)
                    parent.right = null;
                else
                    parent.left = null;
            } else
                root = null;
        } else {
            Node child  = (curr.left != null)? curr.left: curr.right;
            if (curr != root){
                if (curr == parent.left)
                    parent.left = child;
                else
                    parent.right = child;
            }
            else
                root = child;
        }
        size--;
        return true;
    }
    private String concatResString(Node node, String res){
        if (node != null){
            res = res  + concatResString(node.left, res) + ", " + node.value.toString()  + concatResString(node.right, res);
        }
        return res;
    }

    @Override
    public String toString() {
        String result = "";
        if (root == null)
            return "[" + result + "]";
        return "[" + concatResString(root, result).substring(2) + "]";
    }
    @Override
    public boolean contains(Object o) {
        Node curr = root;
        int res = 0;
        while (curr != null && ((res = compareTo(curr.value, (E)o)) != 0))
            if (res > 0)
                curr = curr.left;
            else
                curr = curr.right;
        return curr != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new SetIterator<>();
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E first() {
        if (root == null)
            return null;
        else{
            Node curr = root;
            while (curr.left != null)
                curr = curr.left;
            return curr.value;
        }
    }

    @Override
    public E last() {
        if (root == null)
            return null;
        else{
            Node curr = root;
            while (curr.right != null)
                curr = curr.right;
            return curr.value;
        }
    }
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////         Эти методы реализовывать необязательно      ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public E lower(E e) {
        return null;
    }

    @Override
    public E floor(E e) {
        return null;
    }

    @Override
    public E ceiling(E e) {
        return null;
    }

    @Override
    public E higher(E e) {
        return null;
    }

    @Override
    public E pollFirst() {
        return null;
    }

    @Override
    public E pollLast() {
        return null;
    }


    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public NavigableSet<E> descendingSet() {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return null;
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return null;
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return null;
    }

    @Override
    public Comparator<? super E> comparator() {
        return null;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return null;
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return null;
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return null;
    }


}