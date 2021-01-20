package ca.study.purpose;

import java.util.*;

public class DIYArrayList<T> implements List<T> {

    public int size;
    public Object[] array = new Object[10];

    public static void main(String[] args) {
        DIYArrayList<Integer> list1 = new DIYArrayList<>();
        DIYArrayList<Integer> list2 = new DIYArrayList<>();

        for (int i = 0; i < 25; i++) {     //adding elements
            list1.add(i);
        }
        for (int i = 0; i < 35; i++) {
            list2.add(i+100);
        }

        System.out.print("list1: ");
        for (int i = 0; i < list1.size; i++) {       //1st line seen in console
            System.out.print(list1.get(i) + " ");
        }
        System.out.println();

        System.out.print("list2: ");
        for (int i = 0; i < list2.size; i++) {       //2nd line seen in console is
            System.out.print(list2.get(i) + " ");
        }
        System.out.println();

        Collections.addAll(list1, 2, 5, 56, 2);

        System.out.print("list1 + elements: ");
        for (int i = 0; i < list1.size; i++) {      //3rd line seen in console
            System.out.print(list1.get(i) + " ");
        }
        System.out.println();

        Collections.copy(list2, list1);

        System.out.print("updated list2: ");
        for (int i = 0; i < list2.size; i++) {      //4th line seen in console
            System.out.print(list2.get(i) + " ");
        }
        System.out.println();

        Collections.sort(list2);

        System.out.print("sorted list2: ");
        for (int i = 0; i < list2.size; i++) {      //5th line seen in console
            System.out.print(list2.get(i) + " ");
        }


    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    public void grow() {
        Object[] tempArray = new Object[array.length*2];
        for (int i = 0; i < array.length; i++) {
            tempArray[i] = array[i];
        }
        array = tempArray;
    }

    @Override
    public boolean add(T t) {
        size++;
        if (size == array.length) {
            grow();
            array[size - 1] = t;
        }
        else array[size - 1] = t;
        return true;
    }


    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        return (T)array[index];
    }

    public T array(int index) {
        return (T) array[index];
    }

    public T set(int index, T element) {
        T oldValue = array(index);
        array[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    public class ListItr implements ListIterator<T>{
        int cursor;
        int lastRet = -1;


        @Override
        public boolean hasNext() {
            return false;
        }

        public T next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = array;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public T previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        public void set(T t) {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                DIYArrayList.this.set(lastRet, t);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(T t) {

        }


    }
}
