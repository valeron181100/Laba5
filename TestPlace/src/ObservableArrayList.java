import java.io.Serializable;
import java.util.*;

public class ObservableArrayList<E> implements List<E>, Serializable {
    private ArrayList<E> list;

    private ArrayList<ObservableArrayListListener> listeners;

    public ObservableArrayList(){
        list = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public ObservableArrayList(int capacity){
        list = new ArrayList<>(capacity);
        listeners = new ArrayList<>();
    }

    public ObservableArrayList(List<E> list){
        this.list = new ArrayList<>(list);
        listeners = new ArrayList<>();
    }

    public void addObservableArrayListListener(ObservableArrayListListener listener){
        listeners.add(listener);
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
        listeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        listeners.forEach( p -> p.action(ObservableArrayListAction.REMOVE));
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        listeners.forEach( p -> p.action(ObservableArrayListAction.ADDALL));
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        listeners.forEach( p -> p.action(ObservableArrayListAction.ADDALL));
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        listeners.forEach( p -> p.action(ObservableArrayListAction.REMOVEALL));
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        listeners.forEach( p -> p.action(ObservableArrayListAction.RETAINALL));
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        listeners.forEach( p -> p.action(ObservableArrayListAction.CLEAR));
        list.clear();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        listeners.forEach( p -> p.action(ObservableArrayListAction.SET));
        return list.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        listeners.forEach( p -> p.action(ObservableArrayListAction.ADD));
        list.add(index, element);
    }

    @Override
    public E remove(int index) {
        listeners.forEach( p -> p.action(ObservableArrayListAction.REMOVE));
        return list.remove(index);
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
