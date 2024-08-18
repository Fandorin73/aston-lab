package ru.aston.arrayList.arrays;

import java.util.Collection;
import java.util.Comparator;

public interface ListCustom<E> {
    int size();

    boolean add(Object o);

    void add(int index, E element);

    boolean addAll(Collection<? extends E> c);

    void clear();

    E get(int index);

    boolean isEmpty();

    E remove(int index);

    boolean remove(Object o);

    void sort(Comparator<? super E> c);

}
