package FileSystemStuff;

import java.io.Serializable;
import java.util.*;

/** Класс-коллекция с событие которое срабатывает когда коллекция была отредактирована*/
public class ObservableArrayList<E> implements List<E>, Serializable {
    private ArrayList<E> list;

    private ArrayList<ObservableArrayListListener> listeners;

    private ArrayList<ObservableArrayListListener> beforeChangingListeners;

    public ObservableArrayList(){
        list = new ArrayList<>();
        listeners = new ArrayList<>();
        beforeChangingListeners = new ArrayList<>();
    }

    public ObservableArrayList(int capacity){
        list = new ArrayList<>(capacity);
        listeners = new ArrayList<>();
        beforeChangingListeners = new ArrayList<>();
    }

    public ObservableArrayList(List<E> list){
        this.list = new ArrayList<>(list);
        listeners = new ArrayList<>();
        beforeChangingListeners = new ArrayList<>();
    }

    public void addObservableArrayListListener(ObservableArrayListListener listener){
        listeners.add(listener);
    }

    public void addBeforeChangingListener(ObservableArrayListListener listener) {
        beforeChangingListeners.add(listener);
    }


    public boolean addAllInvisible(Collection<? extends E> c) {
        return list.addAll(c);
    }

    public void clearInvisible() {
        list.clear();
    }

    //// Default Map's methods

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(E e) {
        beforeChangingListeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        boolean b = list.add(e);
        listeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        return b;
    }

    @Override
    public boolean remove(Object o) {
        beforeChangingListeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        boolean b = list.remove(o);
        listeners.forEach( p -> p.action(ObservableArrayListAction.REMOVE));
        return b;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        beforeChangingListeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        boolean b = list.addAll(c);
        listeners.forEach( p -> p.action(ObservableArrayListAction.ADDALL));
        return b;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        beforeChangingListeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        boolean b = list.addAll(index, c);
        listeners.forEach( p -> p.action(ObservableArrayListAction.ADDALL));
        return b;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        beforeChangingListeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        boolean b = list.removeAll(c);
        listeners.forEach( p -> p.action(ObservableArrayListAction.REMOVEALL));
        return b;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        beforeChangingListeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        boolean b = list.retainAll(c);
        listeners.forEach( p -> p.action(ObservableArrayListAction.RETAINALL));
        return b;
    }

    @Override
    public void clear() {
        beforeChangingListeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        list.clear();
        listeners.forEach( p -> p.action(ObservableArrayListAction.CLEAR));
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        beforeChangingListeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        E e = list.set(index, element);
        listeners.forEach( p -> p.action(ObservableArrayListAction.SET));
        return e;
    }

    @Override
    public void add(int index, E element) {
        beforeChangingListeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        list.add(index, element);
        listeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
    }

    @Override
    public E remove(int index) {
        beforeChangingListeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        E e = list.remove(index);
        listeners.forEach( p -> p.action(ObservableArrayListAction.REMOVE));
        return e;
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }


}
