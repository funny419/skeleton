package com.funny.utils;

import java.util.*;
import java.util.concurrent.*;


public class CollectionUtil {
    public static <E> ArrayList<E> createArrayList() {
        return new ArrayList<E>();
    }


    public static <E> ArrayList<E> createArrayList(int initialCapacity) {
        return new ArrayList<E>(initialCapacity);
    }


    public static <E> ArrayList<E> createArrayList(Collection<? extends E> collection) {
        if (collection == null) {
            return new ArrayList<E>();
        }

        return new ArrayList<E>(collection);
    }


    public static <E> ArrayList<E> createArrayList(Iterable<? extends E> iter) {

        if (iter instanceof Collection<?>) {
            return new ArrayList<E>((Collection<? extends E>) iter);
        }

        ArrayList<E> list = new ArrayList<E>();

        iterableToCollection(iter,list);

        list.trimToSize();

        return list;
    }


    @SafeVarargs
    public static <T,V extends T> ArrayList<T> createArrayList(V...args) {
        if (args == null || args.length == 0) {
            return new ArrayList<T>();
        }

        ArrayList<T> list = new ArrayList<T>(args.length);
        Collections.addAll(list,args);

        return list;

    }


    public static <E> LinkedList<E> createLinkedList() {
        return new LinkedList<E>();
    }


    public static <E> LinkedList<E> createLinkedList(Collection<? extends E> collection) {
        if (collection == null) {
            return new LinkedList<E>();
        }

        return new LinkedList<E>(collection);
    }


    public static <T> LinkedList<T> createLinkedList(Iterable<? extends T> c) {
        LinkedList<T> list = new LinkedList<T>();

        iterableToCollection(c,list);

        return list;
    }


    @SafeVarargs
    public static <T,V extends T> LinkedList<T> createLinkedList(V...args) {
        LinkedList<T> list = new LinkedList<T>();

        if (args != null) {
            Collections.addAll(list,args);
        }

        return list;
    }


    public static <E> HashSet<E> createHashSet() {
        return new HashSet<E>();
    }


    public static <E> HashSet<E> createHashSet(int initialCapacity) {
        return new HashSet<E>(initialCapacity);
    }


    public static <E> HashSet<E> createHashSet(Collection<? extends E> collection) {
        if (collection == null) {
            return new HashSet<E>();
        }
        return new HashSet<E>(collection);
    }


    @SafeVarargs
    public static <E,O extends E> HashSet<E> createHashSet(O...args) {
        if (args == null || args.length == 0) {
            return new HashSet<E>();
        }

        HashSet<E> set = new HashSet<E>(args.length);
        Collections.addAll(set,args);

        return set;
    }


    public static <T> HashSet<T> createHashSet(Iterable<? extends T> iter) {
        HashSet<T> set;

        if (iter instanceof Collection<?>) {
            set = new HashSet<T>((Collection<? extends T>) iter);
        } else {
            set = new HashSet<T>();
            iterableToCollection(iter,set);
        }

        return set;
    }


    public static <E> LinkedHashSet<E> createLinkedHashSet() {
        return new LinkedHashSet<E>();
    }


    @SafeVarargs
    public static <T,V extends T> LinkedHashSet<T> createLinkedHashSet(V...args) {
        if (args == null || args.length == 0) {
            return new LinkedHashSet<T>();
        }

        LinkedHashSet<T> set = new LinkedHashSet<T>(args.length);
        Collections.addAll(set,args);
        return set;
    }


    public static <T> LinkedHashSet<T> createLinkedHashSet(Iterable<? extends T> iter) {
        LinkedHashSet<T> set;

        if (iter instanceof Collection<?>) {
            set = new LinkedHashSet<T>((Collection<? extends T>) iter);
        } else {
            set = new LinkedHashSet<T>();
            iterableToCollection(iter,set);
        }

        return set;
    }


    @SafeVarargs
    public static <T,V extends T> TreeSet<T> createTreeSet(V...args) {
        return (TreeSet<T>) createTreeSet(null,args);
    }


    public static <T> TreeSet<T> createTreeSet(Iterable<? extends T> c) {
        return createTreeSet(null,c);
    }


    public static <T> TreeSet<T> createTreeSet(Comparator<? super T> comparator) {
        return new TreeSet<T>(comparator);
    }


    @SafeVarargs
    public static <T,V extends T> TreeSet<T> createTreeSet(Comparator<? super T> comparator,V...args) {
        TreeSet<T> set = new TreeSet<T>(comparator);

        if (args != null) {
            Collections.addAll(set,args);
        }

        return set;
    }


    public static <T> TreeSet<T> createTreeSet(Comparator<? super T> comparator,Iterable<? extends T> c) {
        TreeSet<T> set = new TreeSet<T>(comparator);

        iterableToCollection(c,set);

        return set;
    }


    public static <E> TreeSet<E> createTreeSet(SortedSet<E> set) {
        if (set == null) {
            return new TreeSet<E>();
        }

        return new TreeSet<E>(set);
    }


    public static <K,V> HashMap<K,V> createHashMap() {
        return new HashMap<K,V>();
    }


    public static <K,V> HashMap<K,V> createHashMap(int initialCapacity) {
        return new HashMap<K,V>(initialCapacity);
    }


    public static <K,V> HashMap<K,V> createHashMap(int initialCapacity,float loadFactor) {
        return new HashMap<K,V>(initialCapacity,loadFactor);
    }


    public static <K,V> HashMap<K,V> synchronizedMap() {
        return (HashMap<K,V>) Collections.synchronizedMap(new HashMap<K,V>());
    }


    public static <K,V> HashMap<K,V> createHashMap(Map<? extends K,? extends V> map) {
        return new HashMap<K,V>(map);
    }


    public static <K,V> LinkedHashMap<K,V> createLinkedHashMap() {
        return new LinkedHashMap<K,V>();
    }


    public static <K,V> LinkedHashMap<K,V> createLinkedHashMap(int initialCapacity) {
        return new LinkedHashMap<K,V>(initialCapacity);
    }


    public static <K,V> LinkedHashMap<K,V> createLinkedHashMap(int initialCapacity,float loadFactor) {
        return new LinkedHashMap<K,V>(initialCapacity,loadFactor);
    }


    public static <K,V> LinkedHashMap<K,V> createLinkedHashMap(Map<? extends K,? extends V> map) {
        if (map == null) {
            return new LinkedHashMap<K,V>();
        }

        return new LinkedHashMap<K,V>(map);
    }


    public static <K,V> ConcurrentMap<K,V> createConcurrentMap() {
        return new ConcurrentHashMap<K,V>();
    }


    public static <K,V> ConcurrentMap<K,V> createConcurrentMap(Map<? extends K,? extends V> map) {
        if (map == null) {
            return null;
        }

        return new ConcurrentHashMap<K,V>(map);
    }


    public static <K,V> ConcurrentMap<K,V> createConcurrentMap(int initialCapacity) {
        return new ConcurrentHashMap<K,V>(initialCapacity);
    }


    public static <K,V> ConcurrentMap<K,V> createConcurrentMap(int initialCapacity,float loadFactor) {
        return new ConcurrentHashMap<K,V>(initialCapacity,loadFactor);
    }


    private static <E> void iterableToCollection(Iterable<? extends E> iter,Collection<E> list) {
        if (iter == null) {
            return;
        }

        for (E element : iter) {
            list.add(element);
        }
    }


    public static <E extends Enum<E>> EnumSet<E> createEnumSet(Collection<E> c) {
        if (c == null) {
            return null;
        }

        return EnumSet.copyOf(c);
    }


    public static <E extends Enum<E>> EnumSet<E> createEnumSet(Class<E> elementType) {
        if (elementType == null) {
            return null;
        }

        return EnumSet.allOf(elementType);
    }


    public static <K,V> TreeMap<K,V> createTreeMap() {
        return new TreeMap<K,V>();
    }


    public static <K,V> TreeMap<K,V> createTreeMap(Comparator<? super K> comparator) {
        if (comparator == null) {
            return null;
        }

        return new TreeMap<K,V>(comparator);
    }


    public static <K,V> TreeMap<K,V> createTreeMap(Map<? extends K,? extends V> map) {
        if (map == null) {
            return null;
        }

        return new TreeMap<K,V>(map);
    }


    public static <K,V> TreeMap<K,V> createTreeMap(SortedMap<K,? extends V> map) {
        if (map == null) {
            return null;
        }

        return new TreeMap<K,V>(map);
    }


    public static <K,V> WeakHashMap<K,V> createWeakHashMap() {
        return new WeakHashMap<K,V>();
    }


    public static <K,V> WeakHashMap<K,V> createWeakHashMap(int initialCapacity) {
        return new WeakHashMap<K,V>(initialCapacity);
    }


    public static <K,V> WeakHashMap<K,V> createWeakHashMap(Map<? extends K,? extends V> map) {
        if (map == null) {
            return new WeakHashMap<K,V>();
        }

        return new WeakHashMap<K,V>(map);
    }


    public static <K,V> WeakHashMap<K,V> createWeakHashMap(int initialCapacity,float loadFactor) {
        return new WeakHashMap<K,V>(initialCapacity,loadFactor);
    }


    public static <K,V> IdentityHashMap<K,V> createIdentityHashMap() {
        return new IdentityHashMap<K,V>();
    }


    public static <K,V> IdentityHashMap<K,V> createIdentityHashMap(int initialCapacity) {
        return new IdentityHashMap<K,V>(initialCapacity);
    }


    public static <K,V> IdentityHashMap<K,V> createIdentityHashMap(Map<? extends K,? extends V> map) {
        if (map == null) {
            return null;
        }

        return new IdentityHashMap<K,V>(map);
    }


    public static <K extends Enum<K>,V> EnumMap<K,V> createEnumMap(Class<K> keyType) {
        if (keyType == null) {
            return null;
        }

        return new EnumMap<K,V>(keyType);
    }


    public static <K extends Enum<K>,V> EnumMap<K,V> createEnumMap(Map<K,? extends V> map) {
        if (map == null) {
            return null;
        }

        return new EnumMap<K,V>(map);
    }


    public static <E> PriorityQueue<E> createPriorityQueue() {
        return new PriorityQueue<E>();
    }


    public static <E> PriorityQueue<E> createPriorityQueue(int initialCapacity) {
        return new PriorityQueue<E>(initialCapacity);
    }


    public static <E> PriorityQueue<E> createPriorityQueue(Collection<? extends E> collection) {
        if (collection == null) {
            return null;
        }

        return new PriorityQueue<E>(collection);
    }


    public static <E> PriorityQueue<E> createPriorityQueue(int initialCapacity,Comparator<? super E> comparator) {
        if (comparator == null) {
            return new PriorityQueue<E>(initialCapacity);
        }

        return new PriorityQueue<E>(initialCapacity,comparator);
    }


    public static <E> PriorityQueue<E> createPriorityQueue(PriorityQueue<? extends E> queue) {
        if (queue == null) {
            return null;
        }

        return new PriorityQueue<E>(queue);
    }


    public static <E> PriorityQueue<E> createPriorityQueue(SortedSet<? extends E> set) {
        if (set == null) {
            return null;
        }

        return new PriorityQueue<E>(set);
    }


    public static <E> ArrayDeque<E> createArrayDeque() {
        return new ArrayDeque<E>();
    }


    public static <E> ArrayDeque<E> createArrayDeque(Collection<? extends E> collection) {
        if (collection == null) {
            return null;
        }

        return new ArrayDeque<E>(collection);
    }


    public static <E> ArrayDeque<E> createArrayDeque(int initialCapacity) {
        return new ArrayDeque<E>(initialCapacity);
    }


    public static <E> BitSet createBitSet() {
        return new BitSet();
    }


    public static <E> BitSet createBitSet(int initialCapacity) {
        return new BitSet();
    }


    public static <K,V> ConcurrentSkipListMap<K,V> createConcurrentSkipListMap() {
        return new ConcurrentSkipListMap<K,V>();
    }


    public static <K,V> ConcurrentSkipListMap<K,V> createConcurrentSkipListMap(Comparator<? super K> comparator) {
        if (comparator == null) {
            return new ConcurrentSkipListMap<K,V>();
        }

        return new ConcurrentSkipListMap<K,V>(comparator);
    }


    public static <K,V> ConcurrentSkipListMap<K,V> createConcurrentSkipListMap(Map<? extends K,? extends V> map) {
        if (map == null) {
            return new ConcurrentSkipListMap<K,V>();
        }

        return new ConcurrentSkipListMap<K,V>(map);
    }


    public static <K,V> ConcurrentSkipListMap<K,V> createConcurrentSkipListMap(SortedMap<? extends K,? extends V> map) {
        if (map == null) {
            return new ConcurrentSkipListMap<K,V>();
        }

        return new ConcurrentSkipListMap<K,V>(map);
    }


    public static <E> Queue<E> createConcurrentLinkedQueue() {
        return new ConcurrentLinkedQueue<E>();
    }


    public static <E> Queue<E> createConcurrentLinkedQueue(Collection<? extends E> collection) {
        if (collection == null) {
            return new ConcurrentLinkedQueue<E>();
        }

        return new ConcurrentLinkedQueue<E>(collection);
    }


    public static <E> CopyOnWriteArrayList<E> createCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<E>();
    }


    public static <E> CopyOnWriteArrayList<E> createCopyOnWriteArrayList(Collection<? extends E> collection) {
        if (collection == null) {
            return new CopyOnWriteArrayList<E>();
        }

        return new CopyOnWriteArrayList<E>();
    }


    public static <E> CopyOnWriteArrayList<E> createCopyOnWriteArrayList(E[] toCopyIn) {
        if (toCopyIn == null) {
            return new CopyOnWriteArrayList<E>();
        }

        return new CopyOnWriteArrayList<E>(toCopyIn);
    }


    public static <E> CopyOnWriteArraySet<E> createCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet<E>();
    }


    public static <E> CopyOnWriteArraySet<E> createCopyOnWriteArraySet(Collection<? extends E> collection) {
        return new CopyOnWriteArraySet<E>();
    }


    public static <E> BlockingQueue<E> createLinkedBlockingQueue() {
        return new LinkedBlockingQueue<E>();
    }


    public static <E> BlockingQueue<E> createLinkedBlockingQueue(int capacity) {
        return new LinkedBlockingQueue<E>(capacity);
    }


    public static <E> BlockingQueue<E> createLinkedBlockingQueue(Collection<? extends E> collection) {
        if (collection == null) {
            return new LinkedBlockingQueue<E>();
        }

        return new LinkedBlockingQueue<E>(collection);
    }


    private static final class ArrayIterator<T> implements Iterator<T> {

        private final T[] items;
        private int ix = 0;

        public ArrayIterator(T[] items) {
            this.items = items;
        }

        @Override
        public boolean hasNext() {
            return ix < items.length;
        }

        @Override
        public T next() {
            return items[ix++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot delete from an array");
        }
    }


    public static <T> Iterator<T> toIterator(final Enumeration<T> enumeration) {
        return new EnumIterator<>(enumeration);
    }


    private static final class EnumIterator<T> implements Iterator<T>,Iterable<T> {
        private final Enumeration<T> enumeration;

        public EnumIterator(Enumeration<T> enumeration) {
            this.enumeration = enumeration;
        }

        @Override
        public boolean hasNext() {
            return enumeration.hasMoreElements();
        }

        @Override
        public T next() {
            return enumeration.nextElement();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override
        public Iterator<T> iterator() {
            return this;
        }
    }


    private static final class ReverseArrayIterator<T> implements Iterator<T> {
        private final T[] items;
        private int ix;

        public ReverseArrayIterator(T[] items) {
            this.items = items;
            ix=items.length-1;
        }

        @Override
        public boolean hasNext() {
            return ix >= 0;
        }

        @Override
        public T next() {
            return items[ix--];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot delete from an array");
        }
    }
}
